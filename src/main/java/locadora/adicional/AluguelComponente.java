package locadora.adicional;

// Decorator: interface comum ao aluguel base e aos adicionais.
public interface AluguelComponente {

    String getDescricao();

    double getCusto();
}
