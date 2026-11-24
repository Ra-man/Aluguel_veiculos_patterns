package locadora.adicional;

import locadora.modelo.Aluguel;

// Decorator: componente concreto, o aluguel sem adicionais.
public class AluguelBase implements AluguelComponente {

    private final Aluguel aluguel;

    public AluguelBase(Aluguel aluguel) {
        this.aluguel = aluguel;
    }

    @Override
    public double getCusto() {
        return aluguel.calcularValorBase();
    }

    @Override
    public String getDescricao() {
        return "Cliente: " + aluguel.getCliente().getNome()
                + " | Veículo: " + aluguel.getVeiculo().getModelo()
                + " | Dias: " + aluguel.getDias()
                + " | Tarifa: " + aluguel.getTarifa().getNome();
    }
}
