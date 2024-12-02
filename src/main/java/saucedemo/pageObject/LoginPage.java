package saucedemo.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (id = "user-name")
	private WebElement userName;
	
	@FindBy (id = "password")
	private WebElement password;
	
	@FindBy (id = "login-button")
	private WebElement login;
	
	@FindBy (css = "h3[data-test='error']")
	private WebElement loginError;
	
	public WebElement loginButton() {
		return login;
	}
	
	public void setName(String user) {
		userName.sendKeys(user);
	}
	
	public void setPassword(String pass) {
		password.sendKeys(pass);
	}
	
	public WebElement loginError() {
		return loginError;
	}
	
	public void clearField(String field) {
		switch (field) {
		case "userName":
			userName.clear();
			break;
		case "password":
			password.clear();
			break;
		}
	}
	
	public ProductCatalog clickLogIn() {
		loginButton().click();
		return new ProductCatalog(driver);
	}
}
