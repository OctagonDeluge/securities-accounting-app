package ru.tink.practice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tink.practice.entity.OperationInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationInfoRepository extends PagingAndSortingRepository<OperationInfo, Long>, JpaRepository<OperationInfo, Long> {

    Page<OperationInfo> findAllByClientId(Long clientId, Pageable pageable);

    List<OperationInfo> findAllByClientId(Long cliendId);

    @Query(value = "delete from operation_info where " +
            "operation_date <= 1? and operation_date >= 2? and client_id = 3?", nativeQuery = true)
    void deleteByDate(LocalDateTime start, LocalDateTime end, Long clientId);
}
