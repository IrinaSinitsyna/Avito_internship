package common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import util.Utils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommonTest {

    protected WebDriver driver;

    @BeforeAll
    public void setUpDriver() {
        driver = Utils.setDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    public void cleanUpDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
