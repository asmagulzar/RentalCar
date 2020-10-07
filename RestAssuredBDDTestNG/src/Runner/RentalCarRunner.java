package Runner;

import org.junit.runner.RunWith;
import org.testng.annotations.BeforeSuite;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;





@RunWith(Cucumber.class)
@CucumberOptions(features = "Resources", glue = "StepDefinition",dryRun = false)
public class RentalCarRunner extends AbstractTestNGCucumberTests{
	
	
}

