package com.sawmon.stockdata;

import java.io.IOException;
import java.util.*;

import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.model.StockWrapper;
import com.sawmon.stockdata.repository.StockDataRepository;
import com.sawmon.stockdata.service.RefreshService;
import com.sawmon.stockdata.service.StockRepoService;
import com.sawmon.stockdata.service.StockService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class StockdataApplication
{
	private static final RefreshService refreshService = new RefreshService();
	private static List<StockData> allStockStats = new ArrayList<>();
	public static List<StockData> getAllStockStats() {
		return allStockStats;
	}

	public static void setAllStockStats(List<StockData> allStockStat) {
		allStockStats = allStockStat;
	}

	private static String[] getTickers()
	{
		Scanner scan = new Scanner(System.in);

		String[] tickers = scan.nextLine().toUpperCase().split(" ");

		scan.close();
		return tickers;
	}

	private static void getStockService(List<String> currentStockTickers)
	{
		List<StockWrapper> stocks;
		String[] tickers = getTickers();

		StockService stockService = new StockService(refreshService);


		if (currentStockTickers == null)
		{
			stocks = stockService.findStocks(Arrays.asList(tickers));
		}
		else
		{
			currentStockTickers.addAll(Arrays.asList(tickers));
			stocks = stockService.findStocks(currentStockTickers);
		}

		// Initialize multiMap for keeping stocks as key and data as values.
		Map<StockWrapper, ArrayList<String>> multiValueMap = new HashMap<StockWrapper, ArrayList<String>>();

		stocks.forEach(stock ->
		{
			multiValueMap.put(stock, new ArrayList<String>());
			try
			{
				multiValueMap.get(stock).add(stockService.findSymbol(stock));
				multiValueMap.get(stock).add(stockService.findCompany(stock));
				multiValueMap.get(stock).add(stockService.findPrice(stock).toString());
				multiValueMap.get(stock).add(stockService.findLastChangePercent(stock).toString());
				multiValueMap.get(stock).add(stockService.findChangeFrom200MeanPercent(stock).toString());
				multiValueMap.get(stock).add(stockService.findDividend(stock).toString());
				multiValueMap.get(stock).add(stock.getLastAccessed().toString());
			}
			catch (IOException e)
			{
				System.out.println("Error returned by getStockService() method - StockDataApplication.class.");
			}
			allStockStats.add(new StockData(multiValueMap.get(stock)));
		});

		setAllStockStats(allStockStats);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(StockdataApplication.class, args);

/*
		String[] tickers = getTickers();

		StockService stockService = new StockService(refreshService);

		// Convert 'tickers' array to 'stocks' list.
		final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList(tickers));
		getStockService(stocks);

		StockdataApplication SDA = new StockdataApplication();

		List<StockData> currentStockDataList = SDA.getAllStockStats();
		currentStockDataList.forEach(System.out::println);
*/
	}

	public List<String> fetchCurrentTickersInMongoDB(StockRepoService stockRepoService)
	{
		int totalTickers = stockRepoService.getAllStocks().size();

		if (totalTickers < 1)
		{
			return null;
		}

		List<String> currentTickers = new ArrayList<>();

		for (int i = 0; i <totalTickers; i++)
		{
			currentTickers.add(stockRepoService.getAllStocks().get(i).getId());
		}

		System.out.println("Additionally...Refreshing Current Stock Data in MongoDB: " + currentTickers);
//		currentTickers.forEach(System.out::print);
		return currentTickers;
	}

	public static void getMongoDB(StockRepoService stockRepoService)
	{
		System.out.println("---------------------------------");
		System.out.println("MongoDB Stock Collection");
		System.out.println("---------------------------------");
		System.out.println(stockRepoService.getAllStocks());
	}

	@Bean
	CommandLineRunner runner(StockDataRepository repository, StockRepoService stockRepoService)
	{
		getMongoDB(stockRepoService);

//		/********************************************************************/
		System.out.println("-----------------------------------------------");
		System.out.println("Welcome to the Yahoo StockData and MongoDB API!");
		System.out.println("-----------------------------------------------");
		System.out.println("Please select one of the options below:");
		System.out.println("1 - Enter new ticker(s) to add or update into MongoDB.");
		System.out.println("  - Fetch All Stock Documents from MongoDB.");
		System.out.println("2 - Delete ticker(s) from the data base.");
//		/********************************************************************/

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		switch (choice)
		{
			case 1:
				List<String> currentTickers = fetchCurrentTickersInMongoDB(stockRepoService);
				System.out.println("Enter ticker name(s) to add/update. Press 'Enter' to proceed. ");
				getStockService(currentTickers);
				return args -> {
					stockRepoService.saveAllStocks(getAllStockStats());
					getMongoDB(stockRepoService);
					System.out.println("UPDATED StockData MongoDB.");
				};

			case 2:
				System.out.println("Enter ticker name(s) to DELETE. Press 'Enter' to proceed. ");
				List<String> ticker = Arrays.asList(getTickers());
				return args -> {
					stockRepoService.deleteStocks(ticker);
					getMongoDB(stockRepoService);
					System.out.println("UPDATED StockData MongoDB.");
				};
		}

		System.out.println("UPDATED StockData MongoDB.");
		getMongoDB(stockRepoService);

		scan.close();
		return args -> stockRepoService.saveAllStocks(getAllStockStats());
	}

}
