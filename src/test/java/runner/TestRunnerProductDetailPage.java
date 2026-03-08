package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/java/features/Product/ProductDetailPage.feature",
        glue = {"steps",
                "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/LoginWithInvalidCredentials.html",
                "json:target/cucumber-reports/LoginWithInvalidCredentials.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "hooks.CucumberReportListener"
        },
        monochrome = true
)
@Test
public class TestRunnerProductDetailPage extends AbstractTestNGCucumberTests {
    @DataProvider
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
