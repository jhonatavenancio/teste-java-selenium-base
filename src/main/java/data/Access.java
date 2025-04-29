package data;

/**
 * Configurações de acesso e execução de testes automatizados.
 */
public class Access {

    // ========== CONFIGURAÇÕES DO NAVEGADOR ==========

    /** Nome do navegador a ser utilizado (ex: CHROME, FIREFOX, EDGE) */
    public static String navegador = "CHROME";

    /** Executar navegador em modo headless (sem interface gráfica) */
    public static boolean headless = false;

    /** Fechar navegador automaticamente ao final do teste */
    public static boolean fecharNavegador = true;

    /** Tamanho da janela: 1 = Padrão, 2 = Maximizado, etc. */
    public static int tamanhoTela = 1;

    // ========== CONFIGURAÇÕES DO AMBIENTE ==========

    /** URL do ambiente de testes */
    public static String url = "";

}
