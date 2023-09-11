package App.firstTest;

import App.base.AppiumLoad;
import org.testng.annotations.Test;

public class FirstTest extends AppiumLoad {
    @Test
    public void choosePreference(){
        home.chooseHeadline();
        home.choosepushlishing();
        home.choosePlatform();

    }
    @Test
    public void chooseAnotherList() throws InterruptedException {
        home.chooseHeadline();
        //scrollUntillYoufindElement("UI");
        scrollUntilYouFindElement2("UI");
        scrollUntilYouFindElement2("Perdormance");
    }
}
