package com.itykhan.watchpricecalculator.service;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.data.WatchNotFoundException;
import com.itykhan.watchpricecalculator.data.entity.Discount;
import com.itykhan.watchpricecalculator.data.entity.Watch;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.junit.jupiter.api.DisplayName;
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
public class CheckoutServiceTest {

    @InjectMocks
    private CheckoutService checkoutService;

    @Mock
    private WatchRepository watchRepository;

    @Test
    @DisplayName("Testing price calculation when an empty list is passed as a parameter")
    public void emptyList() {

        ResultPrice resultPrice = checkoutService.calculateTotalPrice(List.of());
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(0), resultPrice.price());
    }

    @Test
    @DisplayName("Testing price calculation when null is passed as a parameter")
    public void nullList() {
        ResultPrice resultPrice = checkoutService.calculateTotalPrice(null);
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(0), resultPrice.price());
    }

    @Test
    @DisplayName("Testing price calculation when there is an unknown watch ID in the list parameter")
    public void watchNotFound() {

        Exception exception = assertThrows(WatchNotFoundException.class,
                () -> checkoutService.calculateTotalPrice(List.of("001")));
        assertTrue(String.format("Could not find watch %s", "001").contains(exception.getMessage()));
    }

    @Test
    @DisplayName("Testing price calculation for a unit without discount")
    public void unitWithoutDiscount() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(new Watch("001", "Test", new BigDecimal(100), null)));

        ResultPrice resultPrice = checkoutService.calculateTotalPrice(List.of("001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(300), resultPrice.price());
    }

    @Test
    @DisplayName("Testing price calculation for a unit with discount and a count of unit equals discount quantity")
    public void unitWithDiscountOfDiscountQuantity() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(
                        new Watch("001", "Test", new BigDecimal(100),
                                new Discount(3, new BigDecimal(200)))));

        ResultPrice resultPrice = checkoutService.calculateTotalPrice(List.of("001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(200), resultPrice.price());
    }

    @Test
    @DisplayName("Testing price calculation for a unit with discount and a unit count equals double discount quantity")
    public void unitWithDiscountOfDoubleDiscountQuantity() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(
                        new Watch("001", "Test", new BigDecimal(80),
                                new Discount(2, new BigDecimal(120)))));

        ResultPrice resultPrice = checkoutService.calculateTotalPrice(List.of("001", "001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(120 * 2), resultPrice.price());
    }

    @Test
    @DisplayName("Testing price calculation for a unit when some part is counted with discount price and some is not")
    public void unitWithMixedPrice() {
        when(watchRepository.findById("001"))
                .thenReturn(Optional.of(
                        new Watch("001", "Test", new BigDecimal(80),
                                new Discount(2, new BigDecimal(120)))));

        ResultPrice resultPrice = checkoutService.calculateTotalPrice(List.of("001", "001", "001", "001", "001"));
        assertNotNull(resultPrice);
        assertEquals(new BigDecimal(120 * 2 + 80), resultPrice.price());
    }

    @Test
    @DisplayName("Testing price calculation for several units with/without discount")
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
        ResultPrice resultPrice = checkoutService.calculateTotalPrice(
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
