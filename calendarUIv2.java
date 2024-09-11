package MySeleniumProjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class calendarUIv2 {

	static WebDriver driver = new ChromeDriver();

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lynx User\\Desktop\\chromedriver.exe");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://formy-project.herokuapp.com/datepicker");

		datePicker().click();
		selectDate("December 2030");
		selectDay("1");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement dateField = driver.findElement(By.cssSelector("input[placeholder='mm/dd/yyyy']"));
		String selectedDate = (String) js.executeScript("return arguments[0].value;", dateField);
		Assert.assertEquals(selectedDate, "12/01/2030");

		driver.quit();

	}

	public static WebElement datePicker() {
		return driver.findElement(By.id("datepicker"));
	}

	public static void selectDate(String date) {
		while (!driver.findElement(By.xpath("//th[@class='datepicker-switch']")).getText().equals(date)) {
			driver.findElement(By.xpath("//th[@class='next']")).click();
		}
	}

	public static void selectDay(String day) {
		List<WebElement> dates = driver.findElements(By.xpath("//td[@class='day']"));
		for (int i = 0; i < dates.size(); i++) {
			String date = driver.findElements(By.xpath("//td[@class='day']")).get(i).getText();
			if (date.equals(day)) {
				dates.get(i).click();
				break;
			}
		}
	}
}
