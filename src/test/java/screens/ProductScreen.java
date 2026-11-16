package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Detalle de un producto. */
public class ProductScreen extends BaseScreen {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private WebElement precio;

    @AndroidFindBy(accessibility = "Tap to add product to cart")
    private WebElement botonAgregar;

    public ProductScreen(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String textoPrecio() {
        return esperarVisible(precio).getText();
    }

    public void agregarAlCarrito() {
        tap(botonAgregar);
    }
}
