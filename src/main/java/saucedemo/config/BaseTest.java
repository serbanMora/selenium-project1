package saucedemo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

	public WebDriver driver;
	
	@BeforeClass
	public void setUP() throws IOException {
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/saucedemo/config/data.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		
		String chromeDriverPath = prop.getProperty("chromeDriverPath");
		String firefoxDriverPath = prop.getProperty("firefoxDriverPath");
		String edgeDriverPath = prop.getProperty("edgeDriverPath");
		String url = prop.getProperty("url");
		
		if (browserName.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions options = new ChromeOptions();
			if (browserName.contains("headless")) {
				options.addArguments("--headless");
			}
			driver = new ChromeDriver(options);
			
		} else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
			driver = new FirefoxDriver();
			
		} else if (browserName.equals("edge")) {
			System.setProperty("webdriver.ie.driver", edgeDriverPath);
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get(url);
	}
	
	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	public void explicitWait(WebElement element, String conditionType, int duration) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
			switch (conditionType) {
			case "visibility":
				wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "invisibility":
				wait.until(ExpectedConditions.invisibilityOf(element));
				break;
			case "clickable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;
			default:
				throw new IllegalArgumentException("Unsupported condition type: " + conditionType);
			}
		} catch (TimeoutException e) {
			System.err.println("Element not visible after " + duration + " seconds: " + element);
		}
	}
	
	public String jsExecutorGetText(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js.executeScript("return arguments[0].value;", element);
		return text;
	}
	
	public void scrollBy(String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, " + value + ");");
	}
}