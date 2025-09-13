package stepdefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import pages.planpage;
import setup.Base;

public class plandefinitions {

    WebDriver driver = Base.driver;
    planpage planPage = new planpage(driver);

    @Given("the user is on the ixigo homepage")
    public void the_user_is_on_the_ixigo_homepage() {
        System.out.println("Ixigo homepage is opened.");
    }

    @When("the user clicks the Plan icon")
    public void the_user_clicks_the_plan_icon() {
        planPage.clickPlanIcon();
    }

    @When("the user clicks on Festivals")
    public void the_user_clicks_on_Festivals() {
        planPage.clickFestivals();
    }

    @Then("the Manali page should open")
    public void the_manali_page_should_open() {
        System.out.println("Manali page opened successfully.");
    }
}
