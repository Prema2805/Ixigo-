package pages;

import java.time.Duration;
import java.time.LocalDate;
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
import objectrepository.Locators;
import parameters.Reporter;
import setup.Base;

public class searchpage {

    WebDriver driver;
    WebDriverWait wait;
    ExtentTest extTest;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public searchpage(WebDriver driver, ExtentTest extTest) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        this.extTest = extTest;
    }

    /** Handle popup safely (if present) */
    public void handlePopupIfExists() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement popupFrame = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("wiz-iframe-intent")));
			driver.switchTo().frame(popupFrame);
			WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeButton")));
			closeBtn.click();
			driver.switchTo().defaultContent();
			Reporter.generateReport(driver, extTest, Status.INFO, "Closed popup successfully");
		} catch (Exception e) {
			Reporter.generateReport(driver, extTest, Status.INFO, "No popup to close or already closed");
		}
	}

    public void openFlightsTab() {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(Locators.flight)).click();
            Reporter.generateReport(driver, extTest, Status.PASS, "Opened Flights tab");
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to open Flights tab: " + e.getMessage());
        }
    }

    public void selectRoundTrip() {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(Locators.round)).click();
            Reporter.generateReport(driver, extTest, Status.PASS, "Selected Round Trip");
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL,
                    "Round Trip selection failed: " + e.getMessage());
        }
    }

    public boolean enterBoardingPlace(String from) {
		try {
			handlePopupIfExists();
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div"))).click();
			driver.findElement(
					By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input"))
					.sendKeys(from);

			List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
							.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]")));

			if (!results.isEmpty()) {
				results.get(0).click();
			} else {
				driver.findElement(By
						.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input"))
						.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
			}
			return true;
		} catch (Exception e) {
			Reporter.generateReport(driver, extTest, Status.FAIL,
					"Failed to enter origin: " + from + " | " + e.getMessage());
			return false;
		}
	}

    public boolean enterLandingPlace(String to) {
        try {
            // ✅ Click on the "To" input box
            By toInput = By.xpath("(//input[contains(@class,'outline-none') and contains(@class,'text-primary')])[2]");
            wait.until(ExpectedConditions.elementToBeClickable(toInput)).click();

            // ✅ Enter the destination city
            driver.findElement(toInput).sendKeys(to);

            // ✅ Wait for dropdown results
            List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//span[@class='block truncate' and text()='" + to + "']")));

            if (!results.isEmpty()) {
                results.get(0).click(); // select first matching result
            } else {
                // fallback if no direct match found
                driver.findElement(toInput).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
            }

            Reporter.generateReport(driver, extTest, Status.PASS, "Entered destination: " + to);
            return true;

        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to enter destination: " + to + " | " + e.getMessage());
            return false;
        }
    }
    public boolean enteringdate(String departure, String ret) {
		try {
			// Convert dd-MM-yyyy → MMMM d, yyyy
			DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter ariaFmt = DateTimeFormatter.ofPattern("MMMM d, yyyy");

			LocalDate depDate = LocalDate.parse(departure, inputFmt);
			LocalDate retDate = LocalDate.parse(ret, inputFmt);

			String depLabel = depDate.format(ariaFmt);
			String retLabel = retDate.format(ariaFmt);

			// Open calendar first
			driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/div/div/div"))
					.click();

			// Select departure date
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//abbr[@aria-label='" + depLabel + "']")))
					.click();

			// Select return date
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//abbr[@aria-label='" + retLabel + "']")))
					.click();
			Reporter.generateReport(driver, extTest, Status.PASS, "The departure and return date are selected");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    public void setTravellersAndClass(int adults, int children, int infants, String travelClass) {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(Locators.travellersPanel)).click();

            // Adults
            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//div[.//p[normalize-space()='Adults'] and .//p[normalize-space()='12 yrs or above']]//button[@data-testid='" + adults + "'])[1]")))
                        .click();
            } catch (Exception ex) {
                Reporter.generateReport(driver, extTest, Status.WARNING,
                        "Adult selection skipped: " + ex.getMessage());
            }

            // Children
            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//div[.//p[normalize-space()='Adults'] and .//p[contains(text(),'12 yrs or above')]]/following::div[.//p[normalize-space()='Children'] and .//p[contains(text(),'2 - 12 yrs')]])[1]//button[@data-testid='" + children + "']")))
                        .click();
            } catch (Exception ex) {
                Reporter.generateReport(driver, extTest, Status.WARNING,
                        "Children selection skipped: " + ex.getMessage());
            }

            // Infants
            if (infants > 0) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("(//div[.//p[normalize-space()='Infants'] and .//p[contains(text(),'below 2 yrs')]])[1]//button[@data-testid='" + infants + "']")))
                            .click();
                } catch (Exception ex) {
                    Reporter.generateReport(driver, extTest, Status.WARNING,
                            "Infant selection skipped: " + ex.getMessage());
                }
            }

            // Travel class
            try {
                wait.until(ExpectedConditions.elementToBeClickable(Locators.travelClassDropdown)).click();
                By classOption = Locators.travelClassOption(travelClass);
                wait.until(ExpectedConditions.elementToBeClickable(classOption)).click();
            } catch (Exception ex) {
                Reporter.generateReport(driver, extTest, Status.WARNING,
                        "Travel class selection skipped: " + ex.getMessage());
            }

            // Apply travellers
            try {
                wait.until(ExpectedConditions.elementToBeClickable(Locators.travellersApplyBtn)).click();
            } catch (Exception ex) {
                Reporter.generateReport(driver, extTest, Status.WARNING,
                        "Apply button not clicked: " + ex.getMessage());
            }

            Reporter.generateReport(driver, extTest, Status.PASS,
                    "Travellers set: A" + adults + " C" + children + " I" + infants + " Class:" + travelClass);
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to set travellers/class: " + e.getMessage());
        }
    }

    public void clickSearch() {
        try {
            handlePopupIfExists();
            wait.until(ExpectedConditions.elementToBeClickable(Locators.searchButton)).click();
            Reporter.generateReport(driver, extTest, Status.PASS, "Clicked Search");
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to click Search: " + e.getMessage());
        }
    }

    public boolean areResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resultsContainer));
            Reporter.generateReport(driver, extTest, Status.PASS, "Search results displayed");
            return true;
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL,
                    "Search results not detected: " + e.getMessage());
            return false;
        }
    }
}
