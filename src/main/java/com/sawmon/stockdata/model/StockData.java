package com.sawmon.stockdata.model;


import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Document(collection = "stockData")
@NoArgsConstructor
@AllArgsConstructor
public class StockData 
{
	@Id
	private String id;
	private String companyName;
	private String stockExchange;
	private String stockPrice;
	private String percentChange;
	private String mean200DayPercent;
	private String dividend;
	private String ROE;
	private String EPS;
	private String bookValuePerShare;
	private String yearHigh;
	private String yearLow;
	private String timeStamp;


	public StockData(List<String> stockList)
	{
		this.id = stockList.get(0);
		this.companyName =stockList.get(1);
		this.stockExchange = stockList.get(2);
		this.stockPrice = stockList.get(3);
		this.percentChange = stockList.get(4);
		this.mean200DayPercent = stockList.get(5);
		this.dividend = stockList.get(6);
		this.ROE = stockList.get(7);
		this.EPS = stockList.get(8);
		this.bookValuePerShare = stockList.get(9);
		this.yearHigh = stockList.get(10);
		this.yearLow = stockList.get(11);
		this.timeStamp = stockList.get(12);
	}


	@Override
	public String toString()
	{
		return "---------------" + companyName + "---------------" + '\n' +
			   " Stock Ticker        = " + id + '\n' +
			   " Stock Exchange      = $ " + stockExchange + '\n' +
               " Stock Price         = $ " + stockPrice + '\n' +
               " Percent Change      = $ " + percentChange +" %" + '\n' +
               " Mean 200 Day        =  " + mean200DayPercent +" %" + '\n' +
               " Dividend            = $ " + dividend + '\n' +
			   " ROE                 = $ " + ROE + '\n' +
			   " EPS                 = $ " + EPS + '\n' +
			   " B.V Per Share       = $ " + bookValuePerShare + '\n' +
			   " Year High           = $ " + yearHigh + '\n' +
			   " Year Low            = $ " + yearLow + '\n' +
			   " Last Updated:       = $ " + timeStamp + '\n';
	}

}
