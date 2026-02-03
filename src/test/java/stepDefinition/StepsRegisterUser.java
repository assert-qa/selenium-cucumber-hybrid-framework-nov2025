package stepDefinition;

import hooks.TestContext;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import pages.LoginPage;
import utils.LogUtils;
import helpers.DataFakerHelper;
import reports.ExtentTestManager;

import java.util.Map;

public class StepsRegisterUser {
    private TestContext testContext;
    private LoginPage loginPage;

    // Constructor for Cucumber Dependency Injection
    public StepsRegisterUser(TestContext testContext){
        this.testContext = testContext;
        this.loginPage = testContext.getLoginPage();
    }

    // Zero-argument constructor as fallback
    public StepsRegisterUser() {
        this(new TestContext());
    }

    @When("I enter name {string} and email address {string}")
    public void iEnterNameAndEmailAddress(String nameParam, String emailParam) {
        String generatedName = DataFakerHelper.getFaker().name().firstName();
        String generatedEmail = DataFakerHelper.getFaker().internet().emailAddress();

        LogUtils.info("Generated Name: " + generatedName);
        LogUtils.info("Generated Email: " + generatedEmail);

        loginPage.typeUserInformation(generatedName, generatedEmail);

        // Set generated name to CommonSteps for verification
        CommonSteps.setGeneratedName(generatedName);

        ExtentTestManager.logMessage("Entered name: " + generatedName);
        ExtentTestManager.logMessage("Entered email: " + generatedEmail);
    }

    @When("I fill account details with title {string}, password {string}, date of birth {string}, month {string}, year {string}")
    public void iFillAccountDetailsWithTitlePasswordDateOfBirthMonthYear(String title, String password, String day, String month, String year) {
        loginPage.fillAccountInformation(title, password, day, month, year);
        ExtentTestManager.logMessage("Filled account details");
    }

    @And("I select checkbox {string}")
    public void iSelectCheckbox(String checkboxLabel) {
        loginPage.selectCheckBox(checkboxLabel);
        ExtentTestManager.logMessage("Selected checkbox: " + checkboxLabel);
    }

    @And("I fill address details:")
    public void iFillAddressDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        loginPage.fillAddressInformation(
                data.get("First name"),
                data.get("Last name"),
                data.get("Company"),
                data.get("Address"),
                data.get("Address2"),
                data.get("Country"),
                data.get("State"),
                data.get("City"),
                data.get("Zipcode"),
                data.get("Mobile Number")
        );
        ExtentTestManager.logMessage("Filled address details");
    }
}
