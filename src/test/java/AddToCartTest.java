import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class AddToCartTest extends BaseTest {

    By cartProductsQuantityLocator = By.xpath("//div[@class='badge quantity']");
    By popularProductsLocator = By.xpath("//*[@id='box-popular-products']/div/article");
    By goHome = By.cssSelector("ul.breadcrumb li a[href='/']");

    @BeforeTest
    void setup() {
        driver.get(pageUrl);
    }

    @Test
    public void addToCartTest() {
        for(int i =0; i<3; i++){
            addProductToCart();
            driver.findElement(goHome).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(popularProductsLocator));
        }
        driver.findElement(cartProductsQuantityLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("box-checkout-cart")));
        removeElementsFromCart(driver.findElements(By.cssSelector("li.item")));
        Assert.assertEquals(driver.findElements(By.cssSelector("li.item")).size(), 0,
                "The cart is not emplty after attempt ot remove all items");
    }

    private int getCartProductsQuantity() {
        String quantity = driver.findElement(cartProductsQuantityLocator).getText();
        return quantity.isEmpty() ? 0 : Integer.parseInt(quantity);
    }

    private void addProductToCart(){
        By acceptCookiesButton = By.xpath("//button[@name='accept_cookies']");
        By addToCartButtonLocator = By.xpath("//button[@name='add_cart_product']");
        List<WebElement> popularProducts = driver.findElements(popularProductsLocator);
        popularProducts.get(new Random().nextInt(popularProducts.size())).click();
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator));
        int quantity = getCartProductsQuantity();
        try {
            addToCartButton.click();
        } catch (ElementClickInterceptedException e) {
            driver.findElement(acceptCookiesButton).click();
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator)).click();
        }
        waitForCartQuantityIncremented(quantity, 5000);
    }

    private void waitForCartQuantityIncremented(int quantity, long timeOut) {
        int newQuantity = quantity;
        long startTime = System.currentTimeMillis();
        while (newQuantity == quantity && System.currentTimeMillis() - startTime < timeOut) {
            newQuantity = getCartProductsQuantity();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        if(newQuantity == quantity)
            throw new TimeoutException();
    }

    private void removeElementsFromCart(List<WebElement> cartItems){
        String cartItemXpath = "//*[@id='box-checkout-cart']/ul/li[%s]";
        for(int i = cartItems.size(); i >=1; i--){
            WebElement cartElement = driver.findElement(By.xpath(String.format(cartItemXpath, i)));
            driver.findElement(By.xpath(String.format(cartItemXpath, i).concat("//button[@name='remove_cart_item']"))).click();
            wait.until(ExpectedConditions.stalenessOf(cartElement));
        }
    }
}
