package StepDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RentalCarTestcases {
	public static Response res;
	
	public static void main(String[] args) throws Exception {
		
        String sURL = "http://localhost:3000/getcars";
        RestAssured.baseURI = sURL;
        
        PrintBlueTesla();
        LowestPerDayRent();
        HighestRevenueCar();
       
	}
    
    public static void PrintBlueTesla() {
    	
    	String make = "Tesla";
    	String color = "Blue";
    	res = (Response)RestAssured.given().contentType("application/json").get();
    	System.out.println("List Of All Cars: ");
    	System.out.println();
    	List<Map<String,String>> allcars= res.jsonPath().getList("Car");
        for(int i=0;i<allcars.size();i++) {
        	System.out.println(allcars.get(i));
        }
        
        List<String> makelist = res.jsonPath().getList("Car.make");
        System.out.println("List Of "+color+" "+make+" Cars:");
        for(int i=0;i<makelist.size();i++) {
        	if(makelist.get(i).equalsIgnoreCase(make)) {
        		int setindex = i;
        		String carcolor = res.jsonPath().getString("Car["+setindex+"].metadata.Color");
        		if(carcolor.equalsIgnoreCase(color)) {
        			System.out.println(allcars.get(i));
        		}
        	        		
        	}
        }
        
        System.out.println("StatusCode():: "+res.statusCode());
        
    }
    
    public static  void LowestPerDayRent() {
    	
    	res = (Response)RestAssured.given().contentType("application/json").get();
    	List<Map<String,String>> allcars= res.jsonPath().getList("Car");
    	
    	List<Float> perdayrentpricelist = res.jsonPath().getList("Car.perdayrent.Price");
    	List<Float> perdayrentdiscountlist = res.jsonPath().getList("Car.perdayrent.Discount");
    	System.out.println(perdayrentpricelist);
    	
    	Float smallprice = res.jsonPath().getFloat("Car[0].perdayrent.Price");
    	Float perdayrentprice = res.jsonPath().getFloat("Car[0].perdayrent.Price");
		Float perdayrentdiscount = res.jsonPath().getFloat("Car[0].perdayrent.Discount");
		Float smallpriceafterdiscount = perdayrentprice - (perdayrentprice*(perdayrentdiscount/100));
    	ArrayList<Integer> smallpriceindex = new ArrayList<>();
    	ArrayList<Integer> smallpriceafterdiscountindex = new ArrayList<>();
    	
		for(int i=1;i<perdayrentpricelist.size();i++) {
    		perdayrentprice = res.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
    		perdayrentdiscount = res.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
    		Float priceafterdiscount = perdayrentprice - (perdayrentprice*(perdayrentdiscount/100));
    		if(perdayrentprice<=smallprice) {
    			smallprice=perdayrentprice;
    			smallpriceindex.add(i);
    			
    		}
    		if(priceafterdiscount<=smallpriceafterdiscount) {
    			smallpriceafterdiscount=priceafterdiscount;
    			smallpriceafterdiscountindex.add(i);
    			
    		}
    	    		
    	}
    	
    	System.out.println("Car with lowest RentPerDay Price is : "+res.jsonPath().get("Car["+smallpriceindex+"].vin"));
				
		System.out.println("Car with lowest RentPerDay Price After Discount is : "+res.jsonPath().get("Car["+smallpriceafterdiscountindex+"].vin"));
		}
    
    public static  void HighestRevenueCar() {
    	Float perdayrentprice,perdayrentdiscount,yoymaintenancecost,depreciation,revenue;
    	float highestrevenue=0;
		int yeartodate,setindex=0;    
    
    	res = (Response)RestAssured.given().contentType("application/json").get();
    	    	
    	
    	List<Float> perdayrentpricelist = res.jsonPath().getList("Car.metrics.yoymaintenancecost");
    	List<Float> perdayrentdiscountlist = res.jsonPath().getList("Car.perdayrent.Discount");
    	List<Float> perdayrentafterdiscountlist = new ArrayList<Float>();
    	
    	
    	
    	
    	for(int i=0;i<perdayrentpricelist.size();i++) {
    		
    		perdayrentprice = res.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
    		perdayrentdiscount = res.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
    		Float priceafterdiscount = perdayrentprice - (perdayrentprice*(perdayrentdiscount/100));
    		
    		yoymaintenancecost = res.jsonPath().getFloat("Car["+i+"].metrics.yoymaintenancecost");
    		depreciation = res.jsonPath().getFloat("Car["+i+"].metrics.depreciation");
    		yeartodate = res.jsonPath().get("Car["+i+"].metrics.rentalcount.yeartodate");
    		
    		revenue = (priceafterdiscount * yeartodate) - (yoymaintenancecost+depreciation);
    		System.out.println(revenue);
    		
    		
    		
    		if(revenue>highestrevenue) {
    		highestrevenue	=revenue;
    		setindex = i;
    		
    		}
    		
    		

    	}
    	System.out.println("Highest revenue car is "+res.jsonPath().get("Car["+setindex+"].vin")+" with a revenue of "+highestrevenue);
    		   		
    	}
    	
    	
    	
    	
    

  	}
