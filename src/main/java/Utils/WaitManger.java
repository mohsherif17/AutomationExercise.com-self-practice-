package Utils;

import Utils.DataReader.PropertyReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

public class WaitManger {
    private WebDriver driver;
    public WaitManger(WebDriver driver) {
        this.driver = driver;
    }
    public FluentWait<WebDriver> getFluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("defaultWait"))))
                .pollingEvery(java.time.Duration.ofMillis(200))
                .ignoreAll(getExceptions());
    }

    private ArrayList<Class<? extends Exception>> getExceptions() {
        ArrayList<Class<? extends Exception>> exceptionsToIgnore = new ArrayList<>();
        exceptionsToIgnore.add(NoSuchElementException.class);
        exceptionsToIgnore.add(StaleElementReferenceException.class);
        exceptionsToIgnore.add(ElementNotInteractableException.class);
        exceptionsToIgnore.add(ElementClickInterceptedException.class);
        return exceptionsToIgnore;
    }
}
