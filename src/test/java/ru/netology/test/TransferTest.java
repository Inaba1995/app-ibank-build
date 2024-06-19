package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class TransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var baseBalance = DataHelper.getCardBaseBalance();
        DashboardPage dashboardPage = new DashboardPage();

        var idCard1 = DataHelper.getCardFirstId();
        var idCard2 = DataHelper.getCardSecondId();

        var balanceCard1 = dashboardPage.getCardBalance(idCard1);
        var balanceCard2 = dashboardPage.getCardBalance(idCard2);

        var replenishmentPage = dashboardPage.getReplenishmentPage(idCard1);

        var replenishmentData = DataHelper.getTransferDataCard1ToCard2();
        dashboardPage = replenishmentPage.doTransfer(replenishmentData);

        var balanceCard1After = dashboardPage.getCardBalance(idCard1);
        var balanceCard2After = dashboardPage.getCardBalance(idCard2);

        Assertions.assertEquals(balanceCard1 + replenishmentData.getSum(), balanceCard1After);
        Assertions.assertEquals(balanceCard2 - replenishmentData.getSum(), balanceCard2After);

        balanceCard1 = balanceCard1After;
        balanceCard2 = balanceCard2After;

        replenishmentPage = dashboardPage.getReplenishmentPage(idCard2);

        replenishmentData = DataHelper.getTransferDataCard2ToCard1();
        dashboardPage = replenishmentPage.doTransfer(replenishmentData);
        balanceCard1After = dashboardPage.getCardBalance(idCard1);
        balanceCard2After = dashboardPage.getCardBalance(idCard2);

        Assertions.assertEquals(balanceCard1 - replenishmentData.getSum(), balanceCard1After);
        Assertions.assertEquals(balanceCard2 + replenishmentData.getSum(), balanceCard2After);
    }

}
