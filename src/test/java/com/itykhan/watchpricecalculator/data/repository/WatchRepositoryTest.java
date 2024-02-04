package com.itykhan.watchpricecalculator.data.repository;

import com.itykhan.watchpricecalculator.data.entity.Discount;
import com.itykhan.watchpricecalculator.data.entity.Watch;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class WatchRepositoryTest {

    @Autowired
    private WatchRepository watchRepository;

    @Test
    @DisplayName("Testing storing and finding a watch by ID")
    public void saveAndFindWatch() {
        String watchName = "Test Watch";
        String watchId = "005";
        Discount discount = new Discount(3, new BigDecimal(200));
        BigDecimal price = new BigDecimal(100);

        //
        watchRepository.save(new Watch(watchId, watchName, price, discount));
        Optional<Watch> found = watchRepository.findById("005");

        //
        assertTrue(found.isPresent());
        Watch watch = found.get();
        assertEquals(watchId, watch.getId());
        assertEquals(watchName, watch.getName());
        assertEquals(price, watch.getPrice());
        assertEquals(discount, watch.getDiscount());
    }

}
