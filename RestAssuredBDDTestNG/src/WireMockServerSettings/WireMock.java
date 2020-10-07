package WireMockServerSettings;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;



public class WireMock {
	
	private int port = 8090;
	public WireMockServer wireMockServer;
	
	@BeforeSuite
	public void SetUpWireMockServer() {
	
		wireMockServer = new WireMockServer(port);
		wireMockServer.start();
		wireMockServer.stubFor(get(urlEqualTo("/getcars"))
                .willReturn(
                		aResponse()
                		.withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("json/RentalCars.json")));
		
	}
	@AfterSuite
	public void teardown () {
        wireMockServer.stop();
    }
	
	
	 
}
