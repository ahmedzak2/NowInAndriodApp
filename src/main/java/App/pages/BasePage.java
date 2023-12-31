package App.pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class BasePage {
    public static AppiumDriver driver;
    String nameApp;
    private By app = AppiumBy.xpath("//android.widget.TextView[contains(@text, '" + nameApp + "')]");
    private  By app2 = (AppiumBy.xpath("//android.widget.TextView[contains(@text,'Now in Android')]"));

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
    }

    public BasePage() {
    }

    public static WebElement findElement(By locater){
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(35));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locater));

        return driver.findElement(locater);

    }
    public void openApp(){

        findElement(app2).click();
    }
    public static WebElement findElementsByLocators( By[] byLocators) {
        for (By byLocator : byLocators) {
            try {
                WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(35));
                wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));

                WebElement element = driver.findElement(byLocator);
                if (element != null) {
                    return element;
                }
            } catch (Exception e) {
                // Ignore exception and continue with the next locator
            }
        }
        System.out.println("wrong");

        return null;
    }
    public Home openHome(){
        return new Home(driver);
    }
    public List<WebElement> findElmentsByListLocaters(By locater){
      return  driver.findElements(locater);
    }



}
