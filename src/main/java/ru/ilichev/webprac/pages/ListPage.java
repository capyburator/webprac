package ru.ilichev.webprac.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@LazyComponent
public class ListPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Добавить')]")
    private WebElement addButton;

    @FindBy(how = How.XPATH, using = "//*/li/a[contains(@class, 'text-primary')]")
    private List<WebElement> list;

    @FindBy(how = How.TAG_NAME, using = "input")
    private WebElement filterInput;

    @FindBy(how = How.TAG_NAME, using = "button")
    private WebElement filterSubmitButton;

    public void goToAddPage() {
        click(addButton);
    }

    public ListPage verifyContains(String needle) {
        List<String> haystack = list.stream().map(this::readFromElement).toList();
        assertThat(haystack).contains(needle);
        return this;
    }

    public void verifyDoesNotContains(String needle) {
        List<String> haystack = list.stream().map(this::readFromElement).toList();
        assertThat(haystack).doesNotContain(needle);
    }

    public void verifyContainsExactlyInAnyOrder(List<String> needles) {
        verifyElementsContainsExactly(list, needles);
    }

    public ListPage filter(String pattern) {
        replaceElementContent(filterInput, pattern);
        click(filterSubmitButton);
        return this;
    }

    public void goToPageByName(String name) {
        WebElement link = driver.findElement(By.xpath("//a[contains(text(),'" + name + "')]"));
        click(link);
    }
}
