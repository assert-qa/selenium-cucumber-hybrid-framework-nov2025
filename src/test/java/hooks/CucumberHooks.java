package hooks;

import factory.DriverManager;
import helpers.PropertiesHelper;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import io.cucumber.java.*;
import reports.ExtentTestManager;
import utils.LogUtils;


public class CucumberHooks {
    private static final String SEPARATOR = "=".repeat(40);

    @BeforeAll
    public static void beforeAll() {
        LogUtils.info(SEPARATOR);
        LogUtils.info("STARTING TEST SUITE");
        LogUtils.info(SEPARATOR);
        PropertiesHelper.loadAllFiles();
    }

    @AfterAll
    public static void afterAll() {
        LogUtils.info(SEPARATOR);
        LogUtils.info("TEST SUITE COMPLETED");
        LogUtils.info(SEPARATOR);
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        LogUtils.info("SCENARIO START: " + scenario.getName());
        LogUtils.info("SCENARIO STATUS: Starting");

        // Initialize ExtentTest for this scenario
        try {
            String testName = scenario.getName();
            ExtentTestManager.createTest(testName);
            LogUtils.info("ExtentTest initialized for: " + testName);
        } catch (Exception e) {
            LogUtils.info("Failed to create ExtentTest: " + e.getMessage());
        }

    }

    @After
    public void afterScenario(Scenario scenario) {
        // Determine status string: try to call getStatus() if available, otherwise fallback to isFailed()
        String status = "PASSED";
        try {
            // reflection to support various cucumber versions where getStatus may exist
            Object statusObj = scenario.getClass().getMethod("getStatus").invoke(scenario);
            if (statusObj != null) {
                status = statusObj.toString();
            } else {
                status = scenario.isFailed() ? "FAILED" : "PASSED";
            }
        } catch (NoSuchMethodException nsme) {
            status = scenario.isFailed() ? "FAILED" : "PASSED";
        } catch (Exception e) {
            // fallback
            status = scenario.isFailed() ? "FAILED" : "PASSED";
        }

        // Log scenario status to ExtentReport (guard against null test)
        try {
            if (ExtentTestManager.getTest() != null) {
                if ("FAILED".equalsIgnoreCase(status)) {
                    ExtentTestManager.getTest().fail("Scenario Failed: " + scenario.getName());
                } else if ("SKIPPED".equalsIgnoreCase(status) || "UNKNOWN".equalsIgnoreCase(status)) {
                    ExtentTestManager.getTest().skip("Scenario Skipped: " + scenario.getName() + " (Status: " + status + ")");
                } else {
                    ExtentTestManager.getTest().pass("Scenario Passed: " + scenario.getName());
                }
            } else {
                LogUtils.info("ExtentTest not available to log scenario status.");
            }
        } catch (Exception e) {
            LogUtils.info("Failed to log scenario status to ExtentReport: " + e.getMessage());
        }

        // Plain-text logging for console/log file
        LogUtils.info("SCENARIO FINISHED: " + scenario.getName());
        LogUtils.info("SCENARIO STATUS: " + status);

        DriverManager.quit();
        LogUtils.info(SEPARATOR + "\n");
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        // Reduced logging to avoid clutter
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            LogUtils.info("Step failed - capturing screenshot");

            final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot - Failed Step");

            // Also attach to ExtentReport (guarded)
            try {
                if (ExtentTestManager.getTest() != null) {
                    ExtentTestManager.getTest().fail("Step Failed - Screenshot attached");
                } else {
                    LogUtils.info("ExtentTest not available to attach screenshot.");
                }
            } catch (Exception e) {
                LogUtils.info("Failed to attach screenshot to ExtentReport: " + e.getMessage());
            }
        }
    }
}
