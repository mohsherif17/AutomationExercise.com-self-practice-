package Drivers;

import Utils.DataReader.PropertyReader;
import Utils.Logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class Chromefactory extends AbstractDriver {
    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-gpu");
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("localHeadless")||
                (PropertyReader.getProperty("executionType").equalsIgnoreCase("remote")))
        {
            options.addArguments("--headless");
        }
        return options;
    }

    @Override
    public WebDriver createDriver()  {

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("localHeadless") ||
                (PropertyReader.getProperty("executionType").equalsIgnoreCase("local"))) {
            return new ChromeDriver(getOptions());
        } else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("remote")) {

            try {
                return new RemoteWebDriver(
                        new URI("https://" + PropertyReader.getProperty("remoteHost") + PropertyReader.getProperty("remotePort") + "/wd/hub").toURL(), getOptions()
                );
            } catch (Exception e) {
                LogsManager.error("failed to create remote web driver");
                throw new RuntimeException();
            }

        } else {
            LogsManager.error("invalid execution Type");
            throw new IllegalArgumentException("invalid execution Type");
        }
    }
}
