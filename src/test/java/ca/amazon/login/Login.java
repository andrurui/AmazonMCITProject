package ca.amazon.login;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {
	public WebDriver driver;

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
	
	@Test(priority = 1)
	public void login() throws InterruptedException {
		System.out.println("Please enter login information");
		// Click on "Account & Lists"
		driver.findElement(By.xpath("//span[normalize-space()='Account & Lists']")).click();
		// Fill in the login form
		driver.findElement(By.id("ap_email")).sendKeys("mcitproj2023@gmail.com");
		driver.findElement(By.id("continue")).click();
		driver.findElement(By.id("ap_password")).sendKeys("Mcit@2023");
		driver.findElement(By.id("signInSubmit")).click();

		// Check if login is successful
		String expectedTitle = "Amazon.ca: Online Shopping";
		String actualTitle = driver.getTitle();
		boolean enterCharacters = driver.getPageSource().contains("enter the characters");

		if (enterCharacters) {
			System.out.println("Please follow login process manually for security reasons");
		} else if (actualTitle.equals(expectedTitle)) {
			System.out.println("Login Successful");
		} else {
			System.out.println("Login Failed");
		}
	}
	@AfterMethod
	public void tearDown() throws InterruptedException {
		// Wait for 5 seconds before closing the browser
		Thread.sleep(3500);
		driver.quit();
	}

}
