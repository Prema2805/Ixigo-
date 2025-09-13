package setup;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import parameters.propertyreader;

public class Base {

	public static WebDriver driver;

	public void launchBrowser() {
		Properties prop = propertyreader.readProperties();
		String browser = prop.getProperty("Browser");
		String url = prop.getProperty("URL");

		if ("Chrome".equalsIgnoreCase(browser)) {
			ChromeOptions chromeOptions = new ChromeOptions();
			Map<String, Object> chromePrefs = new HashMap<>();
			chromePrefs.put("credentials_enable_service", false);
			chromePrefs.put("profile.password_manager_enabled", false);
			chromePrefs.put("profile.password_manager_leak_detection", false);
			chromeOptions.setExperimentalOption("prefs", chromePrefs);
			driver = new ChromeDriver(chromeOptions);
		} else if ("Firefox".equalsIgnoreCase(browser)) {
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.get(url);

		// ✅ Close popup iframe if it appears
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("wiz-iframe-intent")));
			// Inside iframe now
			driver.findElement(By.id("closeButton")).click();
			driver.switchTo().defaultContent();
			System.out.println("Popup closed successfully");
		} catch (Exception e) {
			System.out.println("No popup found, continuing...");
		}
	}

	public static void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
