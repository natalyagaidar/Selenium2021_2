import application.LiteCartApplication;
import org.testng.annotations.BeforeClass;

public class CommonTest {
    public static LiteCartApplication app;

    @BeforeClass
    public static void start(){
        app = new LiteCartApplication();
    }
}
