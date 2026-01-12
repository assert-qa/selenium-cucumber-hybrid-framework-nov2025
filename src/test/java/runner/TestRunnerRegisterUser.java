package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/java/features", // lokasi load feature file (sekaligus semua)
        tags = "@smokeTest", // Kontrol test lewat tags, bukan runner #BEST PRACTICE
        glue = {"stepDefinition",
                "common",
                "hooks"},
        plugin = {
                "hooks.CucumberReportListener",
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true
)
@Test
public class TestRunnerRegisterUser extends AbstractTestNGCucumberTests {
    @DataProvider
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
