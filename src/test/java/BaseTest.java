import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class BaseTest {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver,10);
    String username = "testadmin";
    String password = "R8MRDAYT_test";
    By logout = By.cssSelector("#top-bar a[title='Logout']");
    String pageUrl = "http://158.101.173.161/";

    @AfterTest
    void after() {
        driver.quit();
    }
}
