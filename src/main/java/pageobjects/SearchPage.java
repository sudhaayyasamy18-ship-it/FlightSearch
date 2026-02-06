package pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    WebDriver driver;
    WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    
    @FindBy(id = "ctl00_mainContent_DropDownListCurrency")
    WebElement currencyDropdown;

    @FindBy(id = "divpaxinfo")
    WebElement passengerInfo;

    @FindBy(id = "hrefIncAdt")
    WebElement addAdultBtn;

    @FindBy(id = "btnclosepaxoption")
    WebElement passengerDoneBtn;

    @FindBy(id = "autosuggest")
    WebElement countryInput;

    @FindBy(id = "ctl00_mainContent_ddl_originStation1_CTXT")
    WebElement fromCity;

    @FindBy(xpath = "//a[@value='MAA']")
    WebElement toCity;

    @FindBy(id = "ctl00_mainContent_view_date1")
    WebElement departureDate;

    @FindBy(id = "ctl00_mainContent_view_date2")
    WebElement returnDate;

    @FindBy(id = "ctl00_mainContent_rbtnl_Trip_1")
    WebElement roundTripRadio;

    @FindBy(id="ctl00_mainContent_btn_FindFlights")
    WebElement searchBtn;

    @FindBy(xpath = "//span[contains(text(),'Flights')]")
    WebElement confirmationMessage;

    public void selectFromCity(String cityCode) {
        fromCity.click();
        WebElement city =
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@value='" + cityCode + "']")));
        city.click();
        System.out.println(city.getText());
    }

    public void selectToCity() {
        wait.until(ExpectedConditions.elementToBeClickable(toCity)).click();
        System.out.println(toCity.getText());
    }

    public void selectDepartureDate(String day) {
        departureDate.click();
        selectDayFromCalendar(day);
        
    }

    public void selectRoundTripDates(String departDay, String returnDay) {
        roundTripRadio.click();

        departureDate.click();
        selectDayFromCalendar(departDay);

        returnDate.click();
        selectDayFromCalendar(returnDay);
    }

    private void selectDayFromCalendar(String day) {
        List<WebElement> dates =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector(".ui-state-default")));

        for (WebElement date : dates) {
            if (date.getText().equals(day)) {
                date.click();
                break;
            }
        }
    }

    public String getDepartureDateText() {
        return departureDate.getAttribute("value");
    }

    public String getReturnDateText() {
        return returnDate.getAttribute("value");
    }
    public void selectCountry(String countryName) {
        countryInput.sendKeys(countryName.substring(0, 3));

        List<WebElement> options =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("li.ui-menu-item a")));

        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(countryName)) {
                option.click();
                break;
            }
        }
    }

   

    public void selectPassengers(int adults) {
        wait.until(ExpectedConditions.elementToBeClickable(passengerInfo)).click();

        for (int i = 1; i < adults; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(addAdultBtn)).click();
        }

        passengerDoneBtn.click();
        System.out.println(passengerInfo.getText());
    }
    
    public void selectCurrency(String currency) {
        Select dropdown = new Select(currencyDropdown);
        dropdown.selectByVisibleText(currency);
        System.out.println(dropdown.getFirstSelectedOption().getText());
    }
    public String clickSearchBtnAndConfirmationMsg()
    {
    	searchBtn.click();
    	   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	    wait.until(ExpectedConditions.visibilityOf(confirmationMessage));

    	    return confirmationMessage.getText();
    }
}
