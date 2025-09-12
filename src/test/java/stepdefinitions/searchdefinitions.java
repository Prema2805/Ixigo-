package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import pages.searchpage;
import setup.Base;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class searchdefinitions {

    WebDriver driver = hooks.driver;
    ExtentTest extTest = hooks.extTest;
    searchpage search;
    static String[][] excelData;

    @Given("the user is on the homepage")
    public void the_user_is_on_the_homepage() {
        // Hooks setup already launched the site
        search = new searchpage(driver, extTest);

        // Optionally validate URL
        String currentUrl = Base.driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("ixigo.com"),
                "❌ Unexpected homepage URL: " + currentUrl);
    }

    @When("the user selects {string} trip type")
    public void the_user_selects_trip_type(String tripType) {
        if (tripType.equalsIgnoreCase("Round Trip")) {
            search.selectRoundTrip();
        } else {
            throw new IllegalArgumentException("❌ Unsupported trip type: " + tripType);
        }
    }

    @When("the user enters origin as {string}")
    public void the_user_enters_origin_as(String from) {
    	int row = hooks.currentrow;
    	from = hooks.excelData[row][5];
        search.enterBoardingPlace(from);
    }

    @When("the user enters destination as {string}")
    public void the_user_enters_destination_as(String to) {
    	int row = hooks.currentrow;
    	to = hooks.excelData[row][6];
        search.enterLandingPlace(to);
    }

    @When("the user sets travellers as {string} adults, {string} children, {string} infants and class as {string}")
    public void the_user_sets_travellers_and_class(String adults, String children, String infants, String travelClass) {
    	int row = hooks.currentrow;
    	adults = hooks.excelData[row][7];
    	children = hooks.excelData[row][8];
    	infants = hooks.excelData[row][9];
    	travelClass = hooks.excelData[row][10];
        int a = Integer.parseInt(adults.trim());
        int c = Integer.parseInt(children.trim());
        int i = Integer.parseInt(infants.trim());
        search.setTravellersAndClass(a, c, i, travelClass);
    }

    @When("the user clicks Search")
    public void the_user_clicks_search() {
        search.clickSearch();
    }

    @Then("search results should be displayed")
    public void search_results_should_be_displayed() {
        boolean res = search.areResultsDisplayed();
        Assert.assertTrue(res, "❌ Expected search results to be displayed");
    }
}
