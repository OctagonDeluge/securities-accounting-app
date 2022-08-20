package ru.tink.practice.service.external.exchange.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tink.practice.dto.external.moex.*;
import ru.tink.practice.enumeration.Currency;
import ru.tink.practice.enumeration.SecurityType;
import ru.tink.practice.enumeration.external.moex.Engine;
import ru.tink.practice.enumeration.external.moex.Market;
import ru.tink.practice.enumeration.external.moex.SecurityDTOType;
import ru.tink.practice.exception.CandlesForSecurityNotFound;
import ru.tink.practice.exception.SecurityNotFoundInExternalServiceException;
import ru.tink.practice.service.external.exchange.ExternalExchangeService;

import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoexExternalExchangeService implements ExternalExchangeService {

    private final RestTemplate restTemplate;

    @Value("${issmoex.api.url}")
    private String url;

    @Value("${issmoex.api.serviceName}")
    private String serviceName;

    private final String SECURITY_TYPE_KEY = "securityType";
    private final String FROM_DATE_KEY = "fromDate";
    private final String TILL_DATE_KEY = "tillDate";
    private final String DAY_INTERVAL_KEY = "dayInterval";

    @Override
    public List<SecurityShortInfoDTO> getSecuritiesByName(String securityName) {
        List<SecurityShortInfoDTO> securities =
                getSecuritiesGroupedByType(securityName, SecurityDTOType.SHARE.getType())[1].getSecurities();
        securities.addAll(getSecuritiesGroupedByType(securityName, SecurityDTOType.BOND.getType())[1].getSecurities());
        securities.forEach(security -> security.setExchangeName(serviceName));
        return securities;
    }

    @Override
    public SecurityFullInfoDTO getSecurityBySecid(String secid) {
        Map<String, String> descriptions = getSecurityDescription(secid);
        SecurityFullInfoDTO security = new SecurityFullInfoDTO();
        security.setSecid(descriptions.get("secid"));
        security.setName(descriptions.get("name"));
        security.setGroup(descriptions.get("group"));
        security.setExchangeName(serviceName);
        security.setCurrentPrice(getCurrentSecurityPrice(secid, security.getGroup()));
        security.setCurrency(Currency.RUB);
        return security;
    }

    @Override
    public List<PaymentDTO> getPaymentsBySecid(String secid) {
        String securityGroup = getSecurityDescription(secid).get("group");
        List<PaymentDTO> payments = new ArrayList<>();
        if(securityGroup.contains(SecurityType.SHARE.getName())) {
            getDividends(secid).forEach(dividend -> payments.add(dividend.toPayment()));
        } else {
            getCoupons(secid).forEach(coupon -> payments.add(coupon.toPayment()));
        }
        return payments;
    }

    @Override
    public BigDecimal getCurrentSecurityPrice(String secid, String securityType) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("engines").pathSegment(Engine.STOCK.getName())
                .pathSegment("markets").pathSegment(Market.of(securityType))
                .pathSegment("securities").pathSegment(secid)
                .pathSegment("candles.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.reverse", "true")
                .queryParam("from", LocalDate.now().minusDays(3))
                .queryParam("iss.json", "extended")
                .queryParam("candles.columns", "close,end").build().toUri();
        return getPrices(secid, destUrl, securityType).get(0).getClose();
    }

    @Override
    public List<CurrentPriceDTO> getPriceStatisticsByNumberOfDays(String secid, Map<String, String> params) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("engines").pathSegment(Engine.STOCK.getName())
                .pathSegment("markets").pathSegment(Market.of(params.get(SECURITY_TYPE_KEY)))
                .pathSegment("securities").pathSegment(secid)
                .pathSegment("candles.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.reverse", "false")
                .queryParam("iss.json", "extended")
                .queryParam("candles.columns", "close,end")
                .queryParam("from", formatter.format(new Date(Long.parseLong(params.get(FROM_DATE_KEY)))))
                .queryParam("till", formatter.format(new Date(Long.parseLong(params.get(TILL_DATE_KEY)))))
                .queryParam("interval", Integer.parseInt(params.get(DAY_INTERVAL_KEY))).build().toUri();
        List<CurrentPriceDTO> prices =
                restTemplate.getForObject(destUrl, CurrentPricesDTO[].class)[1].getCurrentPrices();
        if(Market.of(params.get(SECURITY_TYPE_KEY)).equals(SecurityType.BOND.getName())) {
            prices.forEach(CurrentPriceDTO::fixCloseForBond);
        }
        prices.forEach(price -> price.setCurrency(Currency.RUB));
        return prices;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    private SecuritiesShortInfoDTO[] getSecuritiesGroupedByType(String securityName, String securityType) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("securities.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .queryParam("securities.columns", "id,secid,shortname,name,group")
                .queryParam("group_by_filter", securityType)
                .queryParam("group_by", "group")
                .queryParam("q", securityName).build().toUri();
        return restTemplate.getForObject(destUrl, SecuritiesShortInfoDTO[].class);
    }

    private Map<String, String> getSecurityDescription(String secid) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("securities")
                .pathSegment(secid).pathSegment(".json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .queryParam("iss.only", "description")
                .queryParam("description.columns", "name,value")
                .build().toUri();

        List<Map<String, Object>> descriptions =
                restTemplate.getForObject(destUrl, SecurityDescriptionDTO[].class)[1].getDescriptions();
        if(descriptions.isEmpty()) {
            throw new SecurityNotFoundInExternalServiceException(secid);
        }
        Map<String, String> json = new HashMap<>();
        descriptions.forEach(map -> json
                .put(map.get("name").toString().toLowerCase(), map.get("value").toString()));
        return json;
    }

    private List<CurrentPriceDTO> getPrices(String secid, URI destUrl, String securityType) {
        CurrentPricesDTO currentPrices =
                restTemplate.getForObject(destUrl, CurrentPricesDTO[].class)[1];
        if(currentPrices.getCurrentPrices().isEmpty()) {
            throw new CandlesForSecurityNotFound(secid);
        }
        if(Market.of(securityType).equals(SecurityType.BOND.getName())) {
            currentPrices.getCurrentPrices().forEach(CurrentPriceDTO::fixCloseForBond);
        }
        currentPrices.getCurrentPrices().forEach(price -> price.setCurrency(Currency.RUB));
        return currentPrices.getCurrentPrices();
    }

    private List<CouponDTO> getCoupons(String secid) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("securities")
                .pathSegment(secid)
                .pathSegment("bondization.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .queryParam("iss.only", "coupons")
                .queryParam("coupons.columns", "coupondate,value_rub,faceunit")
                .build().toUri();

        return restTemplate.getForObject(destUrl, CouponsDTO[].class)[1].getCoupons();
    }

    private List<DividendDTO> getDividends(String secid) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("securities")
                .pathSegment(secid)
                .pathSegment("dividends.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .build().toUri();

        return restTemplate.getForObject(destUrl, DividendsDTO[].class)[1].getDividends();
    }
}
