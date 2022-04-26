package com.sawmon.stockdata.controller;

import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.service.StockRepoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("api/stocks")
public class StockController
{
    private final StockRepoService stockRepoService;

    @Autowired
    public StockController(StockRepoService stockRepoService)
    {
        this.stockRepoService = stockRepoService;
    }

    @GetMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("StockStats", stockRepoService.getAllStocks());
        return "home";
    }

    @GetMapping
    public List<StockData> fetchAllStockData()
    {
        return stockRepoService.getAllStocks();
    }

    @PostMapping("/addStock")
    public StockData addStockData(@RequestBody StockData stockData)
    {
        return stockRepoService.insertStockData(stockData);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAllStockData(@PathVariable List<String> id)
    {
        stockRepoService.deleteStocks(id);
        return "Deleted Successfully";
    }
}
