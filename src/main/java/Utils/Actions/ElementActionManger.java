package Utils.Actions;

import Utils.Logs.LogsManager;
import Utils.WaitManger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementActionManger {
    private final WebDriver driver;
    private final WaitManger waitManger;

    public ElementActionManger(WebDriver driver) {
        this.driver = driver;
        this.waitManger = new WaitManger(driver);
    }

    //click method
    public ElementActionManger click(By locator) {
        waitManger.getFluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        Point init= element.getLocation();
                        Point end=element.getLocation();
                        if (!init.equals(end)){return false;}
                        element.click();
                        LogsManager.info( "Clicked on element successfully: ", locator.toString());
                        return true;
                    } catch (Exception e) {

                        return false;
                    }
                }
        );
        return this;
    }

    //send keys method
    public ElementActionManger type(By locator, String text) {
        waitManger.getFluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                element.clear();
                element.sendKeys(text);
                LogsManager.info( "Typed into element successfully: ", text);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

    //get text method
    public String getText(By locator) {
        return waitManger.getFluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                LogsManager.info( "Retrieved text from element successfully.", element.getText());
                return !element.getText().isEmpty() ? element.getText() : null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    //scroll to element method
    public ElementActionManger scrollToElement(By locator) {
        waitManger.getFluentWait().until(d ->
        {
            try {
                WebElement element= d.findElement(locator);
                scrollToElementJS(locator);
                LogsManager.info( "Scrolled to element successfully.", element.toString());
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }
    //scroll using js
    public void scrollToElementJS(By locator) {
        ((org.openqa.selenium.JavascriptExecutor)driver)
                .executeScript("""
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""", findElement(locator));
    }
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
    public ElementActionManger uploadFile(By locator, String filePath) {
        String absolutePath = System.getProperty("user.dir") +"/"+ filePath;
        waitManger.getFluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(absolutePath);
                LogsManager.info( "File uploaded successfully: ", absolutePath);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }
    public ElementActionManger selectFromDropDown(By locator,String option){
        waitManger.getFluentWait().until(d->{
            try {
                WebElement element= d.findElement(locator);
                scrollToElementJS(locator);
                Select value=new Select(element);
                value.selectByVisibleText(option);
                LogsManager.info(option+ "is selected successfully");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }
    public ElementActionManger hover(By locator){
        waitManger.getFluentWait().until(d->{
           try {
               WebElement element= d.findElement(locator);
               scrollToElementJS(locator);
               new Actions(d).moveToElement(element).perform();
               LogsManager.info("hover to element done",locator.toString());
               return true;
           }
           catch (Exception e){
              return false;
           }
        });
        return this;
    }
}
