package locadora.adicional;

// Decorator: base abstrata dos adicionais. Embrulha outro componente,
// que pode ser o aluguel base ou outro adicional.
public abstract class AdicionalDecorator implements AluguelComponente {

    protected final AluguelComponente embrulhado;

    protected AdicionalDecorator(AluguelComponente embrulhado) {
        this.embrulhado = embrulhado;
    }
}
