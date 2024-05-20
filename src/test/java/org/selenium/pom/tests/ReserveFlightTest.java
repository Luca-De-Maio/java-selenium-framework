package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.PaymentDetails;
import org.selenium.pom.pages.*;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReserveFlightTest extends BaseTest {

    @Test
    public void bookFlightTest() throws IOException {
        PaymentDetails paymentDetails = JacksonUtils.deserializeJson("paymentDetails.json", PaymentDetails.class);


        LoginPage loginPage = new HomePage(getDriver()).load()
                .getMyHeader().clickOnLoginLink();

        HomePage homePage = loginPage.enterUsername("demaio.martin@gmail.com").enterPassword("Lucatesting27").clickLoginButton();

        FlightsPage flightsPage = homePage.getBookingSelector()
                .selectDeparture("Spain", "Valencia")
                .selectDestination("Ireland", "Dublin")
                .selectFirstAvailableDateForDepartReturn()
                .selectLastAvailableDateForReturn()
                .selectPassengers("adults", 1)
                .clickOnSearchButton();

        assertTrue(flightsPage.isOutboundFlightSelected(), "Flight not selected");
        assertTrue(flightsPage.isInboundFlightSelected(), "Flight not selected");
        assertEquals(flightsPage.selectFare(), "PLUS");

        BagsPage bagsPage = flightsPage
                .fillAllPassengersInformation()
                .continueToSeatSelectionButton()
                .selectSeatsAndContinue();

        ExtrasPage extrasPage = bagsPage.selectSmallBag().clickContinueButton();

        ReviewAndPayPage reviewAndPayPage = extrasPage.clickContinueToTransport().clickContinueToReviewAndPay();

        reviewAndPayPage.fillPhoneNumberField(paymentDetails.getPhoneNumber())
                        .fillPaymentForm(
                                paymentDetails.getExpirationDate(),
                                paymentDetails.getCardNumber(),
                                paymentDetails.getCvv(),
                                paymentDetails.getNameOnCard(),
                                paymentDetails.getBillingAddress(),
                                paymentDetails.getCountry(),
                                paymentDetails.getZipCode()
                        );
        reviewAndPayPage.acceptTermsAndConditionsAndPay();
        //the assert
        assertTrue(reviewAndPayPage.acceptTermsAndConditionsAndPay());
    }

}


