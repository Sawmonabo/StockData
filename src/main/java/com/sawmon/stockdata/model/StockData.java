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
	private String stockPrice;
	private String percentChange;
	private String mean200DayPercent;
	private String dividend;
	private String timeStamp;
	
	public StockData(List<String> stockList)
	{
		this.id = stockList.get(0);
		this.companyName =stockList.get(1);
		this.stockPrice = stockList.get(2);
		this.percentChange = stockList.get(3);
		this.mean200DayPercent = stockList.get(4);
		this.dividend = stockList.get(5);
		this.timeStamp = stockList.get(6);
	}


	@Override
	public String toString()
	{
		return "---------------" + companyName + "---------------" + '\n' +
			   " Stock Ticker         = " + id + '\n' +
               " Stock Price         = $ " + stockPrice + '\n' +
               " Percent Change      = $ " + percentChange +" %" + '\n' +
               " Mean 200 Day        =  " + mean200DayPercent +" %" + '\n' +
               " Dividend            = $ " + dividend + '\n' +
			   " Last Updated:       = $ " + timeStamp + '\n';
	}

}
