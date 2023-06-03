package ca.amazon.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReviewTest {
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;

	public static void main(String[] args) {
		setUp();
		login();
	}
	public static void setUp() {
		if(driver==null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				//log.debug("Configuration file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				//log.debug("OR file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(config.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(config.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(config.getProperty("browser").equals("ie")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\resources\\executables\\IEDriverServer.exe");
		}
		else {
			System.out.print("Failed Configuration");
		}
		driver.get(config.getProperty("testsiteUrl"));
		driver.navigate().refresh();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		}
	}
	

	public static WebDriver invokeBrowser() {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\resources\\executables\\geckodriver.exe");
		driver = new FirefoxDriver();
		WebDriverWait expWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		expWait.pollingEvery(Duration.ofSeconds(2));
		driver.get("https://www.amazon.ca/");
		driver.navigate().refresh();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}
	public static void login() {
		driver.findElement(By.xpath("//span[normalize-space()='Account & Lists']")).click();
		driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys("ENTER_YOUR_EMAIL_HERE");
		driver.findElement(By.xpath("//input[@id='continue']")).click();
		driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys("ENTER_YOUR_PASSWORD_HERE");
		driver.findElement(By.xpath("//input[@id='signInSubmit']")).click();
	}
}
