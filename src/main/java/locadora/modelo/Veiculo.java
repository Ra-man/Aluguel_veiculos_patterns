package locadora.modelo;

public class Veiculo {

    private final String placa;
    private final String modelo;
    private final double valorDiaria;

    public Veiculo(String placa, String modelo, double valorDiaria) {
        this.placa = placa;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - R$ %.2f/dia", modelo, placa, valorDiaria);
    }
}
