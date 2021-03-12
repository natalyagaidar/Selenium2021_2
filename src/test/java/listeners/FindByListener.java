package listeners;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.IOException;

public class FindByListener extends AbstractWebDriverEventListener {


    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver){
        System.out.println(String.format("[Starting search for: %s]", by));
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver){
        System.out.println(String.format("[%s has been found]", by));
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver){
        System.out.println("[Locator not found... Taking a screenshot]");
        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try{
            Files.copy(tempFile, new File(System.currentTimeMillis() + "screen.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
