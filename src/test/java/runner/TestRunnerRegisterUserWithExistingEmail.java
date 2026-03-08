package runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features/Login/RegisterUserWithExistingEmail.feature",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/RegisterUserWithExistingEmail.html",
                "json:target/cucumber-reports/RegisterUserWithExistingEmail.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "hooks.CucumberReportListener"
        },
        monochrome = true
)
public class TestRunnerRegisterUserWithExistingEmail extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
