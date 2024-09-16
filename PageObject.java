package MySeleniumProjects;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class PageObject extends BaseTest{
	
	public static String[] itemNames() {
		return new String[] { "Sauce Labs Fleece Jacket", 
							  "Sauce Labs Backpack", 
							  "Sauce Labs Bolt T-Shirt", 
							  "Test.allTheThings() T-Shirt (Red)", 
							  "Sauce Labs Bike Light", "Sauce Labs Onesie", };
	}
	
	public static WebElement loginButton() {
		return driver.findElement(By.xpath("//input[@type='submit']"));
	}

	public static WebElement loginError() {
		return driver.findElement(By.cssSelector("h3[data-test='error']"));
	}

	public static WebElement credentials(String type) {
		return driver.findElement(By.id(type));
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

	public static void priceOrderValidator(String value) {
		// string value: "az", "za", "lohi", "hilo"
		Select s = new Select(productSort());
		s.selectByValue(value);

		List<WebElement> prices = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));

		int[] intArray = new int[prices.size()];

		for (int i = 0; i < prices.size(); i++) {

			String text = prices.get(i).getText().replace("$", "");
			double num = Double.parseDouble(text);
			int priceAsInt = (int) (num * 100);

			intArray[i] = priceAsInt;
		}

		if (value.equals("lohi")) {
			for (int i = 0; i < intArray.length - 1; i++) {
				if (intArray[i] < intArray[i + 1]) {
					Assert.assertTrue(true);
				}
			}
		}
		
		if (value.equals("hilo")) {
			for (int i = 0; i < intArray.length - 1; i++) {
				if (intArray[i] > intArray[i + 1]) {
					Assert.assertTrue(true);
				}
			}
		}
	}

	public static void addItems(String[] itemNames) {
	    List<String> itemsList = Arrays.asList(itemNames);
	    List<WebElement> products = driver.findElements(By.xpath("//div[@data-test='inventory-item-name']"));
	    List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

	    int j = 0;

	    for (int i = 0; i < products.size(); i++) {
	        String name = products.get(i).getText();
	        
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
		Assert.assertEquals(cartNumber, itemNames().length);
	}
	
	public static void buttonTextValidation(String type) {
		List<WebElement> addToCart = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));
		List<WebElement> remove = driver.findElements(By.xpath("//button[@class='btn btn_secondary btn_small btn_inventory ']"));
		
		if (type.equals("addToCart")) {
			String[] array = new String[addToCart.size()];

			for (int i = 0; i < addToCart.size(); i++) {
				array[i] = addToCart.get(i).getText();
			}

			String[] expectedText = new String[addToCart.size()];
			for (int i = 0; i < expectedText.length; i++) {
				expectedText[i] = "Add to cart";
			}

			Assert.assertEquals(array.length, expectedText.length);

			for (int i = 0; i < array.length; i++) {
				Assert.assertEquals(array[i], expectedText[i]);
			}
		}
		
		if (type.equals("remove")) {
			String[] array = new String[remove.size()];
			
			for (int i = 0; i < remove.size(); i++) {
				array[i] = remove.get(i).getText();
			}
			
			String[] expectedText = new String[remove.size()];
			for (int i = 0; i < expectedText.length; i++) {
				expectedText[i] = "Remove";
			}
			
			Assert.assertEquals(array.length, expectedText.length);
			
			for (int i = 0; i < array.length; i++) {
				Assert.assertEquals(array[i], expectedText[i]);
			}
		}
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
		List<WebElement> products = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
		List<String> expectedProducts = Arrays.asList(itemNames());
		
		for (int i = 0; i < products.size(); i++) {
			String productName = products.get(i).getText();
			String expectedName = expectedProducts.get(i);
			
			Assert.assertEquals(productName, expectedName);
		} 
	}
	
	public static void checkoutPriceValidation() {
		String subtotal = driver.findElement(By.xpath("//div[@class='summary_subtotal_label']")).getText().replaceAll("[^\\d.]", "");
		String tax = driver.findElement(By.xpath("//div[@class='summary_tax_label']")).getText().replaceAll("[^\\d.]", "");
		String total = driver.findElement(By.xpath("//div[@class='summary_total_label']")).getText().replaceAll("[^\\d.]", "");
		
		double subtotalD = Double.parseDouble(subtotal);
		double taxD = Double.parseDouble(tax);
		double totalD = Double.parseDouble(total);
		
		double sum = 0;
		List<WebElement> priceList = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
		for (int i = 0; i < priceList.size(); i++) {
			String price = priceList.get(i).getText().replaceAll("[^\\d.]", "");
		    double priceValue = Double.parseDouble(price);
		    sum += priceValue;
			}
		
		Assert.assertEquals(sum, subtotalD);
		Assert.assertEquals(totalD, subtotalD + taxD);
	}
}