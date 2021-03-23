package pages;

import application.ApplicationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class HomePage extends BasePage {

    By popularProductsLocator = By.xpath("//*[@id='box-popular-products']/div/article");
    By addToCartButtonLocator = By.xpath("//button[@name='add_cart_product']");

    public HomePage(ApplicationContext app) {
        super(app);
    }

    public void pickRandomDuckInPopularProducts() {

        List<WebElement> popularProducts = driver.findElements(popularProductsLocator);
        popularProducts.get(new Random().nextInt(popularProducts.size())).click();// Actions.moveTo(element).pause(500).click();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator));
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductsQuantityLocator));
        driver.findElement(cartProductsQuantityLocator).click();
        return new CartPage(applicationContext);
    }

    public HomePage open() {
        driver.get(applicationContext.getBaseUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(popularProductsLocator));
        return this;
    }
}
