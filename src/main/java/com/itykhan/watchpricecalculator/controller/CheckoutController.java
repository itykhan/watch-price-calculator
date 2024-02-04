package com.itykhan.watchpricecalculator.controller;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.service.CheckoutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckoutController implements CheckoutApi {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Override
    @PostMapping("/checkout")
    public ResultPrice calculatePrice(@RequestBody List<String> ids) {
        return checkoutService.calculateTotalPrice(ids);
    }

}
