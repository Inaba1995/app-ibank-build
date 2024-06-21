package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.test.data.DataHelper;
import ru.netology.test.page.DashboardPage;
import ru.netology.test.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;

public class TransferTest {
    @Test
    void shouldTransferMoneyCard1ToCard2() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

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

    }

    @Test
    void shouldTransferMoneyCard2ToCard1() {

        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        DashboardPage dashboardPage = new DashboardPage();
        var idCard1 = DataHelper.getCardFirstId();
        var idCard2 = DataHelper.getCardSecondId();

        var balanceCard1 = dashboardPage.getCardBalance(idCard1);
        var balanceCard2 = dashboardPage.getCardBalance(idCard2);

        var replenishmentPage = dashboardPage.getReplenishmentPage(idCard2);

        var replenishmentData = DataHelper.getTransferDataCard2ToCard1();
        dashboardPage = replenishmentPage.doTransfer(replenishmentData);

        var balanceCard1After = dashboardPage.getCardBalance(idCard1);
        var balanceCard2After = dashboardPage.getCardBalance(idCard2);

        Assertions.assertEquals(balanceCard1 - replenishmentData.getSum(), balanceCard1After);
        Assertions.assertEquals(balanceCard2 + replenishmentData.getSum(), balanceCard2After);
    }

}
