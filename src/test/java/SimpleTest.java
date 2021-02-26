import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SimpleTest {
    WebDriver driver;

    @BeforeTest
    void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\projects\\Selenium2021_2\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setHeadless(true);
        driver = new ChromeDriver(options);


    }

    @AfterTest
    void after() {
        driver.quit();
    }

   /* @Test
    void firstTest() {
        System.out.println("First");
        Assert.assertEquals(5000, 5000);
    }*/

    @Test
    void secondTest() {

        driver.get("http://google.com");
        driver.findElement(By.name("q")).sendKeys("selenium" + Keys.ENTER);
        Assert.assertTrue(driver.findElement(By.cssSelector("h3")).getText().toLowerCase().contains("selenium"));
    }
}
