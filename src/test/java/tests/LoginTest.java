package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

import utils.Log;
import data.Access;
import data.Info;
import drivers.Browser;
import pages.LoginPage;
public class LoginTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    
    @BeforeAll
    public static void iniciarLog() {
        Log.criarArquivoLog("Log.LoginTest");
        Log.registrar("Lembrete: Ambientes diferentes podem ter usuários distintos");
    }

    @AfterAll
    public static void encerrarLog() {
        Log.encerrarLog();
    }

    @BeforeEach
    public void iniciaDriver() {
        driver = Browser.iniciarNavegador(Access.navegador, Access.headless);
        driver.get(Access.url);
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void encerrarDriver(TestInfo testInfo) {
    	Browser.fecharNavegador(Access.fecharNavegador);
    }
    
    @Test
    public void login() {
    	Log.registrar("===== TESTE REALIZADO ===== - LOGIN");
    	loginPage.Login(Info.usuario, Info.senha);
    }
    
    
}