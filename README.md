## O Espírito da Biblioteca

Jogo de texto para terminal, baseado no *World of Zuul* (Barnes & Kölling). Você é o espírito guardião de uma biblioteca centenária e precisa recolher as páginas mágicas para selar o Grande Tomo.

- Enredo: [proposta_de_enredo_o_esp_rito_da_biblioteca.md](business_rules_docs/proposta_de_enredo_o_esp_rito_da_biblioteca.md)
- Plano de desenvolvimento: [PLANO_DE_DESENVOLVIMENTO.md](business_rules_docs/PLANO_DE_DESENVOLVIMENTO.md)

## Requisitos

- JDK 17 ou superior (`java -version`)
- Maven 3.8 ou superior (`mvn -version`)

## Instalando o Java e o Maven

Depois de instalar, feche e abra o terminal de novo e confira:

```bash
java -version
mvn -version
```

Os dois comandos devem mostrar a versão instalada. Se aparecer "comando não encontrado", o programa não está no `PATH` (veja as dicas de cada sistema).

### Windows

1. Java: abra o PowerShell e rode:

   ```powershell
   winget install EclipseAdoptium.Temurin.17.JDK
   ```

   Ou baixe o instalador `.msi` em [adoptium.net](https://adoptium.net/temurin/releases/?version=17). No instalador, marque as opções "Set JAVA_HOME variable" e "Add to PATH".

2. Maven:
   1. Baixe o arquivo `apache-maven-x.y.z-bin.zip` em [maven.apache.org/download.cgi](https://maven.apache.org/download.cgi).
   2. Extraia em uma pasta fixa, por exemplo `C:\Program Files\Maven\apache-maven-x.y.z`.
   3. Pesquise "variáveis de ambiente" no menu Iniciar e abra "Editar as variáveis de ambiente do sistema".
   4. Em "Variáveis do sistema", edite `Path` e adicione a pasta `bin` do Maven, por exemplo `C:\Program Files\Maven\apache-maven-x.y.z\bin`.

### macOS

Com o [Homebrew](https://brew.sh) instalado, rode no Terminal:

```bash
brew install --cask temurin@17
brew install maven
```

### Linux

Ubuntu, Debian, Mint:

```bash
sudo apt update
sudo apt install openjdk-17-jdk maven
```

Fedora:

```bash
sudo dnf install java-17-openjdk-devel maven
```

Se tiver mais de uma versão do Java instalada, escolha a versão 17 ou superior com `sudo update-alternatives --config java` (Ubuntu/Debian) ou `sudo alternatives --config java` (Fedora).

### Usando uma IDE

IntelliJ IDEA, Eclipse e VS Code (com o "Extension Pack for Java") reconhecem o `pom.xml` automaticamente. Basta abrir a pasta do projeto. O IntelliJ já vem com o Maven embutido, mas o JDK precisa estar instalado.

## Como compilar

```bash
mvn compile
```

## Como rodar os testes

```bash
mvn test
```

## Como jogar

Direto pelo Maven:

```bash
mvn compile exec:java
```

Ou gerando o `.jar`:

```bash
mvn package
java -jar target/library-spirit-rpg-1.0.0.jar
```

## Estrutura do projeto

```
src/main/java/br/edu/ifmg/libraryspirit/   código do jogo (pacote base)
src/test/java/br/edu/ifmg/libraryspirit/   testes JUnit 5
pom.xml                                    configuração do Maven
```

Os pacotes `game`, `commands`, `world`, `items`, `characters` e `ui` serão criados ao longo das fases descritas no plano.

## Convenções

- Código (classes, métodos, pacotes) em inglês.
- Textos exibidos ao jogador e Javadoc em português.
- Commits no formato `tipo: descrição` (`feat`, `fix`, `docs`, `test`, `refactor`, `chore`).
