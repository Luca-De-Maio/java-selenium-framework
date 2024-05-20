package org.selenium.pom.objects;

public class PaymentDetails {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String nameOnCard;
    private String billingAddress;
    private String country;
    private String zipCode;
    private String currency;
    private String phoneNumber;

    public PaymentDetails() {
    }

    public PaymentDetails(String cardNumber, String expirationDate, String cvv, String nameOnCard, String billingAddress,
                          String country, String zipCode, String currency, String phoneNumber) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.nameOnCard = nameOnCard;
        this.billingAddress = billingAddress;
        this.country = country;
        this.zipCode = zipCode;
        this.currency = currency;
        this.phoneNumber = phoneNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
