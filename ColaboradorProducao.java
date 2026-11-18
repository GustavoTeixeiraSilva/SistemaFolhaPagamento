
public class ColaboradorProducao extends Colaborador {

    private int quantidadePecas;
    private double valorPorPeca;

    public ColaboradorProducao(
        int matricula,
        String nome,
        int quantidadePecas,
        double valorPorPeca
    ) {
        super(matricula, nome);
        setQuantidadePecas(quantidadePecas);
        setValorPorPeca(valorPorPeca);
    }

    public int getQuantidadePecas() {
        return quantidadePecas;
    }

    public void setQuantidadePecas(int quantidadePecas) {
        if (quantidadePecas < 0) {
            throw new IllegalArgumentException(
                "A quantidade de peças não pode ser negativa."
            );
        }

        this.quantidadePecas = quantidadePecas;
    }

    public double getValorPorPeca() {
        return valorPorPeca;
    }

    public void setValorPorPeca(double valorPorPeca) {
        if (!Double.isFinite(valorPorPeca) || valorPorPeca < 0) {
            throw new IllegalArgumentException(
                "O valor por peça não pode ser negativo."
            );
        }

        this.valorPorPeca = valorPorPeca;
    }

    // Calcula a produtividade
    public double calcularProdutividade() {
        return quantidadePecas * valorPorPeca;
    }

    // Calcula o salário final
    @Override
    public double calcularSalarioFinal() {
        return getSalarioBase() + calcularProdutividade();
    }

    @Override
    public String getTipo() {
        return "Produção";
    }

    @Override
    public void exibirDados() {
        super.exibirDados();

        System.out.println(
            "Quantidade de Peças: " + quantidadePecas
        );

        System.out.printf(
            "Valor por Peça: R$ %.2f%n",
            valorPorPeca
        );

        System.out.printf(
            "Produtividade: R$ %.2f%n",
            calcularProdutividade()
        );
    }
}
