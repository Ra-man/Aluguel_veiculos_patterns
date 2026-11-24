package locadora.tarifa;

import locadora.modelo.Veiculo;

// Estratégia concreta: 10% de desconto para aluguéis de 7 dias ou mais.
public class TarifaSemanal implements TarifaStrategy {

    private static final int DIAS_MINIMOS_DESCONTO = 7;
    private static final double DESCONTO = 0.10;

    @Override
    public double calcular(Veiculo veiculo, int dias) {
        double valor = veiculo.getValorDiaria() * dias;
        if (dias >= DIAS_MINIMOS_DESCONTO) {
            valor -= valor * DESCONTO;
        }
        return valor;
    }

    @Override
    public String getNome() {
        return "Semanal";
    }
}
