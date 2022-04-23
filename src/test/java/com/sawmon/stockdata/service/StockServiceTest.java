package com.sawmon.stockdata.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sawmon.stockdata.model.StockWrapper;


@SpringBootTest
class StockServiceTest
{
	@Autowired
	private StockService stockService;
	
	@Test
	void invoke() throws IOException
	{
		final StockWrapper stock = stockService.findStock("UU.L");
		System.out.println(stock.getStock());
		
		final BigDecimal price = stockService.findPrice(stock);
		System.out.println(price);
		
		final BigDecimal change = stockService.findLastChangePercent(stock);
		System.out.println(change);
		
		final BigDecimal mean200DayPercent = stockService.findChangeFrom200MeanPercent(stock);
		System.out.println(mean200DayPercent);
		
	}
	
	@Test
	void multiple() throws IOException, InterruptedException
	{
		final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList("GOOG", "AMZN"));
		findPrices(stocks);
		findData(stocks);

		RefreshService.setThread(31000);

		final StockWrapper newStock = stockService.findStock("AAPL");
		stocks.add(newStock);
		
		System.out.println(stockService.findPrice(newStock));
		
		findPrices(stocks);

	}
	
	private void findPrices(List<StockWrapper> stocks)
	{
		System.out.println("Current list of stock prices: ");
		stocks.forEach(stock -> {
			try
			{
				System.out.println(stock.getStock().getSymbol() + ": " + stockService.findPrice(stock));
			}
			catch (IOException e)
			{
				System.out.println("Error returned by YahooFinance API (findPrices - method).");
			}
		});
	}
	
	private void findData(List<StockWrapper> stocks)
	{
		stocks.forEach(stock -> {
			try
			{
				System.out.println("Last Change Percentage: " + stockService.findLastChangePercent(stock));
			}
			catch (IOException e)
			{
				System.out.println("Error returned by YahooFinance API (findPrices - method).");
			}
		});
	}

}
