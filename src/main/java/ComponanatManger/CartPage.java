package ComponanatManger;

import Drivers.GUIDriver;
import Utils.DataReader.PropertyReader;
import Validations.Validation;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private final GUIDriver driver;
    private final NavigationBarComponant navBar;
    private final String cartPageEndPoint = "/view_cart";

    public CartPage(GUIDriver driver) {
        this.driver = driver;
        this.navBar = new NavigationBarComponant(driver);
    }

    // Locators
    private final By ProceedToCheckOut = By.xpath("//a[normalize-space()='Proceed To Checkout']");

    // Dynamic locators
    private By ProductName(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']");
    }

    private By ProductPrice(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']/ancestor::td/following-sibling::td[@class='cart_price']/p");
    }

    private By ProductQuantity(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']/ancestor::td/following-sibling::td[@class='cart_quantity']/button");
    }

    private By ProductTotal(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']/ancestor::td/following-sibling::td[@class='cart_total']/p");
    }

    private By removeProduct(String pName) {
        return By.xpath("//td[@class='cart_description']//a[normalize-space(text())='" + pName + "']/ancestor::td/following-sibling::td[@class='cart_delete']/a");
    }

    // Actions
    @Step("Navigate to cart page")
    public CartPage NavigateToCartPage() {
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb") + cartPageEndPoint);
        return this;
    }

    @Step("Remove element from cart")
    public CartPage deleteElement(String product) {
        driver.element().click(removeProduct(product));
        return this;
    }

    @Step("Proceed to checkout")
    public CheckOutPage proceedToCheckOut() {
        driver.element().click(ProceedToCheckOut);
        return new CheckOutPage(driver);
    }

    // Validations
    @Step("Validate element in cart")
    public CartPage validateElementInCart(String expectedName,
                                          String expectedPrice,
                                          String expectedQuantity,
                                          String expectedTotal) {
        String elementName = driver.element().getText(ProductName(expectedName));
        String elementPrice = driver.element().getText(ProductPrice(expectedName));
        String elementQuantity = driver.element().getText(ProductQuantity(expectedName));
        String elementTotal = driver.element().getText(ProductTotal(expectedName));

        new Validation().equals(elementName, expectedName, "Product name mismatch");
        new Validation().equals(elementPrice, expectedPrice, "Product price mismatch");
        new Validation().equals(elementQuantity, expectedQuantity, "Product quantity mismatch");
        new Validation().equals(elementTotal, expectedTotal, "Product total mismatch");

        return this;
    }
}
