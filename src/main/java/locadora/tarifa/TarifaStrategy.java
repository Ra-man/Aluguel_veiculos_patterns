package locadora.tarifa;

import locadora.modelo.Veiculo;

// Strategy: interface comum para as formas de cálculo da tarifa.
public interface TarifaStrategy {

    double calcular(Veiculo veiculo, int dias);

    String getNome();
}
