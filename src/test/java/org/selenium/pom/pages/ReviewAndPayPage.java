package org.selenium.pom.pages;

import com.github.javafaker.Book;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.selenium.pom.base.BasePage;
import com.github.javafaker.Faker;


public class ReviewAndPayPage extends BasePage {
    private final By phoneNumberField = By.cssSelector("input[name=\"phone-number\"]");
    private final By iframeCardNumber = By.cssSelector("iframe.card-details__iframe");
    private final By inputCardNumber = By.cssSelector("input[name='cardNumber']");
    private final By cardExpiryDateInput = By.cssSelector("card-expiry-date-input[data-ref='add-card-modal__expiration'] input[name='cc-exp']");
    private final By cvvInput = By.cssSelector("verification-code[data-ref='add-card-modal__verification-code'] input[name='cvc']");
    private final By cardholderNameInput = By.cssSelector("ry-input-d[data-ref='add-card-modal__account-name'] input[name='ccname']");
    private final By addressLine1Input = By.cssSelector("ry-input-d[data-ref='add-card-modal__address-line-1'] input[name='address-line1']");
    private final By cityInput = By.cssSelector("ry-input-d[data-ref='add-card-modal__city'] input[name='city']");
    private final By postcodeInput = By.cssSelector("ry-input-d[data-ref='add-card-modal__postcode'] input[name='postcode']");
    private final By countryDropdown = By.cssSelector("reactive-complete[data-ref='add-card-modal__country'] input[name='country']");
    private final By countryOptionArgentina = By.cssSelector("div[data-ref='country-code-AR']");
    private final By virtualScrollContainer = By.cssSelector("[data-ref='add-card-modal__country'] cdk-virtual-scroll-viewport");
    private final By termsAndConditionInput = By.cssSelector("[inputid='termsAndConditions'] div._container");
    private final By payNowButton = By.cssSelector("button.pay-button");
    private final By currencyDropdownToggle = By.cssSelector("[data-ref='currency-converter__mcc'] .dropdown");
    private final By currencyOptionUSD = By.cssSelector("[data-ref=\"USD\"]");


    private final Faker faker = new Faker();

    public ReviewAndPayPage(WebDriver driver) {
        super(driver);
    }

    public ReviewAndPayPage fillPhoneNumberField(String phoneNumber) {
        actionUtils.enterText(phoneNumberField, phoneNumber);
        return this;
    }

    // Methods to interact with elements
    public void enterCardExpiryDate(String expiryDate) {
        actionUtils.enterText(cardExpiryDateInput, expiryDate);
    }

    public void enterCVV(String cvv) {
        actionUtils.enterText(cvvInput, cvv);
    }

    public void enterCardholderName(String cardholderName) {
        actionUtils.enterText(cardholderNameInput, cardholderName);
    }

    public void enterAddressLine1(String addressLine1) {
        actionUtils.enterText(addressLine1Input, addressLine1);
    }

    public void enterCity(String city) {
        actionUtils.enterText(cityInput, city);
    }

    public void enterPostcode(String postcode) {
        actionUtils.enterText(postcodeInput, postcode);
    }

    public void selectCountry(String country) {
        actionUtils.enterText(countryDropdown, country);
        actionUtils.clickInVirtualScroll(virtualScrollContainer, countryOptionArgentina);
    }

    public void selectCurrencyUSD() {
        actionUtils.selectCustomDropdown(currencyDropdownToggle, currencyOptionUSD);
    }

    // Method to fill the entire form
    public void fillPaymentForm(String cardExpiryDate, String cardNumber, String cvv,
                                String cardHolderName, String address, String country, String zipcode) {
        scrollToMiddleOfPage();
        enterCardExpiryDate(cardExpiryDate);
        enterCVV(cvv);
        enterCardholderName(cardHolderName);
        enterAddressLine1(address);
        selectCountry(country);
        enterCity(faker.country().capital());
        enterPostcode(zipcode);
        switchToCardNumberIframe();
        enterCardNumber(generateValidVisaCardNumber());
        switchToDefaultContent();
        selectCurrencyUSD();
    }

    public void switchToCardNumberIframe() {
        driver.switchTo().frame(actionUtils.waitForVisibility(iframeCardNumber));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    //flakiness spot: tends to be slow the wait for the appearing of card number field
    public void enterCardNumber(String cardNumber) {
        actionUtils.enterText(inputCardNumber, cardNumber);
    }

    public void scrollToMiddleOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2)");
    }

    public Boolean acceptTermsAndConditionsAndPay() {
        actionUtils.forceClick(termsAndConditionInput);
        actionUtils.click(payNowButton);
        waitForOverlaysToDisappear(By.cssSelector("div.payment-processing-dialog__content"));
        return actionUtils.waitForVisibility(By.cssSelector("icon.alert__icon--error")).isDisplayed();
    }

    public String generateValidVisaCardNumber() {
        String cardNumber;
        do {
            cardNumber = faker.finance().creditCard(com.github.javafaker.CreditCardType.VISA);
        } while (!isValidVisaCardNumber(cardNumber));
        return formatCardNumber(cardNumber);
    }

    // Validate that the card number has 16 digits
    private boolean isValidVisaCardNumber(String cardNumber) {
        String cleaned = cardNumber.replaceAll("\\D", "");
        return cleaned.length() == 16;
    }

    // Format the card number with spaces
    private String formatCardNumber(String cardNumber) {
        return cardNumber.replaceAll("\\D", "").replaceAll("(.{4})", "$1 ").trim();
    }
}
