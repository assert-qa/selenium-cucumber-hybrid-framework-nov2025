package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import constants.ConstantGlobal;
import helpers.SystemHelper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extentReports = null;

    public synchronized static ExtentReports getExtentReports() {
        if (extentReports == null) {
            try{
                // Generate timestamp for unique report filename
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
                String timestamp = LocalDateTime.now().format(dtf);

                // Get absolute path
                String basePath = System.getProperty("user.dir");
                String relativePath = ConstantGlobal.EXTENT_REPORT_PATH.replace("ExtentReport.html",
                        "ExtentReport_" + timestamp + ".html");

                // Create absolute path
                String reportPath = basePath + File.separator + relativePath;

                // Create directory if not exists
                File reportFile = new File(reportPath);
                File reportDir = reportFile.getParentFile();
                if (!reportDir.exists()) {
                    boolean created = reportDir.mkdirs();
                    System.out.println("Directory created: " + created + " at " + reportDir.getAbsolutePath());
                }

                extentReports = new ExtentReports();
                ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
                reporter.config().setReportName("Extent Report for Automation Test");
                reporter.config().setDocumentTitle("Test Automation Report");

                // ========== Added reporter attachment ==========
                extentReports.attachReporter(reporter);

                // ========== FIXED: Added Null Check ==========
                extentReports.setSystemInfo("Framework Name", "Cucumber Selenium Java");
                extentReports.setSystemInfo("Author", ConstantGlobal.AUTHOR != null ? ConstantGlobal.AUTHOR : "N/A");
                extentReports.setSystemInfo("Browser", ConstantGlobal.BROWSER != null ? ConstantGlobal.BROWSER : "N/A");
                extentReports.setSystemInfo("Environment", ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A");
                extentReports.setSystemInfo("Version", "1.0");

                // ========== ENHANCEMENT: User Information ==========
                extentReports.setSystemInfo("Test User Email", ConstantGlobal.VALID_EMAIL != null ? ConstantGlobal.VALID_EMAIL : "N/A");
                extentReports.setSystemInfo("Test Account Type", getUserAccountType());
                extentReports.setSystemInfo("Test Data Source", "Per Environment - " +
                        (ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "unknown"));

                // ========== ENHANCEMENT: Execution Environment ==========
                extentReports.setSystemInfo("Operating System", SystemHelper.getOSName());
                extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
                extentReports.setSystemInfo("Report Generated", LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                extentReports.setSystemInfo("Headless Mode", ConstantGlobal.HEADLESS != null ? ConstantGlobal.HEADLESS.toString() : "N/A");
                extentReports.setSystemInfo("Page Load Timeout (seconds)", ConstantGlobal.PAGE_LOAD_TIMEOUT != null ? ConstantGlobal.PAGE_LOAD_TIMEOUT : "N/A");

                System.out.println("✅ ExtentReport initialized at: " + reportPath);
            }catch (Exception e){
                System.err.println("❌ Failed to initialize ExtentReport: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return extentReports;
    }

    // ========== ENHANCEMENT: Helper Methods for User Info ==========
    private static String getUserAccountType() {
        if (ConstantGlobal.ENV == null) {
            System.err.println("⚠️  WARNING: ConstantGlobal.ENV is NULL! Check if properties are loaded correctly.");
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

    public static String getFormattedUserInfo() {
        return String.format(
                "User Info:\n" +
                        "  - Email: %s\n" +
                        "  - Account Type: %s\n" +
                        "  - Environment: %s\n" +
                        "  - Test Data Source: Per Environment Configuration",
                ConstantGlobal.VALID_EMAIL != null ? ConstantGlobal.VALID_EMAIL : "N/A",
                getUserAccountType(),
                ConstantGlobal.ENV != null ? ConstantGlobal.ENV : "N/A"
        );
    }
}
