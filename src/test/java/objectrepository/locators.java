package objectrepository;

import org.openqa.selenium.By;

public class locators {
	public static By loginbutton = By.xpath("//button[text()='Log in/Sign up']");
	public static By mobile = By.xpath("//input[@placeholder='Enter Mobile Number']");
	public static By continuebutton = By.xpath("//button[text()='Continue']");
	public static By otpInputs = By.xpath("//input[@type='tel' or @inputmode='numeric']");
	public static By verify = By.xpath("//*[text()='Verify' or normalize-space()='Verify']");
	public static By flight = By.xpath("//a[@href='/flights']");
	public static By round = By.xpath("//button[text()='Round Trip']");
	public static By from = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div");
	public static By click_from = By
			.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input");
	public static By to = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div");
	public static By click_to = By
			.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/input");
	public static By searchButton = By.xpath("//button[normalize-space()='Search' or contains(@class,'search')]");
	public static By travellersPanel = By.xpath("//p[text()='1 Traveller,  Economy']");
	// public static By adultsPlusBtn = By.xpath("//button[text()='"+adults+"']");
	public static By childrenPlusBtn = By.xpath("(//button[contains(.,'+')])[2]");
	public static By infantsPlusBtn = By.xpath("(//button[contains(.,'+')])[3]");
	public static By travellersApplyBtn = By.xpath("//button[normalize-space()='Apply' or normalize-space()='Done']");

	public static By travelClassDropdown = By
			.xpath("//div[contains(@class,'cabin') or contains(.,'Class') or contains(@data-testid,'cabin')]");
	// results container generic
	public static By resultsContainer = By.xpath("//p[text()='Filters']");
	public static By dept = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[2]/div/div");
	public static By ret = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/div/div/div");

	// selecting date
	public static By monthYearHeader = By.xpath("//div[contains(@class,'DayPicker-Caption')]");
	public static By calendarNextBtn = By.xpath("//span[@aria-label='Next Month']");
	public static By calendarPrevBtn = By.xpath("//span[@aria-label='Previous Month']");

	public static By dayCell(String day) {
		return By.xpath("//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))]//p[text()='" + day
				+ "']");
	}

	public static By dayBlock(String day) {
		return By.xpath("//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))]//p[text()='" + day
				+ "']/..");
	}

	public static By dayPrice(String day) {
		return By.xpath("//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))]//p[text()='" + day
				+ "']/following-sibling::p");
	}

	public static By yearLabel = By.xpath("//div[contains(@class,'DayPicker-Caption')]");

	// ----------------------------------------------------

	public static By travelClassOption(String cls) {
		String xpath = String.format(
				"//li[normalize-space(.)='%s'] | " + "//div[contains(@class,'option') and normalize-space(.)='%s'] | "
						+ "//button[normalize-space(.)='%s']",
				cls, cls, cls);
		return By.xpath(xpath);
	}

	// offer page --------------------------------------------------------

	public static By offer = By.xpath("//p[text()='Offers']");
	public static By more = By.xpath("//p[text()='More']");
	public static By plan = By.xpath("//p[text()='Plan']");
	public static By trip = By.cssSelector(
			"body > div.min-h-full.flex.flex-col.page_container__oILIU > div.max-w-screen-2xl.flex.mx-auto.grow.w-full.page_exploreBody__sc0QV > div > div.flex.gap-40.overflow-x-auto.px-20.xl\\:px-30.border-b.border-secondary.xl\\:border-none.no-scrollbar > div:nth-child(4) > svg > use");
	public static By dropdown = By.xpath("//p[text()='From']");
	public static By search_locate = By.xpath("//*[@id=\"portal-root\"]/div/div/div[2]/div/div[2]/input");
	public static By first_search = By.xpath("//*[@id=\"portal-root\"]/div/div/div[3]/a/div/div[1]");
	public static By search_where = By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]");
	public static By loca = By.xpath("//div[contains(@class, 'overflow-auto')]/a[1]");
	public static By view_all = By.xpath("//*[@id=\"places-to-stay\"]/div[1]/div/a");
	public static By hotel = By.xpath("//a[text()='Places to Stay']");
	// public static By price=By.xpath("//div[text()='"+price+"']");
	public static By book_now = By.xpath("//button[text()='Book Now']");

}
