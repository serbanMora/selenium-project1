package TestCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Project2.Config.BaseTest;
import pageObject.CartPage;
import pageObject.LoginPage;
import pageObject.ProductCatalog;

public class TestExecution extends BaseTest {
	
	LoginPage login;
	ProductCatalog productCatalog;
	CartPage cartPage;
	
	String userName = "standard_user";
	String password = "secret_sauce";
	
	@Test (alwaysRun = true, enabled = true)
	public void TC1() throws IOException {
		login = new LoginPage(driver);
		login.loginButton().click();
		explicitWait(login.loginError(), "visibility", 3);
		Assert.assertEquals(login.loginError().getText(), "Epic sadface: Username is required");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC1", enabled = true)
	public void TC2() {
		login = new LoginPage(driver);
		login.setName("standard_user");
		login.loginButton().click();
		explicitWait(login.loginError(), "visibility", 3);
		Assert.assertEquals(login.loginError().getText(), "Epic sadface: Password is required");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC2", enabled = true)
	public void TC3() {
		login = new LoginPage(driver);
		login.setPassword("invalidPass");
		login.loginButton().click();
		explicitWait(login.loginError(), "visibility", 3);
		Assert.assertEquals(login.loginError().getText(), "Epic sadface: Username and password do not match any user in this service");
		login.clearField("userName");
		login.clearField("password");
		driver.navigate().refresh();
	}
	
	@Test (dataProvider = "getData", alwaysRun = true, dependsOnMethods = "TC3", enabled = true)
	public void TC4(String userName, String password) {
		login = new LoginPage(driver);
		login.setName(userName);
		login.setPassword(password);
		Assert.assertEquals(jsExecutorGetText(login.loginButton()), "Login");
		login.clickLogIn();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC4", enabled = true)
	public void TC5() {
		Assert.assertEquals(driver.getTitle(), "Swag Labs");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC5", enabled = true)
	public void TC6() {
		Assert.assertEquals(driver.findElements(By.tagName("a")).size(), 20);
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC6", enabled = true)
	public void TC7() {
		productCatalog = new ProductCatalog(driver);
		productCatalog.priceOrderValidation("hilo");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC7", enabled = true)
	public void TC8() {
		productCatalog = new ProductCatalog(driver);
		productCatalog.nameOrderValidation("za");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC8", enabled = true)
	public void TC9() {
		productCatalog = new ProductCatalog(driver);
		productCatalog.buttonTextValidation("addToCart");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC9", enabled = true)
	public void TC10() {
		productCatalog = new ProductCatalog(driver);
		productCatalog.addItems(ProductCatalog.itemNames());
		productCatalog.buttonTextValidation("remove");
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC10", enabled = true)
	public void TC11() {
		productCatalog = new ProductCatalog(driver);
		productCatalog.cartIconNumberAssertion();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC11", enabled = true)
	public void TC12() {
		productCatalog = new ProductCatalog(driver);
		productCatalog.closeAllTabsExceptMain();
		productCatalog.clickCart();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC12", enabled = true)
	public void TC13() {
		cartPage = new CartPage(driver);
		cartPage.checkout().click();
		cartPage.continueButton().click();
		Assert.assertEquals(cartPage.loginError().getText(), "Error: First Name is required");
		cartPage.completeForm("firstName", "test1");
		cartPage.continueButton().click();
		Assert.assertEquals(cartPage.loginError().getText(), "Error: Last Name is required");
		cartPage.completeForm("lastName", "test2");
		cartPage.continueButton().click();
		Assert.assertEquals(cartPage.loginError().getText(), "Error: Postal Code is required");
		cartPage.completeForm("zip", "test3");
		cartPage.continueButton().click();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC13", enabled = true)
	public void TC14() {
		cartPage = new CartPage(driver);
		cartPage.checkoutProductsValidation();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC14", enabled = true)
	public void TC15() {
		cartPage = new CartPage(driver);
		cartPage.checkoutPriceValidation();
	}
	
	@Test (alwaysRun = true, dependsOnMethods = "TC15", enabled = true)
	public void TC16() {
		cartPage = new CartPage(driver);
		cartPage.submitOrder();
		Assert.assertEquals(cartPage.completeHeader().getText(), "Thank you for your order!");
		Assert.assertEquals(cartPage.completeText().getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
	}
	
	@DataProvider
	public Object[][] getData() {
		Object[][] data = new Object[1][2];
		
		data[0][0] = userName;
		data[0][1] = password;
		
		return data;
	}
}