# Battleship

# 🤝Regras
O jogo é jogado num tabuleiro 10x10, escolha uma coordenada para afundar o navio adversário.


# Os Vencedores

| Curso               |   Número | Nome               |
| ------------------- | -------: | ------------------ |
| LEI | 129880 | Pedro Pereira |
| LEI | 129890 | Rafael Balao |

# ⚓ Tipos de Navios

| Batalha Naval          | Descobrimentos | English  | Dimensão | # Navios |
|------------------------|----------------|----------|:--------:|:--------:|
| **Porta-aviões**       | Galeão         | Galleon  |    5     |    1     |
| **Navio de 4 canhões** | Fragata        | Frigate  |    4     |    1     |
| **Navio de 3 canhões** | Nau            | Carrack  |    3     |    2     |
| **Navio de 2 canhões** | Caravela       | Caravel  |    2     |    3     |
| **Submarino**          | Barca          | Barge    |    1     |    4     |


# Barcos dos descobrimentos
https://pt.wikipedia.org/wiki/Categoria:Embarca%C3%A7%C3%B5es_dos_Descobrimentos


Basic academic version of Battleship game to build upon.

## Testes e integração contínua (ficha 4)

O projeto usa Java 17 ou posterior, Maven e JUnit Jupiter 6.0.0.
Cada caso de teste deve descrever o comportamento esperado com `@DisplayName`.

```shell
mvn clean verify
```

Este comando compila o projeto, executa os testes e gera a cobertura JaCoCo em
`target/site/jacoco/index.html`. Os resultados individuais estão em
`target/surefire-reports/`. Para executar apenas uma classe, usar
`mvn -Dtest=NomeDaClasseTest test`.

O workflow preparado em `.github/workflows/maven.yml` (Java with Maven), a integrar
pelo membro com o menor número de aluno, executa o mesmo processo
em Java 17 em cada push, nos pull requests para `main` e por execução manual.
Os relatórios ficam disponíveis no artefacto `test-and-coverage-reports` da
execução em GitHub Actions. Um teste falhado faz falhar o workflow.

No IntelliJ, sincronizar o Maven após alterar o `pom.xml`. Os testes estão em
`src/test/java`; usar **Run with Coverage** para consultar a cobertura no editor.
O relatório individual para entrega deve incluir apenas as classes atribuídas
ao aluno e ser exportado para `reports/<utilizador-git>/`.

O aluno 129890 trabalha no ramo `UnitTests_129890`, com testes para `Ship`,
`Position`, `Compass`, `Barge`, `Caravel`, `Carrack` e `Galleon`. A configuração
partilhada **Ficha 4 - testes 129890** executa estas sete classes de teste no
IntelliJ e limita a análise de cobertura às respetivas classes de produção.
O trabalho está associado ao issue #10. O relatório exportado pelo IntelliJ
está em `reports/LEI-129890/index.html`: 98,7% de linhas e 98% de ramos.
