package com.wandico.controller;

import com.wandico.entity.Product;
import com.wandico.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/product")
    public String product(Model model) {

        model.addAttribute("userForm", new Product());

        return "product";
    }

    @PostMapping("/product")
    public String placeproduct(@ModelAttribute("userForm") Optional<Product> userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "product";
        }

        productService.saveProduct(userForm);


        return "redirect:/welcome";
    }


    @GetMapping("/prodinfo")
    public String productinfo(Model model) {

        model.addAttribute("userForm", new Product());

        return "prodinfo";
    }

    @PostMapping("/prodinfo")
    public String productinfo(@ModelAttribute("userForm") Optional<Product> userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "prodinfo";
        }

        productService.saveProduct(userForm);


        return "redirect:/welcome";
    }

}
