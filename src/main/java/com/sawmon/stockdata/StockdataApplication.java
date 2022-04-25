package com.sawmon.stockdata;

import java.io.IOException;
import java.util.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.model.StockWrapper;
import com.sawmon.stockdata.repository.StockDataRepository;
import com.sawmon.stockdata.service.RefreshService;
import com.sawmon.stockdata.service.StockService;

import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories(basePackageClasses = StockDataRepository.class)
public class StockdataApplication
{
	private static final RefreshService refreshService = new RefreshService();
	private static List<StockData> allStockStats = new ArrayList<>();
	public List<StockData> getAllStockStats() {
		return allStockStats;
	}


//	private static final StockDataRepository stockDataRepo = new StockDataRepository();

	public void setAllStockStats(List<StockData> allStockStats) {
		this.allStockStats = allStockStats;
	}

	private static String[] getTickers()
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter ticker name(s) to DELETE. Press 'Enter' to proceed. ");
//		System.out.println("Enter ticker name(s) too add/update. Press 'Enter' to proceed. ");
		String[] tickers = scan.nextLine().toUpperCase().split(" ");

		scan.close();
		return tickers;
	}

	//	private static void getStockService( List<StockWrapper> stocks) throws IOException
	private static void getStockService() throws IOException
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter ticker name(s) too add/update. Press 'Enter' to proceed. ");
		String[] tickers = scan.nextLine().split(" ");

		scan.close();

		/////////////////////////
		StockService stockService = new StockService(refreshService);

		// Convert 'tickers' array to 'stocks' list.
		final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList(tickers));


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

		StockdataApplication SDA = new StockdataApplication();
		SDA.setAllStockStats(allStockStats);
	}


	public static void main(String[] args)
	{
		SpringApplication.run(StockdataApplication.class, args);

//		String[] tickers = getTickers();
//
//		StockService stockService = new StockService(refreshService);
//
//		// Convert 'tickers' array to 'stocks' list.
//		final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList(tickers));
//		getStockService(stocks);
//
//		StockdataApplication SDA = new StockdataApplication();
//
//		List<StockData> currentStockDataList = SDA.getAllStockStats();
//		currentStockDataList.forEach(System.out::println);
	}

	public static void getMongoDB(MongoClient mongoClient)
	{
		MongoDatabase mongoDb = mongoClient.getDatabase("StockData");
		MongoCollection<Document> collection = mongoDb.getCollection("stockData");

		MongoCursor<Document> cursor =  collection.find().cursor();

		if (cursor.hasNext())
		{
			System.out.println("\n");
			System.out.println("---------------------------------");
			System.out.println("MongoDB Stock Collection");
			System.out.println("---------------------------------");
			cursor.forEachRemaining(System.out::println);
		}
		else {
			System.out.println("No Stocks currently in MongoDB");
		}
	}


	@Bean
	CommandLineRunner runner(StockDataRepository repository, MongoClient mongoClient)throws IOException
	{
		getMongoDB(mongoClient);

//		/********************************************************************/
		System.out.println("\n");
		System.out.println("-----------------------------------------------");
		System.out.println("Welcome to the Yahoo StockData and MongoDB API!");
		System.out.println("-----------------------------------------------");
		System.out.println("Please select one of the options below:");
		System.out.println("1 - Enter new ticker(s) to add or update into MongoDB.");
		System.out.println("2 - Delete ticker(s) from the data base.");
//		/********************************************************************/

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		scan.close();

		StockdataApplication SDA = new StockdataApplication();

		switch (choice)
		{
			case 1:
				getStockService();
				return args -> {
					repository.saveAll(SDA.getAllStockStats());
					getMongoDB(mongoClient);
				};

			case 2:
				String[] ticker = getTickers();
				return args -> {
					repository.deleteAllById(Arrays.asList(ticker));
					getMongoDB(mongoClient);
				};
		}
		return args -> repository.saveAll(SDA.getAllStockStats());
	}

/*
	@Bean
	CommandLineRunner runner(StockDataRepository repository, MongoOperations mongoOperations, MongoClient mongoClient)throws IOException
	{

			getMongoDB(mongoClient);

		String ticker = getTickers();
		repository.deleteById(ticker);

		getMongoDB(mongoClient);
		String[] tickers = getTickers();

		StockService stockService = new StockService(refreshService);

		// Convert 'tickers' array to 'stocks' list.
		final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList(tickers));
		getStockService(stocks);

		StockdataApplication SDA = new StockdataApplication();

		List<StockData> currentStockDataList = SDA.getAllStockStats();
		currentStockDataList.forEach(System.out::println);

		return args -> repository.saveAll(currentStockDataList);
	}
	public static void menuSystem(StockDataRepository repository, MongoOperations mongoOperations, MongoClient mongoClient)
	{

		getMongoDB(mongoClient);
		Scanner scan = new Scanner(System.in);
		boolean mainLoop = true;
		int choice;
		while (true)
		{
			/*************************************************** /

			System.out.println("Welcome to the Yahoo StockData and MongoDB API!");
			System.out.println("-------------------------\n");
			System.out.println("Please select one of the options below:");
			System.out.println("1 - Enter new ticker(s).");
			System.out.println("2 - Delete ticker(s) from the data base.");
			System.out.println("3 - Print all tickers in the data base.");
			System.out.println("4 - Quit");

			/*************************************************** /

			choice = scan.nextInt();

			try {
				switch (choice)
				{
					case 1:
						getStockService();
						StockdataApplication SDA = new StockdataApplication();
						List<StockData> currentStockDataList = SDA.getAllStockStats();
						break;

					case 2:
						String[] ticker = getTickers();
						repository.deleteAllById(Arrays.asList(ticker));
						break;

					case 3:
						scan.close();
						exit(0);
				}
			}
			catch (Exception ex)
			{
				System.out.println("Please enter an integer value between 1 and 4");
				scan.next();
			}
		}

	}
*/

}