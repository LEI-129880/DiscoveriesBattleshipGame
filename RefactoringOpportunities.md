# Oportunidades de refabricação - proposta para a ficha 4

Esta é uma proposta por revisão direta do código. O aluno confirmou o número
129890 e a escolha de Ship, Position, Compass, Barge, Caravel, Carrack e Galleon
para os seus testes. A distribuição das refabricações deve ser articulada com o grupo.
As refabricações abaixo **ainda não foram aplicadas**. A parte 2 deve partir da
bateria de testes completa do grupo, já integrada na parte 1.

| Local | Cheiro no código | Refabricação (nome no IntelliJ) | Número do aluno |
| --- | --- | --- | --- |
| `Compass::c` | Mysterious Name: o campo não explica que representa a direção | Rename para `direction` | 129890 (proposto) |
| `Ship::buildShip` | Variável temporária redundante, usada apenas para devolver o resultado do switch | Inline Variable: devolver cada navio diretamente no respetivo ramo | 129890 (proposto) |
| `Galleon::fillNorth` | Magic Number: largura 3 da travessa do galeão | Extract Constant para `CROSSBAR_LENGTH` | 129890 (proposto) |
| `Galleon::fillNorth/fillSouth/fillEast/fillWest` | Duplicated Code: criação e inserção repetidas de posições | Extract Method para `addPosition(row, column)` | 129890 (proposto) |
| `Position::isAdjacentTo` | Expressão com distâncias calculadas dentro da condição | Introduce Variable para `rowDistance` e `columnDistance` | 129890 (proposto) |
| `Ship::positions` | Encapsulamento fraco: campo protegido, apesar de os acessos usarem o getter | Change Visibility de `protected` para `private` após verificar todos os usos | 129890 (proposto) |

Para cada alteração: executar a bateria completa antes, aplicar apenas uma
refabricação, executar novamente e criar um commit com o respetivo nome.
Alterações de visibilidade devem também considerar eventuais subclasses externas
ao repositório antes de serem aprovadas pelo grupo.

## Problemas funcionais observados (não são refabricações)

Não foram alterados nesta preparação. Devem ser discutidos e corrigidos
separadamente, com testes que demonstrem o comportamento pretendido:

- `Game`: `countHits` e `countSinks` começam a `null`; a leitura ou incremento
  destes contadores pode lançar `NullPointerException`.
- `Game::validShot`: aceita a coordenada 10, mas um tabuleiro 10x10 é indexado
  de 0 a 9; a impressão posterior pode exceder o array.
- `Fleet::addShip` e `Tasks::buildFleet`: usam `<= FLEET_SIZE`, permitindo ou
  exigindo 11 navios quando a constante vale 10. A tabela atual do README também
  totaliza 11 navios; o grupo deve decidir qual é a regra correta.
- `Position`: `equals` compara apenas coordenadas, enquanto `hashCode` inclui
  ocupação e tiros. Duas posições consideradas iguais podem ter hashes distintos.

## Cobertura dos testes de 129890

Validação local: `mvn clean verify`, 61 execuções sem falhas (60 das sete classes
atribuídas a 129890 e uma de arranque da aplicação). O IntelliJ também executou as 60
execuções das sete classes com sucesso.

| Classe | Linhas (JaCoCo) | Ramos (JaCoCo) |
| --- | ---: | ---: |
| Barge | 5/5 | Não tem ramos |
| Caravel | 13/14 | 8/9 |
| Carrack | 12/12 | 7/7 |
| Compass | 18/18 | 5/5 |
| Galleon | 35/36 | 16/17 |
| Position | 23/23 | 12/12 |
| Ship | 72/72 | 52/52 |

Em `Caravel` e `Galleon`, o `throw NullPointerException` após `super(...)` não
é alcançável com assertions ativas: o construtor de `Ship` rejeita primeiro a
orientação nula. Os testes verificam essa pré-condição; o código de produção
não foi alterado apenas para elevar a métrica. Os restantes métodos e os casos
de `UNKNOWN` são exercitados. O runner do IntelliJ e o JaCoCo podem apresentar
percentagens diferentes por contarem e filtrarem instruções de forma distinta.
