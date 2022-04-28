package com.sawmon.stockdata;

import java.io.IOException;
import java.util.*;

import com.sawmon.stockdata.model.StockData;
import com.sawmon.stockdata.model.StockWrapper;

import com.sawmon.stockdata.service.RefreshService;
import com.sawmon.stockdata.service.StockRepoService;
import com.sawmon.stockdata.service.StockService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
		// Removing duplicate tickers (aka Strings).
		tickers = new HashSet<>(Arrays.asList(tickers)).toArray(new String[0]);
		return tickers;
	}

	private static Boolean getStockData()
	{
		String[] tickers  = getTickers();

		StockService stockService = new StockService(refreshService);
		List<StockWrapper> stocks = stockService.findStocks(Arrays.asList(tickers));
		List<StockData> list = getStockService(stocks, stockService);

		list.forEach(l -> System.out.println(l.toString()));

		System.out.println("Would you like to save the stock(s) to MongoDB?");
		System.out.println("Please select 1 for Yes, and 2 for No.");

		Scanner scan = new Scanner(System.in);
		int answer = scan.nextInt();

		if (answer == 1)
		{
			setAllStockStats(list);
			return true;
		}

		else
			return false;
	}

	private static void updateMongoDb(StockRepoService stockRepoService)
	{

		List<String> updateTickers = fetchCurrentTickersInMongoDB(stockRepoService);

		if (updateTickers == null)
			return;

		StockService stockService = new StockService(refreshService);

		List<StockWrapper> stocks = stockService.findStocks(updateTickers);

		setAllStockStats(getStockService(stocks, stockService));
		stockRepoService.saveAllStocks(getAllStockStats());

	}

	public static List<StockData> getStockService(List<StockWrapper> stocks, StockService stockService)
	{
		Map<StockWrapper, ArrayList<String>> multiValueMap = new HashMap<StockWrapper, ArrayList<String>>();
		List<StockData> temp = new ArrayList<>();

		stocks.forEach(stock ->
		{
			multiValueMap.put(stock, new ArrayList<String>());
			try
			{
				multiValueMap.get(stock).add(stockService.findSymbol(stock));
				multiValueMap.get(stock).add(stockService.findCompany(stock));
				multiValueMap.get(stock).add(stockService.findStockExchange(stock));
				multiValueMap.get(stock).add(stockService.findPrice(stock).toString());
				multiValueMap.get(stock).add(stockService.findLastChangePercent(stock).toString());
				multiValueMap.get(stock).add(stockService.findChangeFrom200MeanPercent(stock).toString());
				multiValueMap.get(stock).add(stockService.findDividend(stock).toString());
				multiValueMap.get(stock).add(stockService.findROE(stock).toString());
				multiValueMap.get(stock).add(stockService.findEPS(stock).toString());
				multiValueMap.get(stock).add(stockService.findBookValuePerShare(stock).toString());
				multiValueMap.get(stock).add(stockService.findYearHigh(stock).toString());
				multiValueMap.get(stock).add(stockService.findYearLow(stock).toString());
				multiValueMap.get(stock).add(stock.getLastAccessed().toString());
			}
			catch (IOException e)
			{
				System.out.println("Error returned by getStockService() method - StockDataApplication.class.");
			}
			temp.add(new StockData(multiValueMap.get(stock)));
		});
		return temp;
	}

	public static List<String> fetchCurrentTickersInMongoDB(StockRepoService stockRepoService)
	{
		int totalTickers = stockRepoService.getAllStocks().size();

		if (totalTickers < 1)
			return null;

		List<String> currentTickers = new ArrayList<>();

		for (int i = 0; i <totalTickers; i++)
			currentTickers.add(stockRepoService.getAllStocks().get(i).getId());

		System.out.println("Refreshing All Stock Data for the ticker(s): " + currentTickers);
		return currentTickers;
	}

	public static void getMongoDB(StockRepoService stockRepoService)
	{
		System.out.println("--------------------------------------");
		System.out.println("   Current MongoDB Stock Collection   ");
		System.out.println("--------------------------------------");
		System.out.println(stockRepoService.getAllStocks());
	}

	public static void main(String[] args)
	{
		SpringApplication.run(StockdataApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StockRepoService stockRepoService)
	{
		getMongoDB(stockRepoService);

		Scanner scan = new Scanner(System.in);

		System.out.println("-----------------------------------------------");
		System.out.println("Welcome to the Yahoo StockData and MongoDB API!");
		System.out.println("-----------------------------------------------");

		while(true)
		{
			System.out.println("Please select one of the options below:");
			System.out.println("1 - Enter new ticker(s) to add");
			System.out.println("2 - Fetch all stocks by company name in alphabetical order");
			System.out.println("3 - Update all stock(s) in the MongoDB.");
			System.out.println("4 - Delete ticker(s) from the data base.");

			System.out.println("Enter your choice:");
			int choice = scan.nextInt();//accept user input

			switch (choice)
			{
				case 1:
					System.out.println("Enter ticker name(s) to add or check. Press 'Enter' to proceed. ");
					Boolean bool = getStockData();

					if (bool)
					{
						stockRepoService.saveAllStocks(getAllStockStats());
						getMongoDB(stockRepoService);
					}
					break;

				case 2:
					String field = "companyName";
					System.out.println(stockRepoService.sortBy(field));
					break;

				case 3:
					System.out.println("Updating all stocks in MongoDB.");
					updateMongoDb(stockRepoService);
					getMongoDB(stockRepoService);
					break;

				case 4:
					System.out.println("Enter ticker name(s) to DELETE. Press 'Enter' to proceed. ");
					List<String> ticker = Arrays.asList(getTickers());
					stockRepoService.deleteStocks(ticker);
					getMongoDB(stockRepoService);
					break;

				case 5:
					System.out.println("Exiting the application");
					scan.close();
					System.exit(0);

				default: System.out.println("Incorrect input!!! Please re-enter choice from our menu");
			}
		}
	}
}