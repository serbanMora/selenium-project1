package MySeleniumProjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestExecution extends BaseTest {
	
	static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	
	@Test
	public void TC1() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageObject.loginButton().click();
		wait.until(ExpectedConditions.visibilityOf(PageObject.loginError()));
		Assert.assertEquals(PageObject.loginError().getText(), "Epic sadface: Username is required");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC1")
	public void TC2() {
		PageObject.credentials(0).sendKeys("standard_user");
		PageObject.loginButton().click();
		wait.until(ExpectedConditions.visibilityOf(PageObject.loginError()));
		Assert.assertEquals(PageObject.loginError().getText(), "Epic sadface: Password is required");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC2")
	public void TC3() {
		PageObject.credentials(1).sendKeys("invalidPass");
		PageObject.loginButton().click();
		Assert.assertEquals(PageObject.loginError().getText(), "Epic sadface: Username and password do not match any user in this service");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC3")
	public void TC4() {
		PageObject.credentials(1).clear();
		PageObject.credentials(1).sendKeys("secret_sauce");
		Assert.assertEquals(PageObject.jsExecutorGetText(PageObject.loginButton()), "Login");
		PageObject.loginButton().click();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC4")
	public void TC5() {
		Assert.assertEquals(driver.getTitle(), "Swag Labs");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC5")
	public void TC6() {
		Assert.assertEquals(driver.findElements(By.tagName("a")).size(), 20);
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC6")
	public void TC7() {
		PageObject.priceOrderValidation("hilo");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC7")
	public void TC8() {
		PageObject.nameOrderValidation("za");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC8")
	public void TC9() {
		PageObject.buttonTextValidation("addToCart");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC9")
	public void TC10() {
		PageObject.addItems(PageObject.itemNames());
		PageObject.buttonTextValidation("remove");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC10")
	public void TC11() {
		PageObject.cartIconNumberAssertion();
	}
	
	@Test  (alwaysRun = true, dependsOnMethods = "TC11")
	public void TC12() {
		PageObject.closeAllTabsExceptMain();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC12")
	public void TC13() {
		PageObject.cart().click();
		PageObject.checkout().click();
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
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC13")
	public void TC14() {
		PageObject.checkoutProductsValidation();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC14")
	public void TC15() {
		PageObject.checkoutPriceValidation();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC15")
	public void TC16() {
		PageObject.scrollBy("1200");
		PageObject.finishBtn().click();
		Assert.assertEquals(PageObject.completeHeader().getText(), "Thank you for your order!");
		Assert.assertEquals(PageObject.completeText().getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
	}
}