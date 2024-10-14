package MySeleniumProjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
	
	static WebDriver driver;

	@BeforeClass 
	public static void setUP() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lynx User\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://www.saucedemo.com/");
	}
	
	@AfterClass
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
