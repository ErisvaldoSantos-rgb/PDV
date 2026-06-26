package view;

import controller.VendaController;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TelaPDV extends JFrame {

    // Componentes do Painel Produto (Exibição)
    private JTextField txtCodProdutoExib;
    private JTextField txtNomeProduto;
    private JTextField txtPrecoProduto;
    private JTextField txtEstoqueProduto;

    // Componentes do Painel Venda (Entrada)
    private JTextField txtCodProdutoInput;
    private JTextField txtQuantidadeInput;
    private JButton btnAdicionar;

    // Componentes do Painel Financeiro
    private JTextField txtTotal;
    private JTextField txtValorPago;
    private JTextField txtTroco;

    // Botões de Ação Geral
    private JButton btnNovaVenda;
    private JButton btnFinalizarVenda;
    private JButton btnCancelarVenda;

   
    public TelaPDV() {
        setTitle("Sistema de Frente de Caixa - PDV");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setJMenuBar(criarBarraDeFerramentas());

        // Inicializa e adiciona os painéis principais
        add(criarPainelSuperior(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);
    }

    

    // --- CONSTRUÇÃO DA INTERFACE ---

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // 1. Painel Produto (Apenas exibição das informações do item consultado)
        JPanel pnlProduto = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlProduto.setBorder(BorderFactory.createTitledBorder("Informações do Produto"));

        txtCodProdutoExib = new JTextField(); txtCodProdutoExib.setEditable(false);
        txtNomeProduto = new JTextField();    txtNomeProduto.setEditable(false);
        txtPrecoProduto = new JTextField();   txtPrecoProduto.setEditable(false);
        txtEstoqueProduto = new JTextField(); txtEstoqueProduto.setEditable(false);

        pnlProduto.add(new JLabel("Código:"));  pnlProduto.add(txtCodProdutoExib);
        pnlProduto.add(new JLabel("Nome:"));    pnlProduto.add(txtNomeProduto);
        pnlProduto.add(new JLabel("Preço R$:")); pnlProduto.add(txtPrecoProduto);
        pnlProduto.add(new JLabel("Estoque:")); pnlProduto.add(txtEstoqueProduto);

        // 2. Painel Venda (Onde o operador digita para inserir no carrinho)
        JPanel pnlVenda = new JPanel(new GridLayout(3, 2, 5, 5));
        pnlVenda.setBorder(BorderFactory.createTitledBorder("Lançamento de Itens"));

        txtCodProdutoInput = new JTextField();
        txtQuantidadeInput = new JTextField();
        btnAdicionar = new JButton("Adicionar Item");

        pnlVenda.add(new JLabel("Código do Produto:")); pnlVenda.add(txtCodProdutoInput);
        pnlVenda.add(new JLabel("Quantidade:"));        pnlVenda.add(txtQuantidadeInput);
        pnlVenda.add(new JLabel(""));                   pnlVenda.add(btnAdicionar);

        painel.add(pnlProduto);
        painel.add(pnlVenda);
        return painel;
    }

    private JPanel criarPainelCentral() {
        // Painel Financeiro
        JPanel pnlFinanceiro = new JPanel(new GridLayout(1, 3, 10, 10));
        pnlFinanceiro.setBorder(BorderFactory.createTitledBorder("Resumo Financeiro"));

        // Customização de fontes para os campos financeiros ficarem em destaque
        Font fonteDestaque = new Font("Arial", Font.BOLD, 20);

        JPanel pnlTotal = new JPanel(new BorderLayout());
        pnlTotal.add(new JLabel("TOTAL R$:"), BorderLayout.NORTH);
        txtTotal = new JTextField("0.00");
        txtTotal.setFont(fonteDestaque);
        txtTotal.setEditable(false);
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        pnlTotal.add(txtTotal, BorderLayout.CENTER);

        JPanel pnlPago = new JPanel(new BorderLayout());
        pnlPago.add(new JLabel("VALOR PAGO R$:"), BorderLayout.NORTH);
        txtValorPago = new JTextField();
        txtValorPago.setFont(fonteDestaque);
        txtValorPago.setHorizontalAlignment(JTextField.RIGHT);
        pnlPago.add(txtValorPago, BorderLayout.CENTER);

        JPanel pnlTroco = new JPanel(new BorderLayout());
        pnlTroco.add(new JLabel("TROCO R$:"), BorderLayout.NORTH);
        txtTroco = new JTextField("0.00");
        txtTroco.setFont(fonteDestaque);
        txtTroco.setEditable(false);
        txtTroco.setHorizontalAlignment(JTextField.RIGHT);
        pnlTroco.add(txtTroco, BorderLayout.CENTER);

        pnlFinanceiro.add(pnlTotal);
        pnlFinanceiro.add(pnlPago);
        pnlFinanceiro.add(pnlTroco);

        // Margem interna para o painel financeiro não colar nas bordas
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        container.add(pnlFinanceiro, BorderLayout.CENTER);

        return container;
    }

    private JPanel criarPainelInferior() {
        // Botões de Controle da Venda
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        btnNovaVenda = new JButton("Nova Venda");
        btnFinalizarVenda = new JButton("Finalizar Venda");
        btnCancelarVenda = new JButton("Cancelar Venda");

        // Dimensionando os botões uniformemente
        Dimension dimBotao = new Dimension(140, 40);
        btnNovaVenda.setPreferredSize(dimBotao);
        btnFinalizarVenda.setPreferredSize(dimBotao);
        btnCancelarVenda.setPreferredSize(dimBotao);

        painelBotoes.add(btnNovaVenda);
        painelBotoes.add(btnCancelarVenda);
        painelBotoes.add(btnFinalizarVenda);

        return painelBotoes;
    }

    private JMenuBar criarBarraDeFerramentas() {
    JMenuBar menuBar = new JMenuBar();

    JMenu menuGerenciamento = new JMenu("Gerenciamento");
    JMenuItem itemCadastrar = new JMenuItem("Cadastrar Produto Manualmente");
    JMenuItem itemPesquisar = new JMenuItem("Pesquisar Produtos");
    JMenuItem itemHistorico = new JMenuItem("Histórico de Vendas");

    menuGerenciamento.add(itemCadastrar);
    menuGerenciamento.add(itemPesquisar);
    menuGerenciamento.addSeparator();
    menuGerenciamento.add(itemHistorico);
    menuBar.add(menuGerenciamento);

    // Ligações diretas usando caixas de input gráficas simples para manter a aplicação modular
    itemCadastrar.addActionListener(e -> {
        try {
            int cod = Integer.parseInt(JOptionPane.showInputDialog(this, "Código do Novo Produto:"));
            String nome = JOptionPane.showInputDialog(this, "Nome do Produto:");
            double preco = Double.parseDouble(JOptionPane.showInputDialog(this, "Preço Unitário (R$):"));
            int estoque = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade Inicial em Estoque:"));

            // Procura a instância ativa do controller para delegar a persistência
            // Para isso funcionar diretamente, certifique-se de que o VendaController está instanciado.
            for (java.awt.event.ActionListener al : btnNovaVenda.getActionListeners()) {
                if (al.getClass().getName().contains("VendaController")) {
                    // Vinculado dinamicamente via eventos do controller
                }
            }
            
            // Alternativa limpa: repassar a ação ao controller usando um padrão simplificado
            // Avisar ao operador para usar a janela de controle associada
            if (controllerInstanciado != null) {
                controllerInstanciado.cadastrarProduto(cod, nome, preco, estoque);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Operação cancelada ou dados inválidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    });

    itemPesquisar.addActionListener(e -> {
        String termo = JOptionPane.showInputDialog(this, "Digite o nome ou parte do nome do produto:");
        if (termo != null && controllerInstanciado != null) {
            java.util.ArrayList<model.Produto> prods = controllerInstanciado.buscarProdutosPorNome(termo);
            if (prods.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum produto correspondente encontrado.");
            } else {
                StringBuilder sb = new StringBuilder("Produtos Encontrados:\n\n");
                for (model.Produto p : prods) {
                    sb.append(String.format("Código: %d | %s | R$ %.2f | Estoque: %d\n", p.getCodigo(), p.getNome(), p.getPreco(), p.getEstoque()));
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        }
    });

    itemHistorico.addActionListener(e -> {
        if (controllerInstanciado != null) {
            controllerInstanciado.exibirHistoricoVendas();
        }
    });

    return menuBar;
}

// Atributo auxiliar para conectar a barra diretamente ao controller ativo
private VendaController controllerInstanciado;
public void associarController(VendaController controller) {
    this.controllerInstanciado = controller;
}

    // --- MÉTODOS DE CONTROLE (GETTERS E SETTERS) ---
    // Essenciais para que o seu Controller manipule a View sem quebrar o encapsulamento.

    public String getCodigoInput() { return txtCodProdutoInput.getText(); }
    public String getQuantidadeInput() { return txtQuantidadeInput.getText(); }
    public String getValorPagoInput() { return txtValorPago.getText(); }

    public void setExibicaoProduto(String cod, String nome, String preco, String estoque) {
        txtCodProdutoExib.setText(cod);
        txtNomeProduto.setText(nome);
        txtPrecoProduto.setText(preco);
        txtEstoqueProduto.setText(estoque);
    }

    public void setTotal(String total) { txtTotal.setText(total); }
    public void setTroco(String troco) { txtTroco.setText(troco); }

    public void limparCampos() {
        txtCodProdutoExib.setText("");
        txtNomeProduto.setText("");
        txtPrecoProduto.setText("");
        txtEstoqueProduto.setText("");
        txtCodProdutoInput.setText("");
        txtQuantidadeInput.setText("");
        txtTotal.setText("0.00");
        txtValorPago.setText("");
        txtTroco.setText("0.00");
    }

    // --- LISTENERS (Para capturar os cliques dos botões no Controller) ---

    public void addAdicionarItemListener(ActionListener listener) { btnAdicionar.addActionListener(listener); }
    public void addNovaVendaListener(ActionListener listener) { btnNovaVenda.addActionListener(listener); }
    public void addFinalizarVendaListener(ActionListener listener) { btnFinalizarVenda.addActionListener(listener); }
    public void addCancelarVendaListener(ActionListener listener) { btnCancelarVenda.addActionListener(listener); }

    // Método main apenas para testar o visual da tela isoladamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPDV().setVisible(true);
        });
    }
}