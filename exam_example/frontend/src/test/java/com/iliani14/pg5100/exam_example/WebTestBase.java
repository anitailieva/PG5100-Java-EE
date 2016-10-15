package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.po.CreateUserPageObject;
import com.iliani14.pg5100.exam_example.po.HomePageObject;
import com.iliani14.pg5100.exam_example.po.LoginPageObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

/**
 * Created by anitailieva on 11/10/2016.
 */

public abstract class WebTestBase {

    private static WebDriver driver;
    protected static HomePageObject hpo;

    @Before
    public void checkIfWildflyIsRunning() {
        assumeTrue("Wildfly is not up and running", JBossUtil.isJBossUpAndRunning());
    }

    @BeforeClass
    public static void init() throws InterruptedException {

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

    @AfterClass
    public static void tearDown() {
        driver.close();
    }


    protected WebDriver getDriver() {
        return driver;
    }

    protected String getPageSource() {
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

    private static WebDriver getChromeDriver() {
        setupDriverExecutable("chromedriver", "webdriver.chrome.driver");

        return new ChromeDriver();
    }


    protected static void createAndLogNewUser(String userId, String name, String surname, String country) {
        hpo.logout();
        LoginPageObject login = hpo.goToLogin();
        CreateUserPageObject create = login.createNewUser();
        create.createUser(userId, "pass", "pass", name, surname, country);
        assertTrue(hpo.isLoggedIn(userId));
    }


    protected static void loginAnExistingUser(String userId, String password){
        hpo.logout();
        LoginPageObject login = hpo.goToLogin();
        login.doLogin(userId, password);
        assertTrue(hpo.isOnPage());

    }

}