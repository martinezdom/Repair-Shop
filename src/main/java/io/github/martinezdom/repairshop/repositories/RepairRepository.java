package io.github.martinezdom.repairshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.github.martinezdom.repairshop.entities.Repair;

import java.math.BigDecimal;
import io.github.martinezdom.repairshop.enums.RepairStatus;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    Page<Repair> findByStatus(RepairStatus status, Pageable pageable);

    Page<Repair> findByMechanicId(Long id, Pageable pageable);

    Long countByStatus(RepairStatus status);

    @Query("SELECT SUM(r.cost) FROM Repair r WHERE r.status = :status")
    BigDecimal sumCostByStatus(@Param("status") RepairStatus status);
}
