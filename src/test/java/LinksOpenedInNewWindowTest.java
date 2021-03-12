import listeners.FindByListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class LinksOpenedInNewWindowTest {

    //WebDriver driver;
    WebDriverWait wait;
    String username = "testadmin";
    String password = "R8MRDAYT_test";
    By logout = By.cssSelector("#top-bar a[title='Logout']");
    String pageUrl = "http://158.101.173.161/";
    EventFiringWebDriver eventListener;

    @BeforeTest
    void setup() {
        eventListener = new EventFiringWebDriver(new ChromeDriver());
        eventListener.register(new FindByListener());
        wait = new WebDriverWait(eventListener, 5);
        eventListener.get(pageUrl.concat("admin/login.php"));
        login();
    }

    @AfterTest
    void after() {
        eventListener.quit();
    }

    @Test
    public void openLinksInNewWindowTest() {

        By editCountryButtons = By.xpath("//a[@title='Edit']");

        eventListener.get("http://158.101.173.161/admin/?app=countries&doc=countries");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='table table-striped table-hover data-table']")));
        List<WebElement> allCountries = eventListener.findElements(editCountryButtons);
        allCountries.get(new Random().nextInt(allCountries.size())).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div [class='panel-body']")));

        List<WebElement> externalLinks = eventListener.findElements(By.xpath("//i[contains(@class, 'external-link')]"));
        String mainHandle = eventListener.getWindowHandle();
        externalLinks.forEach(link -> {
            link.click();
            String newHandle = eventListener.getWindowHandles().stream().filter(h -> !h.equals(mainHandle)).findFirst().get();
            eventListener.switchTo().window(newHandle);
            Assert.assertTrue(eventListener.getTitle().contains("Wikipedia"));
            eventListener.close();
            eventListener.switchTo().window(mainHandle);
        });
    }

    protected void login() {
        if (!isElementPresent(logout)) {
            WebElement usernameField = eventListener.findElement(By.xpath("//input[@name='username']"));
            WebElement passwordField = eventListener.findElement(By.xpath("//input[@name='password']"));
            WebElement loginButton = eventListener.findElement(By.xpath("//button[@name='login']"));
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sidebar")));
        }
    }

    protected boolean isElementPresent(By element) {
        return eventListener.findElements(element).size() > 0;
    }

}
