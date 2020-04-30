package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    Header innerHeader = new Header();
    private final SelenideElement header = $(By.className("page-header"));


    public void goToArticle(int index){
        ElementsCollection links =
        innerHeader.getHeaderArticles();
        links.get(index).click();
    }

    public class Header{
        private ElementsCollection headerArticles = $$(By.xpath("//ul[@class='menu__list']//a[@class='link']"));

        public ElementsCollection getHeaderArticles() {
            return headerArticles;
        }
    }

}
