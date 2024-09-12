package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static util.Utils.TIMEOUT;
import static util.Utils.findClickableElement;
import static util.Utils.findVisibleElement;

public class GamePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public GamePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    private static final By BACK_TO_MAIN_BUTTON = By.xpath("//span[contains(.,'Back to Main')]");

    public void clickBackToMainButton() {
        // Wait until the back button is visible
        WebElement backButton = findVisibleElement(BACK_TO_MAIN_BUTTON, wait);

        // Scroll to the back button
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", backButton);

        // Wait until the back button is clickable
        findClickableElement(BACK_TO_MAIN_BUTTON, wait).click();
    }
}
