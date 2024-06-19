package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.StringReader;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // к сожалению, разработчики не дали нам удобного селектора, поэтому так
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
    }


    public int getCardBalance(String id) {
        // TODO: перебрать все карты и найти по атрибуту data-test-id
        String text = "";
        for (SelenideElement card : cards) {
            text = card.text();
            String curr = card.getAttribute("data-test-id");
            if (curr != null && curr.equals(id)) {
                break;
            }

        }
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


    public ReplenishmentPage getReplenishmentPage(String id) {

        if (id.equals(DataHelper.getCardFirstId()))
            $("[data-test-id=action-deposit]").click();
        else
            $$("[data-test-id=action-deposit]").last().click();

        // SelenideElement card = $("[data-test-id=" + id + "]");
        // SelenideElement btn = card.$("[data-test-id=action-deposit]");
        // btn.click();

        return new ReplenishmentPage();

    }
}
