package com.itykhan.watchpricecalculator.service;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.data.WatchNotFoundException;
import com.itykhan.watchpricecalculator.data.entity.Discount;
import com.itykhan.watchpricecalculator.data.entity.Watch;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WatchServiceTest {

    @InjectMocks
    private WatchService watchService;

    @Mock
    private WatchRepository watchRepository;

    @Test
    public void emptyList() {

        ResultPrice resultPrice = watchService.calculateTotalPrice(List.of());
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(0), resultPrice.price());
    }

    @Test
    public void nullList() {
        ResultPrice resultPrice = watchService.calculateTotalPrice(null);
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(0), resultPrice.price());
    }

    @Test
    public void watchNotFound() {

        Exception exception = assertThrows(WatchNotFoundException.class,
                () -> watchService.calculateTotalPrice(List.of("001")));
        assertTrue(String.format("Could not find watch %s", "001").contains(exception.getMessage()));
    }

    @Test
    public void unitWithoutDiscount() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(new Watch("001", "Test", new BigDecimal(100), null)));

        ResultPrice resultPrice = watchService.calculateTotalPrice(List.of("001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(300), resultPrice.price());
    }

    @Test
    public void unitWithDiscountOfDiscountQuantity() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(
                        new Watch("001", "Test", new BigDecimal(100),
                                new Discount(3, new BigDecimal(200)))));

        ResultPrice resultPrice = watchService.calculateTotalPrice(List.of("001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(200), resultPrice.price());
    }

    @Test
    public void unitWithDiscountOfDoubleDiscountQuantity() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(
                        new Watch("001", "Test", new BigDecimal(80),
                                new Discount(2, new BigDecimal(120)))));

        ResultPrice resultPrice = watchService.calculateTotalPrice(List.of("001", "001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(120 * 2), resultPrice.price());
    }

    @Test
    public void unitWithMixedPrice() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(
                        new Watch("001", "Test", new BigDecimal(80),
                                new Discount(2, new BigDecimal(120)))));

        ResultPrice resultPrice = watchService.calculateTotalPrice(List.of("001", "001", "001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(120 * 2 + 80), resultPrice.price());
    }

    @Test
    public void multipleUnitsWithMixedPrice() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(
                        new Watch("001", "Test1", new BigDecimal(100),
                                new Discount(3, new BigDecimal(200)))));
        when(watchRepository.findById("002"))
                .thenReturn(Optional.of(
                        new Watch("002", "Test2", new BigDecimal(80),
                                new Discount(2, new BigDecimal(120)))));
        when(watchRepository.findById("003"))
                .thenReturn(Optional.of(
                        new Watch("003", "Test3", new BigDecimal(50), null)));
        when(watchRepository.findById("004"))
                .thenReturn(Optional.of(
                        new Watch("004", "Test2", new BigDecimal(30), null)));

        //
        ResultPrice resultPrice = watchService.calculateTotalPrice(
                List.of("004", "003", "002", "001",
                        "004", "003", "002", "001",
                        "004", "003", "002", "001",
                        "004", "003", "002", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(
                200 + 100 /* 001 */
                + 120 * 2 /* 002 */
                + 50 * 4 /* 003 */
                + 30 * 4 /* 003 */
        ), resultPrice.price());
    }
}
