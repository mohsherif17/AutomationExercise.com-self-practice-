package Utils.Actions;

import Utils.Logs.LogsManager;
import Utils.WaitManger;
import org.openqa.selenium.WebDriver;

public class AlertActionManger {
    private final WebDriver driver;
    private final WaitManger waitManger ;
    public AlertActionManger(WebDriver driver)
    {
        this.driver = driver;
        this.waitManger=new WaitManger(driver);
    }
    public void popUpAccept() {
        waitManger.getFluentWait().until(d->
                {
                    try{
                        d.switchTo().alert().accept();
                        return true;
                    }catch (Exception e)
                    {
                        LogsManager.error( "Failed to accept alert: ", e.getMessage());
                        return false;
                    }
                }
        );
    }
    public void popUpDismiss() {

        waitManger.getFluentWait().until(d->
                {
                    try{
                        d.switchTo().alert().dismiss();
                        return true;
                    }catch (Exception e)
                    {
                        LogsManager.error( "Failed to dismiss alert: ", e.getMessage());
                        return false;
                    }
                }
        );
    }
    public void popUpSendKeys(String text)
    {
        waitManger.getFluentWait().until(d->
                {
                    try{
                        d.switchTo().alert().sendKeys(text);
                        return true;
                    }catch (Exception e)
                    {
                        LogsManager.error( "Failed to send keys to alert: ", e.getMessage());
                        return false;
                    }
                }
        );
    }
    public String getPopUpText()
    {
        return waitManger.getFluentWait().until(d->
                {
                    try{
                        return !d.switchTo().alert().getText().isEmpty()? d.switchTo().alert().getText(): null;
                    }catch (Exception e)
                    {
                        LogsManager.error( "Failed to get text from alert: ", e.getMessage());
                        return null;
                    }
                }
        );
    }
}
