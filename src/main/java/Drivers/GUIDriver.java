package Drivers;

import Utils.Actions.AlertActionManger;
import Utils.Actions.BrowserActionManger;
import Utils.Actions.ElementActionManger;
import Utils.Actions.FrameActionManger;
import Utils.DataReader.PropertyReader;
import Utils.Logs.LogsManager;
import Validations.Validation;
import Validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final String browserType= PropertyReader.getProperty("browserType");

    private static ThreadLocal<WebDriver> driverThreadLocal= new ThreadLocal<>();


    public GUIDriver(){
        Browser browser= Browser.valueOf(browserType.toUpperCase());
        LogsManager.info("Initializing {} driver", browserType);
        AbstractDriver abstractDriver=browser.getDriverFactory();
        WebDriver driver= ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementActionManger element(){
        return new ElementActionManger(get());
    }

    public BrowserActionManger browser(){
        return new BrowserActionManger(get());
    }

    public FrameActionManger frame(){
        return new FrameActionManger(get());
    }

    public AlertActionManger alert(){
        return new AlertActionManger(get());
    }
    //Soft Assertions
    public Validation validation(){
        return new Validation(get());
    }

    //Hard Assertions
    public Verification verification(){
        return new Verification(get());
    }



    public static WebDriver get(){
        return driverThreadLocal.get();
    }
    public  void quitDriver(){
        driverThreadLocal.get().quit();
    }

}
