package com.wandico.service;

import com.wandico.entity.OrderSurmmary;
import com.wandico.entity.Product;
import com.wandico.entity.ProductionDetails;
import com.wandico.repo.OrderRepo;
import com.wandico.repo.ProductRepo;
import com.wandico.repo.ProductionRepo;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDetailsService {
    private OrderRepo orderRepo;
    private TurnaAroundTimeService turnaAroundTimeService;


    public OrderDetailsService(OrderRepo orderRepo, TurnaAroundTimeService turnaAroundTimeService) {
        this.orderRepo = orderRepo;
        this.turnaAroundTimeService = turnaAroundTimeService;

    }

    public String saveOrderDetails(Optional<OrderSurmmary> orderSurmmary) {
        OrderSurmmary orderSurmmaryInstance = orderRepo.findByNameAndItemName(orderSurmmary.get().getName(), orderSurmmary.get().getItemName());
        if (null == orderSurmmaryInstance) {
            orderSurmmary.get().setCreatedOn(new Date());

            try {
                orderSurmmary.get().setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().plusDays(21).toString()));

            } catch (ParseException e) {
                throw new RuntimeException("Failed to convert local date to date");
            }
            orderSurmmary.get().setEstTurnaroundTime(turnaAroundTimeService.getTurnAroundTime(orderSurmmary));
            orderSurmmary.get().setStatus("PENDING");

            if(null != orderSurmmary.get().getEstTurnaroundTime()) {
                //saving the order to the database, meaning we have the product selected
                orderRepo.save(orderSurmmary.get());
                return "Successfully created an order for".concat(" ").concat(orderSurmmary.get().getName());

            }

        }
        return "Failed to save the order, we do not have the product selected";
    }



    public List<OrderSurmmary> getOrderDetailsList() {
        return orderRepo.findAll();

    }

    public List<OrderSurmmary> getOrderDetailsListSorted() {
        List<OrderSurmmary> orderSurmmaryList = orderRepo.findAll();
        List<OrderSurmmary> all = new ArrayList<>();
        if (orderSurmmaryList != null && orderSurmmaryList.size() > 0) {
            for(OrderSurmmary orderSurmmary : orderSurmmaryList) {
               if (!orderSurmmary.getStatus().equalsIgnoreCase("COMPLETE")){
                   all.add(orderSurmmary);
               }
            }
            all.sort((OrderSurmmary s1, OrderSurmmary s2) -> (int) (s2.getId() - s1.getId()));

        }

        return all;
    }


    public List<OrderSurmmary> getCompletedOrderDetailsListSorted() {
        List<OrderSurmmary> orderSurmmaryList = orderRepo.findAll();
        List<OrderSurmmary> all = new ArrayList<>();
        if (orderSurmmaryList != null && orderSurmmaryList.size() > 0) {
            for(OrderSurmmary orderSurmmary : orderSurmmaryList) {
                if (orderSurmmary.getStatus().equalsIgnoreCase("COMPLETE")){
                    all.add(orderSurmmary);
                }
            }
            all.sort((OrderSurmmary s1, OrderSurmmary s2) -> (int) (s2.getId() - s1.getId()));

        }

        return all;
    }


    public Optional<OrderSurmmary> getOrderSurmmary(Long id) {
        return orderRepo.findById(id);
    }

    public void deleteOrderSurmmary(OrderSurmmary orderSurmmary) {
        orderRepo.delete(orderSurmmary);
    }

    public void updateOrderSurmmary(OrderSurmmary orderSurmmary) {
        orderRepo.save(orderSurmmary);
    }


}
