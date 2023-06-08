package ca.amazon.login;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTest {
	private WebDriver driver;

	public void slowDownExec() {
		WebDriverWait expWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		expWait.pollingEvery(Duration.ofSeconds(2));
		
	}
	@BeforeMethod
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver",
				System.getProperty("user.dir") + "\\resources\\executables\\geckodriver.exe");
		driver = new FirefoxDriver();
		slowDownExec();
		driver.get("https://www.amazon.ca/");
		driver.navigate().refresh();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		}

	
	public void searchProductFullText() {
		// Enter "laptop" into the search input field
		driver.get("https://www.amazon.ca");
		WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
		searchInput.sendKeys("laptop");

		// Click the search button
		WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
		searchButton.click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500)");

		// Verify that the search results page is displayed
		Assert.assertTrue(driver.getTitle().contains("laptop"), "Search results page is not displayed.");

		// Verify that the search results contain at least one item
		WebElement firstSearchResult = driver.findElement(By.cssSelector("[data-index='1']"));
		Assert.assertTrue(firstSearchResult.isDisplayed(), "Search results do not contain any items.");
	}

	@Test(priority = 1)
	public void searchProductPartialText() throws InterruptedException {
		driver.get("https://www.amazon.ca");
		WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
		
		// Enter partial text "lap"
		searchInput.sendKeys("lap");
		Thread.sleep(3000);

		// Select first result on dropdown list
		searchInput.sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		searchInput.sendKeys(Keys.ENTER);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500)");
		
		 List<WebElement> allInputElements = driver.findElements(By.xpath("//input[@id='twotabsearchtextbox']"));
		 int inputCount =allInputElements.size();
		   for(int i=0;i<inputCount;i++) {
			   WebElement eachItem =allInputElements.get(i);
			   System.out.println(eachItem.getText());
			   //Assert.assertTrue(allInputElements.get(i).getText().contains(searchTerm),"Search result validation failed at instance [ + i + ].");
		   }
		 
		// Verify that the search results page is displayed
		Assert.fail("Search results page is not displayed.");

		// Verify that the search results contain at least one item
		WebElement firstSearchResult = driver.findElement(By.cssSelector("[data-index='1']"));
		Assert.assertTrue(firstSearchResult.isDisplayed(), "Search results do not contain any items.");
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		// Clean up and close the browser
		Thread.sleep(3000);
		driver.quit();
	}
}
