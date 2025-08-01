package atf.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactListPage extends BasePage {

    private final By contactListPageTitleLocator = By.xpath("//div[@class='main-content']//h1");
    private final By contactListPageMessageLocator = By.xpath("//div[@class='main-content']//p");
    
    public ContactListPage(WebDriver driver) {
        super(driver);
    }

    public By getWelcomeSectionTitleLocator() {
        return contactListPageTitleLocator;
    }

    public By getContactListPageMessageLocator() {
        return contactListPageMessageLocator;
    }

}
