package MySeleniumProjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class dragAndDropDemo {
	static WebDriver driver = new ChromeDriver();

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lynx User\\Desktop\\chromedriver.exe");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://www.globalsqa.com/demo-site/draganddrop/");
		consent().click();
		driver.switchTo().frame(frameSwitch());

		List<WebElement> cards = source();
		for (int i = 0; i < cards.size(); i++) {
			String cardName = cards.get(i).getText();
			for (String names : names()) {
				if (cardName.equals(names)) {
					Actions a = new Actions(driver);
					a.dragAndDrop(cards.get(i), target()).build().perform();
					break;
				}
			}
		}
		driver.quit();
	}

	public static WebElement consent() {
		return driver.findElement(By.className("fc-button-label"));
	}

	public static WebElement frameSwitch() {
		return driver.findElement(By.xpath("//iframe[@class='demo-frame lazyloaded']"));
	}

	public static List<WebElement> source() {
		return driver.findElements(By.xpath("//h5[@class='ui-widget-header']"));
	}

	public static WebElement target() {
		return driver.findElement(By.id("trash"));
	}

	public static String[] names() {
		return new String[] { "High Tatras 3", "High Tatras 2", "High Tatras", "High Tatras 4" };
	}
}
