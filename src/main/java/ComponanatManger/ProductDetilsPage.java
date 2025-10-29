package ComponanatManger;

import Drivers.GUIDriver;
import Utils.DataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetilsPage {
    private final GUIDriver driver;

    ProductDetilsPage(GUIDriver driver) {
        this.driver = driver;
    }

    private final String productPageEndPoint = "product_details/";
    private final By productName = new By.ByCssSelector(".product-information>h2");
    private final By productPrice = new By.ByCssSelector(".product-information>span>span");
    private final By ReviewName = new By.ById("name");
    private final By ReviewEmail = new By.ById("email");
    private final By ReviewTextArea = new By.ById("review");
    private final By ReviewButton = new By.ById("button-review");
    private final By ReviewMessage = new By.ByCssSelector("#review-section span");


    //actions
    @Step("navigating to product details page")
    public ProductDetilsPage navigateToProductDetailsPage(String id) {
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb") + productPageEndPoint + id);
        return this;
    }

    @Step("adding review to a product")
    public ProductDetilsPage addReview(String name, String email, String review) {
        driver.element().type(ReviewName, name)
                .type(ReviewEmail, email)
                .type(ReviewTextArea, review)
                .click(ReviewButton);

        return this;
    }


    //validations
    @Step("verify that product details is right")
    public ProductDetilsPage verifyProductDetails(String expectedName, String expectedPrice) {
        String actualName = driver.element().getText(productName);
        String actualPrice = driver.element().getText(productPrice);
        driver.validation().equals(actualName, expectedName, "error wrong product name" + actualName);
        driver.validation().equals(actualPrice, expectedPrice, "error wrong product price" + actualPrice);
        return this;
    }

    @Step("verify review is added successfully")
    public ProductDetilsPage verifyReviewMessage(String expectedMessage) {
        String acctualMessage = driver.element().getText(ReviewMessage);
        driver.verification().equals(acctualMessage, expectedMessage, "review not added");
        return this;
    }
}
