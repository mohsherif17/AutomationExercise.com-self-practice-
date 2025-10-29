package Utils.Reportes;

import Utils.Logs.LogsManager;
import Utils.Logs.TimeManager;
import Utils.OSUtils;
import Utils.TerminalUtils;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static Utils.DataReader.PropertyReader.getProperty;
import static Utils.FileUtils.RenameFile;
import static Utils.Reportes.AllureConstants.HISTORY_FOLDER;
import static Utils.Reportes.AllureConstants.RESULTS_FOLDER;

public class AllureReportGenerator {

    //Generate Allure report
    //--single-file
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORT_PATH;
        Path resultsFolder = AllureConstants.RESULTS_FOLDER;
        Path resultsHistory = AllureConstants.RESULTS_HISTORY_FOLDER;
        Path reportsRoot = AllureConstants.REPORT_PATH.getParent(); // test-output folder

        try {
            // 1️⃣ Find latest existing report with history
            File latestReportDir = findLatestReportWithHistory(reportsRoot.toFile());
            if (latestReportDir != null) {
                File historySrc = new File(latestReportDir, "history");
                File historyDest = resultsHistory.toFile();
                org.apache.commons.io.FileUtils.copyDirectory(historySrc, historyDest);
                System.out.println("📈 Copied history from previous report: " + latestReportDir.getName());
            } else {
                System.out.println("ℹ️ No previous report history found — starting trend fresh.");
            }

            // 2️⃣ Build Allure command
            List<String> command = new ArrayList<>(List.of(
                    AllureBinaryManager.getExecutable().toString(),
                    "generate",
                    resultsFolder.toString(),
                    "-o",
                    outputFolder.toString(),
                    "--clean"
            ));
            if (isSingleFile) command.add("--single-file");

            // 3️⃣ Run Allure command
            TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));

            // 4️⃣ Copy new history back for next run
            File newHistory = new File(outputFolder.toFile(), "history");
            if (newHistory.exists()) {
                File historyBackup = new File(reportsRoot.toFile(), "latest-history");
                org.apache.commons.io.FileUtils.copyDirectory(newHistory, historyBackup);
                System.out.println("✅ History preserved for future trend tracking.");
            }

            System.out.println("📊 Allure report generated successfully with trends support.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to generate Allure report with trend tracking.");
        }
    }

    /**
     * Helper method — finds the latest report folder containing a history subfolder.
     */
    private static File findLatestReportWithHistory(File reportsRoot) {
        if (!reportsRoot.exists()) return null;

        File[] candidates = reportsRoot.listFiles((dir, name) ->
                name.startsWith("allure-report") && new File(dir, name + "/history").exists()
        );

        if (candidates == null || candidates.length == 0) return null;

        return Arrays.stream(candidates)
                .filter(File::isDirectory)
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
    }

    //rename report file to AllureReport_timestamp.html
    public static String renameReport() {
        String newFileName = AllureConstants.REPORT_PREFIX
                + TimeManager.getTimeStamp()
                + AllureConstants.REPORT_EXTENSION;

        Path reportDir = AllureConstants.REPORT_PATH;
        Path sourceFile = reportDir.resolve(AllureConstants.INDEX_HTML);
        Path targetFile = reportDir.resolve(newFileName);

        try {
            // Wait until Allure finishes generating the report
            int attempts = 0;
            while (!Files.exists(sourceFile) && attempts < 15) {
                LogsManager.info("Waiting for Allure report generation...");
                Thread.sleep(1000);
                attempts++;
            }

            if (!Files.exists(sourceFile)) {
                LogsManager.error("index.html not found in: " + reportDir);
                return null;
            }

            Files.move(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            LogsManager.info("Allure report renamed successfully to: " + newFileName);

            return newFileName;

        } catch (Exception e) {
            LogsManager.error("Failed to rename Allure report! " + e.getMessage());
            return null;
        }
    }



    //open Allure report in browser
    public static void openReport(String reportFileName) {
        try {
            Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
            File file = reportPath.toFile();

            if (!file.exists()) {
                LogsManager.error("Report file not found: " + file.getAbsolutePath());
                return;
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(file.toURI());
                LogsManager.info("Allure report opened successfully: " + file.getAbsolutePath());
            } else {
                LogsManager.error("Desktop browsing is not supported on this system.");
            }

        } catch (IOException e) {
            LogsManager.error("Failed to open Allure report automatically! " + e.getMessage());
        }
    }

    //copy history folder to results folder
    public static void copyHistory(){
        try{
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULTS_FOLDER.toFile());
        }
        catch (Exception e){
            LogsManager.error("Error while copying history folder to results folder" , e.getMessage());
        }
    }
}
