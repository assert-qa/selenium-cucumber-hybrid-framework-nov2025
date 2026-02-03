package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import keywords.WebUI;
import pages.LoginPage;
import reports.ExtentTestManager;

public class StepsLoginWithInvalidCredentials {
    private TestContext testContext;
    private LoginPage loginPage;

    // Constructor for Cucumber Dependency Injection
    public StepsLoginWithInvalidCredentials(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = testContext.getLoginPage();
    }

    // Zero-argument constructor as fallback
    public StepsLoginWithInvalidCredentials() {
        this(new TestContext());
    }

    @When("I enter email {string} and password {string}")
    public void iEnterEmailAndPassword(String email, String password) {
        loginPage.loginAccount(email, password);
        ExtentTestManager.logMessage("Entered email: " + email);
        ExtentTestManager.logMessage("Entered password: ********");
    }

    @Then("I verify error {string} is visible")
    public void iVerifyErrorIsVisible(String errorMessage) {
        String actualError = loginPage.verifyInlineErrorMessage();
        WebUI.verifyEquals(actualError, errorMessage);
        ExtentTestManager.logMessage("Verified error message: " + errorMessage);
    }
}
