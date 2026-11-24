package locadora;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import locadora.adicional.AluguelBase;
import locadora.adicional.AluguelComponente;
import locadora.adicional.GpsDecorator;
import locadora.adicional.SeguroDecorator;
import locadora.modelo.Aluguel;
import locadora.modelo.Cliente;
import locadora.modelo.Veiculo;
import locadora.tarifa.TarifaDiaria;
import locadora.tarifa.TarifaSemanal;
import locadora.tarifa.TarifaStrategy;

public class Main {

    private static final Locale PORTUGUES_BRASIL = Locale.forLanguageTag("pt-BR");

    private static final List<TarifaStrategy> TARIFAS = List.of(
            new TarifaDiaria(),
            new TarifaSemanal());

    private static final List<Map.Entry<String, UnaryOperator<AluguelComponente>>> ADICIONAIS = List.of(
            Map.entry("GPS", GpsDecorator::new),
            Map.entry("Seguro", SeguroDecorator::new));

    private final Scanner scanner;
    private final List<Cliente> clientes = new ArrayList<>(List.of(
            new Cliente("Ana Souza", "000.000.000-01"),
            new Cliente("Bruno Lima", "000.000.000-02")));
    private final List<Veiculo> veiculos = new ArrayList<>(List.of(
            new Veiculo("ABC1D23", "Fiat Uno", 120.00),
            new Veiculo("DEF4G56", "Hyundai HB20", 150.00)));
    private final List<AluguelComponente> alugueis = new ArrayList<>();

    private Main(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] argumentos) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Main(scanner).executar();
        } catch (NoSuchElementException excecao) {
            System.out.println("\nEntrada encerrada.");
        }
    }

    private void executar() {
        while (true) {
            System.out.print("""

                    === Locadora de veículos ===
                    1. Cadastrar cliente
                    2. Cadastrar veículo
                    3. Listar clientes e veículos
                    4. Criar aluguel
                    5. Listar aluguéis realizados
                    6. Sair
                    """);

            switch (lerInteiro("Escolha uma opção", 1, 6)) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarVeiculo();
                case 3 -> listarCadastros();
                case 4 -> criarAluguel();
                case 5 -> listarAlugueis();
                case 6 -> {
                    System.out.println("Até logo!");
                    return;
                }
            }
        }
    }

    private void cadastrarCliente() {
        String nome = lerTexto("Nome");
        String cpf = lerTexto("CPF");
        clientes.add(new Cliente(nome, cpf));
        System.out.println("Cliente cadastrado.");
    }

    private void cadastrarVeiculo() {
        String placa = lerTexto("Placa").toUpperCase(Locale.ROOT);
        String modelo = lerTexto("Modelo");
        double valorDiaria = lerValorDiaria();
        veiculos.add(new Veiculo(placa, modelo, valorDiaria));
        System.out.println("Veículo cadastrado.");
    }

    private void listarCadastros() {
        System.out.println("\nClientes");
        exibirOpcoes(clientes, Cliente::toString);
        System.out.println("\nVeículos");
        exibirOpcoes(veiculos, Main::descreverVeiculo);
    }

    private void criarAluguel() {
        Cliente cliente = escolher("Escolha o cliente", clientes, Cliente::toString);
        Veiculo veiculo = escolher("Escolha o veículo", veiculos, Main::descreverVeiculo);
        int dias = lerInteiro("Quantidade de dias", 1, Integer.MAX_VALUE);
        TarifaStrategy tarifa = escolher("Escolha a tarifa", TARIFAS, TarifaStrategy::getNome);

        Aluguel aluguel = new Aluguel(cliente, veiculo, dias, tarifa);
        AluguelComponente aluguelComAdicionais = adicionarServicos(new AluguelBase(aluguel));
        if (!Double.isFinite(aluguelComAdicionais.getCusto())) {
            System.out.println("O valor total excede o limite de cálculo. Tente novamente com menos dias.");
            return;
        }

        alugueis.add(aluguelComAdicionais);
        System.out.println("\nAluguel criado.");
        exibirResumo(aluguelComAdicionais);
    }

    private AluguelComponente adicionarServicos(AluguelComponente aluguel) {
        var disponiveis = new ArrayList<>(ADICIONAIS);
        while (!disponiveis.isEmpty()) {
            System.out.println("\nAdicionais disponíveis");
            exibirOpcoes(disponiveis, Map.Entry::getKey);
            System.out.println("0. Concluir aluguel");
            int opcao = lerInteiro("Escolha um adicional", 0, disponiveis.size());
            if (opcao == 0) {
                break;
            }
            var adicional = disponiveis.remove(opcao - 1);
            aluguel = adicional.getValue().apply(aluguel);
        }
        return aluguel;
    }

    private void listarAlugueis() {
        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel realizado.");
            return;
        }

        System.out.println("\nAluguéis realizados");
        for (int indice = 0; indice < alugueis.size(); indice++) {
            System.out.printf("%nAluguel %d%n", indice + 1);
            exibirResumo(alugueis.get(indice));
        }
    }

    private void exibirResumo(AluguelComponente aluguel) {
        System.out.println(aluguel.getDescricao());
        System.out.println("Valor total: " + formatarValor(aluguel.getCusto()));
    }

    private <T> T escolher(String mensagem, List<T> opcoes, Function<T, String> descrever) {
        System.out.println("\n" + mensagem);
        exibirOpcoes(opcoes, descrever);
        return opcoes.get(lerInteiro("Opção", 1, opcoes.size()) - 1);
    }

    private <T> void exibirOpcoes(List<T> opcoes, Function<T, String> descrever) {
        for (int indice = 0; indice < opcoes.size(); indice++) {
            System.out.printf("%d. %s%n", indice + 1, descrever.apply(opcoes.get(indice)));
        }
    }

    private String lerLinha(String mensagem) {
        System.out.print(mensagem + ": ");
        return scanner.nextLine().strip();
    }

    private String lerTexto(String mensagem) {
        while (true) {
            String texto = lerLinha(mensagem);
            if (!texto.isBlank()) {
                return texto;
            }
            System.out.println("O campo não pode ficar vazio.");
        }
    }

    private int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            String entrada = lerLinha(mensagem);
            try {
                int numero = Integer.parseInt(entrada);
                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException excecao) {
                // A mensagem abaixo também vale para entradas que não são números inteiros.
            }
            System.out.printf("Informe um número inteiro entre %d e %d.%n", minimo, maximo);
        }
    }

    private double lerValorDiaria() {
        while (true) {
            String entrada = lerLinha("Valor da diária em reais");
            if (entrada.matches("[0-9]+([.,][0-9]{1,2})?")) {
                double valor = Double.parseDouble(entrada.replace(',', '.'));
                if (Double.isFinite(valor) && valor > 0) {
                    return valor;
                }
            }
            System.out.println("Informe um valor positivo com até duas casas decimais (exemplo 120,50).");
        }
    }

    private static String descreverVeiculo(Veiculo veiculo) {
        return veiculo.getModelo() + " (" + veiculo.getPlaca() + ") - "
                + formatarValor(veiculo.getValorDiaria()) + "/dia";
    }

    private static String formatarValor(double valor) {
        return String.format(PORTUGUES_BRASIL, "R$ %,.2f", valor);
    }
}
