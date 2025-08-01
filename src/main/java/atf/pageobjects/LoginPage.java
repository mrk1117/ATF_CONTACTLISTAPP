package atf.pageobjects;

import atf.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By welcomeSectionTitle = By.xpath("//body/h1");
    private By emailInputLocator = By.id("email");
    private By passwordInputLocator = By.id("password");
    private By submitButtonLocator = By.id("submit");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void load() {
        waitForElement(welcomeSectionTitle);
    }

    public By getWelcomeSectionTitle() {
        return welcomeSectionTitle;
    }

    public void goToLoginPage() {
        super.goToUrl(ConfigReader.get("base.url"));
        load();
    }

    public void enterEmailAddress(String username) {
        driver.findElement(emailInputLocator).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInputLocator).sendKeys(password);
    }

    public void clickSubmitButton() {
        driver.findElement(submitButtonLocator).click();
    }

    public void performLoginSteps() {
        String email = ConfigReader.get("base.email");
        String password = ConfigReader.get("base.password");
        waitForElement(emailInputLocator);
        enterEmailAddress(email);
        waitForElement(passwordInputLocator);
        enterPassword(password);
        waitForElement(submitButtonLocator);
        clickSubmitButton();
    }
}
