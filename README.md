## O Espírito da Biblioteca

Jogo de texto para terminal, baseado no *World of Zuul* (Barnes & Kölling). Você é o espírito guardião de uma biblioteca centenária e precisa recolher as páginas mágicas para selar o Grande Tomo.

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

Direto pelo Maven (terminal):

```bash
mvn compile exec:java
```

Ou, com a interface gráfica:

```bash
mvn compile javafx:run
```

Ou gerando o `.jar`:

```bash
mvn package
java -jar target/library-spirit-rpg-1.0.0.jar
```

### Interface gráfica (JavaFX) ou terminal

| Modo | Comando | O que abre |
|---|---|---|
| Gráfico (GUI) | `mvn javafx:run` | Janela "O Espírito da Biblioteca" (`ui.gui.GuiLauncher`) |
| Terminal | `mvn exec:java` | Jogo de texto, como antes (`Main`) |

O JavaFX 17 é baixado pelo Maven (`javafx-controls`), sem instalação extra. A GUI precisa de um ambiente gráfico; em servidor sem tela use o modo terminal. Os dois modos usam a mesma lógica de jogo.

### Imagens opcionais

A arte da GUI é desenhada por código, então o jogo roda sem nenhum arquivo de imagem. Para trocar o fundo de uma sala por uma ilustração, coloque um PNG em `src/main/resources/images/` com um destes nomes (o `ImageLoader` aceita só `.png`, em minúsculas):

| Arquivo | Sala |
|---|---|
| `scene_hall.png` | Hall de Entrada |
| `scene_horror.png` | Seção de Terror |
| `scene_adventure.png` | Seção de Aventura |
| `scene_sci_fi.png` | Seção de Ficção Científica |
| `scene_reading_room.png` | Sala de Leitura Central |

O `ImageLoader` também resolve `threat_<nome>.png` e `item_<nome>.png`, mas a GUI atual só usa as imagens de cena (`scene_*`). A imagem é esticada para o tamanho da cena, então prefira proporção parecida com a da janela. Se o arquivo faltar ou estiver corrompido, o jogo usa o desenho em código, sem erro. Depois de adicionar uma imagem, rode `mvn javafx:run` de novo (o Maven copia `resources` para `target/classes`).

### Licenças

- O projeto usa o OpenJFX (JavaFX), licenciado sob GPL v2 com Classpath Exception, e o JUnit 5 (EPL 2.0), ambos baixados pelo Maven.
- Use como imagens apenas arquivos que você mesmo criou ou que tenham licença que permita uso em trabalho acadêmico (por exemplo CC0 ou CC BY). Para CC BY e semelhantes, a atribuição é obrigatória.
- Registre a origem e a licença de cada imagem adicionada na tabela abaixo.

| Arquivo | Autor / fonte | Licença |
|---|---|---|
| (nenhuma imagem adicionada ainda) | | |

## Roteiro de teste manual da GUI

Roteiro para conferir a interface antes da entrega. Rode `mvn javafx:run` e marque cada item. Dados do jogo: o jogador começa com 3 vidas; a resposta do enigma é `machado de assis`.

Mapa: Hall no centro; Terror a oeste, Aventura a leste, Ficção Científica ao norte e Sala de Leitura ao sul. Aventura exige a `chave de bronze` (está no Hall) e Ficção Científica exige a `chave de prata` (está no Terror). A porta da Sala de Leitura exige as três páginas mágicas e o enigma.

Cada caso começa com a janela recém-aberta (ou depois de "Jogar novamente").

### 1. Vencer o jogo

1. Tela de introdução com o enredo e o botão "Começar". Clique nele; deve aparecer o Hall de Entrada.
2. Clique na `chave de bronze` na cena. Ela deve sumir da cena e aparecer no inventário.
3. Clique na seta oeste (ou tecla `A`) para ir ao Terror. Reaja à ameaça: sombra = `esconder`, depois livro voador = `abaixar`. A figura deve sair da cena e os botões de reação devem sumir.
4. Pegue a `pagina do terror`, o `marcador de escudo` e a `chave de prata`. Clique no marcador no inventário: o ícone de escudo deve aparecer no HUD.
5. Volte ao Hall (seta leste). Vá à Aventura (leste) e reaja: raízes = `pular`, cipós = `abaixar`, tigre = `desviar`. Pegue a `pagina do aventura` e o `marcador de vida`, e use o marcador (um coração extra no HUD).
6. Volte ao Hall e vá ao norte (Ficção Científica). Reaja: laser = `desviar`, robô = `esconder`. Pegue a página e o marcador de vida e use o marcador.
7. No Hall, com as três páginas, o painel do enigma deve abrir. Digite `machado de assis` e clique em "Responder". O painel fecha e a porta sul abre.
8. Vá ao sul (Sala de Leitura). Deve aparecer o Grande Tomo, a barra de três etapas e a ameaça da etapa 1.
9. Reaja nas três rodadas da etapa (terror: `esconder`, `abaixar`, `desviar`). Quando não houver ameaça, a página `terror` deve ficar destacada com a dica "Use a página mágica". Clique nela. Um espaço da capa deve ser preenchido.
10. Repita para aventura (`pular`, `abaixar`, `desviar`) e ficção científica (`desviar`, `esconder`, `pular`).
11. Deve aparecer a tela de vitória com o livro selado e o botão "Jogar novamente". O campo de comandos deve estar desativado.

