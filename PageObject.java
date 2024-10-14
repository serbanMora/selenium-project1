package MySeleniumProjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class PageObject extends BaseTest {
	
	public static String[] itemNames() {
		
		return new String[] { "Sauce Labs Fleece Jacket", 
							  "Sauce Labs Backpack", 
							  "Sauce Labs Bolt T-Shirt", 
							  "Test.allTheThings() T-Shirt (Red)", 
							  "Sauce Labs Bike Light", 
							  "Sauce Labs Onesie" };
	}
	
	public static WebElement loginButton() {
		return driver.findElement(By.xpath("//input[@type='submit']"));
	}

	public static WebElement loginError() {
		return driver.findElement(By.cssSelector("h3[data-test='error']"));
	}

	public static WebElement credentials(int index) {
		List<WebElement> inputs = driver.findElements(By.xpath("//input[@class='input_error form_input error']"));
		return inputs.get(index);
	}

	public static WebElement cart() {
		return driver.findElement(By.cssSelector("a[data-test='shopping-cart-link']"));
	}

	public static WebElement productSort() {
		return driver.findElement(By.xpath("//select[@class='product_sort_container']"));
	}
	
	public static WebElement checkout() {
		return driver.findElement(By.id("checkout"));
	}
	
	public static WebElement continueBtn() {
		return driver.findElement(By.id("continue"));
	}
	
	public static WebElement finishBtn() {
		return driver.findElement(By.id("finish"));
	}
	
	public static WebElement completeHeader() {
		return driver.findElement(By.xpath("//h2[@class='complete-header']"));
	}
	
	public static WebElement completeText() {
		return driver.findElement(By.xpath("//div[@class='complete-text']"));
	}
	
	public static String jsExecutorGetText(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js.executeScript("return arguments[0].value;", element);
		return text;
	}
	
	public static void scrollBy(String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, " + value + ");");
	}
	
	public static void nameOrderValidation(String value) {
		Select s = new Select(productSort());
		s.selectByValue(value);
		
		List<WebElement> titles = driver.findElements(By.cssSelector("div[data-test='inventory-item-name']"));
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

	public static void priceOrderValidation(String value) {
		Select s = new Select(productSort());
		s.selectByValue(value);
		List<WebElement> pricesList = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));

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

	public static void addItems(String[] itemNames) {
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
	
	public static void cartIconNumberAssertion() {
		String cartText = cart().getText();
		int cartNumber = Integer.parseInt(cartText);
		Assert.assertEquals(cartNumber, itemNames().length, "TC11 FAILED: Cart does not display the correct number.");
	}
	
	public static void buttonTextValidation(String type) {
		List<WebElement> addToCart = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

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
	
	public static void closeAllTabsExceptMain() {
		List<WebElement> socialLinks = driver.findElements(By.xpath("//a[@rel='noreferrer']"));
		for (int i = 0; i < socialLinks.size(); i++) {
			socialLinks.get(i).click();
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
	
	public static void checkoutProductsValidation() {
		List<WebElement> productsList = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
		List<String> expectedProducts = Arrays.asList(itemNames());
		
		List<String> products = new ArrayList<>();
		for (int i = 0; i < productsList.size(); i++) {
			products.add(productsList.get(i).getText());
		} 
		
		Collections.sort(products);
		Collections.sort(expectedProducts);
		
		Assert.assertTrue(products.equals(expectedProducts), "TC14 FAILED: " + expectedProducts + " not displayed at checkout.");
		
	}
	
	public static void checkoutPriceValidation() {
		List<WebElement> pricesList = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
		String subtotal = driver.findElement(By.xpath("//div[@class='summary_subtotal_label']")).getText().replaceAll("[^\\d.]", "");
		String tax = driver.findElement(By.xpath("//div[@class='summary_tax_label']")).getText().replaceAll("[^\\d.]", "");
		String total = driver.findElement(By.xpath("//div[@class='summary_total_label']")).getText().replaceAll("[^\\d.]", "");
		
		double subtotalD = Double.parseDouble(subtotal);
		double taxD = Double.parseDouble(tax);
		double totalD = Double.parseDouble(total);
		
		double sum = 0;
		
		for (int i = 0; i < pricesList.size(); i++) {
			String price = pricesList.get(i).getText().replaceAll("[^\\d.]", "");
		    double priceValue = Double.parseDouble(price);
		    sum += priceValue;
			}
		
		Assert.assertEquals(sum, subtotalD, 0.01, "TC 15 FAILED: Item total is not displaying correct value.");
		Assert.assertEquals(totalD, subtotalD + taxD, 0.01, "TC 15 FAILED: Total price is not displaying correct value.");
	}
}