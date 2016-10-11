import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import po.HomePageObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.fail;

/**
 * Created by anitailieva on 11/10/2016.
 */
public class SeleniumTestBase {

    protected static HomePageObject hp;
    private static WebDriver driver;

    protected WebDriver getDriver() {
        return driver;
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    private static boolean tryToSetGeckoIfExists(String property, Path path) {
        if (Files.exists(path)) {
            System.setProperty(property, path.toAbsolutePath().toString());
            return true;
        }
        return false;
    }

    private static void setupDriverExecutable(String executableName, String property) {
        String homeDir = System.getProperty("user.home");

        //first try Linux/Mac executable
        if (!tryToSetGeckoIfExists(property, Paths.get(homeDir, executableName))) {
            //then check if on Windows
            if (!tryToSetGeckoIfExists(property, Paths.get(homeDir, executableName + ".exe"))) {
                fail("Cannot locate the " + executableName + " in your home directory " + homeDir);
            }
        }
    }

    private static WebDriver getChromeDriver(){
        setupDriverExecutable("chromedriver", "webdriver.chrome.driver");

        return new ChromeDriver();
    }

    @BeforeClass
    public static void init() throws InterruptedException{
        driver = getChromeDriver();

        for (int i = 0; i < 30; i++) {
            boolean ready = JBossUtil.isJBossUpAndRunning();
            if (!ready) {
                Thread.sleep(1_000); //check every second
                continue;
            } else {
                break;
            }
        }

    }

    public void tearDown(){
        driver.close();
    }

    protected static void createAndLoginNewUser(String user, String password){


    }
    protected static void createEvent(){

    }

    protected static void loginUser(){

    }
}


