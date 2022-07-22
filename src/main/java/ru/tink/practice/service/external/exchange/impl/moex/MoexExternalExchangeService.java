package ru.tink.practice.service.external.exchange.impl.moex;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tink.practice.dto.external.moex.*;
import ru.tink.practice.enums.external.moex.Engine;
import ru.tink.practice.enums.external.moex.Market;
import ru.tink.practice.enums.external.moex.SecurityDTOType;
import ru.tink.practice.service.external.exchange.ExternalExchangeService;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MoexExternalExchangeService implements ExternalExchangeService {

    private final RestTemplate restTemplate;

    @Value("${issmoex.api.url}")
    private String url;

    @Value("${issmoex.api.serviceName}")
    private String serviceName;

    @Override
    public List<SecurityDTO> getSecuritiesByName(String securityName) {
        List<SecurityDTO> securities =
                getSecuritiesGroupedByType(securityName, SecurityDTOType.SHARE.getType())[1].getSecurities();
        securities.addAll(getSecuritiesGroupedByType(securityName, SecurityDTOType.BOND.getType())[1].getSecurities());
        securities.forEach(security -> security.setExchangeName(serviceName));
        return securities;
    }

    @Override
    public SecurityDTO getSecurityBySecid(String secid) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("securities")
                .pathSegment(secid).pathSegment(".json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .queryParam("iss.only", "description")
                .queryParam("description.columns", "name,value")
                .build().toUri();

        List<Map<String, Object>> descriptions = restTemplate
                .getForObject(destUrl, SecurityDescriptionsDTO[].class)[1].getDescriptions();
        Map<String, String> json = new HashMap<>();
        descriptions.forEach(map -> json
                .put(map.get("name").toString().toLowerCase(), map.get("value").toString()));

        SecurityDTO security = new SecurityDTO();
        security.setSecid(json.get("secid"));
        security.setShortname(json.get("shortname"));
        security.setName(json.get("name"));
        security.setGroup(json.get("group"));
        security.setExchangeName(serviceName);
        security.setCurrentPrice(getPrices(Engine.STOCK.getName(), Market.of(security.getGroup()), secid).get(0).getClose());
        return security;
    }

    @Override
    public List<PaymentDTO> getPaymentsBySecid(String secid) {
        return null;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    private SecuritiesDTO[] getSecuritiesGroupedByType(String securityName, String type) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("securities.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .queryParam("securities.columns", "secid,shortname,name,group")
                .queryParam("group_by_filter", type)
                .queryParam("group_by", "group")
                .queryParam("q", securityName).build().toUri();
        return restTemplate.getForObject(destUrl, SecuritiesDTO[].class);
    }

    private List<CurrentPriceDTO> getPrices(String engine, String market, String secid) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("engines").pathSegment(engine)
                .pathSegment("markets").pathSegment(market)
                .pathSegment("securities").pathSegment(secid)
                .pathSegment("candles.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.reverse", "true")
                .queryParam("from", LocalDate.now().minusDays(1))
                .queryParam("iss.json", "extended")
                .queryParam("candles.columns", "close,end").build().toUri();
        CurrentPricesDTO currentPrices = restTemplate.getForObject(destUrl, CurrentPricesDTO[].class)[1];
        if(market.equals("bonds")) {
            currentPrices.getCurrentPrices().forEach(CurrentPriceDTO::fixCloseForBond);
        }
        return currentPrices.getCurrentPrices();
    }
}
