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


public class BackToMainTest extends CommonTest {

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
    @DisplayName("Checking the transition to the main page by clicking the button 'Back to Main' with the card from the 1 pagination page")
    public void transitionToTheMainPageByClickingTheButtonBackToMain() {
        mainPage.clickRandomGameCard();
        gamePage.clickBackToMainButton();
        mainPage.verifyMainPageInInitialState();
    }

    @Test
    @DisplayName("Checking the transition to the main page by clicking the button 'Back to Main' with the card from the 2 pagination page")
    public void transitionToTheMainPageByClickingTheButtonBackToMain2Page() {
        mainPage.goToPaginationPage(2);
        mainPage.clickRandomGameCard();
        gamePage.clickBackToMainButton();
        mainPage.verifyMainPageInInitialState();
    }

    @Test
    @DisplayName("Checking the transition to the main page after choosing platform and category")
    public void transitionToTheMainPageByClickingTheButtonBackToMainAfterChoosingPlatformAndCategory() {
        mainPage.selectPlatformInFilter("PC");
        mainPage.selectGameCategoryInFilter("mmorpg");
        mainPage.clickRandomGameCard();
        gamePage.clickBackToMainButton();
        mainPage.verifyMainPageInInitialState();
    }

    @Test
    @DisplayName("Checking the transition to the main page after choosing category")
    public void transitionToTheMainPageByClickingTheButtonBackToMainAfterChoosingCategory() {
        mainPage.selectGameCategoryInFilter("social");
        mainPage.clickRandomGameCard();
        gamePage.clickBackToMainButton();
        mainPage.verifyMainPageInInitialState();
    }

    @Test
    @DisplayName("Checking the transition to the main page after choosing platform")
    public void transitionToTheMainPageByClickingTheButtonBackToMainAfterChoosingPlatform() {
        mainPage.selectPlatformInFilter("PC");
        mainPage.clickRandomGameCard();
        gamePage.clickBackToMainButton();
        mainPage.verifyMainPageInInitialState();
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
