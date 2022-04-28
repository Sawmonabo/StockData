package com.sawmon.stockdata.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.sawmon.stockdata.model.StockWrapper;
import lombok.AllArgsConstructor;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

@AllArgsConstructor
@Service
public class StockService 
{
	private final RefreshService refreshService;
	
	
	// A wrapper class is a class that encapsulates types, so that those types can be used to create object instances and methods in another class that need those types.
	// Method that creates a wrapper object that contains the yahooFinance stock object and a time stamp for Refresh Service.
	public StockWrapper findStock(final String ticker)
	{
		// YahooFinance API get() method can throw an IOException, so we use try/catch.
		try 
		{
			return new StockWrapper(YahooFinance.get(ticker));
		}
		catch (IOException e)
		{
			System.out.println("Error returned by YahooFinance API (findStock() method - StockService.class).");
		}
		return null;
	}
	
	public List<StockWrapper> findStocks(final List<String> tickers)
	{
		return tickers.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	public String findCompany(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getName();
	}
	
	public String findSymbol(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getSymbol();
	}

	public String findStockExchange(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getStockExchange();
	}
	
	public BigDecimal findPrice(final StockWrapper stock) throws IOException
	{
		// getQuote() method from YahooAPI checks whether we need to refresh our stock data or not.	
		return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPrice();
	}
	
	public BigDecimal findLastChangePercent(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeInPercent();
	}
	
	public BigDecimal findChangeFrom200MeanPercent(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeFromAvg200InPercent();
	}
	
	public BigDecimal findDividend(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getDividend(refreshService.shouldRefresh(stock)).getAnnualYield();
	}

	public BigDecimal findBookValuePerShare(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getStats(refreshService.shouldRefresh(stock)).getBookValuePerShare();
	}

	public BigDecimal findEPS(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getStats(refreshService.shouldRefresh(stock)).getEps();

	}

	public BigDecimal findROE(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getStats(refreshService.shouldRefresh(stock)).getROE();

	}

	public BigDecimal findYearHigh(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getYearHigh();
	}

	public BigDecimal findYearLow(final StockWrapper stock) throws IOException
	{
		return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getYearLow();
	}


}
