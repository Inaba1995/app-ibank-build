package ru.netology.test.page;

import org.openqa.selenium.Keys;
import ru.netology.test.data.DataHelper;
import ru.netology.test.page.DashboardPage;

import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {

    public DashboardPage doTransfer(DataHelper.ReplenishmentData replenishmentData) {
        $("[data-test-id=amount] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=amount] input").setValue(replenishmentData.getSum().toString());
        $("[data-test-id=from] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=from] input").setValue(replenishmentData.getCardNumber());
        $("[data-test-id=action-transfer]").click();

        return new DashboardPage();
    }
}
