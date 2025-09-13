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
    public void clickFestivals() {
        driver.findElement(By.cssSelector("body > div.min-h-full.flex.flex-col.page_container__oILIU > div.max-w-screen-2xl.flex.mx-auto.grow.w-full.page_exploreBody__sc0QV > div > div.flex.gap-40.overflow-x-auto.px-20.xl\\:px-30.border-b.border-secondary.xl\\:border-none.no-scrollbar > div:nth-child(6)")).click();
        System.out.println("Clicked Festivals.");
    }
}
