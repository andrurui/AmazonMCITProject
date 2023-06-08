package ca.amazon.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CustomerService {
	public static WebDriver driver;
	@BeforeMethod(alwaysRun=true)
	public void CustomerServiceSetUp() {
		Base.setUp();
	}
	@Test(priority=1)
	public void CustomerServiceLogin() throws InterruptedException {
		Base.login();
	}
	@Test(priority=2)
	public void CustomerServiceUserAccount() throws InterruptedException {
		Base.userAccount();
	}
	@Test(priority=3)
	public void CustomerServiceorderProfile() throws InterruptedException {
		Base.orderProfile();
	}
	@Test(priority=4)
	public void CustomerServiceSelectOrder() throws InterruptedException {
		Base.selectOrder();
	}
	@Test(priority = 5)
	public static void CloseBrowser() throws InterruptedException {
		Base.closeBrowser();
	}


	
	
	

}
