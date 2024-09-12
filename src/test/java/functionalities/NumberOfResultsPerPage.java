package functionalities;

import common.CommonTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import pages.GamePage;
import pages.MainPage;

import java.util.ArrayList;

import static util.Utils.MAIN_PAGE;


public class NumberOfResultsPerPage extends CommonTest {

    private MainPage mainPage;
    private GamePage gamePage;

    @BeforeEach
    public void setUpBeforeEach() {
        // Open a new tab using JavaScript
        ((JavascriptExecutor) driver).executeScript("window.open();");

        // Get all open tabs
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        // Switch to the new tab
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        // Open the main page in the new tab
        driver.get(MAIN_PAGE);

        mainPage = new MainPage(driver);
        gamePage = new GamePage(driver);
    }

    @Test
    @DisplayName("Verify records quantity after choosing the number 10 from the dropdown")
    public void verifyPaginationChoose10FromDropdown() {
        mainPage.selectTheNumberOfResultsPerPage(10);
    }

    @Test
    @DisplayName("Verify records quantity after choosing the number 20 from the dropdown")
    public void verifyPaginationChoose20FromDropdown() {
        mainPage.selectTheNumberOfResultsPerPage(20);
    }

    @Test
    @DisplayName("Verify records quantity after choosing the number 50 from the dropdown")
    public void verifyPaginationChoose50FromDropdown() {
        mainPage.selectTheNumberOfResultsPerPage(50);
    }

    @Test
    @DisplayName("Verify records quantity pagination after choosing the number 100 from the dropdown")
    public void verifyPaginationChoose100FromDropdown() {
        mainPage.selectTheNumberOfResultsPerPage(100);
    }

    @AfterEach
    public void tearDownAfterEach() {
        // Get all open tabs
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        // Close the current tab
        driver.close();

        // If there are other tabs, switch to the previous one
        if (!tabs.isEmpty()) {
            driver.switchTo().window(tabs.get(0));
        }
    }
    }
