/*
package com.sawmon.stockdata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sawmon.stockdata.StockdataApplication;
import com.sawmon.stockdata.model.StockData;

@Controller
public class StockController
{
//	@Autowired
//	StockdataApplication stockdataApplication;

	
	@GetMapping("/")
	public String home(Model model)
	{
		StockdataApplication SDA = new StockdataApplication();
		List<StockData> stocks = SDA.getAllStockStats();
		
		stocks.forEach(stock -> {
			model.addAttribute("StockStats", stock);
			
		});
		return "home";
	}

	

}
*/
