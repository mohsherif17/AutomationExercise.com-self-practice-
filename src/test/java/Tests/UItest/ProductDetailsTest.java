package Tests.UItest;

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
public class ProductDetailsTest extends BaseTest {
    GUIDriver driver;
    NavigationBarComponant navBar;
   @Test
   public void addReviewTC(){
       new ProductsPage(driver).NavigateToProductPage()
               .viewProductDetails(testData.getJsonData("product1.name"))
               .addReview(testData.getJsonData("review.name"),
                       testData.getJsonData("review.email"),
                       testData.getJsonData("review.reviewMsg"))
               .verifyReviewMessage(testData.getJsonData("message"));
   }
   @Test
   public void checkDetailsTC(){
       new ProductsPage(driver).NavigateToProductPage()
               .viewProductDetails(testData.getJsonData("product1.name"))
               .verifyProductDetails(testData.getJsonData("product1.name"),testData.getJsonData("product1.price"));

   }

    //configuration
    @BeforeClass
    void beforeClassConditions(){
        testData = new JsonReader("ProductDetails-Data");
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
