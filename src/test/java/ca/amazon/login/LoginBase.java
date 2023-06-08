package ca.amazon.login;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginBase {
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
	public void register() throws InterruptedException {
		slowDownExec();
		// Click on "Account & Lists"
		driver.findElement(By.xpath("//span[normalize-space()='Account & Lists']")).click();
		// Click on "Create your Amazon account"
		driver.findElement(By.id("createAccountSubmit")).click();
		// Fill in the registration form
		driver.findElement(By.id("ap_customer_name")).sendKeys("Mcit 2023");
		driver.findElement(By.id("ap_email")).sendKeys("mcitproj2023@gmail.com");
		driver.findElement(By.id("ap_password")).sendKeys("Mcit@2023");
		driver.findElement(By.id("ap_password_check")).sendKeys("Mcit@2023");
		// Click on the "Create your Amazon account" button
		driver.findElement(By.id("continue")).click();

		// Check if "E-mail address already in use" message is present
		boolean emailAlreadyInUse = driver.getPageSource().contains("E-mail address already in use");

		if (emailAlreadyInUse) {
			System.out.println("Registration failed - Email already in use");
		} else {
			System.out.println("Continue the verification manually");
		}
	}

	

	@AfterMethod
	public void tearDown() throws InterruptedException {
		// Wait for 5 seconds before closing the browser
		Thread.sleep(3500);
		driver.quit();
	}
}
