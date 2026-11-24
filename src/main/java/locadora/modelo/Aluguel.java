package locadora.modelo;

import locadora.tarifa.TarifaStrategy;

// Contexto do Strategy: delega o cálculo do valor à tarifa escolhida.
public class Aluguel {

    private final Cliente cliente;
    private final Veiculo veiculo;
    private final int dias;
    private final TarifaStrategy tarifa;

    public Aluguel(Cliente cliente, Veiculo veiculo, int dias, TarifaStrategy tarifa) {
        if (dias <= 0) {
            throw new IllegalArgumentException("A quantidade de dias deve ser maior que zero.");
        }
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dias = dias;
        this.tarifa = tarifa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public int getDias() {
        return dias;
    }

    public TarifaStrategy getTarifa() {
        return tarifa;
    }

    public double calcularValorBase() {
        return tarifa.calcular(veiculo, dias);
    }
}
