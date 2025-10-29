package Tests;


import Drivers.GUIDriver;
import Drivers.WebDriverProvider;
import Utils.AllureUtils;
import Utils.DataReader.JsonReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest implements WebDriverProvider {

    protected GUIDriver driver;
    public JsonReader testData;



    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
