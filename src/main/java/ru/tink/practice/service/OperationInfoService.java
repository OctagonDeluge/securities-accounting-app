package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.response.OperationDTO;
import ru.tink.practice.dto.response.OperationInfoDTO;
import ru.tink.practice.entity.OperationInfo;
import ru.tink.practice.repository.OperationInfoRepository;
import ru.tink.practice.security.SecurityUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationInfoService {

    private final OperationInfoRepository operationInfoRepository;

    public OperationInfo save(OperationInfo operationInfo) {
        return operationInfoRepository.save(operationInfo);
    }

    public List<OperationInfo> getAllByWalletId(Long walletId, Pageable pageable) {
        return operationInfoRepository
                .findAllByClientId(walletId, pageable).getContent();
    }

    public List<OperationDTO> getAllByClientId(Long clientId) {
        List<OperationDTO> history = operationInfoRepository
                .findAllByClientId(clientId).stream()
                .map(OperationDTO::new).collect(Collectors.toList()) ;
        return history;
    }

    public void deleteHistoryByDate(SecurityUser user, LocalDateTime fromDate, LocalDateTime toDate) {
        operationInfoRepository.deleteByDate(fromDate, toDate, user.getId());
    }

}
