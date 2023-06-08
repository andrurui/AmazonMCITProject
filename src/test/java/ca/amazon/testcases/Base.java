package ca.amazon.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Base {
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");

	@BeforeMethod
	public static void setUp() {
		if (driver == null) {
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Configuration file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				log.debug("Firefox Launced!!!");
			} else if (config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (config.getProperty("browser").equals("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\resources\\executables\\IEDriverServer.exe");
			} else {
				System.out.print("Failed Configuration");
			}
			// Navigate to the Amazon website.
			driver.get(config.getProperty("testsiteUrl"));
			log.debug("Navigated to : " + config.getProperty("testsiteUrl"));
			driver.navigate().refresh();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void login() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty("signIn")))).click();
		WebElement email = driver.findElement(By.xpath(OR.getProperty("email")));
		Thread.sleep(3000);
		Boolean isEmailBoxPresent = email.isDisplayed();
		if (isEmailBoxPresent) {
			email.sendKeys(config.getProperty("EMAIL_HERE"));
			log.debug("Email text box is present");
		} else {
			Assert.fail("No email text box is present in the webpage");
		}

		driver.findElement(By.xpath(OR.getProperty("emailNxtBtn"))).click();
		WebElement password = driver.findElement(By.xpath(OR.getProperty("pwd")));
		Thread.sleep(3000);
		Boolean isPasswordBoxPresent = password.isDisplayed();
		if (isPasswordBoxPresent) {
			password.sendKeys(config.getProperty("PASSWORD_HERE"));
			log.debug("Password box is present");
		} else {
			Assert.fail("Password text box is not present in the webpage");
		}

		driver.findElement(By.xpath(OR.getProperty("signInBtn"))).click();
	}

	// Search for a product of your choice and click on its product listing.
	public static void searchProduct() {
		WebElement search;
		search = driver.findElement(By.id(OR.getProperty("searchBar")));
		Boolean isSearchBoxDisplayed = search.isDisplayed();
		if (isSearchBoxDisplayed) {
			log.debug("The search box is displayed");
			search.sendKeys("iPhone");
			log.debug("Search task is successfully completed");
		} else {
			log.debug("The search box is not displayed");
		}
	}

	public static void submit() {
		WebElement submitBtn;
		submitBtn = driver.findElement(By.id(OR.getProperty("submitBtn")));
		if (submitBtn.isDisplayed()) {
			submitBtn.click();
			log.debug("Submit Button clicked");
		} else {
			log.debug("Submit Button is not available on the webpage");
		}

	}

	// Scroll down to the product details section.
	public static void scrollDown() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(3000);
		// Locating the product
		WebElement Element = driver.findElement(By.xpath(OR.getProperty("elementFound")));
		if (Element.isDisplayed()) {
			log.debug("Product found on the webpage");
			// Scrolling down the page till the element is found
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Thread.sleep(3000);
			js.executeScript("arguments[0].scrollIntoView();", Element);
			Element.click();
		} else {
			log.debug("Product is not found on the webpage");
		}
	}

	public static void checkReview() throws InterruptedException {
		WebElement locateRating = driver.findElement(By.xpath(OR.getProperty("clickRatingBtn")));
		if (locateRating.isDisplayed()) {
			locateRating.click();
			log.debug("Element Located on the webpage");
			Thread.sleep(3000);
		} else {
			log.debug("Please try again. Element is not located");
		}

		WebElement clickRating = driver.findElement(By.linkText("5 star"));
		clickRating.click();
		log.debug("Top Review clicked");
		Thread.sleep(3000);
		// Verify that the reviews section is visible
		WebElement reviewClickBtn = driver.findElement(By.xpath(OR.getProperty("reviewSection")));
		if (reviewClickBtn.isDisplayed()) {
			reviewClickBtn.click();

			WebElement cleanStar = driver.findElement(By.xpath(OR.getProperty("clearFiveStar")));
			if (cleanStar.isDisplayed()) {
				cleanStar.click();
			}

			driver.findElement(By.xpath(OR.getProperty("clickFiveStar"))).click();

			WebElement headline = driver.findElement(By.xpath(OR.getProperty("headLineText")));
			headline.clear();
			Thread.sleep(3000);
			headline.sendKeys(OR.getProperty("titleContent"));

			WebElement textBox = driver.findElement(By.xpath(OR.getProperty("locateTextBox")));
			textBox.clear();
			Thread.sleep(5000);
			textBox.sendKeys(OR.getProperty("text"));
			// driver.findElement(By.xpath(OR.getProperty("ReviewSubmitBtn"))).click();
		} else {
			log.debug("Try again to load review section");
		}
	}

	public static void reviewPage() throws InterruptedException {
		driver.get(config.getProperty("siteURL"));
		WebElement reviewPageSearchBar = driver.findElement(By.id(OR.getProperty("reviewSearchBar")));
		if (reviewPageSearchBar.isDisplayed()) {
			reviewPageSearchBar.sendKeys(OR.getProperty("titleContent"));
			Thread.sleep(5000);
			driver.findElement(By.xpath(OR.getProperty("reviewSearchBtn"))).click();
			Thread.sleep(3000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement locateReviewText = driver.findElement(By.xpath(OR.getProperty("locateReview")));
			if (locateReviewText.isDisplayed()) {
				js.executeScript("arguments[0].scrollIntoView();", locateReviewText);
				locateReviewText.click();
				log.debug("Review is located");
			} else {
				log.debug("Review is not located");
			}
		} else {
			log.debug("Review page search bar is not located");
		}

	}

	public static void userAccount() throws InterruptedException {
		WebElement userProfile = driver.findElement(By.xpath(OR.getProperty("signIn")));
		Thread.sleep(5000);
		if (userProfile.isDisplayed()) {
			userProfile.click();
		} else {
			log.debug("Link is not available on user profile");
		}

	}

	public static void orderProfile() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement userOrders = driver.findElement(By.xpath(OR.getProperty("userOrders")));
		Thread.sleep(5000);
		if (userOrders.isDisplayed()) {
			userOrders.click();
		} else {
			System.out.println("Link is not available on order profile");
		}
	}

	public static void selectOrder() throws InterruptedException {
		WebElement selectOrder = driver.findElement(By.xpath(OR.getProperty("selectOrd")));
		Thread.sleep(5000);
		if (selectOrder.isDisplayed()) {
			selectOrder.click();
			log.debug("Placed Order Found");
		} else {
			log.debug("Not able to find the placed order");
		}
		Thread.sleep(2000);
		WebElement searchOrder = driver.findElement(By.xpath(OR.getProperty("searchOrd")));
		searchOrder.sendKeys("kinganda");
		searchOrder.sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		WebElement placedOrder = driver.findElement(By.xpath(OR.getProperty("selectPlacedOrder")));
		if (placedOrder.isDisplayed()) {
			placedOrder.click();
		} else {
			log.debug("Order not found");
		}
		Thread.sleep(3000);
		WebElement checkStatus = driver.findElement(By.xpath(OR.getProperty("orderStatus")));
		if (checkStatus.isDisplayed()) {
			checkStatus.click();
		} else {
			log.debug("Not able to find order status");
		}
		Thread.sleep(3000);
		WebElement needMoreHelp = driver.findElement(By.xpath(OR.getProperty("moreHelp")));
		if (needMoreHelp.isDisplayed()) {
			Actions action = new Actions(driver);
			action.moveToElement(needMoreHelp).click();
			needMoreHelp.click();
		} else {
			log.debug("Package Tracker not clicked");
		}
		Thread.sleep(3000);

		WebElement requestCall = driver.findElement(By.xpath(OR.getProperty("callRequest")));
		if (requestCall.isDisplayed()) {
			requestCall.click();
			log.debug("Phone call button found");
		} else {
			log.debug("not able to find phone call button");
		}
		Thread.sleep(3000);
		WebElement number_Phone = driver.findElement(By.xpath(OR.getProperty("phoneNumber")));
		number_Phone.sendKeys("1234567890");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty("callMeBtn")))).click();

	}

	public static void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

	}

	public static void customerService() {
		driver.findElement(By.partialLinkText("Customer Service")).click();

	}
	public static void Recommendation_Product() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement ele = driver.findElement(By.xpath("//span[normalize-space()='Account & Lists']"));

		//Creating object of an Actions class
		Actions action = new Actions(driver);

		//Performing the mouse hover action on the target element.
		action.moveToElement(ele).perform();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[normalize-space()='Your Recommendations']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[@class='nav-a-content'][normalize-space()='Your Browsing History']")).click();
	}
	
	public static void scrollDownToElem() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(5000);
		// Locating the product
		WebElement Element = driver.findElement(
				By.xpath("//div[normalize-space()='Apple iPhone 12, 64GB, Blue - Fully Unlocked (Renewed)']"));
		log.debug("Product found");
		// Scrolling down the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
		Element.click();
	}
	/*
	 * 5. Write code to interact with the product page elements, such as selecting a
	 * size or color, adding the product to the cart, and checking out.
	 */
	
	public static void selectProduct() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement list = driver.findElement(By.xpath("//ul[@class='a-unordered-list a-nostyle a-button-list a-declarative a-button-toggle-group a-horizontal a-spacing-top-micro swatches swatchesSquare']"));
		log.debug("Product Selected");
		Thread.sleep(3000);
		List<WebElement> items = list.findElements(By.tagName("li"));
		WebElement desiredItem = null;
		
		for (int i = 0; i < items.size(); i++) {
			if (i == 1) {
				desiredItem = items.get(i);
				break;
			}
		}
		desiredItem.click();
		log.debug("Item clicked");	
	}
	public static void selectQtn() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(3000);
		WebElement addToBtn = driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
		addToBtn.click();
		Thread.sleep(3000);
		WebElement clnBtn = driver.findElement(By.xpath("//input[@aria-labelledby='attachSiNoCoverage-announce']"));
		clnBtn.click();
		log.debug("Add to cart and cancel protection plan");
	}
	public static void checkCart() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(3000);
		WebElement chkCart = driver.findElement(By.xpath("//a[@href='/cart?ref_=sw_gtc']"));
		chkCart.click();
		Thread.sleep(3000);
		WebElement checkQtn = driver.findElement(By.id("quantity"));
		Select quantity = new Select(checkQtn);
		if (quantity.getOptions().isEmpty()) {
			log.debug("Quantity dropdown is empty.");
		} else {
			WebElement checkout = driver.findElement(By.name("proceedToRetailCheckout"));
			checkout.click();
		}
	}
	@AfterMethod
	public static void closeBrowser() throws InterruptedException {
		Thread.sleep(5000);
		if (driver != null) {
			driver.close();
		}
		log.debug("Test Execution completed !!!");
	}
	
}
