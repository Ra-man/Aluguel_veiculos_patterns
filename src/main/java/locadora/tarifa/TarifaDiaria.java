package locadora.tarifa;

import locadora.modelo.Veiculo;

// Estratégia concreta: cobra o valor da diária por dia.
public class TarifaDiaria implements TarifaStrategy {

    @Override
    public double calcular(Veiculo veiculo, int dias) {
        return veiculo.getValorDiaria() * dias;
    }

    @Override
    public String getNome() {
        return "Diária";
    }
}
