package ca.amazon.testcases;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReviewTest {
	@BeforeMethod(alwaysRun=true)
	public void ReviewSetUp() {
		Base.setUp();
	}
	@Test(priority=2)
	public void ReviewLogin() throws InterruptedException {
		Base.login();
	}
	@Test(priority=3)
	public void ReviewSearchProduct() {
		Base.searchProduct();
	}
	@Test(priority=4)
	public void ReviewSubmit() {
		Base.submit();
	}
	@Test(priority=5)
	public void ReviewScrollDown() throws InterruptedException {
		Base.scrollDown();
	}
	@Test(priority=6)
	public void ReviewCheckReview() throws InterruptedException {
		Base.checkReview();
	}
	@Test(priority=7)
	public void ReviewPage() throws InterruptedException {
		Base.reviewPage();
	}
	@Test(priority = 8)
	public static void Close_Browser() throws InterruptedException {
		Base.closeBrowser();
	}
	
}
