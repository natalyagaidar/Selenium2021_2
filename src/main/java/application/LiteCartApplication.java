package application;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.DuckPage;
import pages.HomePage;

public class LiteCartApplication {

    WebDriver driver;
    WebDriverWait wait;
    String USERNAME = "testadmin";
    String PASSWORD = "R8MRDAYT_test";
    String BASE_URL = "http://158.101.173.161/";

    private final DuckPage duckPage;
    private final CartPage cartPage;
    private final HomePage homePage;


    public LiteCartApplication() {
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5);
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.setDriver(driver);
        applicationContext.setBaseUrl(BASE_URL);
        applicationContext.setUser(new ApplicationContext.AppUser(USERNAME, PASSWORD));
        duckPage = new DuckPage(applicationContext);
        cartPage = new CartPage(applicationContext);
        homePage = new HomePage(applicationContext);

    }

    public void closeApp() {
        driver.quit();
    }

    public void loginToAdminPane() {
        driver.get(BASE_URL + "/admin");
        if (!isElementPresent(By.cssSelector("#top-bar a[title='Logout']"))) {
            WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
            WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
            WebElement loginButton = driver.findElement(By.xpath("//button[@name='login']"));
            usernameField.sendKeys(USERNAME);
            passwordField.sendKeys(PASSWORD);
            loginButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sidebar")));
        }
    }

    public void goToHomePage() {
        driver.get(BASE_URL);

    }

    public void logout() {
        driver.get(BASE_URL + "/logout");
    }

    private boolean isElementPresent(By element) {
        return driver.findElements(element).size() > 0;
    }

    public HomePage openHomePage() {
        return homePage.open();
    }

    public DuckPage addProductAndGoHome() {
        return duckPage.addProductAndGoHome();
    }

    public HomePage goToHomePageFromDuckPage() {
        return duckPage.goToHomePage();
    }

    public CartPage goToCart() {
        return homePage.goToCart();
    }

    public void removeAllElementsFromCart() {
        cartPage.removeAllElements();
    }

    public int getCartProductsQuantity() {
        return cartPage.getCartProductsQuantity();
    }
}
