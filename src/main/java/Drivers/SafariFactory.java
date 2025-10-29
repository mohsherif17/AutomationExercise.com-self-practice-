package Drivers;

import Utils.DataReader.PropertyReader;
import Utils.Logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URI;

public class SafariFactory  extends AbstractDriver
{
    private SafariOptions getOptions() {
        SafariOptions options = new SafariOptions();
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    @Override
    public WebDriver createDriver() {

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("localHeadless") ||
                (PropertyReader.getProperty("executionType").equalsIgnoreCase("local"))) {
            return new SafariDriver(getOptions());
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
