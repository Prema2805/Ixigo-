package pages;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import objectrepository.locators;
import parameters.reporter;
import setup.Base;

public class searchpage {

    WebDriver driver;
    WebDriverWait wait;
    ExtentTest extTest;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public searchpage(WebDriver driver, ExtentTest extTest) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.extTest = extTest;
    }

    /** Handle popup safely (if present) */
    public void handlePopupIfExists() {
        try {
            List<WebElement> popups = driver.findElements(By.id("wiz-iframe-intent"));
            if (!popups.isEmpty()) {
                driver.switchTo().frame(popups.get(0));
                driver.findElement(By.id("closeButton")).click();
                driver.switchTo().defaultContent();
                reporter.generateReport(driver, extTest, Status.INFO, "Closed popup successfully");
            }
        } catch (Exception ignore) {
            // Popup absence is not critical
        }
    }

    public void openFlightsTab() {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(locators.flight)).click();
            reporter.generateReport(driver, extTest, Status.PASS, "Opened Flights tab");
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to open Flights tab: " + e.getMessage());
        }
    }

    public void selectRoundTrip() {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(locators.round)).click();
            reporter.generateReport(driver, extTest, Status.PASS, "Selected Round Trip");
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Round Trip selection failed: " + e.getMessage());
        }
    }

    public void enterBoardingPlace(String from) {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(locators.from)).click();
            driver.findElement(locators.click_from).sendKeys(from);

            List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]")));

            if (!results.isEmpty()) {
                results.get(0).click();
            } else {
                driver.findElement(locators.click_from).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
            }
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to enter origin: " + from + " | " + e.getMessage());
        }
    }

    public void enterLandingPlace(String to) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locators.to)).click();
            driver.findElement(locators.click_to).sendKeys(to);

            List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//span[@class='block truncate' and text()='" + to + "']")));

            if (!results.isEmpty()) {
                results.get(0).click();
            } else {
            	Base.sleep();
                driver.findElement(locators.click_from).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
            }
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to enter destination: " + to + " | " + e.getMessage());
        }
    }

    public void setTravellersAndClass(int adults, int children, int infants, String travelClass) {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(locators.travellersPanel)).click();

            // Adults
            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//div[.//p[normalize-space()='Adults'] and .//p[normalize-space()='12 yrs or above']]//button[@data-testid='" + adults + "'])[1]")))
                        .click();
            } catch (Exception ex) {
                reporter.generateReport(driver, extTest, Status.WARNING,
                        "Adult selection skipped: " + ex.getMessage());
            }

            // Children
            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//div[.//p[normalize-space()='Adults'] and .//p[contains(text(),'12 yrs or above')]]/following::div[.//p[normalize-space()='Children'] and .//p[contains(text(),'2 - 12 yrs')]])[1]//button[@data-testid='" + children + "']")))
                        .click();
            } catch (Exception ex) {
                reporter.generateReport(driver, extTest, Status.WARNING,
                        "Children selection skipped: " + ex.getMessage());
            }

            // Infants
            if (infants > 0) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("(//div[.//p[normalize-space()='Infants'] and .//p[contains(text(),'below 2 yrs')]])[1]//button[@data-testid='" + infants + "']")))
                            .click();
                } catch (Exception ex) {
                    reporter.generateReport(driver, extTest, Status.WARNING,
                            "Infant selection skipped: " + ex.getMessage());
                }
            }

            // Travel class
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locators.travelClassDropdown)).click();
                By classOption = locators.travelClassOption(travelClass);
                wait.until(ExpectedConditions.elementToBeClickable(classOption)).click();
            } catch (Exception ex) {
                reporter.generateReport(driver, extTest, Status.WARNING,
                        "Travel class selection skipped: " + ex.getMessage());
            }

            // Apply travellers
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locators.travellersApplyBtn)).click();
            } catch (Exception ex) {
                reporter.generateReport(driver, extTest, Status.WARNING,
                        "Apply button not clicked: " + ex.getMessage());
            }

            reporter.generateReport(driver, extTest, Status.PASS,
                    "Travellers set: A" + adults + " C" + children + " I" + infants + " Class:" + travelClass);
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to set travellers/class: " + e.getMessage());
        }
    }

    public void clickSearch() {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(locators.searchButton)).click();
            reporter.generateReport(driver, extTest, Status.PASS, "Clicked Search");
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to click Search: " + e.getMessage());
        }
    }

    public boolean areResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locators.resultsContainer));
            reporter.generateReport(driver, extTest, Status.PASS, "Search results displayed");
            return true;
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Search results not detected: " + e.getMessage());
            return false;
        }
    }
}
