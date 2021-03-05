import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ClickAllMenuItemsTests {

    WebDriver driver;
    WebDriverWait wait;
    String username = "testadmin";
    String password = "R8MRDAYT_test";
    By logout = By.cssSelector("#top-bar a[title='Logout']");
    String pageUrl = "http://158.101.173.161/";

    @BeforeTest
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
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
