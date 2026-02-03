package stepDefinition;

import constants.ConstantGlobal;
import hooks.TestContext;
import io.cucumber.java.en.When;
import pages.LoginPage;
import reports.ExtentTestManager;

public class StepsLoginWithValidCredentials {
    private TestContext testContext;
    private LoginPage loginPage;

    // Constructor for Cucumber Dependency Injection
    public StepsLoginWithValidCredentials(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = testContext.getLoginPage();
    }

    // Zero-argument constructor as fallback
    public StepsLoginWithValidCredentials() {
        this(new TestContext());
    }

    @When("I enter correct email address and password")
    public void iEnterCorrectEmailAddressAndPassword() {
        String email = ConstantGlobal.EMAIL;
        String password = ConstantGlobal.PASSWORD;

        loginPage.loginAccount(email, password);

        ExtentTestManager.logMessage("Entered email: " + email);
        ExtentTestManager.logMessage("Entered password: ********");
    }
}