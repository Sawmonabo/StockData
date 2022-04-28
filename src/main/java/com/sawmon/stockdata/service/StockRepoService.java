package com.sawmon.stockdata.service;

import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.repository.StockDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@AllArgsConstructor
@Service
public class StockRepoService
{
    private final StockDataRepository stockDataRepository;


    public List<StockData> getAllStocks()
    {
        return stockDataRepository.findAll();
    }

    public StockData insertStockData(StockData stockData)
    {
        return stockDataRepository.save(stockData);
    }

    public void saveAllStocks(List<StockData> stocks)
    {
         stockDataRepository.saveAll(stocks);
    }

    public void deleteStocks(List stocks)
    {
        stockDataRepository.deleteAllById((stocks));
    }
}
