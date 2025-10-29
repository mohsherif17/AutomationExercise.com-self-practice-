package ComponanatManger;

import Drivers.Browser;
import Drivers.GUIDriver;
import Utils.DataReader.PropertyReader;
import Validations.Validation;
import Validations.Verification;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import javax.lang.model.element.Element;

public class ProductsPage {
    public GUIDriver driver;
    public NavigationBarComponant navBar;

    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        navBar = new NavigationBarComponant(driver);
    }
String productPageEndPoint="/products";
    private final By searchField = new By.ById("search_product");
    private final By searchBtn = new By.ById("submit_search");

    private final By viewCart= new By.ByCssSelector("p > [href=\"/view_cart\"]");


    private final By itemAddedLabel = new By.ByCssSelector(".modal-body > p");
    private final By continueShopping = new By.ByCssSelector(".modal-footer > button");

    private By productName(String productName) {
        return By.xpath(String.format("//div[@class='overlay-content']/p[normalize-space(text())='%s']", productName));
    }

    private By productPrice(String productName) {
        return By.xpath(String.format("//div[@class='overlay-content']/p[normalize-space(text())='%s']/preceding-sibling::h2", productName));
    }

    private By hoverOnElement(String productName) {
        return By.xpath(String.format("//div[@class='productinfo text-center']/p[normalize-space(text())='%s']", productName));
    }

    private By addToCart(String productName) {
        return By.xpath(String.format(
                "//div[@class='overlay-content']/p[normalize-space(text())='%s']/following-sibling::a[contains(@class,'add-to-cart')]",
                productName
        ));
    }
    private By viewProduct(String productName) {
        return By.xpath(String.format("//p[normalize-space(text())='%s']/following::div[@class='choose'][1]", productName));
    }
    //actions
    public  ProductsPage NavigateToProductPage(){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb")+productPageEndPoint);
        return this;
    }
    @Step("search for Product {ProductName}")
    public ProductsPage searchProduct(String ProductName){
        driver.element().type(searchField,ProductName).click(searchBtn);
        return this;
    }
    @Step("Add Product To Cart ")
    public ProductsPage addProductToCart(String ProductName){
        driver.element().hover(hoverOnElement(ProductName)).click(addToCart(ProductName));
        return this;
    }
    @Step("view product details ")
    public ProductDetilsPage viewProductDetails(String ProductName){
        driver.element().click(viewProduct(ProductName));
        return new ProductDetilsPage(driver);
    }
    @Step("view Cart")
    public CartPage viewCart(){
        driver.element().click(viewCart);
        return new CartPage(driver);
    }
    @Step("continue Shopping")
    public ProductsPage continueShopping(){
        driver.element().click(continueShopping);
        return this;
    }

    @Step("validate product details after search")
    public ProductsPage validateSearch(String productName,String Price){
       String AcctualName= driver.element().hover(productName(productName)). getText(productName(productName)) ;
       String AcctualPrice= driver.element().hover(productPrice(productName)).getText(productPrice(productName));
        driver.validation().equals(AcctualName,
                productName,
                "error product name miss match");
        driver.validation().equals(AcctualPrice,
                Price,
                "error product price miss match");

        return this;
    }
    @Step("validate item added to cart")
    public ProductsPage validateAddToCart(String expectedText){
        String acctualText=driver.element().getText(itemAddedLabel);
        driver.verification().equals(acctualText,expectedText,"product is not added successfully" );
        return this;
    }
}
