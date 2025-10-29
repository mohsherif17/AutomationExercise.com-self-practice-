package Utils;

import Utils.Logs.LogsManager;

public class TerminalUtils {
    public static void executeTerminalCommand(String... command) {
        try {
            Process process= Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogsManager.error("Command execution failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            LogsManager.error("Error while executing terminal command: " + e.getMessage());
        }
    }
}
