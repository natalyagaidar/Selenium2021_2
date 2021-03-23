package pages;

import application.ApplicationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    public CartPage(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    public void removeAllElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("box-checkout-cart")));
        String cartItemXpath = "//*[@id='box-checkout-cart']/ul/li[%s]";
        for (int i = getCartItems().size(); i >= 1; i--) { //while there are no products in your cart
            WebElement cartElement = driver.findElement(By.xpath(String.format(cartItemXpath, i)));
            driver.findElement(By.xpath(String.format(cartItemXpath, i).concat("//button[@name='remove_cart_item']"))).click();
            wait.until(ExpectedConditions.stalenessOf(cartElement));
        }
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(By.cssSelector("li.item"));
    }
}
