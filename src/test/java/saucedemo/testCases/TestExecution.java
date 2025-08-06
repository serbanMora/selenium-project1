package saucedemo.testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import saucedemo.config.BaseTest;
import saucedemo.pageObject.CartPage;
import saucedemo.pageObject.LoginPage;
import saucedemo.pageObject.ProductCatalog;

public class TestExecution extends BaseTest {
	
	LoginPage login;
	ProductCatalog productCatalog;
	CartPage cartPage;
	
	SoftAssert softAssert;
	
	private static final String USER_NAME = "standard_user";
	private static final String PASSWORD = "secret_sauce";
	
	@Test
	public void TC1() {
		login = new LoginPage(driver);
		login.loginButton().click();
		explicitWait(login.loginError(), "visibility", 3);
		Assert.assertEquals(login.loginError().getText(), "Epic sadface: Username is required");
	}
	
	@Test (dependsOnMethods = "TC1")
	public void TC2() {
		login.setName("standard_user");
		login.loginButton().click();
		explicitWait(login.loginError(), "visibility", 3);
		Assert.assertEquals(login.loginError().getText(), "Epic sadface: Password is required");
	}
	
	@Test (dependsOnMethods = "TC2")
	public void TC3() {
		login.setPassword("invalidPass");
		login.loginButton().click();
		explicitWait(login.loginError(), "visibility", 3);
		Assert.assertEquals(login.loginError().getText(), "Epic sadface: Username and password do not match any user in this service");
		login.clearField("userName");
		login.clearField("password");
		driver.navigate().refresh();
	}
	
	@Test (dataProvider = "getData", dependsOnMethods = "TC3")
	public void TC4(String userName, String password) {
		login.setName(userName);
		login.setPassword(password);
		Assert.assertEquals(jsExecutorGetText(login.loginButton()), "Login");
		login.clickLogIn();
	}
	
	@Test (dependsOnMethods = "TC4")
	public void TC5() {
		Assert.assertEquals(driver.getTitle(), "Swag Labs");
	}
	
	@Test (dependsOnMethods = "TC5")
	public void TC6() {
		Assert.assertEquals(driver.findElements(By.tagName("a")).size(), 20);
	}
	
	@Test (dependsOnMethods = "TC6")
	public void TC7() {
		productCatalog = new ProductCatalog(driver);
		productCatalog.priceOrderValidation("hilo");
	}
	
	@Test (dependsOnMethods = "TC7")
	public void TC8() {
		productCatalog.nameOrderValidation("za");
	}
	
	@Test (dependsOnMethods = "TC8")
	public void TC9() {
		productCatalog.buttonTextValidation("addToCart");
	}
	
	@Test (dependsOnMethods = "TC9")
	public void TC10() {
		productCatalog.addItems(ProductCatalog.itemNames());
		productCatalog.buttonTextValidation("remove");
	}
	
	@Test (dependsOnMethods = "TC10")
	public void TC11() {
		productCatalog.cartIconNumberAssertion();
	}
	
	@Test (dependsOnMethods = "TC11")
	public void TC12() {
		productCatalog.closeAllTabsExceptMain();
		productCatalog.clickCart();
	}
	
	@Test (dependsOnMethods = "TC12")
	public void TC13() {
		softAssert = new SoftAssert();
		cartPage = new CartPage(driver);
		cartPage.checkout().click();
		cartPage.continueButton().click();
		softAssert.assertEquals(cartPage.loginError().getText(), "Error: First Name is required");
		cartPage.completeForm("firstName", "test1");
		cartPage.continueButton().click();
		softAssert.assertEquals(cartPage.loginError().getText(), "Error: Last Name is required");
		cartPage.completeForm("lastName", "test2");
		cartPage.continueButton().click();
		softAssert.assertEquals(cartPage.loginError().getText(), "Error: Postal Code is required");
		cartPage.completeForm("zip", "test3");
		cartPage.continueButton().click();
		softAssert.assertAll();
	}
	
	@Test (dependsOnMethods = "TC13")
	public void TC14() {
		cartPage.checkoutProductsValidation();
	}
	
	@Test (dependsOnMethods = "TC14")
	public void TC15() {
		cartPage.checkoutPriceValidation();
	}
	
	@Test (dependsOnMethods = "TC15")
	public void TC16() {
		cartPage.submitOrder();
		softAssert.assertEquals(cartPage.completeHeader().getText(), "Thank you for your order!");
		softAssert.assertEquals(cartPage.completeText().getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] getData() {
		Object[][] data = new Object[1][2];
		
		data[0][0] = USER_NAME;
		data[0][1] = PASSWORD;
		
		return data;
	}
}