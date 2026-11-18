
public class ColaboradorComissionado extends Colaborador {

    private double valorVendas;
    private double percentualComissao;

    public ColaboradorComissionado(
        int matricula,
        String nome,
        double valorVendas,
        double percentualComissao
    ) {
        super(matricula, nome);
        setValorVendas(valorVendas);
        setPercentualComissao(percentualComissao);
    }

    public double getValorVendas() {
        return valorVendas;
    }

    public void setValorVendas(double valorVendas) {
        if (!Double.isFinite(valorVendas) || valorVendas < 0) {
            throw new IllegalArgumentException(
                "O valor das vendas não pode ser negativo."
            );
        }

        this.valorVendas = valorVendas;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        if (!Double.isFinite(percentualComissao)
                || percentualComissao < 0
                || percentualComissao > 100) {
            throw new IllegalArgumentException(
                "O percentual deve estar entre 0 e 100."
            );
        }

        this.percentualComissao = percentualComissao;
    }

    // Calcula a comissão
    public double calcularComissao() {
        return valorVendas * percentualComissao / 100;
    }

    // Calcula o salário final
    @Override
    public double calcularSalarioFinal() {
        return getSalarioBase() + calcularComissao();
    }

    @Override
    public String getTipo() {
        return "Comissionado";
    }

    @Override
    public void exibirDados() {
        super.exibirDados();

        System.out.printf(
            "Valor das Vendas: R$ %.2f%n",
            valorVendas
        );

        System.out.printf(
            "Percentual de Comissão: %.2f%%%n",
            percentualComissao
        );

        System.out.printf(
            "Comissão: R$ %.2f%n",
            calcularComissao()
        );
    }
}
