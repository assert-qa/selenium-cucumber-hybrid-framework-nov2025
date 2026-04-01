package reports;

import constants.ConstantGlobal;
import factory.DriverManager;
import helpers.SystemHelper;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AllureManager {
    // Text attachment
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    // HTML attachment
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    //Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    // ========== ENHANCEMENT: Environment Information ==========
    @Attachment(value = "Test Environment Information", type = "text/plain")
    public static String attachEnvironmentInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== TEST ENVIRONMENT INFORMATION ===\n\n");
        info.append("Environment: ").append(ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A").append("\n");
        info.append("Base URL: ").append(ConstantGlobal.BASE_URL != null ? ConstantGlobal.BASE_URL : "N/A").append("\n");
        info.append("Browser: ").append(ConstantGlobal.BROWSER != null ? ConstantGlobal.BROWSER : "N/A").append("\n");
        info.append("Headless Mode: ").append(ConstantGlobal.HEADLESS != null ? ConstantGlobal.HEADLESS : "N/A").append("\n");
        info.append("Execution Time: ").append(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))).append("\n");
        return info.toString();
    }

    // ========== ENHANCEMENT: User Account Information ==========
    @Attachment(value = "Test User Account Information", type = "text/plain")
    public static String attachUserAccountInfo() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("=== TEST USER ACCOUNT INFORMATION ===\n\n");
        userInfo.append("Email: ").append(ConstantGlobal.VALID_EMAIL != null ? ConstantGlobal.VALID_EMAIL : "N/A").append("\n");
        userInfo.append("Account Type: ").append(getAccountType()).append("\n");
        userInfo.append("Environment: ").append(ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A").append("\n");
        userInfo.append("Test Data Source: Per Environment Configuration\n");
        userInfo.append("Credentials Loaded From: env/").append(ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "unknown").append(".properties\n");
        return userInfo.toString();
    }

    // ========== ENHANCEMENT: System Configuration ==========
    @Attachment(value = "System Configuration", type = "text/plain")
    public static String attachSystemConfig() {
        StringBuilder config = new StringBuilder();
        config.append("=== SYSTEM CONFIGURATION ===\n\n");
        config.append("Operating System: ").append(SystemHelper.getOSName()).append("\n");
        config.append("Java Version: ").append(System.getProperty("java.version")).append("\n");
        config.append("Browser: ").append(ConstantGlobal.BROWSER).append("\n");
        config.append("Explicit Wait Timeout: ").append(ConstantGlobal.EXPLICIT_TIMEOUT).append(" seconds\n");
        config.append("Page Load Timeout: ").append(ConstantGlobal.PAGE_LOAD_TIMEOUT).append(" seconds\n");
        config.append("Hard Wait Timeout: ").append(ConstantGlobal.HARD_WAIT_TIMEOUT).append(" seconds\n");
        return config.toString();
    }

    // ========== ENHANCEMENT: Complete Test Context ==========
    @Attachment(value = "Complete Test Context Information", type = "text/plain")
    public static String attachCompleteTestContext() {
        StringBuilder context = new StringBuilder();
        context.append("=== COMPLETE TEST CONTEXT ===\n\n");
        context.append(attachEnvironmentInfo()).append("\n");
        context.append(attachUserAccountInfo()).append("\n");
        context.append(attachSystemConfig());
        return context.toString();
    }

    // ========== ENHANCEMENT: Test Result Summary ==========
    @Attachment(value = "Test Result Summary", type = "text/plain")
    public static String attachTestResultSummary(String testName, String status) {
        StringBuilder summary = new StringBuilder();
        summary.append("=== TEST RESULT SUMMARY ===\n\n");
        summary.append("Test Name: ").append(testName).append("\n");
        summary.append("Status: ").append(status).append("\n");
        summary.append("Executed By: ").append(ConstantGlobal.VALID_EMAIL != null ? ConstantGlobal.VALID_EMAIL : "N/A").append("\n");
        summary.append("Environment: ").append(ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A").append("\n");
        summary.append("Execution Date: ").append(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))).append("\n");
        summary.append("Browser: ").append(ConstantGlobal.BROWSER != null ? ConstantGlobal.BROWSER : "N/A").append("\n");
        return summary.toString();
    }

    // ========== HELPER METHOD ==========
    private static String getAccountType() {
        // ========== FIXED: Add null check ==========
        if (ConstantGlobal.ENV == null) {
            System.err.println("⚠️  WARNING: ConstantGlobal.ENV is NULL!");
            return "Unknown Account";
        }

        String env = ConstantGlobal.ENV.toLowerCase();
        if (env.contains("prod")) {
            return "Production Account";
        } else if (env.contains("staging")) {
            return "Staging Account";
        } else if (env.contains("dev")) {
            return "Development Account";
        }
        return "Test Account (" + env + ")";
    }
}
