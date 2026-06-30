package view;

import controller.VendaController;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class TelaPDV extends JFrame {

    // Constantes de Cores e Fontes (Idênticas à Tela de Login)
    private static final Color COR_FUNDO_JANELA = new Color(240, 242, 245); 
    private static final Color COR_PRINCIPAL_CARD = new Color(52, 152, 219); 
    private static final Color COR_TEXTO_BRANCO = Color.WHITE;
    private static final Color COR_TEXTO_ESCURO = new Color(44, 62, 80);
    private static final Color COR_BORDA_CAMPO = new Color(189, 195, 199);
    private static final Color COR_BOTAO_PERIGO = new Color(231, 76, 60); // Vermelho pastel para cancelar
    private static final Color COR_BOTAO_SUCESSO = new Color(46, 204, 113); // Verde pastel para finalizar
    
    private static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 14);

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

    private VendaController controllerInstanciado;

    public TelaPDV() {
        setTitle("Sistema de Frente de Caixa - PDV");
        setSize(900, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Altera a cor de fundo nativa do container principal
        getContentPane().setBackground(COR_FUNDO_JANELA);
        setLayout(new BorderLayout(15, 15));
        setJMenuBar(criarBarraDeFerramentas());

        // Inicializa e adiciona os painéis principais com espaçamentos adequados
        add(criarPainelSuperior(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);
    }

    // --- CONSTRUÇÃO DA INTERFACE ---

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 15, 15));
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        // 1. Painel Produto (Apenas exibição das informações)
        JPanel pnlProduto = new RoundedPanel(15, COR_PRINCIPAL_CARD);
        pnlProduto.setLayout(new GridLayout(4, 2, 8, 8));
        pnlProduto.setBorder(criarBordaPainel(" Informações do Produto "));

        txtCodProdutoExib = new JTextField(); 
        txtNomeProduto = new JTextField();    
        txtPrecoProduto = new JTextField();   
        txtEstoqueProduto = new JTextField(); 

        estilizarCampoTexto(txtCodProdutoExib, false);
        estilizarCampoTexto(txtNomeProduto, false);
        estilizarCampoTexto(txtPrecoProduto, false);
        estilizarCampoTexto(txtEstoqueProduto, false);

        pnlProduto.add(criarLabelForm("Código:"));      pnlProduto.add(txtCodProdutoExib);
        pnlProduto.add(criarLabelForm("Nome:"));        pnlProduto.add(txtNomeProduto);
        pnlProduto.add(criarLabelForm("Preço R$:"));    pnlProduto.add(txtPrecoProduto);
        pnlProduto.add(criarLabelForm("Estoque:"));     pnlProduto.add(txtEstoqueProduto);

        // 2. Painel Venda (Entrada de itens)
        JPanel pnlVenda = new RoundedPanel(15, COR_PRINCIPAL_CARD);
        pnlVenda.setLayout(new GridLayout(4, 2, 8, 8));
        pnlVenda.setBorder(criarBordaPainel(" Lançamento de Itens "));

        txtCodProdutoInput = new JTextField();
        txtQuantidadeInput = new JTextField();
        btnAdicionar = new JButton("Adicionar Item");
        
        estilizarCampoTexto(txtCodProdutoInput, true);
        estilizarCampoTexto(txtQuantidadeInput, true);
        estilizarBotaoEstatico(btnAdicionar, COR_PRINCIPAL_CARD.darker());

        pnlVenda.add(criarLabelForm("Código do Item:")); pnlVenda.add(txtCodProdutoInput);
        pnlVenda.add(criarLabelForm("Quantidade:"));     pnlVenda.add(txtQuantidadeInput);
        pnlVenda.add(new JLabel(""));                    pnlVenda.add(new JLabel("")); // Espaçadores para alinhar o botão embaixo
        pnlVenda.add(new JLabel(""));                    pnlVenda.add(btnAdicionar);

        painel.add(pnlProduto);
        painel.add(pnlVenda);
        return painel;
    }

    private JPanel criarPainelCentral() {
        // Painel Financeiro
        JPanel pnlFinanceiro = new RoundedPanel(15, COR_PRINCIPAL_CARD);
        pnlFinanceiro.setLayout(new GridLayout(1, 3, 15, 15));
        pnlFinanceiro.setBorder(criarBordaPainel(" Resumo Financeiro "));

        Font fonteDestaque = new Font("Segoe UI", Font.BOLD, 24);

        JPanel pnlTotal = new JPanel(new BorderLayout(5, 5));
        pnlTotal.setOpaque(false);
        pnlTotal.add(criarLabelForm("TOTAL R$:"), BorderLayout.NORTH);
        txtTotal = new JTextField("0.00");
        estilizarCampoFinanceiro(txtTotal, fonteDestaque, false);
        pnlTotal.add(txtTotal, BorderLayout.CENTER);

        JPanel pnlPago = new JPanel(new BorderLayout(5, 5));
        pnlPago.setOpaque(false);
        pnlPago.add(criarLabelForm("VALOR PAGO R$:"), BorderLayout.NORTH);
        txtValorPago = new JTextField();
        estilizarCampoFinanceiro(txtValorPago, fonteDestaque, true);
        pnlPago.add(txtValorPago, BorderLayout.CENTER);

        JPanel pnlTroco = new JPanel(new BorderLayout(5, 5));
        pnlTroco.setOpaque(false);
        pnlTroco.add(criarLabelForm("TROCO R$:"), BorderLayout.NORTH);
        txtTroco = new JTextField("0.00");
        estilizarCampoFinanceiro(txtTroco, fonteDestaque, false);
        pnlTroco.add(txtTroco, BorderLayout.CENTER);

        pnlFinanceiro.add(pnlTotal);
        pnlFinanceiro.add(pnlPago);
        pnlFinanceiro.add(pnlTroco);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        container.add(pnlFinanceiro, BorderLayout.CENTER);

        return container;
    }

    private JPanel criarPainelInferior() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelBotoes.setOpaque(false);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        btnNovaVenda = new JButton("Nova Venda");
        btnCancelarVenda = new JButton("Cancelar Venda");
        btnFinalizarVenda = new JButton("Finalizar Venda");

        // Estilização Estática dos botões (Sem Hover)
        estilizarBotaoEstatico(btnNovaVenda, COR_PRINCIPAL_CARD.darker());
        estilizarBotaoEstatico(btnCancelarVenda, COR_BOTAO_PERIGO);
        estilizarBotaoEstatico(btnFinalizarVenda, COR_BOTAO_SUCESSO);

        Dimension dimBotao = new Dimension(150, 45);
        btnNovaVenda.setPreferredSize(dimBotao);
        btnCancelarVenda.setPreferredSize(dimBotao);
        btnFinalizarVenda.setPreferredSize(dimBotao);

        painelBotoes.add(btnNovaVenda);
        painelBotoes.add(btnCancelarVenda);
        painelBotoes.add(btnFinalizarVenda);

        return painelBotoes;
    }

    // --- MÉTODOS AUXILIARES DE ESTILIZAÇÃO COMPARTILHADA ---

    private JLabel criarLabelForm(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_LABEL);
        label.setForeground(COR_TEXTO_BRANCO);
        return label;
    }

    private Border criarBordaPainel(String titulo) {
        Border bordaVazia = BorderFactory.createEmptyBorder(15, 20, 15, 20);
        TitledBorder bordaTitulo = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                titulo, TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), COR_TEXTO_BRANCO
        );
        return BorderFactory.createCompoundBorder(bordaTitulo, bordaVazia);
    }

    private void estilizarCampoTexto(JTextField campo, boolean editavel) {
        campo.setEditable(editavel);
        campo.setFont(FONTE_CAMPO);
        campo.setForeground(COR_TEXTO_ESCURO);
        campo.setCaretColor(COR_TEXTO_ESCURO);
        campo.setBackground(editavel ? Color.WHITE : new Color(245, 246, 250)); // Diferença sutil se for apenas exibição
        campo.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorderField(10, COR_BORDA_CAMPO),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
    }

    private void estilizarCampoFinanceiro(JTextField campo, Font fonte, boolean editavel) {
        campo.setEditable(editavel);
        campo.setFont(fonte);
        campo.setHorizontalAlignment(JTextField.RIGHT);
        campo.setForeground(COR_TEXTO_ESCURO);
        campo.setCaretColor(COR_TEXTO_ESCURO);
        campo.setBackground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorderField(12, COR_BORDA_CAMPO),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    /**
     * Aplica um estilo moderno, arredondado e totalmente ESTÁTICO ao botão (sem hover).
     */
    private void estilizarBotaoEstatico(JButton botao, Color corFundo) {
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.setForeground(COR_TEXTO_BRANCO);
        botao.setFont(FONTE_BOTAO);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Remove todos os MouseListeners antigos para garantir que nenhuma ação visual ocorra
        for (java.awt.event.MouseListener ml : botao.getMouseListeners()) {
            botao.removeMouseListener(ml);
        }

        // Sobrescrevemos a pintura do componente para desenhar uma cor sólida e fixa
        botao.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(corFundo);
                g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 12, 12);
                g2d.dispose();
                super.paint(g, c);
            }
        });
    }

    // --- MENUS ---

    private JMenuBar criarBarraDeFerramentas() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDA_CAMPO));

        JMenu menuGerenciamento = new JMenu("Gerenciamento");
        menuGerenciamento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JMenuItem itemCadastrar = new JMenuItem("Cadastrar Produto Manualmente");
        JMenuItem itemPesquisar = new JMenuItem("Pesquisar Produtos");
        JMenuItem itemHistorico = new JMenuItem("Histórico de Vendas");

        menuGerenciamento.add(itemCadastrar);
        menuGerenciamento.add(itemPesquisar);
        menuGerenciamento.addSeparator();
        menuGerenciamento.add(itemHistorico);
        menuBar.add(menuGerenciamento);

        // Ações de gerenciamento legadas mantidas idênticas
        itemCadastrar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(JOptionPane.showInputDialog(this, "Código do Novo Produto:"));
                String nome = JOptionPane.showInputDialog(this, "Nome do Produto:");
                double preco = Double.parseDouble(JOptionPane.showInputDialog(this, "Preço Unitário (R$):"));
                int estoque = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade Inicial em Estoque:"));

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

    public void associarController(VendaController controller) {
        this.controllerInstanciado = controller;
    }

    // --- GETTERS, SETTERS E LISTENERS MANTIDOS PARA O CONTROLLER ---

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

    public void addAdicionarItemListener(ActionListener listener) { btnAdicionar.addActionListener(listener); }
    public void addNovaVendaListener(ActionListener listener) { btnNovaVenda.addActionListener(listener); }
    public void addFinalizarVendaListener(ActionListener listener) { btnFinalizarVenda.addActionListener(listener); }
    public void addCancelarVendaListener(ActionListener listener) { btnCancelarVenda.addActionListener(listener); }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new TelaPDV().setVisible(true));
    }

    // =========================================================================
    // CLASSES DE DESIGN PERSONALIZADO (Componentes Gráficos)
    // =========================================================================

    class RoundedPanel extends JPanel {
        private int radius;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Sombra leve projetada nos cards internos do PDV
            g2d.setColor(new Color(0, 0, 0, 15));
            for (int i = 1; i <= 3; i++) {
                g2d.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, radius + i, radius + i);
            }

            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, radius, radius);
            g2d.dispose();
        }
    }

    class RoundedBorderField implements Border {
        private int radius;
        private Color color;

        public RoundedBorderField(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public boolean isBorderOpaque() { return false; }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
        }
    }
}