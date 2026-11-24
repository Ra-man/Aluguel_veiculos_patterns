# Locadora de veículos

## Descrição do sistema

Aplicação de console para cadastrar clientes e veículos, registrar aluguéis e consultar o histórico com descrição e valor total. O programa inicia com dois clientes e dois veículos de exemplo. Os cadastros e aluguéis ficam em listas na memória durante a sessão.

Cada aluguel associa um cliente a um veículo por um número de dias. O usuário escolhe a tarifa e pode incluir GPS, seguro ou ambos.

## Tecnologias e pré-requisitos

- JDK 17 para compilar e executar o programa.
- Apache Maven para compilar o projeto.
- A aplicação usa a biblioteca padrão do Java e funciona sem banco de dados.

## Instruções de execução

Na raiz do repositório, compile e execute o programa com os comandos abaixo.

```sh
mvn compile
java -cp target/classes locadora.Main
```

## Exemplos de uso

A sessão abaixo registra um aluguel de sete dias para Ana Souza com tarifa semanal, GPS e seguro. A tarifa semanal aplica 10% de desconto sobre os R$ 840,00 das diárias, o que resulta em R$ 756,00. O GPS soma R$ 30,00 e o seguro soma R$ 50,00.

Ao selecionar um adicional, ele sai das opções daquele aluguel e a lista é renumerada. A opção `0` conclui o aluguel a qualquer momento nessa etapa. Se todos os adicionais forem escolhidos, o aluguel é concluído automaticamente.

```text
$ java -cp target/classes locadora.Main

=== Locadora de veículos ===
1. Cadastrar cliente
2. Cadastrar veículo
3. Listar clientes e veículos
4. Criar aluguel
5. Listar aluguéis realizados
6. Sair
Escolha uma opção: 4

Escolha o cliente
1. Ana Souza (CPF: 000.000.000-01)
2. Bruno Lima (CPF: 000.000.000-02)
Opção: 1

Escolha o veículo
1. Fiat Uno (ABC1D23) - R$ 120,00/dia
2. Hyundai HB20 (DEF4G56) - R$ 150,00/dia
Opção: 1
Quantidade de dias: 7

Escolha a tarifa
1. Diária
2. Semanal
Opção: 2

Adicionais disponíveis
1. GPS
2. Seguro
0. Concluir aluguel
Escolha um adicional: 1

Adicionais disponíveis
1. Seguro
0. Concluir aluguel
Escolha um adicional: 1

Aluguel criado.
Cliente: Ana Souza | Veículo: Fiat Uno | Dias: 7 | Tarifa: Semanal + GPS + Seguro
Valor total: R$ 836,00

=== Locadora de veículos ===
1. Cadastrar cliente
2. Cadastrar veículo
3. Listar clientes e veículos
4. Criar aluguel
5. Listar aluguéis realizados
6. Sair
Escolha uma opção: 5

Aluguéis realizados

Aluguel 1
Cliente: Ana Souza | Veículo: Fiat Uno | Dias: 7 | Tarifa: Semanal + GPS + Seguro
Valor total: R$ 836,00

=== Locadora de veículos ===
1. Cadastrar cliente
2. Cadastrar veículo
3. Listar clientes e veículos
4. Criar aluguel
5. Listar aluguéis realizados
6. Sair
Escolha uma opção: 6
Até logo!
```

## Patterns utilizados

- **Strategy.** `TarifaStrategy` define o contrato de cálculo. `TarifaDiaria` multiplica o valor da diária pelo número de dias. `TarifaSemanal` aplica um desconto de 10% em aluguéis de sete dias ou mais. `Aluguel` recebe a estratégia escolhida e delega a ela o cálculo do valor base. Assim, o aluguel pode usar diferentes regras de preço sem concentrar essas regras em uma única classe.
- **Decorator.** `AluguelComponente` define o contrato de descrição e custo. `AluguelBase` representa o aluguel sem adicionais e fornece a descrição e o custo base. `AdicionalDecorator` mantém uma referência ao componente envolvido. `GpsDecorator` e `SeguroDecorator` acrescentam seus nomes à descrição e somam seus preços ao custo. Isso permite combinar adicionais sem criar uma classe para cada combinação.

O menu lê suas opções das listas `TARIFAS` e `ADICIONAIS` em `Main`. Para disponibilizar uma implementação nova no console, basta acrescentá-la à lista correspondente.

## Declaração de IA

Conforme informado pela equipe, cada integrante usou o Claude Code para gerar o código da sua parte e contribuir para a elaboração deste README. O Codex, assistente da OpenAI baseado no GPT-6, implementou o menu de console e redigiu esta versão do README.

## Contribuição de cada integrante

| Integrante | Nome | Contribuição |
| --- | --- | --- |
| 1 | [Nome do integrante 1] | Entidades e Strategy nos pacotes `locadora.modelo` e `locadora.tarifa`. |
| 2 | [Nome do integrante 2] | Decorator no pacote `locadora.adicional`. |
| 3 | [Nome do integrante 3] | Menu de console em `locadora.Main` e `README.md`. |
