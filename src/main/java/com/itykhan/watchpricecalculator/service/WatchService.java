package com.itykhan.watchpricecalculator.service;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.data.WatchNotFoundException;
import com.itykhan.watchpricecalculator.data.entity.Discount;
import com.itykhan.watchpricecalculator.data.entity.Watch;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WatchService {

    @Autowired
    private WatchRepository watchRepository;

    public ResultPrice calculateTotalPrice(Collection<String> ids) {

        if (ids == null || ids.isEmpty()) {
            return new ResultPrice(BigDecimal.ZERO);
        }

        Map<String, Long> idToCountMap = ids.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        AtomicReference<BigDecimal> result = new AtomicReference<>(BigDecimal.ZERO);
        idToCountMap.forEach((unitId, unitCount) ->
                calculatePriceForUnit(result, unitId, unitCount));

        return new ResultPrice(result.get());
    }

    private void calculatePriceForUnit(
            AtomicReference<BigDecimal> result, String itemId, Long itemCount) {

        Watch watch = watchRepository.findById(itemId)
                .orElseThrow(() -> new WatchNotFoundException(itemId));

        BigDecimal totalPrice = result.get();
        if (watch.getDiscount() != null) {

            Discount discount = watch.getDiscount();
            BigDecimal discountQuantity = new BigDecimal(discount.getDiscountQuantity());
            BigDecimal[] quotientAndRemainder = new BigDecimal(itemCount)
                    .divideAndRemainder(discountQuantity);
            BigDecimal discountPriceTimes = quotientAndRemainder[0];
            BigDecimal itemCountWithoutDiscount = quotientAndRemainder[1];

            //
            BigDecimal priceForItemsWithDiscount = discount.getPriceForQuantity().multiply(discountPriceTimes);
            BigDecimal priceForRemainingItems = watch.getPrice().multiply(itemCountWithoutDiscount);

            //
            totalPrice = totalPrice.add(priceForItemsWithDiscount)
                                   .add(priceForRemainingItems);

        } else {

            totalPrice = totalPrice.add(watch.getPrice().multiply(new BigDecimal(itemCount)));
        }

        result.set(totalPrice);
    }
}
