package Tests.UItest;

import Api.UserManagementApi;
import ComponanatManger.CartPage;
import ComponanatManger.CheckOutPage;
import ComponanatManger.NavigationBarComponant;
import ComponanatManger.ProductsPage;
import Drivers.GUIDriver;
import Drivers.UITest;
import Tests.BaseTest;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import Utils.Logs.TimeManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@UITest
public class PaymentTest extends BaseTest {
    GUIDriver driver;
    NavigationBarComponant navBar;
    String extension = TimeManager.getSimpleTimeStamp();

    @Test
    public void RegisterAnewAccount() {
        navBar.navigateToHOmePAge();
        new UserManagementApi().createRegisterUserAcc(testData.getJsonData("name"),
                testData.getJsonData("customer.email") + extension + "@gmail.com",
                testData.getJsonData("customer.title"),
                testData.getJsonData("customer.password"),
                testData.getJsonData("customer.day"),
                testData.getJsonData("customer.month"),
                testData.getJsonData("customer.year"),
                testData.getJsonData("customer.FName"),
                testData.getJsonData("customer.LName"),
                testData.getJsonData("customer.company"),
                testData.getJsonData("customer.address1"),
                testData.getJsonData("customer.address2"),
                testData.getJsonData("customer.country"),
                testData.getJsonData("customer.state"),
                testData.getJsonData("customer.city"),
                testData.getJsonData("customer.zipCode"),
                testData.getJsonData("customer.mobileNumber")).verifyAccCreated();
    }

    @Test(dependsOnMethods = "RegisterAnewAccount")
    public void loginToAccount() {
        navBar.clickOnLoginSignUpLink().enterLoginEmail(testData.getJsonData("customer.email") + extension + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("customer.password")).submitLoginForm().VerifyLoginSuccessful();

    }

    @Test(dependsOnMethods = {"loginToAccount", "RegisterAnewAccount"})
    public void addToCart() {
        new ProductsPage(driver).NavigateToProductPage().addProductToCart(testData.getJsonData("product1.name"))
                .viewCart().validateElementInCart(testData.getJsonData("product1.name"),
                        testData.getJsonData("product1.price"),
                        testData.getJsonData("product1.quantity"),
                        testData.getJsonData("product1.total")).
                proceedToCheckOut();
    }

    @Test(dependsOnMethods = {"addToCart", "loginToAccount", "RegisterAnewAccount"})
    public void CheckOutPageTest() {
        new CheckOutPage(driver).validateBillingAddress(testData.getJsonData("customer.title"),
                        testData.getJsonData("customer.FName"),
                        testData.getJsonData("customer.LName"),
                        testData.getJsonData("customer.company"),
                        testData.getJsonData("customer.address1"),
                        testData.getJsonData("customer.address2"),
                        testData.getJsonData("customer.city"),
                        testData.getJsonData("customer.state"),
                        testData.getJsonData("customer.zipCode"),
                        testData.getJsonData("customer.country"),
                        testData.getJsonData("customer.mobileNumber")).validateDeliveryAddress(testData.getJsonData("customer.title"),
                        testData.getJsonData("customer.FName"),
                        testData.getJsonData("customer.LName"),
                        testData.getJsonData("customer.company"),
                        testData.getJsonData("customer.address1"),
                        testData.getJsonData("customer.address2"),
                        testData.getJsonData("customer.city"),
                        testData.getJsonData("customer.state"),
                        testData.getJsonData("customer.zipCode"),
                        testData.getJsonData("customer.country"),
                        testData.getJsonData("customer.mobileNumber")).
                validateElementInCart(testData.getJsonData("product1.name"),
                        testData.getJsonData("product1.price"),
                        testData.getJsonData("product1.quantity"),
                        testData.getJsonData("product1.total"))
                .validateCartTotal(testData.getJsonData("product1.total"));

    }

    @Test(dependsOnMethods = {"addToCart", "loginToAccount", "RegisterAnewAccount", "CheckOutPageTest"})
    public void paymentTest() {
        new CheckOutPage(driver).clickProceedTOPayment()
                .fillCardData(
                        testData.getJsonData("card.name"),
                        testData.getJsonData("card.number"),
                        testData.getJsonData("card.cvv"),
                        testData.getJsonData("card.expMonth"),
                        testData.getJsonData("card.expYear"))
                .submitPayment().validatePayment("ORDER PLACED!").downloadInvoice();
    }


    @BeforeClass
    void beforeClassConditions() {
        testData = new JsonReader("Payment-Data");
        driver = new GUIDriver();
        navBar = new NavigationBarComponant(driver);
        navBar.navigateToHOmePAge();
        LogsManager.info("before test register test");
    }

    @AfterClass
    public void TearDown() {
        driver.quitDriver();
        LogsManager.info("after test register test");
    }
}
