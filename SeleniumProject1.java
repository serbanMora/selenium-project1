package MySeleniumProjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SeleniumProject1 extends BaseTest{

	public static void main(String[] args) throws InterruptedException {

		BaseTest.setUP();
 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		//click on Login button without completing fields and assert that error message is displayed
		PageObject.loginButton().click();
		wait.until(ExpectedConditions.visibilityOf(PageObject.loginError()));
		Assert.assertEquals(PageObject.loginError().getText(), "Epic sadface: Username is required");
		PageObject.credentials(0).sendKeys("standard_user");
		PageObject.loginButton().click();
		wait.until(ExpectedConditions.visibilityOf(PageObject.loginError()));
		Assert.assertEquals(PageObject.loginError().getText(), "Epic sadface: Password is required");
		
		PageObject.credentials(1).sendKeys("invalidPass");
		PageObject.loginButton().click();
		Assert.assertEquals(PageObject.loginError().getText(), "Epic sadface: Username and password do not match any user in this service");
		PageObject.credentials(1).clear();
		PageObject.credentials(1).sendKeys("secret_sauce");
		
		//assert using JavascriptExecutor that Login text is correct
		Assert.assertEquals(PageObject.jsExecutorGetText(PageObject.loginButton()), "Login");
		
		PageObject.loginButton().click();

		//after login with valid credentials, get page title and assert that link count is equal to 20
		Assert.assertEquals(driver.getTitle(), "Swag Labs");
		Assert.assertEquals(driver.findElements(By.tagName("a")).size(), 20);

		//set the price order from low-high or high-low and assert that products are ordered correctly
		PageObject.priceOrderValidator("hilo");
		
		//validate that add to cart is diplayed on buttons
		PageObject.buttonTextValidation("addToCart");
		
		//click on add to cart button for items based on String[] itemNames() method
		PageObject.addItems(PageObject.itemNames());
		
		//validate that remove is displayed on buttons
		PageObject.buttonTextValidation("remove");

		//asserting that the cart number is displaying the correct number of items are added to cart
		PageObject.cartIconNumberAssertion();
		
		//opening social links from footer and iterates through them, closing tabs except main tab
		PageObject.closeAllTabsExceptMain();
		
		PageObject.cart().click();
		
		PageObject.checkout().click();

		//click on Continue button without completing any field and assert that error message is displayed
		PageObject.continueBtn().click();
		Assert.assertEquals(PageObject.loginError().getText(), "Error: First Name is required");
		PageObject.credentials(0).sendKeys("test1");
		PageObject.continueBtn().click();
		Assert.assertEquals(PageObject.loginError().getText(), "Error: Last Name is required");
		PageObject.credentials(1).sendKeys("test2");
		PageObject.continueBtn().click();
		Assert.assertEquals(PageObject.loginError().getText(), "Error: Postal Code is required");
		PageObject.credentials(2).sendKeys("1234");
		PageObject.continueBtn().click();

		//validate that all products appear at checkout
		PageObject.checkoutProductsValidation();
		
		//validate that products total price is correct at checkout
		PageObject.checkoutPriceValidation();
		
		//click finish button and validate that the send order message is displayed
		PageObject.scrollBy("1200");
		PageObject.finishBtn().click();
		Assert.assertEquals(PageObject.completeHeader().getText(), "Thank you for your order!");
		Assert.assertEquals(PageObject.completeText().getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
		
		BaseTest.tearDown();
	}
}