package App.firstTest;

import App.base.AppiumLoad;
import org.testng.annotations.Test;

public class FirstTest extends AppiumLoad {
    @Test
    public void choosePreference(){
        home.chooseHeadline();
        home.choosePlatform();
        home.choosepushlishing();
         }
}
