package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import objectrepository.locators;
import parameters.reporter;

public class selectionpage extends searchpage {

    public selectionpage(WebDriver driver, ExtentTest extTest) {
        super(driver, extTest);
    }

    /** Load flight search page and perform default search */
    public boolean loadPage() {
        openFlightsTab();
        handlePopupIfExists();
        selectRoundTrip();
        enterBoardingPlace("chennai");
        enterLandingPlace("Mumbai");
        setTravellersAndClass(1, 0, 1, "Economy");
        clickSearch();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locators.resultsContainer));
            closePriceLockPopupIfPresent(); // close any blocking popup
            reporter.generateReport(driver, extTest, Status.PASS, "The page has been landed");
            return true;
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Failed to load page: " + e.getMessage());
            return false;
        }
    }

    /**
     * Try multiple known popup locators and dismiss using click/JS/forced-visible.
     */
    public void closePriceLockPopupIfPresent() {
        By[] popupLocators = new By[]{
            By.xpath("//*[@id='portal-root']/div/div[2]/div/button"),
            By.cssSelector("#portal-root div.bg-white button"),
            By.cssSelector("button.OnboardingSheetLottie_OnboardingSheetInternationalButton__CUHff"),
            By.xpath("//*[@id='portal-root']//button")
        };

        for (By pLoc : popupLocators) {
            try {
                WebElement btn = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.presenceOfElementLocated(pLoc));

                try {
                    WebElement clickable = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(2))
                            .until(ExpectedConditions.elementToBeClickable(pLoc));
                    clickable.click();
                    reporter.generateReport(driver, extTest, Status.INFO,
                            "Popup dismissed using locator: " + pLoc);
                    return;
                } catch (Exception clickEx) {
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                        reporter.generateReport(driver, extTest, Status.INFO,
                                "Popup JS-clicked using locator: " + pLoc);
                        return;
                    } catch (Exception jsEx) {
                        try {
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].style.opacity = 1; " +
                                    "arguments[0].style.pointerEvents='auto'; " +
                                    "arguments[0].style.visibility='visible';", btn);
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                            reporter.generateReport(driver, extTest, Status.INFO,
                                    "Popup forced-visible + clicked for locator: " + pLoc);
                            return;
                        } catch (Exception forceEx) {
                            // try next locator
                        }
                    }
                }
            } catch (Exception ignore) {
                // locator not present -> try next
            }
        }
        reporter.generateReport(driver, extTest, Status.INFO,
                "Price-lock popup not found or could not be dismissed");
    }

    /** Apply Departure filter 06:00-12:00 (MORNING) */
    public boolean applyDepartureMorning() {
        closePriceLockPopupIfPresent();
        try {
            By departureCheckbox = By.xpath("//input[@name='takeOff' and @value='MORNING']");
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(departureCheckbox));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            reporter.generateReport(driver, extTest, Status.PASS,
                    "Departure filter applied: 06:00-12:00 (MORNING)");
            return true;
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Could not apply Departure (MORNING) filter: " + e.getMessage());
            return false;
        }
    }

    /** Apply Arrival filter 06:00-12:00 (MORNING) */
    public boolean applyArrivalMorning() {
        closePriceLockPopupIfPresent();
        try {
            By arrivalCheckbox = By.xpath("//input[@name='landing' and @value='MORNING']");
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(arrivalCheckbox));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            reporter.generateReport(driver, extTest, Status.PASS,
                    "Arrival filter applied: 06:00-12:00 (MORNING)");
            return true;
        } catch (Exception e) {
            reporter.generateReport(driver, extTest, Status.FAIL,
                    "Could not apply Arrival (MORNING) filter: " + e.getMessage());
            return false;
        }
    }

    /** Select the first available flight */
    public boolean selectFirstAvailableFlight() {
        closePriceLockPopupIfPresent();

        By[] candidateXPaths = new By[]{
            By.xpath("(//button[contains(.,'Select') or contains(.,'Book') or contains(.,'Continue')])[1]"),
            By.xpath("(//a[contains(.,'Select') or contains(.,'Book')])[1]"),
            By.xpath("(//button[contains(@data-testid,'select') or contains(@class,'select')])[1]"),
            By.xpath("(//div[contains(@class,'FlightCard') or contains(@class,'result-card')])[1]//button")
        };

        for (By cand : candidateXPaths) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(cand));
                el.click();
                reporter.generateReport(driver, extTest, Status.PASS, "Clicked first available flight");
                return true;
            } catch (Exception ignore) {
                // try next candidate
            }
        }

        try {
            WebElement results = wait.until(ExpectedConditions.visibilityOfElementLocated(locators.resultsContainer));
            WebElement btn = results.findElement(By.xpath(".//button[.//text() or @aria-label][1]"));
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
            reporter.generateReport(driver, extTest, Status.PASS,
                    "Clicked first available flight (fallback)");
            return true;
        } catch (Exception ignore) {
        }

        reporter.generateReport(driver, extTest, Status.FAIL,
                "Failed to select first available flight");
        return false;
    }
}
