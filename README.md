# Projeto Base de Testes Automatizados com Selenium, Java e JUnit 5

Este é um projeto **genérico e reutilizável** para automação de testes com **Java**, **Selenium WebDriver** e **JUnit 5**, utilizando o padrão de design **Page Object Model (POM)**. A estrutura é pensada para facilitar manutenções, escalabilidade e integração com pipelines de CI/CD.

---

## 🧩 Tecnologias Utilizadas

- Java 11
- Maven
- Selenium WebDriver 4.22.0
- JUnit 5 (Jupiter)
- BrowserMob Proxy
- SLF4J (para logging)
- OkHttp (para integração com APIs, ex: Discord Webhooks)

---

## 🧱 Estrutura do Projeto


src/
├── main/java/
│   ├── data/
│   │   ├── Access.java     → Configurações do navegador e da URL de teste
│   │   └── Info.java       → Dados do usuário utilizados nos testes
│   ├── drivers/
│   │   └── Browser.java    → Configuração do WebDriver
│   ├── pages/
│   │   └── LoginPage.java  → Estrutura da tela de login (Page Object)
│   └── utils/
│       ├── Actions.java    → Métodos reutilizáveis (ex: clicar, preencher, esperar)
│       └── Log.java        → Registro de execução dos testes em arquivo `.txt`
│
└── test/java/
    └── tests/
        └── LoginTest.java  → Execução dos testes automatizados


---

## 🎯 Padrão de Projeto: Page Object Model

O projeto segue o padrão **Page Object Model (POM)**, que visa isolar a lógica de interação com a interface (UI) em classes específicas, melhorando a **manutenção** e a **reusabilidade** dos testes.

---

## ▶️ Executando os Testes

Para executar os testes localmente, utilize o comando:


mvn test


Certifique-se de ter o Java 11 e o Maven instalados corretamente.

---

## 🛠️ CI/CD com YAML (Exemplo GitLab CI)


stages:
  - test

test:
  image: maven:3.8.5-openjdk-11
  script:
    - mvn test
  artifacts:
    when: always
    paths:
      - target/surefire-reports/
      - test/java/logs/log_execucao.txt


---

## 📦 Dependências (POM)

Este projeto utiliza as seguintes dependências (já incluídas no `pom.xml`):

- `selenium-java`
- `junit-jupiter-api` e `junit-jupiter-engine`
- `browsermob-core`
- `slf4j-api`
- `okhttp` (para possíveis integrações com notificações)

---

## 📁 Registro de Execução

Os resultados dos testes são gravados em um **arquivo `.txt`** através da classe `Log.java`, localizado em:


test/java/logs/log_execucao.txt

