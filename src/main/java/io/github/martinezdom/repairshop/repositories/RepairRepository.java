package io.github.martinezdom.repairshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.martinezdom.repairshop.entities.Repair;



@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

}
