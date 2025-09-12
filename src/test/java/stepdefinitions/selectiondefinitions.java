package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import pages.selectionpage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class selectiondefinitions {

    WebDriver driver = hooks.driver;
    ExtentTest extTest = hooks.extTest;
    selectionpage select;

    @Given("the user is on the selection page")
    public void the_user_is_on_the_selection_page() {
        select = new selectionpage(driver, extTest);
        boolean actRes = select.loadPage();
        Assert.assertTrue(actRes, "❌ Failed to load selection page");
        extTest.log(Status.PASS, "✅ Selection page loaded successfully");
    }

    @Given("the user clicks third filter departure as {string}")
    public void the_user_clicks_third_filter_departure_as(String departureFilter) {
        // For now, only Morning is supported
        boolean actRes = select.applyDepartureMorning();
        Assert.assertTrue(actRes, "❌ Failed to apply departure filter: " + departureFilter);
        extTest.log(Status.PASS, "✅ Departure filter applied: " + departureFilter);
    }

    @Given("the user clicks fourth filter arrival as {string}")
    public void the_user_clicks_fourth_filter_arrival_as(String arrivalFilter) {
        // For now, only Morning is supported
        boolean actRes = select.applyArrivalMorning();
        Assert.assertTrue(actRes, "❌ Failed to apply arrival filter: " + arrivalFilter);
        extTest.log(Status.PASS, "✅ Arrival filter applied: " + arrivalFilter);
    }

    @Then("select the first available flight")
    public void select_the_first_available_flight() {
        boolean actRes = select.selectFirstAvailableFlight();
        Assert.assertTrue(actRes, "❌ Failed to select first available flight");
        extTest.log(Status.PASS, "✅ First available flight selected");
    }
}
