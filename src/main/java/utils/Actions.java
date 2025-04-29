package utils;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Actions {

    private final WebDriver driver;

    public Actions(WebDriver driver) {
        this.driver = driver;
    }

    // ========== MÉTODOS PRIVADOS AUXILIARES ==========

    private WebElement localizarElementoComTentativas(By by) {
        int tentativas = 0;
        while (tentativas < 3) {
            try {
                return driver.findElement(by);
            } catch (Exception e) {
                esperar(500);
                tentativas++;
            }
        }
        Log.registrar("Elemento não localizado após tentativas: " + by);
        throw new RuntimeException("Elemento não localizado após tentativas: " + by);
    }

    private void executarAcaoComTentativas(Runnable acao) {
        int tentativas = 0;
        while (tentativas < 3) {
            try {
                acao.run();
                return;
            } catch (Exception e) {
                esperar(500);
                tentativas++;
            }
        }
        throw new RuntimeException("Ação falhou após tentativas");
    }

    private void clicarElemento(WebElement elemento, String locatorInfo) {
        Log.registrar("Clicar em elemento: " + locatorInfo);
        executarAcaoComTentativas(elemento::click);
    }

    private void escreverNoElemento(WebElement elemento, String texto, String locatorInfo) {
        Log.registrar("Escrever '" + texto + "' em elemento: " + locatorInfo);
        executarAcaoComTentativas(() -> elemento.sendKeys(texto));
    }

    // ========== MÉTODOS PÚBLICOS POR TIPO DE LOCATOR ==========

    // ---------- CSS ----------
    public WebElement pegarElementoPeloCss(String css) {
        Log.registrar("Pegar elemento CSS: " + css);
        return localizarElementoComTentativas(By.cssSelector(css));
    }

    public void clicarBotaoPegandoPeloCss(String css) {
        clicarElemento(pegarElementoPeloCss(css), "CSS: " + css);
    }

    public void escreverPegandoPeloCss(String css, String texto) {
        escreverNoElemento(pegarElementoPeloCss(css), texto, "CSS: " + css);
    }

    public String lerTextoPegandoPeloCss(String css) {
        Log.registrar("Ler texto CSS: " + css);
        return pegarElementoPeloCss(css).getText();
    }

    public boolean validarVisibilidadeCss(String css) {
        WebElement elemento = pegarElementoPeloCss(css);
        Log.registrar("Validar visibilidade CSS: " + css);
        return elemento != null && elemento.isDisplayed();
    }

    // ---------- ID ----------
    public WebElement pegarElementoPeloId(String id) {
        Log.registrar("Pegar elemento ID: " + id);
        return localizarElementoComTentativas(By.id(id));
    }

    public void clicarBotaoPegandoPeloId(String id) {
        clicarElemento(pegarElementoPeloId(id), "ID: " + id);
    }

    public void escreverPegandoPeloId(String id, String texto) {
        escreverNoElemento(pegarElementoPeloId(id), texto, "ID: " + id);
    }

    // ---------- CLASS ----------
    public WebElement pegarElementoPeloClassName(String className) {
        Log.registrar("Pegar elemento CLASS NAME: " + className);
        return localizarElementoComTentativas(By.className(className));
    }

    public void clicarBotaoPegandoPeloClassName(String className) {
        clicarElemento(pegarElementoPeloClassName(className), "CLASS NAME: " + className);
    }

    public void escreverPegandoPeloClassName(String className, String texto) {
        escreverNoElemento(pegarElementoPeloClassName(className), texto, "CLASS NAME: " + className);
    }

    // ---------- XPATH ----------
    public WebElement pegarElementoPeloXpath(String xpath) {
        Log.registrar("Pegar elemento XPATH: " + xpath);
        return localizarElementoComTentativas(By.xpath(xpath));
    }

    public void clicarBotaoPegandoPeloXpath(String xpath) {
        clicarElemento(pegarElementoPeloXpath(xpath), "XPATH: " + xpath);
    }

    public void escreverPegandoPeloXpath(String xpath, String texto) {
        escreverNoElemento(pegarElementoPeloXpath(xpath), texto, "XPATH: " + xpath);
    }

    // ---------- NAME ----------
    public WebElement pegarElementoPeloName(String name) {
        Log.registrar("Pegar elemento NAME: " + name);
        return localizarElementoComTentativas(By.name(name));
    }

    public void clicarBotaoPegandoPeloName(String name) {
        clicarElemento(pegarElementoPeloName(name), "NAME: " + name);
    }

    public void escreverPegandoPeloName(String name, String texto) {
        escreverNoElemento(pegarElementoPeloName(name), texto, "NAME: " + name);
    }

    // ========== MÉTODOS DE ESPERA ==========

    public void esperar(int tempoEspera) {
        try {
            Log.registrar("Esperar " + tempoEspera + "ms");
            Thread.sleep(tempoEspera);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Log.registrar("Erro na espera: " + e.getMessage());
        }
    }

    public void esperarElementoVisivel(By by, int tempoSegundos) {
        Log.registrar("Esperar elemento visível: " + by + " por " + tempoSegundos + "s");
        new WebDriverWait(driver, Duration.ofSeconds(tempoSegundos))
            .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    // ========== NAVEGAÇÃO ==========

    public void recarregarPagina() {
        Log.registrar("Recarregar página");
        driver.navigate().refresh();
    }

    // ========== NOTIFICAÇÕES E ALERTAS ==========

    public void validarNotificacao(String msg) {
        validarNotificacaoGenerica(msg, ".p-toast-detail", "Notificação toast");
    }

    private void validarNotificacaoGenerica(String msg, String seletor, String tipoNotificacao) {
        executarAcaoComTentativas(() -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement notificacao = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(seletor)));
            String textoNotificacao = notificacao.getText();
            Assert.assertEquals(msg, textoNotificacao);
            Log.registrar(tipoNotificacao + " exibida: " + textoNotificacao + " | Esperado: " + msg);
        });
    }

    // ========== DROPDOWNS ==========

    public void dropdown(By byDropdown, String nomeEsperado) {
        manipularDropdown(byDropdown, nomeEsperado, false);
    }

    private void manipularDropdown(By byDropdown, String nomeEsperado, boolean partialMatch) {
        try {
            clicarElemento(localizarElementoComTentativas(byDropdown), "Dropdown: " + byDropdown.toString());
            esperarElementoVisivel(By.xpath("//p-dropdownitem/li"), 30);

            List<WebElement> itens = driver.findElements(By.xpath("//p-dropdownitem/li"));
            boolean encontrado = false;

            for (WebElement item : itens) {
                String textoItem = item.getText();
                boolean corresponde = partialMatch
                        ? textoItem.toLowerCase().contains(nomeEsperado.toLowerCase())
                        : textoItem.equalsIgnoreCase(nomeEsperado);

                if (corresponde) {
                    esperar(300);
                    item.click();
                    Log.registrar("Item '" + nomeEsperado + "' selecionado no dropdown");
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                throw new NoSuchElementException("Item '" + nomeEsperado + "' não encontrado no dropdown");
            }

        } catch (Exception e) {
            Log.registrar("Erro no dropdown: " + e.getMessage());
            throw e;
        }
    }

    // ========== MULTI-SELECT ==========

    public void selecionarItensMultiselect(By byOpcoes, List<String> itensEsperados) {
        Log.registrar("Selecionar múltiplos itens: " + itensEsperados);
        esperarElementoVisivel(byOpcoes, 15);

        List<WebElement> opcoes = driver.findElements(byOpcoes);
        for (String item : itensEsperados) {
            boolean encontrado = false;
            for (WebElement opcao : opcoes) {
                if (opcao.getText().trim().equalsIgnoreCase(item)) {
                    opcao.click();
                    Log.registrar("Item selecionado: " + item);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                Log.registrar("Item não encontrado: " + item);
            }
        }
    }
}

