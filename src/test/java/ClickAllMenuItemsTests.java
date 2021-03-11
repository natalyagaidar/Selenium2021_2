import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ClickAllMenuItemsTests extends BaseTest{

    By logout = By.cssSelector("#top-bar a[title='Logout']");

    @BeforeTest
    void setup() {
        driver.get(pageUrl.concat("admin/login.php"));
        login();
    }

    @AfterTest
    void after() {
        driver.quit();
    }

    @Test
    public void ClickAllMenuItemsTest(){
        By menuItemLocator = By.className("app");
        By subMenuItemLocator = By.className("doc");
        By subMenuItemXpathLocator = By.xpath("//li[contains(@class,'doc')]");
        By heading = By.className("panel-heading");
        By leftMenu = By.id("box-apps-menu");
        wait.until(ExpectedConditions.visibilityOfElementLocated(leftMenu));
        for(int i=0; i< driver.findElements(menuItemLocator).size(); i++){
            driver.findElements(menuItemLocator).get(i).click();
            Assert.assertTrue(isElementPresent(heading), "Heading not present");
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
            for(int j = 0; j< driver.findElements(subMenuItemLocator).size(); j++){
                driver.findElements(subMenuItemXpathLocator).get(j).click();
                Assert.assertTrue(isElementPresent(heading), "Heading not present");
            }
        }
    }

    private void login(){
        if(!isElementPresent(logout)) {
            WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
            WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
            WebElement loginButton = driver.findElement(By.xpath("//button[@name='login']"));
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginButton.click();
        }
    }

    private boolean isElementPresent(By element){
        return driver.findElements(element).size() > 0;
    }
}
