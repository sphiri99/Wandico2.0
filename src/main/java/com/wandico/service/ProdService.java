package com.wandico.service;

import com.wandico.entity.OrderSurmmary;
import com.wandico.entity.ProductionDetails;
import com.wandico.repo.OrderRepo;
import com.wandico.repo.ProductionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProdService {
    private ProductionRepo productionRepo;
    private OrderRepo orderRepo;
    private TurnaAroundTimeService turnaAroundTimeService;

    public ProdService(ProductionRepo productionRepo, OrderRepo orderRepo,TurnaAroundTimeService turnaAroundTimeService) {
        this.productionRepo = productionRepo;
        this.orderRepo = orderRepo;
        this.turnaAroundTimeService = turnaAroundTimeService;
    }

    public ProductionDetails getProductionDetails() {
        return productionRepo.findAll().get(0);

    }

    public void saveProductionDetails(ProductionDetails productionDetails) {
        productionRepo.deleteAll();
        productionRepo.save(productionDetails);
        List<OrderSurmmary> orderDetailsListSorted = getCompletedOrderDetailsListSorted(orderRepo.findAll());
        //recalculate the est date when number of empoyeess increase or decrease
        for (OrderSurmmary orderSurmmary : orderDetailsListSorted) {
            orderSurmmary.setEstTurnaroundTime(turnaAroundTimeService.getTurnAroundTime(java.util.Optional.of(orderSurmmary)));
        }

    }

    public List<OrderSurmmary> getCompletedOrderDetailsListSorted(List<OrderSurmmary> orderSurmmaryList) {
        List<OrderSurmmary> all = new ArrayList<>();
        if (orderSurmmaryList != null && orderSurmmaryList.size() > 0) {
            for (OrderSurmmary orderSurmmary : orderSurmmaryList) {
                if (!orderSurmmary.getStatus().equalsIgnoreCase("COMPLETE")) {
                    all.add(orderSurmmary);
                }
            }
            all.sort((OrderSurmmary s1, OrderSurmmary s2) -> (int) (s2.getId() - s1.getId()));

        }

        return all;
    }
}
