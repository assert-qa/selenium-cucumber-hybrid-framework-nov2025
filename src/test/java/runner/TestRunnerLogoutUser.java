package runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features/Login/LogoutUser.feature",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/LogoutUser.html",
                "json:target/cucumber-reports/LogoutUser.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "hooks.CucumberReportListener"
        },
        monochrome = true
)
public class TestRunnerLogoutUser extends AbstractTestNGCucumberTests{
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
