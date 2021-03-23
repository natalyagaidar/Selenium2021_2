import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pages.HomePage;


public class AddToCartTest extends CommonTest {

    @AfterTest
    private static void after(){
        app.closeApp();
    }

    @Test
    public void addToCartTest() {
        for (int i = 0; i < 3; i++) {
            addProductAndGoHome();
        }
        app.goToCart();
        app.removeAllElementsFromCart();
        Assert.assertEquals(app.getCartProductsQuantity(), 0, "The cart is not empty after attempt ot remove all items");
    }

    public HomePage addProductAndGoHome() {
        app.openHomePage().pickRandomDuckInPopularProducts();
        app.addProductAndGoHome();
        return app.goToHomePageFromDuckPage();
    }
}
