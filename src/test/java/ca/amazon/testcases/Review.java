package ca.amazon.testcases;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

public class Review {

	public static WebDriver driver;


	// 1. Launch the browser and navigate to the Amazon website.
	@BeforeClass(alwaysRun = true)
	public static WebDriver invokeBrowser() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Singh\\Desktop\\Driver\\geckodriver.exe");
		driver = new FirefoxDriver();
		WebDriverWait expWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		expWait.pollingEvery(Duration.ofSeconds(2));
		driver.get("https://www.amazon.ca/");
		driver.navigate().refresh();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}

	
}
