import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CareerPage;
import pages.MainPage;
import properties.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static com.codeborne.selenide.Selenide.open;

public class TestBasic implements Properties {
    MainPage main = new MainPage();
    MainPage.Header header = new MainPage().new Header();

    CareerPage careerMain = new CareerPage();
    CareerPage.Header headerCareer = new CareerPage().new Header();

    ExecutorService exs;
    CountDownLatch CDL;

    @BeforeClass
    public void preset(){
        Configuration.browser = "chrome";
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 5000;
        open(propReturn("URL"));
    }

    @Description("Проверка смены цвета при наведений на заголовок")
    @Test(priority = 1)
    public void checkForHoverColorChange(){
        header.getHeaderArticles().get(6)
                .shouldBe(Condition.visible)
                .hover()
                .shouldHave(Condition.cssValue("color",propReturn("SelectedArticle")));
    }

    @Description("Переход во вкладку карьера")
    @Test(priority = 2)
    public void goToCareerArticle(){
        main.goToArticle(6);
    }

    @Description("Проверка, что все ссылки из хедера приходят со статусом 200")
    @Test(priority = 3)
    public void CheckForAvailableLinks(){
        Long a  = System.currentTimeMillis();
        exs = Executors.newFixedThreadPool(headerCareer.getHeaderLinks().size());
        CDL = new CountDownLatch(headerCareer.getHeaderLinks().size());
        for(SelenideElement elem : headerCareer.getHeaderLinks()) {
            exs.submit(() -> headerCareer.checkForLinksAvailable(CDL, elem));
        }
        try {
            CDL.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            exs.shutdown();
        }
        System.out.println(System.currentTimeMillis() - a );
    }
}
