package com.itykhan.watchpricecalculator.service;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.data.WatchNotFoundException;
import com.itykhan.watchpricecalculator.data.entity.Discount;
import com.itykhan.watchpricecalculator.data.entity.Watch;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WatchService {

    private final WatchRepository watchRepository;

    public WatchService(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    public ResultPrice calculateTotalPrice(Collection<String> ids) {

        if (ids == null || ids.isEmpty()) {
            return new ResultPrice(BigDecimal.ZERO);
        }

        Map<String, Long> idToCountMap = ids.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Optional<BigDecimal> result = idToCountMap.entrySet().stream()
                .map(entity -> calculatePriceForUnit(entity.getKey(), entity.getValue()))
                .reduce(BigDecimal::add);

        return new ResultPrice(result.orElse(BigDecimal.ZERO));
    }

    private BigDecimal calculatePriceForUnit(String itemId, Long itemCount) {

        Watch watch = watchRepository.findById(itemId)
                .orElseThrow(() -> new WatchNotFoundException(itemId));

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
            return priceForItemsWithDiscount.add(priceForRemainingItems);

        }

        return watch.getPrice().multiply(new BigDecimal(itemCount));

    }
}
