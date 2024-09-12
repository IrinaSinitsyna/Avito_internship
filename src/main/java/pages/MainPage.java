package pages;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static util.Utils.TIMEOUT;
import static util.Utils.findClickableElement;
import static util.Utils.findVisibleElement;

public class MainPage {

    private final WebDriverWait wait;
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    // Category locators
    private static final By FILTER_BY_CATEGORY_DROPDOWN = By.xpath("//*[@id='root']/div/div[3]/div/div[2]/div[2]/div/span[2]");
    private static final By NOT_CHOSEN_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='not chosen']");
    private static final By MMORPG_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='mmorpg']");
    private static final By SHOOTER_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='shooter']");
    private static final By STRATEGY_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='strategy']");
    private static final By MOBA_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='moba']");
    private static final By RACING_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='racing']");
    private static final By SPORTS_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='sports']");
    private static final By SOCIAL_CATEGORY_DROPDOWN_OPTION = By.xpath("//div[@title='social']");

    // Filtration by platform locators
    private static final By FILTER_BY_PLATFORM_DROPDOWN = By.xpath("//*[@id='root']/div/div[3]/div/div[1]/div[2]/div/span[2]");
    private static final By PC_PLATFORM_DROPDOWN_OPTION = By.xpath("//div[@title='PC']");
    private static final By BROWSER_PLATFORM_DROPDOWN_OPTION = By.xpath("//div[@title='Browser']");

    // Sorting locators
    private static final By SORTING_DROPDOWN = By.xpath("//*[@id='root']/div/div[3]/div/div[3]/div[2]/div/span[2]");
    private static final By RELEASE_DATE_SORTING_DROPDOWN_OPTION = By.xpath("//div[@title='Release date']");
    private static final By POPULARITY_SORTING_DROPDOWN_OPTION = By.xpath("//div[@title='Popularity']");
    private static final By ALPHABETICAL_SORTING_DROPDOWN_OPTION = By.xpath("//div[@title='Alphabetical']");
    private static final By RELEVANCE_SORTING_DROPDOWN_OPTION = By.xpath("//div[@title='Relevance']");

    // Pagination locators
    private static final By ACTIVE_PAGE_LOCATOR = By.xpath("//li[contains(@class, 'ant-pagination-item-active')]");
    private static final By RIGHT_BUTTON_PARENT = By.xpath("//li[@class='ant-pagination-next']");
    private static final By LEFT_BUTTON_PARENT = By.xpath("//li[@class='ant-pagination-prev']");
    private static final By PAGINATION_DROPDOWN = By.xpath("//div[@aria-label='Page Size']");
    private static final By PAGE_ITEM_LOCATOR = By.xpath("//li[@class='ant-pagination-item']//a");

    // Quantity of records locators
    private static final By TEN_RECORDS_OPTION = By.xpath("//div[@title='10 / page']");
    private static final By TWENTY_RECORDS_OPTION = By.xpath("//div[@title='20 / page']");
    private static final By FIFTY_RECORDS_OPTION = By.xpath("//div[@title='50 / page']");
    private static final By HUNDRED_RECORDS_OPTION = By.xpath("//div[@title='100 / page']");
    private static final By TITLE_RECORDS_ELEMENT = By.xpath("//span[contains(@title, '/ page')]");
    private static final By LIST_ITEM_RECORDS_LOCATOR = By.xpath("//li[contains(@class, 'ant-list-item')]");


    // Map for storing locators by categories
    private static final Map<String, By> CATEGORY_LOCATORS = new HashMap<>();

    static {
        CATEGORY_LOCATORS.put("not chosen", NOT_CHOSEN_CATEGORY_DROPDOWN_OPTION);
        CATEGORY_LOCATORS.put("mmorpg", MMORPG_CATEGORY_DROPDOWN_OPTION);
        CATEGORY_LOCATORS.put("shooter", SHOOTER_CATEGORY_DROPDOWN_OPTION);
        CATEGORY_LOCATORS.put("strategy", STRATEGY_CATEGORY_DROPDOWN_OPTION);
        CATEGORY_LOCATORS.put("moba", MOBA_CATEGORY_DROPDOWN_OPTION);
        CATEGORY_LOCATORS.put("racing", RACING_CATEGORY_DROPDOWN_OPTION);
        CATEGORY_LOCATORS.put("sports", SPORTS_CATEGORY_DROPDOWN_OPTION);
        CATEGORY_LOCATORS.put("social", SOCIAL_CATEGORY_DROPDOWN_OPTION);
    }

    public void selectGameCategoryInFilter(String category) {
        // Open the category dropdown
        WebElement categoryDropdown = findClickableElement(FILTER_BY_CATEGORY_DROPDOWN, wait);
        categoryDropdown.click();

        By categoryOption = CATEGORY_LOCATORS.get(category.toLowerCase());
        if (categoryOption == null) {
            throw new IllegalArgumentException("Category " + category + " not found.");
        }

        // Select the category
        WebElement categoryElement = findClickableElement(categoryOption, wait);
        categoryElement.click();
    }

    public void verifyGameCategorySpanTitleSelected(String category) {
        // Find the category dropdown element itself
        WebElement dropdownElement = findVisibleElement(FILTER_BY_CATEGORY_DROPDOWN, wait);

        // Verify that the dropdown element has the correct 'title' attribute
        String actualTitle = dropdownElement.getAttribute("title");

        if (actualTitle == null || !actualTitle.equalsIgnoreCase(category)) {
            throw new AssertionError("Expected category title '" + category + "' was not found in the dropdown. Actual title: '" + actualTitle + "'");
        }

        // If the title matches
        System.out.println("Category title '" + category + "' was correctly applied to the category filter.");
    }


    public void verifyGameCategoryInGenreField(String category) {
        int currentPage = 1;
        boolean isCategoryFound = false;

        // Loop to iterate through pagination pages
        while (!isCategoryFound) {
            try {
                // Locator for the genre text on the current page
                By genreText = By.xpath("//div[contains(.,'Genre: " + category + "')]");
                findVisibleElement(genreText, wait);

                // If the element is found, print message and exit the loop
                System.out.println("Filtering by category " + category + " was successful. The name was found on the page.");
                isCategoryFound = true;
            } catch (TimeoutException e) {
                // If the element is not found, attempt to go to the next page
                By nextPageButton = By.xpath("//li[@title='" + (currentPage + 1) + "']");

                try {
                    WebElement nextPageElement = findClickableElement(nextPageButton, wait);
                    nextPageElement.click();
                    currentPage++;
                    System.out.println("Moving to the next page: " + currentPage);
                } catch (TimeoutException ex) {
                    // If the next page button is not found, end the loop
                    System.out.println("No more pages available. The category was not found.");
                    break;
                }
            }
        }

        // If the category was not found after going through all pages
        if (!isCategoryFound) {
            throw new AssertionError("Category " + category + " was not found after going through all pages.");
        }
    }

    public void verifyPlatformSpanTitleSelected(String platform) {
        // Find the dropdown element itself
        WebElement dropdownElement = findVisibleElement(SORTING_DROPDOWN, wait);

        // Verify that the dropdown element has the correct 'title' attribute
        String actualTitle = dropdownElement.getAttribute("title");

        if (actualTitle == null || !actualTitle.equalsIgnoreCase(platform)) {
            throw new AssertionError("Expected platform title '" + platform + "' was not found in the dropdown. Actual title: '" + actualTitle + "'");
        }

        // If the title matches
        System.out.println("Platform title '" + platform + "' was correctly applied to the platform filter.");
    }

    public void selectPlatformInFilter(String platform) {
        // Open the platform dropdown
        WebElement platformDropdown = findClickableElement(FILTER_BY_PLATFORM_DROPDOWN, wait);
        platformDropdown.click();

        By platformOption;

        switch (platform.toLowerCase()) {
            case "pc":
                platformOption = PC_PLATFORM_DROPDOWN_OPTION;
                break;
            case "browser":
                platformOption = BROWSER_PLATFORM_DROPDOWN_OPTION;
                break;
            default:
                throw new IllegalArgumentException("Unknown platform: " + platform);
        }

        // Select the platform
        WebElement platformElement = findClickableElement(platformOption, wait);
        platformElement.click();
    }

    public void selectGameSortingOption(String sortingOption) {
        // Open the sorting dropdown
        WebElement sortingDropdown = findClickableElement(SORTING_DROPDOWN, wait);
        sortingDropdown.click();

        By sortingOptionLocator;

        switch (sortingOption.toLowerCase()) {
            case "release date":
                sortingOptionLocator = RELEASE_DATE_SORTING_DROPDOWN_OPTION;
                break;
            case "popularity":
                sortingOptionLocator = POPULARITY_SORTING_DROPDOWN_OPTION;
                break;
            case "alphabetical":
                sortingOptionLocator = ALPHABETICAL_SORTING_DROPDOWN_OPTION;
                break;
            case "relevance":
                sortingOptionLocator = RELEVANCE_SORTING_DROPDOWN_OPTION;
                break;
            default:
                throw new IllegalArgumentException("Unknown sorting parameter: " + sortingOption);
        }

        // Select the sorting option
        WebElement sortingElement = findClickableElement(sortingOptionLocator, wait);
        sortingElement.click();
    }

    public void verifyGameSortingSpanTitleSelected(String sortingOption) {
        // Find the sorting dropdown element itself
        WebElement sortingDropdownElement = findVisibleElement(SORTING_DROPDOWN, wait);

        // Verify that the sorting dropdown element has the correct 'title' attribute
        String actualTitle = sortingDropdownElement.getAttribute("title");

        if (actualTitle == null || !actualTitle.equalsIgnoreCase(sortingOption)) {
            throw new AssertionError("Expected sorting option '" + sortingOption + "' was not applied. Actual sorting: '" + actualTitle + "'");
        }

        // If the title matches
        System.out.println("Sorting option '" + sortingOption + "' was correctly applied to the sorting filter.");
    }

    public void clickRandomGameCard() {
        By cardLocator = By.xpath("//li[@class='ant-list-item']");

        // Wait until the game cards appear on the page
        findVisibleElement(cardLocator, wait);

        // Find all the game card elements
        List<WebElement> cards = driver.findElements(cardLocator);

        // Check that the game cards are found
        if (cards.isEmpty()) {
            throw new IllegalStateException("No game cards were found on the page.");
        }

        // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(cards.size());

        WebElement randomCard = cards.get(randomIndex);

        // Wait until the randomly selected card is clickable
        findClickableElement(cardLocator, wait);

        // Click the randomly selected card
        randomCard.click();
    }

    public void verifyMainPageHeader() {
        By headerLocator = By.tagName("h1");

        // Wait until the header appears on the page
        WebElement headerElement = findVisibleElement(headerLocator, wait);

        // Check that the h1 header contains the text "Main Page"
        String headerText = headerElement.getText();
        if (!headerText.equals("Main Page")) {
            throw new AssertionError("Expected header 'Main Page', but found: " + headerText);
        }

        // If the check passes
        System.out.println("The h1 header contains the text 'Main Page'.");
    }

    public void checkInitialPaginationState(boolean isInitialPaginationState) {
        // Locator for the element with title='40'
        By paginationElement = By.xpath("//li[@title='40']");

        if (isInitialPaginationState) {
            // Expect the element to be present on the page (40 pages)
            try {
                findVisibleElement(paginationElement, wait);
                System.out.println("40 pages are displayed, meaning category or platform is not selected.");
            } catch (TimeoutException e) {
                throw new AssertionError("Expected 40 pages to be displayed. Category or platform may not have been reset.");
            }
        } else {
            // Expect the element for 40 pages to not be present (fewer than 40 pages)
            try {
                WebElement invisibleElement = findVisibleElement(paginationElement, wait);
                if (invisibleElement != null) {
                    throw new AssertionError("Expected fewer than 40 pages, but found: " + invisibleElement.getText());
                }
            } catch (TimeoutException e) {
                System.out.println("Fewer than 40 pages are displayed, meaning category or platform is selected.");
            }
        }
    }

    public void verifyCurrentPaginationPage(int expectedPageNumber) {
        // Locator for the current active pagination page
        By activePageLocator = By.xpath("//li[contains(@class, 'ant-pagination-item-active')]");

        WebElement activePageElement = findVisibleElement(activePageLocator, wait);

        String activePageText = activePageElement.getText();

        // Convert the text to a number and verify it matches the expected page number
        int currentPageNumber = Integer.parseInt(activePageText);

        if (currentPageNumber != expectedPageNumber) {
            throw new AssertionError("Expected current page: " + expectedPageNumber + ", but found: " + currentPageNumber);
        }

        // If the check passes
        System.out.println("The current pagination page matches the expected page: " + expectedPageNumber);
    }

    public void goToPaginationPage(int pageNumber) {
        // Locator for the pagination page by its number
        By pageLocator = By.xpath("//li[@title='" + pageNumber + "']");

        // Wait until the pagination page is clickable
        WebElement pageElement = findClickableElement(pageLocator, wait);

        // Click the pagination page
        pageElement.click();

        // If the successful page transition
        System.out.println("Successfully navigated to pagination page: " + pageNumber);
    }

    public void navigateThroughPagination() {
        // Start navigating right
        while (isElementClickable(RIGHT_BUTTON_PARENT) && !isRightButtonDisabled()) {
            // Click the right button
            WebElement rightButton = findClickableElement(RIGHT_BUTTON_PARENT, wait);
            rightButton.click();

            // Wait until the next page becomes active
            findVisibleElement(ACTIVE_PAGE_LOCATOR, wait);

            // Check if the active page updated correctly
            WebElement activePage = driver.findElement(ACTIVE_PAGE_LOCATOR);
            if (!activePage.getAttribute("class").contains("ant-pagination-item-active")) {
                throw new AssertionError("Expected the next page to be active, but it wasn't.");
            }

            System.out.println("Moved to the next page: " + activePage.getText());
        }

        System.out.println("Reached the last page.");

        // Now, let's navigate back using the left button
        while (isElementClickable(LEFT_BUTTON_PARENT)) {
            // Click the left button
            WebElement leftButton = findClickableElement(LEFT_BUTTON_PARENT, wait);
            leftButton.click();

            // Wait until the previous page becomes active
            findVisibleElement(ACTIVE_PAGE_LOCATOR, wait);

            // Check if the active page updated correctly
            WebElement activePage = driver.findElement(ACTIVE_PAGE_LOCATOR);
            if (!activePage.getAttribute("class").contains("ant-pagination-item-active")) {
                throw new AssertionError("Expected the previous page to be active, but it wasn't.");
            }

            System.out.println("Moved to the previous page: " + activePage.getText());
        }

        System.out.println("Pagination navigation completed.");
    }

    private boolean isElementClickable(By locator) {
        try {
            findClickableElement(locator, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isRightButtonDisabled() {
        // Check if the right pagination button has the 'ant-pagination-next ant-pagination-disabled' class
        WebElement rightButton = driver.findElement(RIGHT_BUTTON_PARENT);
        String rightButtonClass = rightButton.getAttribute("class");
        return rightButtonClass.contains("ant-pagination-disabled");
    }

    public void verifyMainPageInInitialState() {
        // 1. Check the h1 header contains "Main Page"
        verifyMainPageHeader();

        // 2. Check platform dropdown is set to "not chosen"
        verifyPlatformSpanTitleSelected("not chosen");

        // 3. Check game category dropdown is set to "not chosen"
        verifyGameCategorySpanTitleSelected("not chosen");

        // 4. Check sorting dropdown is set to "not chosen"
        verifyGameSortingSpanTitleSelected("not chosen");

        // 5. Check that the current pagination page is set to 1
        verifyCurrentPaginationPage(1);

        // 6. Check pagination is set to +40
        checkInitialPaginationState(true);

        System.out.println("Main page is in its initial state.");
    }

    // Method to switch to a specific page number by clicking the pagination number
    public void goToPage(int pageNumber) {
        // Build a locator for the specific page number
        By pageLocator = By.xpath("//li[@title='" + pageNumber + "']/a");

        // Wait until the specific page number is clickable
        WebElement pageElement = wait.until(ExpectedConditions.elementToBeClickable(pageLocator));

        // Click on the specific page number
        pageElement.click();

        // Wait until the current active page matches the selected page
        wait.until(ExpectedConditions.textToBe(ACTIVE_PAGE_LOCATOR, String.valueOf(pageNumber)));

        // Confirm successful transition
        System.out.println("Successfully navigated to page: " + pageNumber);
    }


    public void goThroughAllPages() {
        int currentPage = 1; // Start from the first page
        while (true) {
            try {
                // Wait for the active page to become visible (indicating that the current page has loaded)
                WebElement activePageElement = findVisibleElement(ACTIVE_PAGE_LOCATOR, wait);
                String activePageNumber = activePageElement.getText();

                // Check if we are on the current page
                if (Integer.parseInt(activePageNumber) == currentPage) {
                    // Find all the page elements (excluding the active page)
                    List<WebElement> pages = driver.findElements(By.xpath("//li[contains(@class, 'ant-pagination-item') and not(contains(@class, 'ant-pagination-item-active'))]/a"));

                    // If there are no more pages to navigate
                    if (pages.isEmpty()) {
                        System.out.println("No more pages to navigate.");
                        break; // Exit the loop if no more pages are found
                    }

                    // Find the next available page number to click
                    boolean pageFound = false;
                    for (WebElement page : pages) {
                        String pageNumber = page.getText();

                        // If the page number is greater than the current one, navigate to it
                        if (Integer.parseInt(pageNumber) > currentPage) {
                            page.click();
                            currentPage = Integer.parseInt(pageNumber); // Update the current page
                            System.out.println("Moved to the next page: " + currentPage);

                            // Wait for the new page to become active
                            wait.until(ExpectedConditions.textToBe(ACTIVE_PAGE_LOCATOR, pageNumber));
                            pageFound = true; // Mark that the page was found
                            break; // Exit the loop once the page is clicked
                        }
                    }

                    // If no valid page was found, we are on the last page
                    if (!pageFound) {
                        System.out.println("Reached the last page.");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Reached the last page or encountered an error: " + e.getMessage());
                break; // Exit the loop on an error or reaching the last page
            }
        }
    }

    // Method to select the number of items per page
    public void selectPaginationOption(int numberOfItems) {
        // Open the pagination dropdown
        WebElement paginationDropdown = wait.until(ExpectedConditions.elementToBeClickable(PAGINATION_DROPDOWN));
        paginationDropdown.click();

        // Select the correct number of items per page based on the input parameter
        By optionLocator;
        switch (numberOfItems) {
            case 10:
                optionLocator = TEN_RECORDS_OPTION;
                break;
            case 20:
                optionLocator = TWENTY_RECORDS_OPTION;
                break;
            case 50:
                optionLocator = FIFTY_RECORDS_OPTION;
                break;
            case 100:
                optionLocator = HUNDRED_RECORDS_OPTION;
                break;
            default:
                throw new IllegalArgumentException("Invalid number of items per page: " + numberOfItems);
        }

        // Click on the selected option
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        optionElement.click();
    }

    // Method to verify the displayed number of items on the page
    public void verifyDisplayedItemsCount(int expectedCount) {
        // Wait for the list items to load
        List<WebElement> items = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(LIST_ITEM_RECORDS_LOCATOR, 0));

        // Verify that the number of items matches the expected count
        if (items.size() != expectedCount) {
            throw new AssertionError("Expected " + expectedCount + " items on the page, but found: " + items.size());
        }

        System.out.println("Correct number of items (" + expectedCount + ") are displayed on the page.");
    }

    // Method to verify the title attribute of the specific element
    public void verifyTitleAttribute(int expectedValue) {
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TITLE_RECORDS_ELEMENT));
        String titleValue = titleElement.getAttribute("title");

        // Format the expected title in the format "100 / page" for 100 items
        String expectedTitle = expectedValue + " / page";

        // Verify that the title attribute contains the expected value
        if (!titleValue.equals(expectedTitle)) {
            throw new AssertionError("Expected title to be '" + expectedTitle + "', but was: " + titleValue);
        }

        System.out.println("Element has the correct title attribute: " + titleValue);
    }

    // Main test method
    public void selectTheNumberOfResultsPerPage(int numberOfItems) {
        // Step 1: Select the number of items per page
        selectPaginationOption(numberOfItems);

        // Step 2: Verify that the number of items displayed matches the selected option
        verifyDisplayedItemsCount(numberOfItems);

        // Step 3: Verify the title attribute of the specified element
        verifyTitleAttribute(numberOfItems);
    }

}


