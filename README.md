# library_spirt_rpg_game
Projeto desenvolvido como trabalho final na disciplina de Programação 2 focado em programação orientada objetos usando JAVA

## O Espírito da Biblioteca

Jogo de texto para terminal, baseado no *World of Zuul* (Barnes & Kölling). Você é o espírito guardião de uma biblioteca centenária e precisa recolher as páginas mágicas para selar o Grande Tomo.

- Enredo: [proposta_de_enredo_o_esp_rito_da_biblioteca.md](proposta_de_enredo_o_esp_rito_da_biblioteca.md)
- Plano de desenvolvimento: [PLANO_DE_DESENVOLVIMENTO.md](PLANO_DE_DESENVOLVIMENTO.md)

## Requisitos

- JDK 17 ou superior (`java -version`)
- Maven 3.8 ou superior (`mvn -version`)

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
