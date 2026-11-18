import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class InterfaceGrafica {

    private static final ArrayList<Colaborador> colaboradores = new ArrayList<>();
    private static final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final Color FUNDO = new Color(13, 20, 35);
    private static final Color CARTAO = new Color(24, 34, 54);
    private static final Color AZUL = new Color(52, 142, 244);
    private static final Color TEXTO = new Color(232, 239, 250);
    private static JFrame janela;
    private static JTable tabela;
    private static DefaultTableModel modelo;
    private static JLabel totalPessoas;
    private static JLabel totalFolha;
    private static JTextField busca;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignorada) {
                // A aparência personalizada continua disponível sem o tema do sistema.
            }
            criarJanela();
        });
    }

    private static void criarJanela() {
        janela = new JFrame("Folha Certa | Gestão de pagamentos");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setMinimumSize(new Dimension(900, 620));
        janela.setSize(1100, 720);
        janela.setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout(0, 22));
        raiz.setBackground(FUNDO);
        raiz.setBorder(BorderFactory.createEmptyBorder(28, 34, 24, 34));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);
        JPanel titulos = new JPanel(new GridLayout(2, 1, 0, 5));
        titulos.setOpaque(false);
        JLabel titulo = rotulo("Folha Certa", 29, Font.BOLD, TEXTO);
        JLabel subtitulo = rotulo("Gestão de colaboradores e pagamentos", 14, Font.PLAIN, new Color(158, 174, 198));
        titulos.add(titulo);
        titulos.add(subtitulo);
        JLabel estado = rotulo("SISTEMA PRONTO", 11, Font.BOLD, new Color(99, 220, 173));
        estado.setHorizontalAlignment(SwingConstants.RIGHT);
        cabecalho.add(titulos, BorderLayout.WEST);
        cabecalho.add(estado, BorderLayout.EAST);

        JPanel conteudo = new JPanel(new BorderLayout(0, 18));
        conteudo.setOpaque(false);
        JPanel indicadores = new JPanel(new GridLayout(1, 2, 16, 0));
        indicadores.setOpaque(false);
        totalPessoas = rotulo("0 colaboradores", 20, Font.BOLD, TEXTO);
        totalFolha = rotulo(moeda.format(0), 20, Font.BOLD, TEXTO);
        indicadores.add(cartao("EQUIPE CADASTRADA", totalPessoas));
        indicadores.add(cartao("TOTAL ESTIMADO DA FOLHA", totalFolha));

        JPanel areaTabela = new JPanel(new BorderLayout(0, 13));
        areaTabela.setBackground(CARTAO);
        areaTabela.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(43, 58, 82)),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        JPanel barra = new JPanel(new BorderLayout(12, 0));
        barra.setOpaque(false);
        JLabel listaTitulo = rotulo("Colaboradores", 18, Font.BOLD, TEXTO);
        busca = new JTextField();
        busca.setToolTipText("Pesquise por nome, matrícula ou tipo");
        busca.setPreferredSize(new Dimension(260, 36));
        busca.addActionListener(e -> atualizarTabela());
        JButton pesquisar = botao("Pesquisar", false);
        pesquisar.addActionListener(e -> atualizarTabela());
        JPanel buscaPainel = new JPanel(new BorderLayout(8, 0));
        buscaPainel.setOpaque(false);
        buscaPainel.add(busca, BorderLayout.CENTER);
        buscaPainel.add(pesquisar, BorderLayout.EAST);
        barra.add(listaTitulo, BorderLayout.WEST);
        barra.add(buscaPainel, BorderLayout.EAST);

        modelo = new DefaultTableModel(new Object[]{"Matrícula", "Nome", "Categoria", "Salário final"}, 0) {
            @Override public boolean isCellEditable(int linha, int coluna) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setRowHeight(34);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabela.setFillsViewportHeight(true);
        JScrollPane rolagem = new JScrollPane(tabela);
        areaTabela.add(barra, BorderLayout.NORTH);
        areaTabela.add(rolagem, BorderLayout.CENTER);

        JPanel acoes = new JPanel(new GridLayout(2, 3, 12, 12));
        acoes.setOpaque(false);
        adicionarAcao(acoes, "Cadastrar salário fixo", () -> cadastrarPadrao(), true);
        adicionarAcao(acoes, "Cadastrar com comissão", () -> cadastrarComissionado(), false);
        adicionarAcao(acoes, "Cadastrar por produção", () -> cadastrarProducao(), false);
        adicionarAcao(acoes, "Gerar folha detalhada", () -> gerarFolha(), false);
        adicionarAcao(acoes, "Ver resumo da folha", () -> exibirResumo(), false);
        adicionarAcao(acoes, "Remover selecionado", () -> removerSelecionado(), false);

        conteudo.add(indicadores, BorderLayout.NORTH);
        conteudo.add(areaTabela, BorderLayout.CENTER);
        conteudo.add(acoes, BorderLayout.SOUTH);
        JLabel rodape = rotulo("Os valores são calculados automaticamente conforme a categoria de cada colaborador.", 12, Font.PLAIN, new Color(139, 156, 181));
        raiz.add(cabecalho, BorderLayout.NORTH);
        raiz.add(conteudo, BorderLayout.CENTER);
        raiz.add(rodape, BorderLayout.SOUTH);
        janela.setContentPane(raiz);
        atualizarTabela();
        janela.setVisible(true);
    }

    private static JLabel rotulo(String texto, int tamanho, int estilo, Color cor) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", estilo, tamanho));
        label.setForeground(cor);
        return label;
    }

    private static JPanel cartao(String titulo, JLabel valor) {
        JPanel painel = new JPanel(new GridLayout(2, 1, 0, 10));
        painel.setBackground(CARTAO);
        painel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(43, 58, 82)), BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        painel.add(rotulo(titulo, 11, Font.BOLD, new Color(151, 171, 201)));
        painel.add(valor);
        return painel;
    }

    private static JButton botao(String texto, boolean principal) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 13));
        botao.setBackground(principal ? AZUL : new Color(37, 52, 77));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(150, 42));
        return botao;
    }

    private static void adicionarAcao(JPanel painel, String texto, Runnable acao, boolean principal) {
        JButton botao = botao(texto, principal);
        botao.addActionListener(e -> acao.run());
        painel.add(botao);
    }

    private static void atualizarTabela() {
        if (modelo == null) return;
        modelo.setRowCount(0);
        String filtro = busca == null ? "" : busca.getText().trim().toLowerCase(new Locale("pt", "BR"));
        double total = 0;
        for (Colaborador c : colaboradores) {
            total += c.calcularSalarioFinal();
            String matricula = String.valueOf(c.getMatricula());
            String nome = c.getNome();
            String tipo = c.getTipo();
            if (filtro.isEmpty() || matricula.contains(filtro) || nome.toLowerCase(new Locale("pt", "BR")).contains(filtro) || tipo.toLowerCase(new Locale("pt", "BR")).contains(filtro)) {
                modelo.addRow(new Object[]{matricula, nome, tipo, moeda.format(c.calcularSalarioFinal())});
            }
        }
        totalPessoas.setText(colaboradores.size() + (colaboradores.size() == 1 ? " colaborador" : " colaboradores"));
        totalFolha.setText(moeda.format(total));
    }

    private static boolean matriculaExiste(int matricula) {
        for (Colaborador c : colaboradores) if (c.getMatricula() == matricula) return true;
        return false;
    }

    private static Integer lerMatricula() {
        while (true) {
            Integer matricula = lerInteiro("Matrícula", true);
            if (matricula == null) return null;
            if (!matriculaExiste(matricula)) return matricula;
            avisar("Esta matrícula já está cadastrada. Informe outra matrícula.");
        }
    }

    private static void cadastrarPadrao() {
        Integer matricula = lerMatricula(); if (matricula == null) return;
        String nome = lerTexto("Nome completo"); if (nome == null) return;
        colaboradores.add(new ColaboradorPadrao(matricula, nome));
        concluirCadastro("Colaborador cadastrado com salário fixo.");
    }

    private static void cadastrarComissionado() {
        Integer matricula = lerMatricula(); if (matricula == null) return;
        String nome = lerTexto("Nome completo"); if (nome == null) return;
        Double vendas = lerDecimal("Valor total das vendas"); if (vendas == null) return;
        Double percentual = lerDecimal("Percentual de comissão (de 0 a 100)"); if (percentual == null) return;
        if (percentual > 100) { avisar("O percentual de comissão deve estar entre 0 e 100."); return; }
        colaboradores.add(new ColaboradorComissionado(matricula, nome, vendas, percentual));
        concluirCadastro("Colaborador comissionado cadastrado.");
    }

    private static void cadastrarProducao() {
        Integer matricula = lerMatricula(); if (matricula == null) return;
        String nome = lerTexto("Nome completo"); if (nome == null) return;
        Integer quantidade = lerInteiro("Quantidade de peças produzidas", false); if (quantidade == null) return;
        Double valor = lerDecimal("Valor pago por peça"); if (valor == null) return;
        colaboradores.add(new ColaboradorProducao(matricula, nome, quantidade, valor));
        concluirCadastro("Colaborador por produção cadastrado.");
    }

    private static void concluirCadastro(String mensagem) { atualizarTabela(); avisar(mensagem); }

    private static void removerSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { avisar("Selecione um colaborador na tabela para remover."); return; }
        String matricula = String.valueOf(modelo.getValueAt(linha, 0));
        Colaborador encontrado = null;
        for (Colaborador c : colaboradores) if (String.valueOf(c.getMatricula()).equals(matricula)) encontrado = c;
        if (encontrado != null && JOptionPane.showConfirmDialog(janela, "Deseja remover " + encontrado.getNome() + " da equipe?", "Confirmar remoção", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            colaboradores.remove(encontrado);
            atualizarTabela();
        }
    }

    private static void gerarFolha() {
        if (colaboradores.isEmpty()) { avisar("Cadastre colaboradores para gerar a folha de pagamento."); return; }
        StringBuilder texto = new StringBuilder("FOLHA DE PAGAMENTO\n\n");
        for (Colaborador c : colaboradores) {
            texto.append("Matrícula: ").append(c.getMatricula()).append("\nNome: ").append(c.getNome())
                .append("\nCategoria: ").append(c.getTipo()).append("\nSalário fixo: ").append(moeda.format(c.getSalarioBase()));
            if (c instanceof ColaboradorComissionado) {
                ColaboradorComissionado com = (ColaboradorComissionado) c;
                texto.append("\nVendas: ").append(moeda.format(com.getValorVendas())).append("\nComissão: ").append(moeda.format(com.calcularComissao()));
            } else if (c instanceof ColaboradorProducao) {
                ColaboradorProducao prod = (ColaboradorProducao) c;
                texto.append("\nPeças produzidas: ").append(prod.getQuantidadePecas()).append("\nValor da produção: ").append(moeda.format(prod.calcularProdutividade()));
            }
            texto.append("\nSalário final: ").append(moeda.format(c.calcularSalarioFinal())).append("\n\n");
        }
        JTextArea area = new JTextArea(texto.toString(), 22, 54);
        area.setEditable(false); area.setFont(new Font("Monospaced", Font.PLAIN, 13)); area.setCaretPosition(0);
        JOptionPane.showMessageDialog(janela, new JScrollPane(area), "Folha de pagamento", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void exibirResumo() {
        if (colaboradores.isEmpty()) { avisar("Cadastre colaboradores para consultar o resumo da folha."); return; }
        double total = 0;
        for (Colaborador c : colaboradores) total += c.calcularSalarioFinal();
        String mensagem = "Colaboradores: " + colaboradores.size() + "\nTotal estimado da folha: " + moeda.format(total) + "\nMédia salarial: " + moeda.format(total / colaboradores.size());
        JOptionPane.showMessageDialog(janela, mensagem, "Resumo da folha", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String lerTexto(String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(janela, mensagem);
            if (valor == null) return null;
            valor = valor.trim();
            if (!valor.isEmpty()) return valor;
            avisar("Preencha este campo para continuar.");
        }
    }

    private static Integer lerInteiro(String mensagem, boolean positivo) {
        while (true) {
            String valor = JOptionPane.showInputDialog(janela, mensagem);
            if (valor == null) return null;
            try {
                int numero = Integer.parseInt(valor.trim());
                if (positivo ? numero <= 0 : numero < 0) { avisar(positivo ? "Informe um número maior que zero." : "Informe um número igual ou maior que zero."); continue; }
                return numero;
            } catch (NumberFormatException e) { avisar("Informe um número inteiro válido."); }
        }
    }

    private static Double lerDecimal(String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(janela, mensagem);
            if (valor == null) return null;
            try {
                String normalizado = valor.trim().replace(" ", "").replace("R$", "");
                if (normalizado.contains(",")) normalizado = normalizado.replace(".", "").replace(",", ".");
                double numero = Double.parseDouble(normalizado);
                if (!Double.isFinite(numero) || numero < 0) { avisar("Informe um valor igual ou maior que zero."); continue; }
                return numero;
            } catch (NumberFormatException e) { avisar("Informe um valor numérico válido, como 1250,50."); }
        }
    }

    private static void avisar(String mensagem) {
        JOptionPane.showMessageDialog(janela, mensagem, "Folha Certa", JOptionPane.INFORMATION_MESSAGE);
    }
}
