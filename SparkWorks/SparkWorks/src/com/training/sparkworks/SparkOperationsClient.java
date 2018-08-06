package com.training.sparkworks;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import com.training.commons.DataSource;
import com.training.commons.SparkConnection;
import com.training.commons.Utilities;

public class SparkOperationsClient {

	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.ERROR);
		//optional
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		JavaSparkContext sparkContext = SparkConnection.getContext();
		
		//start loading the data
		//1.load the collection and cache it
		
		JavaRDD<Integer> collData = DataSource.getCollData();
		System.out.println("Total Number of Records"+collData.count());
		
		//2.load the file and cache it
		JavaRDD<String> autoDataContent = sparkContext.textFile("./data/auto-data.csv");
		
		//to know number of records
		System.out.println("Number of Records"+autoDataContent.count());
		
		//printing 5 lines of data
		
		//autoDataContent.take(5).forEach(System.out::println);
		
		System.out.println("Loading data from file");
		
		Utilities.printStringRDD(autoDataContent, 10);
		
		//storing RDD
		//autoDataContent.saveAsTextFile("./data/auto-data-modified.csv");
		
		//spark Transformation
		//conversion from csv to tsv
		
		JavaRDD<String> tsvData = autoDataContent.map(str->str.replace(",","\t"));
		Utilities.printStringRDD(tsvData, 5);
		
		////////////FILTER EXAMPLE/////
		//to remove header
		
		String header = autoDataContent.first();
		
		JavaRDD<String> autoDataWithoutHeader = autoDataContent
				.filter(s->!s.equals(header));
		
		Utilities.printStringRDD(autoDataWithoutHeader, 5);
		
		//filter those records which has only "toyota"
		JavaRDD<String> toyotaData = autoDataContent.filter(str->str
				.contains("toyota"));
		System.out.println("----only toyota vehicles");
		Utilities.printStringRDD(toyotaData, 10);
		
		//to print distinct words from csv
		JavaRDD<String> distWords = autoDataContent.distinct();
		System.out.println("--printing only distinct words----");
		Utilities.printStringRDD(distWords, 20);
		
		//to count number of words in the given RDD
		JavaRDD<String> words = 
				toyotaData.flatMap(new FlatMapFunction<String,String>(){
		
				public Iterator<String> call(String t) throws Exception{
					return Arrays.asList(t.split(",")).iterator();
					
				}
		});	
	System.out.println("ToyotaRDD word Count:"+ words.count());
	
	//after cleansing the data
	System.out.println("*******After Cleansing Data******");
	
	JavaRDD<String> cleansRDD = autoDataContent.map(new CleanseRDDCars());
	Utilities.printStringRDD(cleansRDD, 5);
	
	//Set Operation
	JavaRDD<String> words1 = sparkContext.parallelize(Arrays.asList("hello","how","are","you","today"));
	JavaRDD<String> words2 = sparkContext.parallelize(Arrays.asList("hello","how","were","yesterday"));
	
	System.out.println("Union Operation - Set");
	Utilities.printStringRDD(words1.union(words2),9);
	
	System.out.println("InterSection Operation Set");
	Utilities.printStringRDD(words1.intersection(words2),9);
	
	//3,4,56,43,2,66,77,23
	//find sum of numbers in the given RDD
	Integer collDataCount = collData.reduce((x,y)->x+y);
	System.out.println("Sum Of Given Integers"+collDataCount);
	
	//to get avg MPGCity for all the cars in city
	//JavaRDD<Integer> totalMautoDataWithoutHeader.map(new MPGWorks());
	
	
	
	}

}