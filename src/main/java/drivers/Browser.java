package drivers;

import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import data.Access;

public class Browser {

    private static final String SCALE_FACTOR = "1.35";
    private static WebDriver driver;

    /**
     * Seleciona o navegador a ser executado
     *
     * @param navegador Nome do navegador a ser utilizado
     * @param headless  Define se executará com interface gráfica
     * @return WebDriver configurado
     */
    public static WebDriver iniciarNavegador(String navegador, boolean headless) {
        switch (navegador.toUpperCase()) {
            case "FIREFOX":
                driver = iniciarFirefox(headless);
                break;
            case "EDGE":
                driver = iniciarEdge(headless);
                break;
            case "CHROME":
            default:
                driver = iniciarChrome(headless);
                break;
        }

        configurarTamanhoTela(Access.tamanhoTela);
        return driver;
    }

    private static WebDriver iniciarFirefox(boolean headless) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("layout.css.devPixelsPerPx", SCALE_FACTOR);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        if (headless) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver iniciarEdge(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("force-device-scale-factor=" + SCALE_FACTOR);
        if (headless) {
            options.addArguments("--headless");
        }

        return new EdgeDriver(options);
    }

    private static WebDriver iniciarChrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("force-device-scale-factor=" + SCALE_FACTOR);
        options.addArguments("--disable-notifications");

        if (headless) {
            options.addArguments("--headless");
        }

        return new ChromeDriver(options);
    }

    /**
     * Configura o tamanho da janela do navegador
     *
     * @param opcao 1 para maximizado, 2 para 1920x1080
     */
    private static void configurarTamanhoTela(int opcao) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        switch (opcao) {
            case 1:
                driver.manage().window().maximize();
                break;
            case 2:
                driver.manage().window().setSize(new Dimension(1920, 1080));
                break;
            default:
                break;
        }
    }

    /**
     * Fecha o navegador utilizado
     *
     * @param fechar Se verdadeiro, fecha o navegador
     */
    public static void fecharNavegador(boolean fechar) {
        if (fechar && driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
