package com.wandico.repo;

import com.wandico.entity.OrderSurmmary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderSurmmary, Long> {
    OrderSurmmary findByNameAndItemName(String name, String itemName);
    Optional<OrderSurmmary> findById(Long id);

}
