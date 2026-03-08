package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import constants.ConstantGlobal;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extentReports = null;

    public synchronized static ExtentReports getExtentReports() {
        if (extentReports == null) {
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
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Framework Name", "Cucumber Selenium Java");
            extentReports.setSystemInfo("Author", ConstantGlobal.AUTHOR);
            extentReports.setSystemInfo("Browser", ConstantGlobal.BROWSER);
            extentReports.setSystemInfo("Environment", ConstantGlobal.ENV);
            extentReports.setSystemInfo("Version", "1.0");

            System.out.println("ExtentReport initialized at: " + reportPath);
            System.out.println("Report file will be created at: " + reportFile.getAbsolutePath());
        }

        return extentReports;
    }
}
