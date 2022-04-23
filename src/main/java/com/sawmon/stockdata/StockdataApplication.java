package com.sawmon.stockdata;

import java.io.IOException;
import java.util.*;

import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.model.StockWrapper;
import com.sawmon.stockdata.repository.StockDataRepository;
import com.sawmon.stockdata.service.RefreshService;
import com.sawmon.stockdata.service.StockService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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

	public void setAllStockStats(List<StockData> allStockStats) {
		this.allStockStats = allStockStats;
	}

	private static String[] getTickers()
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter ticker name(s). Press 'Enter' to start application. ");
		String[] tickers = scan.nextLine().split(" ");

		scan.close();
		return tickers;
	}

	private static void getStockService( List<StockWrapper> stocks) throws IOException
	{
		StockService stockService = new StockService(refreshService);

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


	public static void main(String[] args) throws IOException 
	{
		SpringApplication.run(StockdataApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(StockDataRepository repository)throws IOException
	{
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

	public static void menuSystem()
	{

		/***************************************************/

		System.out.println("Welcome to the Yahoo StockData API!");
		System.out.println("-------------------------\n");
		System.out.println("Please select one of the options below:");
		System.out.println("1 - Enter new ticker(s).");
		System.out.println("2 - Print the tickers in the data base.");
		System.out.println("3 - Get an update on the ticker(s).");
		System.out.println("4 - Delete ticker(s) from the data base.");
		System.out.println("5 - Quit");

		/***************************************************/



		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		switch (choice) {
			case 1:
				// Perform "original number" case.
				break;
			case 2:
				// Perform "encrypt number" case.
				break;
			case 3:
				// Perform "decrypt number" case.
				break;
			case 4:
				// Perform "quit" case.
				break;
			default:
				// The user input an unexpected choice.
		}
	}



}
