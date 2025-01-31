package saucedemo.pageObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class CartPage {

	WebDriver driver;
	SoftAssert softAssert;
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (id = "checkout")
	private WebElement checkout;
	
	@FindBy (id = "continue")
	private WebElement continueButton;
	
	@FindBy (css = "h3[data-test='error']")
	private WebElement loginError;
	
	@FindBy (xpath = "//input[@class='input_error form_input error']")
	private List<WebElement> checkoutForm;
	
	@FindBy (xpath = "//div[@class='inventory_item_name']")
	private List<WebElement> checkoutProductList;
	
	@FindBy (xpath = "//div[@class='inventory_item_price']")
	private List<WebElement> priceList;
	
	@FindBy (xpath = "//div[@class='summary_subtotal_label']")
	private WebElement subtotalLabel;
	
	@FindBy (xpath = "//div[@class='summary_tax_label']")
	private WebElement taxLabel;
	
	@FindBy (xpath = "//div[@class='summary_total_label']")
	private WebElement summaryTotalLabel;
	
	@FindBy (id = "finish")
	private WebElement finishButton;
	
	@FindBy (xpath = "//h2[@class='complete-header']")
	private WebElement completeHeader;
	
	@FindBy (xpath = "//div[@class='complete-text']")
	private WebElement completeText;
	
	public WebElement completeHeader() {
		return completeHeader;
	}
	
	public WebElement completeText() {
		return completeText;
	}
	
	public WebElement checkout() {
		return checkout;
	}
	
	public WebElement continueButton() {
		return continueButton;
	}
	
	public WebElement loginError() {
		return loginError;
	}

	public void completeForm(String field, String name) {
		switch (field) {
		case "firstName":
			checkoutForm.get(0).sendKeys(name);
			break;
		case "lastName":
			checkoutForm.get(1).sendKeys(name);
			break;
		case "zip":
			checkoutForm.get(2).sendKeys(name);
			break;
		}
	}
	
	public void checkoutProductsValidation() {
		List<WebElement> productsList = checkoutProductList;
		List<String> expectedProducts = Arrays.asList(ProductCatalog.itemNames());
		
		List<String> products = new ArrayList<>();
		for (int i = 0; i < productsList.size(); i++) {
			products.add(productsList.get(i).getText());
		} 
		
		Collections.sort(products);
		Collections.sort(expectedProducts);
		
		Assert.assertTrue(products.equals(expectedProducts), "TC14 FAILED: " + expectedProducts + " not displayed at checkout.");
	}
	
	public void checkoutPriceValidation() {
		softAssert = new SoftAssert();
		List<WebElement> pricesList = priceList;
		
		double subtotalD = Double.parseDouble(subtotalLabel.getText().replaceAll("[^\\d.]", ""));
		double taxD = Double.parseDouble(taxLabel.getText().replaceAll("[^\\d.]", ""));
		double totalD = Double.parseDouble(summaryTotalLabel.getText().replaceAll("[^\\d.]", ""));
		
		double sum = 0;
		
		for (int i = 0; i < pricesList.size(); i++) {
			String price = pricesList.get(i).getText().replaceAll("[^\\d.]", "");
		    double priceValue = Double.parseDouble(price);
		    sum += priceValue;
			}
		
		softAssert.assertEquals(sum, subtotalD, 0.01, "TC 15 FAILED: Item total is not displaying correct value.");
		softAssert.assertEquals(totalD, subtotalD + taxD, 0.01, "TC 15 FAILED: Total price is not displaying correct value.");
		softAssert.assertAll();
	}
	
	public void submitOrder() {
		finishButton.click();
	}
}