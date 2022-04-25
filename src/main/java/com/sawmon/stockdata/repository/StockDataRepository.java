package com.sawmon.stockdata.repository;

import com.sawmon.stockdata.model.StockData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepository extends MongoRepository<StockData,List<String>>
{

}
