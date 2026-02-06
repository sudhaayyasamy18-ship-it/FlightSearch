package testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobjects.SearchPage;

public class FlightSearchPage {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
    }

    @Test
    public void FlightSearchTest() {

        SearchPage searchpage = new SearchPage(driver);

        searchpage.selectCountry("India");
        searchpage.selectFromCity("BLR");
        searchpage.selectToCity();
        searchpage.selectDepartureDate("15");
        String departDate = searchpage.getDepartureDateText();
        System.out.println("Departure Date: " + departDate);
        String returnDate = searchpage.getReturnDateText();
        System.out.println("Return Date: " + returnDate);

        searchpage.selectPassengers(5);
        searchpage.selectCurrency("AED");
        String message = searchpage.clickSearchBtnAndConfirmationMsg();

        Assert.assertTrue(
            message.contains("Flights"),
            "Search confirmation message is not displayed"
        );
;

        
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
