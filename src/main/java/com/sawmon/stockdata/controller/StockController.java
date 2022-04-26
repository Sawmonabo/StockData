package com.sawmon.stockdata.controller;

import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.service.StockRepoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stocks")
@AllArgsConstructor
public class StockController
{
    private final StockRepoService stockRepoService;
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
