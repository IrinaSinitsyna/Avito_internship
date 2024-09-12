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


public class PaginationTest extends CommonTest {

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
    @DisplayName("Verify pagination after performing a search by platform")
    public void verifyNavigationThroughPaginationAfterPerformingASearchByPlatformPc() {
        mainPage.selectPlatformInFilter("PC");
        mainPage.navigateThroughPagination();
    }

    @Test
    @DisplayName("Verify pagination after performing a search by category")
    public void verifyNavigationThroughPaginationAfterPerformingASearchByGameCategorySocial() {
        mainPage.selectGameCategoryInFilter("Social");
        mainPage.navigateThroughPagination();
    }

    @Test
    @DisplayName("Verify pagination after performing a sorting")
    public void verifyNavigationThroughPaginationAfterPerformingASortingAlphabetical() {
        mainPage.selectGameSortingOption("alphabetical");
        mainPage.navigateThroughPagination();
    }

    @Test
    @DisplayName("Verify go to the 2 page after performing a search by platform")
    public void verifyGoToTheDirectPageAfterPerformingASearchByPlatform() {
        mainPage.selectPlatformInFilter("PC");
        mainPage.goToPage(2);
    }

    @Test
    @DisplayName("Verify go to the 2 page after performing a search by category")
    public void verifyGoToTheDirectPageAfterPerformingASearchByGameCategory() {
        mainPage.selectGameCategoryInFilter("mmorpg");
        mainPage.goToPage(2);
    }

    @Test
    @DisplayName("Verify go to the 2 page after performing the alphabetical sorting")
    public void verifyGoToTheDirectPageAfterPerformingTheAlphabeticalSorting() {
        mainPage.selectGameSortingOption("alphabetical");
        mainPage.goToPage(2);
    }

    @Test
    @DisplayName("Verify goThroughAllPages after performing a search by category")
    public void verifyGoThroughAllPagesAfterPerformingASearchByGameCategory() {
        mainPage.selectGameCategoryInFilter("mmorpg");
        mainPage.goThroughAllPages();
    }

    @Test
    @DisplayName("Verify goThroughAllPages after performing a search by platform")
    public void verifyGoThroughAllPagesAfterPerformingASearchByPlatform() {
        mainPage.selectPlatformInFilter("Browser");
        mainPage.goThroughAllPages();
    }

    @Test
    @DisplayName("Verify goThroughAllPages after performing the alphabetical sorting")
    public void verifyGoThroughAllPagesAfterPerformingTheAlphabeticalSorting() {
        mainPage.selectGameSortingOption("alphabetical");
        mainPage.goThroughAllPages();
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
