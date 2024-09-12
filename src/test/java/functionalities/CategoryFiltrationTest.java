package functionalities;

import common.CommonTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import pages.MainPage;

import java.util.ArrayList;

import static util.Utils.MAIN_PAGE;

public class CategoryFiltrationTest extends CommonTest {

    private MainPage mainPage;

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
    }

    @Test
    @DisplayName("Checking filtering by MMORPG category")
    public void filteringByMmorpgCategory() {
        mainPage.selectGameCategoryInFilter("mmorpg");
        mainPage.verifyGameCategorySpanTitleSelected("mmorpg");
        mainPage.verifyGameCategoryInGenreField("MMORPG");
    }

    @Test
    @DisplayName("Checking filtering by Shooter category")
    public void filteringByShooterCategory() {
        mainPage.selectGameCategoryInFilter("shooter");
        mainPage.verifyGameCategorySpanTitleSelected("shooter");
        mainPage.verifyGameCategoryInGenreField("Shooter");
    }


    @Test
    @DisplayName("Checking filtering by Strategy category")
    public void filteringByStrategyCategory() {
        mainPage.selectGameCategoryInFilter("strategy");
        mainPage.verifyGameCategorySpanTitleSelected("strategy");
        mainPage.verifyGameCategoryInGenreField("Strategy");
    }

    @Test
    @DisplayName("Checking filtering by Moba category")
    public void filteringByMobaCategory() {
        mainPage.selectGameCategoryInFilter("moba");
        mainPage.verifyGameCategorySpanTitleSelected("moba");
        mainPage.verifyGameCategoryInGenreField("MOBA");
    }

    @Test
    @DisplayName("Checking filtering by Racing category")
    public void filteringByRacingCategory() {
        mainPage.selectGameCategoryInFilter("racing");
        mainPage.verifyGameCategorySpanTitleSelected("racing");
        mainPage.verifyGameCategoryInGenreField("Racing");
    }

    @Test
    @DisplayName("Checking filtering by Sports category")
    public void filteringBySportsCategory() {
        mainPage.selectGameCategoryInFilter("sports");
        mainPage.verifyGameCategorySpanTitleSelected("sports");
        mainPage.verifyGameCategoryInGenreField("Sports");
    }

    @Test
    @DisplayName("Checking filtering by Social category")
    public void filteringBySocialCategory() {
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategoryInGenreField("Social");
    }

    @Test
    @DisplayName("Checking not chosen category")
    public void filteringNotChosenCategory() {
        mainPage.selectGameCategoryInFilter("not chosen");
        mainPage.verifyGameCategorySpanTitleSelected("not chosen");
        mainPage.checkInitialPaginationState(true);
    }

    @Test
    @DisplayName("Reset filter by category from chosen to not chosen category")
    public void resetFilterByCategory () {
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategorySpanTitleSelected("not chosen");
        mainPage.checkInitialPaginationState(true);
    }

    @Test
    @DisplayName("Checking filter compatibility by category and PC platform")
    public void compatibilityByCategoryAndPCPlatform() {
        mainPage.selectPlatformInFilter("PC");
        mainPage.selectGameCategoryInFilter("sports");
        mainPage.verifyGameCategorySpanTitleSelected("sports");
        mainPage.verifyGameCategoryInGenreField("Sports");
    }

    @Test
    @DisplayName("Checking filter compatibility by category and Browser platform")
    public void compatibilityByCategoryAndBrowserPlatform() {
        mainPage.selectPlatformInFilter("Browser");
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategoryInGenreField("Social");
    }

    @Test
    @DisplayName("Checking filter compatibility by category and release date sorting")
    public void compatibilityByCategoryAndReleaseDateSorting() {
        mainPage.selectGameSortingOption("Release date");
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategoryInGenreField("Social");
    }

    @Test
    @DisplayName("Checking filter compatibility by category and popularity sorting")
    public void compatibilityByCategoryAndPopularitySorting() {
        mainPage.selectGameSortingOption("Popularity");
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategoryInGenreField("Social");
    }

    @Test
    @DisplayName("Checking filter compatibility by category and relevance sorting")
    public void compatibilityByCategoryAndRelevanceSorting() {
        mainPage.selectGameSortingOption("Relevance");
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategoryInGenreField("Social");
    }

    @Test
    @DisplayName("Checking filter compatibility by category and platform with sorting")
    public void compatibilityByCategoryAndPlatformWithSorting() {
        mainPage.selectPlatformInFilter("PC");
        mainPage.selectGameSortingOption("Relevance");
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategoryInGenreField("Social");
    }

    @Test
    @DisplayName("Checking changing category on the 2+ pagination page")
    public void changingCategoryOnThe2PaginationPage() {
        mainPage.goToPage(2);
        mainPage.selectGameCategoryInFilter("social");
        mainPage.verifyGameCategorySpanTitleSelected("social");
        mainPage.verifyGameCategoryInGenreField("Social");
        mainPage.verifyCurrentPaginationPage(1);
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
