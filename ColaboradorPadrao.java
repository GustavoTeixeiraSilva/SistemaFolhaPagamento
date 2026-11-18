
public class ColaboradorPadrao extends Colaborador {

    public ColaboradorPadrao(int matricula, String nome) {
        super(matricula, nome);
    }

    @Override
    public double calcularSalarioFinal() {
        return getSalarioBase();
    }

    @Override
    public String getTipo() {
        return "Padrão";
    }

    @Override
    public void exibirDados() {
        super.exibirDados();
    }
}
