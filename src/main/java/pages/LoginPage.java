package pages;

import org.openqa.selenium.WebDriver;

import utils.Actions;

public class LoginPage {

    private WebDriver driver;
    private Actions actions;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver); 
    }
    

    public void Login(String login, String senha) {
        actions.escreverPegandoPeloName("entrada", login);
        actions.esperar(200);
        actions.escreverPegandoPeloName("senha", senha);
        actions.clicarBotaoPegandoPeloCss(".p-button-label");
    }
    
    
    public void realizarLogout() {
    	actions.esperar(300);
        actions.validarVisibilidadeCss(".p-menubar");
        actions.clicarBotaoPegandoPeloCss(".material-symbols-outlined");
        actions.esperar(600);
        actions.clicarBotaoPegandoPeloXpath("//div/ul/li[3]/div/a/span[2]");
    }

}