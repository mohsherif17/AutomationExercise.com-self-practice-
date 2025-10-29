package Tests.UItest;

import ComponanatManger.NavigationBarComponant;
import ComponanatManger.ProductsPage;
import Drivers.GUIDriver;
import Drivers.UITest;
import Tests.BaseTest;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Test Cases for Product Page")
@Feature("Ui user Management")
@Story("product page")
@Severity(SeverityLevel.CRITICAL)
@Owner("Muhammed Sherif")
@UITest
public class ProductPageTest extends BaseTest {
    GUIDriver driver;
    NavigationBarComponant navBar;


    @Test
    public void addProductTC(){
        new ProductsPage(driver).NavigateToProductPage().addProductToCart(testData.getJsonData("product1.name"))
                .validateAddToCart("Your product has been added to cart.");
    }

@Test
public void searchForProductTc(){
        new ProductsPage(driver).NavigateToProductPage()
                .searchProduct(testData.getJsonData("product2.name"))
                .validateSearch(testData.getJsonData("product2.name"),
                        testData.getJsonData("product2.price"));
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
