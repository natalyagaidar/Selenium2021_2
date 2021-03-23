package pages;

import application.ApplicationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    WebDriver driver;
    WebDriverWait wait;
    ApplicationContext applicationContext;

    By cartProductsQuantityLocator = By.xpath("//div[@class='badge quantity']");
    By acceptCookiesButton = By.xpath("//button[@name='accept_cookies']");

    public BasePage(ApplicationContext appContext) {
        this.applicationContext = appContext;
        this.driver = appContext.getDriver();
        this.wait = new WebDriverWait(driver, 5);
    }

    public void waitForCartQuantityIncremented(int quantity, long timeOut) { // new quantity can be the number from the loop
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
        if (newQuantity == quantity)
            throw new TimeoutException();
    }

    public int getCartProductsQuantity() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductsQuantityLocator));
        } catch (TimeoutException e) {
            try {
                driver.findElement(acceptCookiesButton).click();
            } catch (NoSuchElementException message) {
            }
        }
        if (driver.findElement(By.xpath("//div[@id='content']")).getText().contains("There are no items in your cart."))
            return 0;
        String quantity = driver.findElement(cartProductsQuantityLocator).getText();
        return quantity.isEmpty() ? 0 : Integer.parseInt(quantity);
    }
}
