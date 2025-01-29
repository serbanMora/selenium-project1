package saucedemo.pageObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class ProductCatalog {

	WebDriver driver;
	
	public ProductCatalog(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (xpath = "//button[@class='btn btn_primary btn_small btn_inventory ']")
	private List<WebElement> addToCartButtons;
	
	@FindBy (css = "div[data-test='inventory-item-name']")
	private List<WebElement> inventoryItemName;
	
	@FindBy (xpath = "//select[@class='product_sort_container']")
	private WebElement productSortContainer;
	
	@FindBy (xpath = "//div[@class='inventory_item_price']")
	private List<WebElement> priceList;
	
	@FindBy (xpath = "//a[@rel='noreferrer']")
	private List<WebElement> socialLinks;
	
	@FindBy (css = "a[data-test='shopping-cart-link']")
	private WebElement cartIcon;
	
	public static String[] itemNames() {
		return new String[] { "Sauce Labs Fleece Jacket", 
							  "Sauce Labs Backpack", 
							  "Sauce Labs Bolt T-Shirt", 
							  "Test.allTheThings() T-Shirt (Red)", 
							  "Sauce Labs Bike Light", 
							  "Sauce Labs Onesie" };
	}
	
	public WebElement productSort() {
		return productSortContainer;
	}
	
	public WebElement cartIcon() {
		return cartIcon;
	}
	
	public void priceOrderValidation(String value) {
		Select s = new Select(productSort());
		s.selectByValue(value);
		List<WebElement> pricesList = priceList;

		List<Double> pricesDouble = new ArrayList<>();
		for (int i = 0; i < pricesList.size(); i++) {
			Double val = Double.parseDouble(pricesList.get(i).getText().replaceAll("[^\\d.]", ""));
			pricesDouble.add(val);
		}

		List<Double> copiedPrices = new ArrayList<>();
		for (int i = 0; i < pricesDouble.size(); i++) {
			copiedPrices.add(pricesDouble.get(i));
		}
		
		switch (value.toLowerCase()) {
		case "lohi": 
			Collections.sort(copiedPrices);
			break;
		case "hilo":
			Collections.sort(copiedPrices, Collections.reverseOrder());
			break;
		default:
			throw new IllegalArgumentException("Invalid sorting order: " + value);
		}
		Assert.assertTrue(pricesDouble.equals(copiedPrices), "TC7 FAILED: Products are not sorted in " + value + " order.");
	}
	
	public void nameOrderValidation(String value) {
		Select s = new Select(productSort());
		s.selectByValue(value);
		
		List<WebElement> titles = inventoryItemName;
		List<String> titleTexts = new ArrayList<>();
		for (int i = 0; i < titles.size(); i++) {
			titleTexts.add(titles.get(i).getText());
		}

		List<String> copiedTexts = new ArrayList<>();
		for (int i = 0; i < titleTexts.size(); i++) {
			copiedTexts.add(titleTexts.get(i));
		}

		switch (value.toLowerCase()) {
		case "az":
			Collections.sort(copiedTexts);
			break;
		case "za":
			Collections.sort(copiedTexts, Collections.reverseOrder());
			break;
		default:
			throw new IllegalArgumentException("Invalid sorting order: " + value);
		}
		Assert.assertTrue(titleTexts.equals(copiedTexts), "TC8 FAILED: Products are not sorted in " + value + " order.");
	}
	
	public void buttonTextValidation(String type) {
		List<WebElement> addToCart = addToCartButtons;

		List<String> btn = new ArrayList<>();
		for (WebElement element : addToCart) {
			btn.add(element.getText());
		}

		List<String> expected = new ArrayList<>();
		
		if (type.equals("addToCart")) {
			for (int i = 0; i < btn.size(); i++) {

				expected.add("Add to cart");
			}

			if (type.equals("remove")) {
				for (int i = 0; i < itemNames().length; i++) {

					expected.add("Remove");
				}
			}
		}
		Assert.assertTrue(btn.containsAll(expected), "TC10 FAILED: '" + type + "' button is not displaying.");
	}
	
	public void addItems(String[] itemNames) {
	    List<String> itemsList = Arrays.asList(itemNames);
	    List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));
	    List<WebElement> titles = driver.findElements(By.cssSelector("div[data-test='inventory-item-name']"));
	    
	    int j = 0;

	    for (int i = 0; i < titles.size(); i++) {
	        String name = titles.get(i).getText();
	        
	        if (itemsList.contains(name)) {
	        	addToCartButtons.get(i).click();
	            j++;

	            if (j == itemNames.length) {
	                break;
	            }
	        }
	    }
	}
	
	public void cartIconNumberAssertion() {
		String cartText = cartIcon().getText();
		int cartNumber = Integer.parseInt(cartText);
		Assert.assertEquals(cartNumber, itemNames().length, "TC11 FAILED: Cart does not display the correct number.");
	}
	
	public void closeAllTabsExceptMain() {
		List<WebElement> socialLink = socialLinks;
		for (int i = 0; i < socialLink.size(); i++) {
			socialLink.get(i).click();
		}
		String mainTab = driver.getWindowHandle();

		Set<String> ab = driver.getWindowHandles();
		Iterator<String> it = ab.iterator();

		while (it.hasNext()) {
			driver.switchTo().window(it.next());
		}
		if (!driver.getWindowHandle().equals(mainTab)) {
			driver.close();
		}
		driver.switchTo().window(mainTab);
	}
	
	public CartPage clickCart() {
		cartIcon.click();
		return new CartPage(driver);
	}
}