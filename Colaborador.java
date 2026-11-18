
public class Colaborador {

    public static final double SALARIO_BASE = 2000.00;

    private int matricula;
    private String nome;
    private double salarioBase;

    // Construtor
    public Colaborador(int matricula, String nome) {
        setMatricula(matricula);
        setNome(nome);
        this.salarioBase = SALARIO_BASE;
    }

    // Getters e Setters
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        if (matricula <= 0) {
            throw new IllegalArgumentException(
                "A matrícula deve ser maior que zero."
            );
        }

        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome não pode ficar vazio."
            );
        }

        this.nome = nome.trim();
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (!Double.isFinite(salarioBase) || salarioBase < 0) {
            throw new IllegalArgumentException(
                "O salário-base não pode ser negativo."
            );
        }

        this.salarioBase = salarioBase;
    }

    // Calcula o salário final
    public double calcularSalarioFinal() {
        return salarioBase;
    }

    // Retorna o tipo do colaborador
    public String getTipo() {
        return "Padrão";
    }

    // Exibe os dados do colaborador
    public void exibirDados() {
        System.out.println("Matrícula: " + matricula);
        System.out.println("Nome: " + nome);
        System.out.println("Tipo: " + getTipo());
        System.out.printf("Salário Base: R$ %.2f%n", salarioBase);
        System.out.printf(
            "Salário Final: R$ %.2f%n",
            calcularSalarioFinal()
        );
    }
}
