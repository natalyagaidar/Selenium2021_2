package pages;

import application.ApplicationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DuckPage extends BasePage {

    By goHome = By.cssSelector("ul.breadcrumb li a[href='/']");
    By addToCartButtonLocator = By.xpath("//button[@name='add_cart_product']");


    public DuckPage(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    public DuckPage addProductAndGoHome() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator));
        addProductToCart();
        return this;
    }

    private void addProductToCart() {
        WebElement addToCartButton = driver.findElement(addToCartButtonLocator);
        int quantity = getCartProductsQuantity();
        try {
            addToCartButton.click();
        } catch (ElementClickInterceptedException e) {
            driver.findElement(acceptCookiesButton).click();
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator)).click();
        }
        waitForCartQuantityIncremented(quantity, 5000);
    }

    public HomePage goToHomePage() {
        driver.findElement(goHome).click();
        return new HomePage(applicationContext);
    }


}
