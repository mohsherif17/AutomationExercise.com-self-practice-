package Utils.Actions;

import Utils.Logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActionManger {
    final private WebDriver driver;
    public BrowserActionManger(WebDriver driver)
    {
        this.driver = driver;
    }
    public void maximizeWindow()
    {
        driver.manage().window().maximize();
    }
    public String getCurrentUrl()
    {
        String url= driver.getCurrentUrl();
        LogsManager.info("Current URL: " , url);
        return url;
    }
    public void navigateToUrl(String url)
    {
        driver.get(url);
        LogsManager.info("Navigated to URL: ", url);
    }
    public void refreshPage()
    {
        driver.navigate().refresh();
    }
    public void closeCurrentTab()
    {
        driver.close();
    }
    public void openNewTab()
    {
        driver.switchTo().newWindow(WindowType.WINDOW);

    }
}
