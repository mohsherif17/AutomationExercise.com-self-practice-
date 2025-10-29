package Media;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenShotsManger {
    public static final String SCREENSHOT_PATH = "/test-output/ScreenShots/";
    // Takes a screenshot and returns the file path, or null if it fails
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folderPath = "test-output/screenshots/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String destPath = folderPath + testName + ".png";
            File destFile = new File(destPath);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("📸 Screenshot saved at: " + destPath);
            return destPath;
        } catch (IOException e) {
            System.err.println("⚠️ Failed to save screenshot: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("⚠️ Unexpected error while taking screenshot: " + e.getMessage());
            return null;
        }
    }
}