### 2. Perder para uma ameaça comum

1. Clique em "Começar" e vá ao Terror (seta oeste). A sombra pede `esconder`.
2. Clique em `desviar` (reação errada). A cena deve tremer e piscar em vermelho, e um coração cheio deve virar vazio.
3. Erre mais duas vezes. Ao zerar as vidas, deve aparecer a tela "Fim de jogo" com o botão "Jogar novamente", e o campo de comandos deve ficar desativado.
4. Com a ameaça ativa, tente clicar numa seta de saída: o log deve mostrar a recusa e a sala não deve mudar.
5. Variante com escudo: pegue e use o `marcador de escudo` antes de errar. O erro deve ser absorvido, com a cena piscando em azul e o ícone de escudo sumindo, sem perder vida.

### 3. Perder no chefe

1. Chegue à Sala de Leitura como no caso 1 (passos 1 a 8). Para acelerar, não pegue os marcadores de vida.
2. Erre as reações do Grande Tomo até zerar as vidas.
3. Deve aparecer o texto de recomeço do jogo e a etapa inicial redesenhada (barra na etapa 1, ameaça da primeira rodada). A janela não deve mostrar a tela de "Fim de jogo", porque a partida continua.

### 4. Errar o enigma

1. Chegue ao Hall com as três páginas (passos 1 a 6 do caso 1).
2. No painel do enigma, digite `dom pedro` e clique em "Responder". O log deve mostrar que a resposta está errada, o painel deve continuar aberto e a porta sul deve continuar trancada (seta com cadeado).
3. Responda de novo com `machado de assis`. O painel deve fechar e a porta deve abrir.
4. Confira também sem as três páginas: o painel do enigma não deve aparecer.

### 5. Jogar de novo

1. Em qualquer tela final (vitória ou fim de jogo), clique em "Jogar novamente".
2. A janela deve continuar aberta e voltar a uma partida nova: Hall de Entrada, 3 vidas, sem escudo, inventário vazio, itens de volta nas salas, ameaças e portas restauradas, minimapa sem salas visitadas e chefe na etapa 1.
3. A entrada de comandos deve estar ativa de novo.
4. Digite `sair` no campo de comandos: a janela deve fechar.

### 6. Verificações gerais

- Digite `ajuda`, `status` e um comando inválido (`xyz`): a resposta deve ser a mesma do terminal ("Não entendi...").
- Passe o mouse sobre um item: deve aparecer a dica com nome e descrição.
- Ao trocar de sala, a cena deve ter transição em fade, e o log deve rolar até a última mensagem.
- Sem nenhum PNG em `src/main/resources/images`, o jogo deve rodar normalmente. Se quiser testar o `ImageLoader`, copie um `scene_hall.png` e confira que ele aparece no Hall.
- `mvn exec:java` deve abrir o jogo de texto, sem janela, e `mvn test` deve passar.

## Estrutura do projeto

```
src/main/java/br/edu/ifmg/libraryspirit/   código do jogo (pacote base)
src/main/resources/images/                 imagens opcionais da GUI (PNG)
src/test/java/br/edu/ifmg/libraryspirit/   testes JUnit 5
pom.xml                                    configuração do Maven
```

Os pacotes `game`, `commands`, `world`, `items`, `characters` e `ui` serão criados ao longo das fases descritas no plano.

## Convenções

- Código (classes, métodos, pacotes) em inglês.
- Textos exibidos ao jogador e Javadoc em português.
- Commits no formato `tipo: descrição` (`feat`, `fix`, `docs`, `test`, `refactor`, `chore`).
