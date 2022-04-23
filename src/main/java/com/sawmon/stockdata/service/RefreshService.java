package com.sawmon.stockdata.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.stereotype.Service;
import com.sawmon.stockdata.model.StockWrapper;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class RefreshService 
{
	private final Map<StockWrapper, Boolean> stocksToRefresh;
	
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private static final Duration refreshPeriod = Duration.ofSeconds(30);
	
	public RefreshService()
	{
		stocksToRefresh = new HashMap<>();
		setRefreshEvery15Minutes();
	}
	
	public boolean shouldRefresh(final StockWrapper stock)
	{
		if (!stocksToRefresh.containsKey(stock))
		{
			stocksToRefresh.put(stock, false);
			return true;
		}

		return stocksToRefresh.get(stock);
	}
	
	public static void setThread(int time) throws InterruptedException
	{
		Thread.sleep(time);
	}
	
	private void setRefreshEvery15Minutes()
	{
		scheduler.scheduleAtFixedRate(() ->
				stocksToRefresh.forEach((stock, value) -> {
		
					if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshPeriod)))
					{
						System.out.println("Refreshing the stock setting of: " + stock.getStock().getSymbol());
						stocksToRefresh.remove(stock);
						stocksToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
					}
				}), 0, 15, SECONDS);
	}

}
