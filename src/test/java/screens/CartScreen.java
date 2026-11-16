package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartScreen extends BaseScreen {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private WebElement nombreProducto;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement botonCheckout;

    public CartScreen(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String textoProducto() {
        return esperarVisible(nombreProducto).getText();
    }

    public void irACheckout() {
        tap(botonCheckout);
    }
}
