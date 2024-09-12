package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class Utils {

    public static final Duration TIMEOUT = Duration.ofSeconds(10);
    public static final String MAIN_PAGE = "https://makarovartem.github.io/frontend-avito-tech-test-assignment/";

    public static ChromeDriver setDriver() {
        Browser browser = getBrowser();
        switch (browser) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
                return new ChromeDriver();
            case YANDEX:
                System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver");
                ChromeOptions options = new ChromeOptions();
                String yandexBinaries = Optional.ofNullable(System.getenv("yandex-browser-binaries"))
                        .orElse("/Applications/Yandex.app/Contents/MacOS/Yandex");
                options.setBinary(yandexBinaries);
                return new ChromeDriver(options);
        }
        throw new IllegalStateException();
    }

    public static WebElement findClickableElement(By by, WebDriverWait wait) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static WebElement findVisibleElement(By by, WebDriverWait wait) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private static Browser getBrowser() {
        try {
            String browser = System.getenv("browser");
            return Browser.valueOf(browser);
        } catch (Exception e) {
            return Browser.CHROME;
        }
    }

    private enum Browser {
        CHROME,
        YANDEX
    }
}

