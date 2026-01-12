package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features/Login/LoginWithValidCredentials.feature",
        glue = {"stepDefinition", "hooks"},
        tags = "@smokeTest",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/LoginWithValidCredentials.html",
                "json:target/cucumber-reports/LoginWithValidCredentials.json"
        },
        monochrome = true
)
public class TestRunnerLoginWithValidCredentials extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

