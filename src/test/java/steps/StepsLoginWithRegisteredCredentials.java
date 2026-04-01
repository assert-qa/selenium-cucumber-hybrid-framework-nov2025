package steps;

import constants.ConstantGlobal;
import helpers.UserInfoHelper;
import hooks.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import keywords.WebUI;
import pages.LoginPage;
import pages.models.CredentialsData;
import reports.AllureManager;
import utils.LogUtils;

public class StepsLoginWithRegisteredCredentials {
    private TestContext testContext;
    private LoginPage loginPage;

    private final String validEmail = ConstantGlobal.VALID_EMAIL;
    private final String validPassword = ConstantGlobal.VALID_PASSWORD;

    public StepsLoginWithRegisteredCredentials(TestContext testContext) {
        this.testContext = testContext;
        this.loginPage = new LoginPage();
    }

    public StepsLoginWithRegisteredCredentials(){
        this(new TestContext());
    }

    @When("I enter registered email address and password")
    public void iEnterCorrectEmailAddressAndPassword() {
        // ========== ENHANCEMENT: Log user account info ==========
        LogUtils.info("Logging in with account: " + UserInfoHelper.getCurrentUserEmail());
        LogUtils.info("Account Type: " + UserInfoHelper.getUserAccountType());

        CredentialsData credentialsData = CredentialsData.builder()
                .userEmail(validEmail)
                .userPassword(validPassword)
                .build();

        loginPage.loginAccount(credentialsData);

        // ========== ENHANCEMENT: Attach user action to Allure ==========
        AllureManager.attachUserAccountInfo();
    }

    @Then("I verify that {string} is visible")
    public void iVerifyThatIsVisible(String expectedText) {
        String actualText = loginPage.getSuccessLogin();

        if ("Logged in as user email".equalsIgnoreCase(expectedText.trim())) {
            WebUI.verifyEquals(actualText, validEmail,
                    "Success login label is not showing the expected user email.");
            return;
        }

        WebUI.verifyEquals(actualText, expectedText,
                "Success login label is different from expected text.");
    }
}
