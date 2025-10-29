package Listeners;

import Drivers.GUIDriver;
import Media.ScreenShotsManger;
import Utils.AllureUtils;
import Utils.DataReader.PropertyReader;
import Utils.FileUtils;
import Utils.Logs.LogsManager;
import Utils.Reportes.AllureConstants;
import Utils.Reportes.AllureEnvironmentManager;
import Utils.Reportes.AllureReportGenerator;
import Validations.Validation;
import org.testng.*;


import java.io.File;


public class TestNGListener implements ISuiteListener,IInvokedMethodListener, ITestListener, IExecutionListener {

    public void onStart(ISuite suite) {
suite.getXmlSuite().setName("automation exercise");
    }
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            LogsManager.info(method.getTestMethod().getMethodName() + " about to execute");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {

            Validation.assertAll(testResult);
            try {
                // Take screenshot
                String screenshotPath = ScreenShotsManger.takeScreenshot(GUIDriver.get(), testResult.getName());
                //AllureUtils.attachLogsAllure(testResult.getName());
                // Attach screenshot to Allure
                if (screenshotPath != null && !screenshotPath.isEmpty()) {
                    Utils.Reportes.AllureAttachmentManager.attachScreenshot("Screenshot - " + testResult.getName(), screenshotPath);
                }

                LogsManager.info(method.getTestMethod().getMethodName() + " Finished");

            } catch (Exception e) {
                System.err.println("Error taking or attaching screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsManager.info(result.getMethod().getMethodName() + " passed");
        AllureUtils.attachLogsAllure(result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsManager.info(result.getMethod().getMethodName() + " failed");
        AllureUtils.attachLogsAllure(result.getName());

        ScreenShotsManger.takeScreenshot(GUIDriver.get(), result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsManager.info(result.getMethod().getMethodName() + " skipped");
        AllureUtils.attachLogsAllure(result.getName());
    }

    @Override
    public void onExecutionStart() {
        LogsManager.info("Execution started");
        cleanTestOutputDirectories();
        LogsManager.info("Test output directories cleaned");
        createTestOutputDirectories();
        LogsManager.info("Test output directories created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentInfo();
        LogsManager.info("Allure environment info set");
    }


    @Override
    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Execution finished");
    }
    private void cleanTestOutputDirectories(){
        //FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.forceClean(new File(ScreenShotsManger.SCREENSHOT_PATH));
        FileUtils.forceClean(new File(LogsManager.LOGS_PATH+"/logs.log"));
    }

    private void createTestOutputDirectories(){
        FileUtils.createDirectory(ScreenShotsManger.SCREENSHOT_PATH);
    }
}
