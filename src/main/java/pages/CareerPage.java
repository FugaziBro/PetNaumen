package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import org.openqa.selenium.By;

import java.util.concurrent.CountDownLatch;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CareerPage {
    public SelenideElement header = $(By.className("page-header fixed"));


    public class Header{
        private ElementsCollection headerLinks = $$(By.xpath("//header[@class='page-header']//a"));

        public void checkForLinksAvailable(CountDownLatch CDL, SelenideElement element){
                String link = element.getAttribute("href");
                RestAssured.given().baseUri(link).when().get().then().statusCode(200);
                CDL.countDown();
        }

        public ElementsCollection getHeaderLinks() {
            return headerLinks;
        }

    }
}
