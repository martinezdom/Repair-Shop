package io.github.martinezdom.repairshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.github.martinezdom.repairshop.entities.Repair;

import java.math.BigDecimal;
import java.util.List;
import io.github.martinezdom.repairshop.enums.RepairStatus;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    List<Repair> findByStatus(RepairStatus status);

    List<Repair> findByMechanicId(Long id);

    Long countByStatus(RepairStatus status);

    @Query("SELECT SUM(r.cost) FROM Repair r WHERE r.status = :status")
    BigDecimal sumCostByStatus(@Param("status") RepairStatus status);
}
