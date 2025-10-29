package Tests.UItest;

import ComponanatManger.CartPage;
import ComponanatManger.NavigationBarComponant;
import ComponanatManger.ProductsPage;
import Drivers.GUIDriver;
import Drivers.UITest;
import Tests.BaseTest;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
@UITest
public class CartTest extends BaseTest {
    GUIDriver driver;
    NavigationBarComponant navBar;
@Test
public void checkCartTC(){
new ProductsPage(driver).NavigateToProductPage()
        .addProductToCart(testData.getJsonData("product1.name")).continueShopping()
        .addProductToCart(testData.getJsonData("product2.name")).viewCart()
        .validateElementInCart(
        testData.getJsonData("product1.name"),
        testData.getJsonData("product1.price"),
        testData.getJsonData("product1.quantity"),
        testData.getJsonData("product1.total"));
}

    //configuration
    @BeforeClass
    void beforeClassConditions(){
        testData = new JsonReader("Product-Data");
    }
    @BeforeMethod
    public void SetUp(){
        driver= new GUIDriver();
        navBar = new NavigationBarComponant(driver);
        navBar.navigateToHOmePAge();
        LogsManager.info("before test register test");
    }
    @AfterMethod
    public void TearDown(){
        driver.quitDriver();
        LogsManager.info("after test register test");
    }
}
