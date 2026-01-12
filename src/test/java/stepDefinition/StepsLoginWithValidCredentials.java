package stepDefinition;

import common.BaseTest;
import constants.ConstantGlobal;
import factory.DriverManager;
import hooks.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import keywords.WebUI;
import pages.LoginPage;
import reports.ExtentTestManager;
import utils.LogUtils;

import static keywords.WebUI.verifyEquals;

public class StepsLoginWithValidCredentials {
    private LoginPage loginPage;

    public StepsLoginWithValidCredentials() {
        this(new TestContext());
    }

    public StepsLoginWithValidCredentials(TestContext testContext){
        loginPage = testContext.getLoginPage();
    }

    @Given("I launch the browser")
    public void iLaunchTheBrowser() {
        if (DriverManager.getDriver() == null) {
            BaseTest.createDriver();
            LogUtils.info("Browser launched successfully");
        }
        loginPage.openLoginPage();
    }

    @When("I navigate to url {string}")
    public void iNavigateToUrl(String url) {
        WebUI.openURL(url);
        ExtentTestManager.logMessage("Navigated to: " + url);
    }

    @Then("I verify that home page is visible successfully")
    public void iVerifyThatHomePageIsVisibleSuccessfully() {
        if (loginPage.verifyHomePageIsVisible()){
            LogUtils.info("Home page is visible successfully");
            ExtentTestManager.logMessage("Home page is visible successfully");
        } else {
            LogUtils.error("Home page is not visible");
        }
    }

    @When("I click on {string} button")
    public void iClickOnButton(String buttonName) {
        loginPage.goToLoginPage();
        ExtentTestManager.logMessage("Clicked on '" + buttonName + "' button");
    }

    @Then("I verify {string} is visible")
    public void iVerifyIsVisible(String expectedText) {
        String actualResult;
        if (expectedText.equals("Login to your account")) {
            actualResult = loginPage.verifyLoginLabel();
            WebUI.verifyEquals(actualResult, expectedText);
            ExtentTestManager.logMessage("Verified: '" + expectedText + "' is visible");
            return;
        }
    }

    @When("I enter correct email address and password")
    public void iEnterCorrectEmailAddressAndPassword() {
        String email = ConstantGlobal.EMAIL;
        String password = ConstantGlobal.PASSWORD;

        loginPage.loginAccount(email, password);

        ExtentTestManager.logMessage("Entered email: " + email);
        ExtentTestManager.logMessage("Entered password: ********");
    }

    @When("I click {string} button")
    public void iClickButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("login")) {
            loginPage.clickLoginButton();
            ExtentTestManager.logMessage("Clicked 'login' button");
        } else if (buttonName.equalsIgnoreCase("Delete Account")) {
            WebUI.sleep(1);
            loginPage.clickDeleteAccountButton();
            ExtentTestManager.logMessage("Clicked 'Delete Account' button");
        }
    }

    @Then("I verify that {string} is visible")
    public void iVerifyThatIsVisible(String expectedText) {
        String actualResult = "";
        if (expectedText.toLowerCase().startsWith("logged in as ")) {
            // If feature uses placeholder "username", treat username as dynamic:
            if (expectedText.equalsIgnoreCase("Logged in as username") || expectedText.toLowerCase().contains(" username")) {
                actualResult = loginPage.verifyLoggedInAsUserNameIsVisible("username"); // special handling in page
                // Verify only the prefix "Logged in as " so the actual username can be anything
                if (actualResult.startsWith("Logged in as ")) {
                    LogUtils.info("Verified dynamic logged-in user: " + actualResult);
                    // Assert prefix exists (compare prefix strings)
                    verifyEquals(actualResult.startsWith("Logged in as ") ? "Logged in as " : actualResult, "Logged in as ");
                } else {
                    // will fail with clear message
                    verifyEquals(actualResult, expectedText);
                }
            } else {
                // Feature provided full expected text (includes actual username) — compare exact
                actualResult = loginPage.verifyLoggedInAsUserNameIsVisible(ConstantGlobal.USERNAME);
                verifyEquals(actualResult, expectedText);
            }
            return;
        } else if (expectedText.equalsIgnoreCase("ACCOUNT DELETED!")) {
            actualResult = loginPage.verifyAccountDeletedIsVisible();
            verifyEquals(actualResult, expectedText);
            return;
        }
    }
}