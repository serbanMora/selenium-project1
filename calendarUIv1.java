package MySeleniumProjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class calendarUIv1 {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lynx User\\Desktop\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://seleniumpractise.blogspot.com/2016/08/how-to-handle-calendar-in-selenium.html");
		driver.findElement(By.id("cookieChoiceDismiss")).click();
		driver.findElement(By.id("datepicker")).click();

		while (!driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText().contains("January")
				|| !driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText().contains("2030")) {
			driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
		}

		List<WebElement> dates = driver.findElements(By.xpath("//td[@data-handler='selectDay']"));

		int date = dates.size();

		for (int i = 0; i < date; i++) {

			String dat = dates.get(i).getText();

			if (dat.equals("15")) {
				dates.get(i).click();
			}
		}
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String selectedDate = (String) js.executeScript("return arguments[0].value;", driver.findElement(By.id("datepicker")));
		Assert.assertEquals(selectedDate, "01/15/2030");
		
		driver.quit();
	}
}