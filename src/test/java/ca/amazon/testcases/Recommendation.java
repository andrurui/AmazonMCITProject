package ca.amazon.testcases;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Recommendation {

	@BeforeMethod(alwaysRun = true)
	public void RecommendationSetUp() {
		Base.setUp();
	}

	@Test(priority = 2)
	public static void RecommendationLogin() throws InterruptedException {
		Base.login();
	}

	@Test(priority = 3)
	public static void RecommendationProduct() throws InterruptedException {
		Base.Recommendation_Product();
	}

	@Test(priority = 4)
	public static void RecommendationScrollDown() throws InterruptedException {
		Base.scrollDownToElem();
	}

	@Test(priority = 5)
	public static void RecommendedSelectProduct() throws InterruptedException {
		Base.selectProduct();
	}

	@Test(priority = 6)
	public static void RecommendedSelectQtn() throws InterruptedException {
		Base.selectQtn();
	}

	@Test(priority = 7)
	public static void RecommendedSelectionCart() throws InterruptedException {
		Base.checkCart();
	}

	@Test(priority = 8)
	public static void Close_Browser() throws InterruptedException {
		Base.closeBrowser();
	}

}
