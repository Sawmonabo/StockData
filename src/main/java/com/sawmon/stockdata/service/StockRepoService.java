package com.sawmon.stockdata.service;

import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.repository.StockDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
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


    public List<StockData> sortBy(String field)
    {
        return stockDataRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

}
