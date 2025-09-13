package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class planpage {

    WebDriver driver;

    public planpage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By popupClose = By.xpath("//div[contains(@class,'ixiIcon-cross') or @aria-label='close']");
    private By planIcon = By.xpath("/html/body/main/div[2]/div[2]/div/div/div/div[13]/a/span");
    private By manaliCard = By.xpath("/html/body/div[1]/div[2]/div/div[2]/a[1]/div[1]/div");

    // Handle popup if present
    public void handlePopup() {
        try {
            WebElement popup = driver.findElement(popupClose);
            if (popup.isDisplayed()) {
                popup.click();
                System.out.println("Popup closed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("No popup found.");
        }
    }

    // Click the Plan icon
    public void clickPlanIcon() {
        handlePopup();
        driver.findElement(planIcon).click();
        System.out.println("Clicked Plan icon.");
    }

    // Click the Manali card
    public void clickManali() {
        driver.findElement(manaliCard).click();
        System.out.println("Clicked Manali card.");
    }
}
