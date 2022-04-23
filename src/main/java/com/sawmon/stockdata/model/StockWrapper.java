package com.sawmon.stockdata.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import yahoofinance.Stock;

//	Wrapper classes provide a way to use primitive data types (integer, boolean, etc..) 
//	as objects. Sometimes you must use wrapper classes, for example when working with Collection objects, 
//	such as ArrayList, where primitive types cannot be used (the list can only store objects).

// Using @Getter annotation on a class is like you annotate all the non-static fields in that class with the annotation.
// Using @With annotation to be able to update our "lastAccessed" variable whenever we access it by creating a new object.
// (We don't use @Setter because that can mutate the state of the object).
// Using @AllArgsConstructor because it is needed with the use of the @With annotation.


@Getter
@With
@AllArgsConstructor
public class StockWrapper 
{
	// Refresh Service
	private final Stock stock;
	private final LocalDateTime lastAccessed;
	
	public StockWrapper(final Stock stock)
	{
		this.stock = stock;
		// We can put a clock function in the .now() method if we want to set a specific time for testing purposes.
		lastAccessed = LocalDateTime.now();
	}

}
