package locadora.adicional;

// Decorator concreto: adiciona o GPS (R$ 30,00).
public class GpsDecorator extends AdicionalDecorator {

    private static final double VALOR = 30.00;

    public GpsDecorator(AluguelComponente embrulhado) {
        super(embrulhado);
    }

    @Override
    public double getCusto() {
        return embrulhado.getCusto() + VALOR;
    }

    @Override
    public String getDescricao() {
        return embrulhado.getDescricao() + " + GPS";
    }
}
