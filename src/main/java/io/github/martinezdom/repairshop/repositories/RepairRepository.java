package io.github.martinezdom.repairshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.martinezdom.repairshop.entities.Repair;
import java.util.List;
import io.github.martinezdom.repairshop.enums.RepairStatus;




@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    List<Repair> findByStatus(RepairStatus status);
    List<Repair> findByMechanicId(Long id);
}
