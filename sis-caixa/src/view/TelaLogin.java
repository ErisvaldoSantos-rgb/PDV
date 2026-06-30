package view;

import controller.Autenticacao;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class TelaLogin extends JFrame {

    // Constantes de Cores e Fontes para Consistência
    private static final Color COR_FUNDO_JANELA = new Color(240, 242, 245); // Cinza super claro, moderno
    private static final Color COR_PRINCIPAL_CARD = new Color(52, 152, 219); // Azul vibrante do dashboard
    private static final Color COR_TEXTO_BRANCO = Color.WHITE;
    private static final Color COR_TEXTO_ESCURO = new Color(44, 62, 80);
    private static final Color COR_BORDA_CAMPO = new Color(189, 195, 199);
    private static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    private Autenticacao autenticacao;

    public TelaLogin() {
        autenticacao = new Autenticacao();

        setTitle("Login PDV - Bem-vindo");
        setSize(500, 480); // Ligeiramente mais alto para acomodar a sombra e novos elementos
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Configura o fundo da janela
        getContentPane().setBackground(COR_FUNDO_JANELA);
        setLayout(new GridBagLayout());

        // Painel Principal (Card de Login) com bordas arredondadas e SOMBRA
        JPanel painelPrincipal = new RoundedPanel(20, COR_PRINCIPAL_CARD, true); // true para adicionar sombra
        painelPrincipal.setLayout(new GridBagLayout());
        painelPrincipal.setOpaque(false);
        painelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40)); // Espaçamento interno generoso

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento padrão entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preencher horizontalmente por padrão

        // --- Título de Boas-Vindas ---
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblBoasVindas = new JLabel("Seja Bem-vindo");
        lblBoasVindas.setFont(FONTE_TITULO);
        lblBoasVindas.setForeground(COR_TEXTO_BRANCO);
        lblBoasVindas.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(lblBoasVindas, gbc);

        // --- Subtítulo ---
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10); // Menos espaço acima, mais espaço abaixo
        JLabel lblTituloApp = new JLabel("Sistema de Ponto de Venda (PDV)");
        lblTituloApp.setFont(FONTE_SUBTITULO);
        lblTituloApp.setForeground(new Color(255, 255, 255, 200)); // Branco levemente transparente
        lblTituloApp.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(lblTituloApp, gbc);

        // Reinicia gridwidth e insets para os campos
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        // --- Campo Login ---
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblLogin = new JLabel("Login:"); // Adicionado ícone unicode
        lblLogin.setFont(FONTE_LABEL);
        lblLogin.setForeground(COR_TEXTO_BRANCO);
        painelPrincipal.add(lblLogin, gbc);

        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        estilizarCampoTexto(txtLogin);
        painelPrincipal.add(txtLogin, gbc);

        // --- Campo Senha ---
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblSenha = new JLabel("Senha:"); // Adicionado ícone unicode
        lblSenha.setFont(FONTE_LABEL);
        lblSenha.setForeground(COR_TEXTO_BRANCO);
        painelPrincipal.add(lblSenha, gbc);

        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        estilizarCampoTexto(txtSenha);
        txtSenha.addActionListener(e -> realizarLogin());
        painelPrincipal.add(txtSenha, gbc);

        // --- Botão Entrar ---
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa a largura total do card
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 8, 10, 8); // Mais espaço acima do botão
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ocupa toda a largura disponível

        btnEntrar = new JButton("Entrar");
        estilizarBotao(btnEntrar);
        btnEntrar.addActionListener(e -> realizarLogin());
        painelPrincipal.add(btnEntrar, gbc);

        // Adiciona o card principal centralizado
        add(painelPrincipal);

        pack();
    }

    /**
     * Aplica um estilo moderno e consistente aos campos de texto/senha.
     */
    private void estilizarCampoTexto(JTextField campo) {
        campo.setFont(FONTE_CAMPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorderField(12, COR_BORDA_CAMPO), // Borda arredondada suave
                BorderFactory.createEmptyBorder(8, 12, 8, 12) // Mais espaçamento interno (padding)
        ));
        campo.setBackground(Color.WHITE);
        campo.setForeground(COR_TEXTO_ESCURO);
        campo.setCaretColor(COR_TEXTO_ESCURO);
    }

    /**
     * Aplica um estilo moderno e efeitos de hover ao botão.
     */
   private void estilizarBotao(JButton botao) {
    botao.setContentAreaFilled(false); // Fundo transparente para customização no paintComponent
    botao.setOpaque(false);
    botao.setForeground(COR_TEXTO_BRANCO);
    botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
    botao.setFocusPainted(false);
    botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    botao.setBorder(new EmptyBorder(10, 25, 10, 25)); // Padding interno do botão
    
    // O MouseListener antigo foi removido daqui para eliminar o efeito visual
}

    // --- Subclasse para renderizar o botão com fundo customizado e hover ---
    // (Poderia ser um componente separado, mas mantendo aqui para simplicidade)
    @Override
   public void paint(Graphics g) {
    super.paint(g);
    if (btnEntrar != null) {
        Graphics2D g2d = (Graphics2D) btnEntrar.getGraphics();
        if (g2d != null) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Define uma cor sólida e fixa para o botão (sem hover)
            Color corFixaBotao = COR_PRINCIPAL_CARD.darker(); 
            
            g2d.setColor(corFixaBotao);
            g2d.fillRoundRect(0, 0, btnEntrar.getWidth(), btnEntrar.getHeight(), 15, 15);
            btnEntrar.paintComponents(g2d); // Desenha o texto do botão por cima
            g2d.dispose();
        }
    }
}

    private void realizarLogin() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        // Simulação de autenticação (mantenha a integração com seu controller real)
        if (autenticacao.autenticar(login, senha)) {
            TelaPDV telaPdv = new TelaPDV();
            new controller.VendaController(telaPdv);
            telaPdv.setVisible(true);
            dispose();
        } else {
            // Estilizando a mensagem de erro para ser mais amigável
            UIManager.put("OptionPane.messageFont", FONTE_LABEL);
            UIManager.put("OptionPane.okButtonText", "Entendido");
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.\nPor favor, tente novamente.", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText(""); // limpa a senha
            txtSenha.requestFocus(); // foca na senha para reentrada
        }
    }

    public static void main(String[] args) {
        // Tenta usar o visual do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }

    // =========================================================================
    // CLASSES AUXILIARES DE UI (Refatoradas e Melhoradas)
    // =========================================================================

    /**
     * Classe para criar um painel com bordas arredondadas e sombra opcional.
     */
    class RoundedPanel extends JPanel {

        private int radius;
        private Color backgroundColor;
        private boolean dropShadow;

        public RoundedPanel(int radius, Color bgColor, boolean dropShadow) {
            this.radius = radius;
            this.backgroundColor = bgColor;
            this.dropShadow = dropShadow;
            setOpaque(false); // Importante para transparência da borda
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // --- Desenha a Sombra (se ativada) ---
            if (dropShadow) {
                g2d.setColor(new Color(0, 0, 0, 30)); // Preto super transparente
                // Desenha múltiplos retângulos ligeiramente deslocados para simular sombra suave
                for (int i = 1; i <= 4; i++) {
                    g2d.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, radius + i, radius + i);
                }
            }

            // --- Desenha o Fundo Arredondado ---
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, getWidth() - (dropShadow ? 5 : 0), getHeight() - (dropShadow ? 5 : 0), radius, radius);

            g2d.dispose();
        }
    }

    /**
     * Classe auxiliar para criar uma borda arredondada SÓLIDA para os campos de entrada.
     */
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
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            // Retorna insets baseados no raio para centralizar o texto internamente
            return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
        }
    }
}