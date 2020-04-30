import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CareerPage;
import pages.MainPage;
import properties.Properties;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.codeborne.selenide.Selenide.open;

public class Garbage implements Properties {
    MainPage main = new MainPage();
    MainPage.Header header = new MainPage().new Header();

    CareerPage careerMain = new CareerPage();
    CareerPage.Header headerCareer = new CareerPage().new Header();

    @BeforeClass
    public void preset(){
        Configuration.browser = "chrome";
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 5000;
    }

    @Test(priority = 1)
    public void test(){
        open(propReturn("URL"));
        main.goToArticle(6);
    }

    @Test(priority = 2)
    public void test1()  {
        Long a  = System.currentTimeMillis();
        ExecutorService exs = Executors.newFixedThreadPool(headerCareer.getHeaderLinks().size());
        CountDownLatch CDL = new CountDownLatch(headerCareer.getHeaderLinks().size());
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
