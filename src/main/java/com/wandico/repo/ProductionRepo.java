package com.wandico.repo;

import com.wandico.entity.ProductionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepo extends JpaRepository<ProductionDetails, Long> {

}
