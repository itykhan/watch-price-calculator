package com.itykhan.watchpricecalculator.controller;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WatchController {

    @Autowired
    private WatchService watchService;

    @PostMapping("/checkout")
    public ResultPrice calculatePrice(@RequestBody List<String> ids) {
        return watchService.calculateTotalPrice(ids);
    }

}
