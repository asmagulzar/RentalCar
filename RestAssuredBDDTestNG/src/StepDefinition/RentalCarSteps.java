package StepDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

	public class RentalCarSteps {
		String make,color;
		Response res;
		List<Map<String,String>> allcars;
		
		
		@Given("^Request Is Sent$")
		public void request_Is_Sent() throws Throwable {
		
			String sURL = "http://localhost:3000/getcars";
	        RestAssured.baseURI = sURL;
	        res = (Response)RestAssured.given().contentType("application/json").get();
	   	}
		
		@When("^List Of All Cars Is Received$")
		public void list_Of_All_Cars_Is_Received() throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			allcars= res.jsonPath().getList("Car");
		}
		
		@Then("^Print All Cars$")
		public void print_All_Cars() throws Throwable {
			System.out.println("List Of All Cars: ");
	    	System.out.println();
	    	
	        for(int i=0;i<allcars.size();i++) {
	        	System.out.println(allcars.get(i));
	        }
	        System.out.println();
		}
		
		@And("^\"([^\"]*)\" and \"([^\"]*)\" Is Entered$")
		public void and_Is_Entered(String make, String color) throws Throwable {
		    this.make=make;
		    this.color=color;
		    
		}

		@Then("^Print The Cars$")
		public void print_The_Cars() throws Throwable {
			
			List<String> makelist = res.jsonPath().getList("Car.make");	   
			System.out.println();
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
		}

		@Then("^Print Cars With Lowest PerDayRent Price$")
		public void print_Cars_With_Lowest_PerDayRent_Price() throws Throwable {
			List<Float> perdayrentpricelist = res.jsonPath().getList("Car.perdayrent.Price");
	    	System.out.println();
			System.out.println("PricePerDay Price List: "+perdayrentpricelist);
	    	
	    	Float smallprice = res.jsonPath().getFloat("Car[0].perdayrent.Price");
	    	Float perdayrentprice = res.jsonPath().getFloat("Car[0].perdayrent.Price");
			ArrayList<Integer> smallpriceindex = new ArrayList<>();
	    		    	
			for(int i=1;i<perdayrentpricelist.size();i++) {
	    		perdayrentprice = res.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
	    		
	    		if(perdayrentprice<=smallprice) {
	    			smallprice=perdayrentprice;
	    			smallpriceindex.add(i);
	    			
	    		}
	    		
	    	    		
	    	}
	    	System.out.println();
	    	System.out.println("Car with lowest RentPerDay Price is : "+res.jsonPath().get("Car["+smallpriceindex+"].vin"));
					

		}

		@Then("^Print Cars With Lowest PerDayRent Price After Discount$")
		public void print_Cars_With_Lowest_PerDayRent_Price_After_Discount() throws Throwable {
			
	    	List<Float> perdayrentdiscountlist = res.jsonPath().getList("Car.perdayrent.Discount");
	    		    	
	    	Float perdayrentprice = res.jsonPath().getFloat("Car[0].perdayrent.Price");
			Float perdayrentdiscount = res.jsonPath().getFloat("Car[0].perdayrent.Discount");
			Float smallpriceafterdiscount = perdayrentprice - (perdayrentprice*(perdayrentdiscount/100));
	    	ArrayList<Integer> smallpriceindex = new ArrayList<>();
	    	ArrayList<Integer> smallpriceafterdiscountindex = new ArrayList<>();
	    	
			for(int i=1;i<perdayrentdiscountlist.size();i++) {
	    		perdayrentprice = res.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
	    		perdayrentdiscount = res.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
	    		Float priceafterdiscount = perdayrentprice - (perdayrentprice*(perdayrentdiscount/100));
	    		
	    		if(priceafterdiscount<=smallpriceafterdiscount) {
	    			smallpriceafterdiscount=priceafterdiscount;
	    			smallpriceafterdiscountindex.add(i);
	    			
	    		}
	    	    		
	    	}
	    	  
			System.out.println();		
			System.out.println("Car with lowest RentPerDay Price After Discount is : "+res.jsonPath().get("Car["+smallpriceafterdiscountindex+"].vin"));
			
    
		}

		
}
