package com.itykhan.watchpricecalculator;

import com.itykhan.watchpricecalculator.data.ResultPrice;
import com.itykhan.watchpricecalculator.service.WatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WatchPriceCalculatorApplicationTests {

	@Autowired
	private WatchService watchService;

	@Test
	void calculateTotalPrice() {
		ResultPrice resultPrice = watchService.calculateTotalPrice(
				List.of("001", "002", "001", "004", "003"));
		assertEquals(new BigDecimal(360), resultPrice.price());
	}

}
