package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.ilichev.webprac.annotation.LazyAutowired;
import ru.ilichev.webprac.pages.ListPage;
import ru.ilichev.webprac.util.ListUtil;

import java.util.List;

public class CommonSteps {
    @LazyAutowired
    ListPage commonListPage;

    @When("Я фильтрую по ключу {string}")
    public void filter(String pattern) {
        commonListPage.filter(pattern);
    }

    @Then("Список содержит только {string}")
    public void listContainsExactly(String thingsCommaSeparated) {
        List<String> things = ListUtil.commaSeparatedToList(thingsCommaSeparated);
        commonListPage.verifyContainsExactlyInAnyOrder(things);
    }

    @And("Список не содержит {string}")
    public void listDoesNotContains(String needle) {
        commonListPage.verifyDoesNotContains(needle);
    }
}
