package com.itykhan.watchpricecalculator;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.service.CheckoutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WatchPriceCalculatorApplicationTests {

	@Autowired
	private CheckoutService checkoutService;

	@Test
	@DisplayName("Integration testing of price calculation logic")
	void calculateTotalPrice() {
		ResultPrice resultPrice = checkoutService.calculateTotalPrice(
				List.of("001", "002", "001", "004", "003"));
		assertEquals(new BigDecimal(360),
				resultPrice.price().setScale(0, RoundingMode.UNNECESSARY));
	}

}
