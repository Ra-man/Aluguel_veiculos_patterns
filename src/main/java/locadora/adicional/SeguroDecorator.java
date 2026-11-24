package locadora.adicional;

// Decorator concreto: adiciona o seguro (R$ 50,00).
public class SeguroDecorator extends AdicionalDecorator {

    private static final double VALOR = 50.00;

    public SeguroDecorator(AluguelComponente embrulhado) {
        super(embrulhado);
    }

    @Override
    public double getCusto() {
        return embrulhado.getCusto() + VALOR;
    }

    @Override
    public String getDescricao() {
        return embrulhado.getDescricao() + " + Seguro";
    }
}
