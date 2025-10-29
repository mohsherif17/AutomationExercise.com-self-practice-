package Utils.Actions;

import Utils.Logs.LogsManager;
import Utils.WaitManger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActionManger {
    private final WebDriver driver;
    private final WaitManger waitManger;

    public FrameActionManger(WebDriver driver) {
        this.driver = driver;
        this.waitManger = new WaitManger(driver);

    }

    public void switchToFrameByIndex(int index) {
        waitManger.getFluentWait().until(d -> {
            try {
                d.switchTo().frame(index);
                LogsManager.info( "Switched to frame by Index successfully: ", String.valueOf(index));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void switchToFrameByNameOrId(String nameOrId) {
        waitManger.getFluentWait().until(d -> {
            try {
                d.switchTo().frame(nameOrId);
                LogsManager.info( "Switched to frame by Name or ID successfully: ", nameOrId);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void switchToFrameByWebElement(By frameElement) {
        waitManger.getFluentWait().until(d -> {
            try {
                d.switchTo().frame(d.findElement(frameElement));
                LogsManager.info( "Switched to frame by WebElement successfully: ", frameElement.toString());
                return true;
            } catch (Exception e) {;
                return false;
            }
        });
    }

    public void switchToDefaultContent() {
        waitManger.getFluentWait().until(d -> {
            try {
                d.switchTo().defaultContent();
                LogsManager.info( "Switched to default content successfully.");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
