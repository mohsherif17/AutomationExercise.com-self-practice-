package Utils;

import Utils.DataReader.PropertyReader;
import Utils.Logs.LogsManager;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static java.nio.file.Files.newInputStream;

public class AllureUtils {

    private static final String RESULTS_DIR = System.getProperty(
            "allure.results.dir",
            "test-output/allure-results" // Default fallback
    );
    private static final String REPORT_DIR = "allure-report";

    // Clean allure-results folder before execution
    public static void cleanAllureResults() {

            File resultsDir = new File("test-output/allure-results");
            File historyDir = new File(resultsDir, "history");

            try {
                if (resultsDir.exists()) {
                    File tempHistory = new File("test-output/temp-history");
                    if (historyDir.exists()) {
                        FileUtils.copyDirectory(historyDir, tempHistory);
                    }

                    FileUtils.cleanDirectory(resultsDir);

                    if (tempHistory.exists()) {
                        FileUtils.copyDirectory(tempHistory, historyDir);
                        FileUtils.deleteDirectory(tempHistory);
                    }

                    System.out.println("🧹 Cleaned Allure results but kept history.");
                } else {
                    resultsDir.mkdirs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    // Attach screenshot to Allure report
    public static void attachScreenshotAllure(String screenName, String screenPath) {
        try {
            Path path = Path.of(screenPath);
            Allure.addAttachment(screenName, newInputStream(path));
            System.out.println("📸 Screenshot attached to Allure: " + screenName);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to attach screenshot: " + e.getMessage());
        }
    }
    public static void attachLogsAllure(String testName) {
        try {
            String logs = LogsManager.getAllLogs();
            if (logs == null || logs.isEmpty()) {
                logs = "No logs captured for this test.";
            }
            Allure.addAttachment(testName + " Logs", "text/plain", logs, ".txt");
            System.out.println("📝 Logs attached to Allure: " + testName);
        } catch (Exception e) {
            LogsManager.error("Failed to attach logs to Allure:", e.getMessage());
        }
    }
    // Set environment details in Allure report
    public static void setEnvironmentInfo() {
        try {
            allureEnvironmentWriter(
                    ImmutableMap.<String, String>builder()
                            .put("OS", System.getProperty("os.name"))
                            .put("Browser", System.getProperty("browserType", "Unknown"))
                            .put("JDK Version", System.getProperty("java.runtime.version"))
                            .put("URL", System.getProperty("baseUrlWeb", "Not defined"))
                            .build(),
                    PropertyReader.getProperty("user.dir") + File.separator + RESULTS_DIR + File.separator
            );
            System.out.println("🌍 Allure environment info set.");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to set Allure environment info: " + e.getMessage());
        }
    }
    public static void copyHistory() {
        try {
            File historySrc = new File(REPORT_DIR + "/history");
            File historyDest = new File(RESULTS_DIR + "/history");

            if (historySrc.exists()) {
                FileUtils.copyDirectory(historySrc, historyDest);
                System.out.println("📈 History copied to allure-results for trends.");
            } else {
                System.out.println("ℹ️ No previous history found — trends will start fresh.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

