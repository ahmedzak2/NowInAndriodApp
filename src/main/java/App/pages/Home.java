package App.pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Home extends BasePage {
    public static AppiumDriver driver;
    private final By sources = By.id("forYou:topicSelection");
    private final By head = By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.e1/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View[1]");
    private final By[] headLine = new By[]{By.linkText("Headlines"),AppiumBy.id("00000000-0000-00d4-0000-004e00000004"), AppiumBy.xpath("(//android.widget.TextView)[0]")};
    private final By pushlishing = By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.e1/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View[2]");
    private final By[] platform = new By[]{By.xpath("//android.widget.FrameLayout[@resource-id='forYou:topicSelection']/android.view.View[2]"), By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.e1/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View[3]")};
    private final By accpetAll = By.id("com.android.permissioncontroller:id/permission_allow_button");
    private final By dontAllow = By.id("com.android.permissioncontroller:id/permission_deny_button");
    TouchAction touchAction;
    public Home(AppiumDriver driver) {
        this.driver = driver;
    }
    public void chooseHeadline(){

        //findElementsByLocators(headLine).click();
        Actions actions = new Actions(driver);
        actions.click(findElement(head)).perform();

    }
    public void choosepushlishing(){
        findElement(pushlishing).click();
    }
    public void choosePlatform(){
       // TouchAction touchAction = new TouchAction((AndroidDriver)driver);
        //touchAction.tap((TapOptions) findElement(platform)).perform();

        Actions actions = new Actions(driver);
        actions.click(findElementsByLocators(platform)).perform();

/*        findElementsByLocators(platform).click();
  */      System.out.println("why");
    }
    public By getTopicSeection(){
        return sources;
    }
    public void acceptNotification (){
        findElement(accpetAll).click();
    }


}
