package com.itykhan.watchpricecalculator.service;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class WatchService {

    @Autowired
    private WatchRepository watchRepository;

    public ResultPrice calculateTotalPrice(Collection<String> ids) {

        //TODO: implement
        return new ResultPrice(new BigDecimal(360));
    }
}
