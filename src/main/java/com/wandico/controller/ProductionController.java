package com.wandico.controller;

import com.wandico.entity.ProductionDetails;
import com.wandico.repo.OrderRepo;
import com.wandico.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductionController {
    @Autowired
    private ProdService prodService;


    @GetMapping("/prod")
    public String prodConfig(Model model) {

        model.addAttribute("userForm", new ProductionDetails());

        return "prodinfo";
    }

    @PostMapping("/prod")
    public String placeOrder(@ModelAttribute("userForm") ProductionDetails userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "prodinfo";
        }

        prodService.saveProductionDetails(userForm);
        return "redirect:/orders";
    }

}
