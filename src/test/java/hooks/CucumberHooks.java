package hooks;

import constants.ConstantGlobal;
import factory.DriverFactory;
import factory.DriverManager;
import helpers.PropertiesHelper;
import helpers.UserInfoHelper;
import io.cucumber.java.*;
import reports.AllureManager;
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

        // ========== ENHANCEMENT: Log user info at suite level ==========
        LogUtils.info(UserInfoHelper.getUserTestHeader());
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

        if (DriverManager.getDriver() == null) {
            new DriverFactory().createDriver();
            LogUtils.info("Driver initialized for scenario: " + scenario.getName());
        }

        try {
            String testName = scenario.getName();
            ExtentTestManager.createTest(testName);
            LogUtils.info("ExtentTest initialized for: " + testName);

            // Log scenario info to ExtentReport
            if (ExtentTestManager.getTest() != null) {
                ExtentTestManager.getTest().info("Starting scenario: " + testName);

                // ========== ENHANCEMENT: Log user info to Extent Report ==========
                ExtentTestManager.getTest().info("Test User: " + ConstantGlobal.VALID_EMAIL);
                ExtentTestManager.getTest().info("Environment: " + ConstantGlobal.ENV);
                ExtentTestManager.getTest().info("Account Type: " + UserInfoHelper.getUserAccountType());
            }

            // ========== ENHANCEMENT: Attach user info to Allure Report ==========
            AllureManager.attachEnvironmentInfo();
            AllureManager.attachUserAccountInfo();

        } catch (Exception e) {
            LogUtils.info("Failed to create ExtentTest: " + e.getMessage());
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        String status = "PASSED";
        try {
            Object statusObj = scenario.getClass().getMethod("getStatus").invoke(scenario);
            if (statusObj != null) {
                status = statusObj.toString();
            } else {
                status = scenario.isFailed() ? "FAILED" : "PASSED";
            }
        } catch (NoSuchMethodException nsme) {
            status = scenario.isFailed() ? "FAILED" : "PASSED";
        } catch (Exception e) {
            status = scenario.isFailed() ? "FAILED" : "PASSED";
        }

        try {
            if (ExtentTestManager.getTest() != null) {
                if ("FAILED".equalsIgnoreCase(status)) {
                    ExtentTestManager.getTest().fail("Scenario Failed: " + scenario.getName());
                    // ========== ENHANCEMENT: Attach summary on failure ==========
                    AllureManager.attachTestResultSummary(scenario.getName(), "FAILED");
                } else if ("SKIPPED".equalsIgnoreCase(status) || "UNKNOWN".equalsIgnoreCase(status)) {
                    ExtentTestManager.getTest().skip("Scenario Skipped: " + scenario.getName());
                } else {
                    ExtentTestManager.getTest().pass("Scenario Passed: " + scenario.getName());
                    // ========== ENHANCEMENT: Attach summary on pass ==========
                    AllureManager.attachTestResultSummary(scenario.getName(), "PASSED");
                }
            }
        } catch (Exception e) {
            LogUtils.error("Failed to log scenario status to ExtentReport: " + e.getMessage());
        }

        LogUtils.info("SCENARIO FINISHED: " + scenario.getName());
        LogUtils.info("SCENARIO STATUS: " + status);

        DriverManager.quit();
        LogUtils.info(SEPARATOR + "\n");

        TestContext.reset();
    }

}
