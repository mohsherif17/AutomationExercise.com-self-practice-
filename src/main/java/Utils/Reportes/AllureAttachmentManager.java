package Utils.Reportes;

import Utils.Logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureAttachmentManager {

    /**
     * Attach screenshot to Allure report
     */
    public static void attachScreenshot(String name, String path) {
        try {
            Path screenshot = Path.of(path);
            if (Files.exists(screenshot)) {
                Allure.addAttachment(name, Files.newInputStream(screenshot));
                LogsManager.info("📸 Screenshot attached to Allure: " + path);
            } else {
                LogsManager.warn("⚠️ Screenshot file not found: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("❌ Failed to attach screenshot: " + e.getMessage());
        }
    }

    /**
     * Attach logs to Allure report
     */
    public static void attachLogs() {
        try {
            // Close Log4j appenders to release file locks
            LogManager.shutdown();
            ((LoggerContext) LogManager.getContext(false)).reconfigure();

            File logFile = new File(LogsManager.LOGS_PATH + "logs.log");
            if (logFile.exists()) {
                Allure.addAttachment("📜 Execution Logs (logs.log)", Files.newInputStream(logFile.toPath()));
                LogsManager.info("🟢 Logs attached to Allure report successfully");
            } else {
                LogsManager.warn("⚠️ Log file not found at: " + logFile.getAbsolutePath());
            }
        } catch (Exception e) {
            LogsManager.error("❌ Error attaching logs to Allure report: " + e.getMessage());
        }
    }

}
