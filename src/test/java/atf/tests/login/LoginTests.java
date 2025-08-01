package atf.tests.login;

import atf.pageobjects.ContactListPage;
import atf.pageobjects.LoginPage;
import atf.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality() {
        logger.info("Starting testLoginFunctionality");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();
        loginPage.performLoginSteps();
        logger.info("Verify Contact List Page Title");
        ContactListPage contactListPage = new ContactListPage(driver);
        Assert.assertEquals("Contact List", driver.findElement(contactListPage.getWelcomeSectionTitleLocator()).getText());
        Assert.assertEquals("https://thinking-tester-contact-list.herokuapp.com/contactList", loginPage.getCurrentUrl());
        Assert.assertEquals("Click on any contact to view the Contact Details", 
                                driver.findElement(contactListPage.getContactListPageMessageLocator()).getText());
    }
}
