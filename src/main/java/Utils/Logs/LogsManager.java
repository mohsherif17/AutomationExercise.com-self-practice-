package Utils.Logs;

import Utils.Reportes.AllureConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsManager {

    public static final String LOGS_PATH = AllureConstants.USER_DIR + "/test-output/Logs/";
    private static final ThreadLocal<StringBuilder> testLogs = ThreadLocal.withInitial(StringBuilder::new);

    private static Logger logger() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static void info(String... message) {
        String msg = String.join(" ", message);
        logger().info(msg);
        append("[INFO] " + msg);
    }

    public static void warn(String... message) {
        String msg = String.join(" ", message);
        logger().warn(msg);
        append("[WARN] " + msg);
    }

    public static void error(String... message) {
        String msg = String.join(" ", message);
        logger().error(msg);
        append("[ERROR] " + msg);
    }

    public static void fatal(String... message) {
        String msg = String.join(" ", message);
        logger().fatal(msg);
        append("[FATAL] " + msg);
    }

    public static void debug(String... message) {
        String msg = String.join(" ", message);
        logger().debug(msg);
        append("[DEBUG] " + msg);
    }

    private static void append(String message) {
        testLogs.get().append(message).append("\n");
    }

    public static String getAllLogs() {
        return testLogs.get().toString();
    }

    public static void clearLogs() {
        testLogs.remove();
    }
}
