package com.sawmon.stockdata.model;


import java.util.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
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
		String[] data = stockList.toArray(new String[stockList.size()]);

		this.id = data[0];
		this.companyName = data[1];
		this.stockPrice = data[2];
		this.percentChange = data[3];
		this.mean200DayPercent = data[4];
		this.dividend = data[5];
		this.timeStamp = data[6];
		
	}
	
	@Override
	public String toString()
	{
		return "---------------" + companyName + "---------------" + '\n' +
               " Stock Price         = $ " + stockPrice + '\n' +
               " Percent Change      = $ " + percentChange +" %" + '\n' +
               " Mean 200 Day        =  " + mean200DayPercent +" %" + '\n' +
               " Dividend            = $ " + dividend + '\n' +
			   " Last Updated:       = $ " + timeStamp + '\n';
	}
	
	
//    @Override
//    public String toString() {
//        return companyName + "{" +
//                "Stock Price ='" + stockPrice + '\'' +
//                ", Percent Change='" + percentChange + '\'' +
//                ", Mean 200 Day ='" + mean200DayPercent + '\'' +
//                ", Dividend=" + dividend +
//                '}';
//    }
	
}
