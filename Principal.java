
import java.util.ArrayList;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class Principal {

    private static ArrayList<Colaborador> colaboradores =
            new ArrayList<>();

    private static Scanner entrada = new Scanner(System.in);

    private static NumberFormat moeda =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println("       SISTEMA DE FOLHA DE PAGAMENTO");
            System.out.println("========================================");
            System.out.println("1 - Cadastrar Colaborador Padrão");
            System.out.println("2 - Cadastrar Colaborador Comissionado");
            System.out.println("3 - Cadastrar Colaborador Produção");
            System.out.println("4 - Gerar Folha de Pagamento");
            System.out.println("5 - Exibir Resumo da Folha");
            System.out.println("0 - Sair");
            System.out.println("========================================");

            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {

                case 1:
                    cadastrarPadrao();
                    break;

                case 2:
                    cadastrarComissionado();
                    break;

                case 3:
                    cadastrarProducao();
                    break;

                case 4:
                    gerarFolhaPagamento();
                    break;

                case 5:
                    exibirResumo();
                    break;

                case 0:
                    System.out.println("Sistema encerrado!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        entrada.close();
    }

    // Verifica se a matrícula já está cadastrada
    private static boolean matriculaExiste(int matricula) {

        for (Colaborador c : colaboradores) {
            if (c.getMatricula() == matricula) {
                return true;
            }
        }

        return false;
    }

    // Solicita uma matrícula válida e não repetida
    private static int lerMatricula() {

        while (true) {

            int matricula = lerInteiroPositivo("Matrícula: ");

            if (matriculaExiste(matricula)) {
                System.out.println("Esta matrícula já está cadastrada!");
            } else {
                return matricula;
            }
        }
    }

    // Cadastro padrão
    private static void cadastrarPadrao() {

        System.out.println("\n--- CADASTRO PADRÃO ---");

        int matricula = lerMatricula();
        String nome = lerTexto("Nome completo: ");

        colaboradores.add(
            new ColaboradorPadrao(matricula, nome)
        );

        System.out.println("Colaborador cadastrado com sucesso!");
    }

    // Cadastro comissionado
    private static void cadastrarComissionado() {

        System.out.println("\n--- CADASTRO COMISSIONADO ---");

        int matricula = lerMatricula();
        String nome = lerTexto("Nome completo: ");

        double vendas = lerDouble("Valor das vendas: R$ ");
        double percentual = lerDouble("Percentual de comissão: ");

        if (percentual > 100) {
            System.out.println("O percentual máximo permitido é 100%.");
            return;
        }

        colaboradores.add(
            new ColaboradorComissionado(
                matricula, nome, vendas, percentual
            )
        );

        System.out.println("Colaborador cadastrado com sucesso!");
    }

    // Cadastro de produção
    private static void cadastrarProducao() {

        System.out.println("\n--- CADASTRO PRODUÇÃO ---");

        int matricula = lerMatricula();
        String nome = lerTexto("Nome completo: ");

        int quantidade = lerInteiroNaoNegativo(
            "Quantidade de peças: "
        );

        double valor = lerDouble("Valor por peça: R$ ");

        colaboradores.add(
            new ColaboradorProducao(
                matricula, nome, quantidade, valor
            )
        );

        System.out.println("Colaborador cadastrado com sucesso!");
    }

    // Geração da folha de pagamento
    private static void gerarFolhaPagamento() {

        System.out.println("\n========== FOLHA DE PAGAMENTO ==========");

        if (colaboradores.isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado.");
            return;
        }

        for (Colaborador c : colaboradores) {

            System.out.println("\n----------------------------------------");

            c.exibirDados();

            System.out.println("----------------------------------------");
        }

        System.out.println("========== FIM DA FOLHA =================");
    }

    // Resumo da folha de pagamento
    private static void exibirResumo() {

        System.out.println("\n========== RESUMO DA FOLHA =============");

        int quantidade = colaboradores.size();
        double total = 0;

        if (quantidade == 0) {
            System.out.println("Nenhum colaborador cadastrado.");
            return;
        }

        for (Colaborador c : colaboradores) {
            total += c.calcularSalarioFinal();
        }

        double media = total / quantidade;

        System.out.println("Total de colaboradores: " + quantidade);

        System.out.println(
            "Valor total da folha: " + moeda.format(total)
        );

        System.out.println(
            "Média salarial: " + moeda.format(media)
        );

        System.out.println("========================================");
    }

    // Leitura de números inteiros
    private static int lerInteiro(String mensagem) {

        while (true) {

            try {
                System.out.print(mensagem);

                return Integer.parseInt(
                    entrada.nextLine().trim()
                );

            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido!");
            }
        }
    }

    // Matrícula obrigatoriamente positiva
    private static int lerInteiroPositivo(String mensagem) {

        int valor;

        do {
            valor = lerInteiro(mensagem);

            if (valor <= 0) {
                System.out.println("Digite um número maior que zero!");
            }

        } while (valor <= 0);

        return valor;
    }

    // Quantidade de peças não negativa
    private static int lerInteiroNaoNegativo(String mensagem) {

        int valor;

        do {
            valor = lerInteiro(mensagem);

            if (valor < 0) {
                System.out.println("Não são permitidos valores negativos!");
            }

        } while (valor < 0);

        return valor;
    }

    // Leitura de valores decimais
    private static double lerDouble(String mensagem) {

        while (true) {

            try {
                System.out.print(mensagem);

                String texto = entrada.nextLine()
                        .trim()
                        .replace(",", ".");

                double valor = Double.parseDouble(texto);

                if (!Double.isFinite(valor) || valor < 0) {
                    System.out.println(
                        "Digite um valor válido e não negativo!"
                    );
                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numérico válido!");
            }
        }
    }

    // Leitura de texto
    private static String lerTexto(String mensagem) {

        while (true) {

            System.out.print(mensagem);

            String texto = entrada.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("O nome não pode ficar vazio!");
        }
    }
}
