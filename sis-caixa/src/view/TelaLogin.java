package view;

import controller.Autenticacao;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class TelaLogin extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    private Autenticacao autenticacao;

    public TelaLogin() {
        autenticacao = new Autenticacao();

        setTitle("Login PDV");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Configura o fundo do frame e o layout para centralizar o card de login
        getContentPane().setBackground(new Color(255, 255,0));
        setLayout(new GridBagLayout());

        // Painel principal (card de login) com a mesma identidade visual da aplicação (Dark Slate Blue)
        JPanel painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(218, 165, 32)); // Mesma cor do menu lateral da aplicação
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        painelPrincipal.setLayout(new GridBagLayout());
        painelPrincipal.setOpaque(false);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Espaçamento entre os componentes

       
        // Subtítulo do sistema
        gbc.gridy = 1;
        JLabel lblTituloApp = new JLabel("Sistema de PDV");
        lblTituloApp.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTituloApp.setForeground(Color.WHITE);
        painelPrincipal.add(lblTituloApp, gbc);

        // Etiquetas e campos de texto
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLogin.setForeground(Color.WHITE);
        painelPrincipal.add(lblLogin, gbc);

        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        txtLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtLogin.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(189, 195, 199)), // Borda cinza suave
                BorderFactory.createEmptyBorder(5, 10, 5, 10)     // Espaçamento interno
        ));
        txtLogin.setBackground(Color.WHITE);             // Fundo branco de alto contraste
        txtLogin.setForeground(new Color(44, 62, 80));   // Texto escuro
        txtLogin.setCaretColor(new Color(44, 62, 80));
        painelPrincipal.add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSenha.setForeground(Color.WHITE);
        painelPrincipal.add(lblSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        txtSenha = new JPasswordField(15);
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtSenha.setBackground(Color.WHITE);
        txtSenha.setForeground(new Color(44, 62, 80));
        txtSenha.setCaretColor(new Color(44, 62, 80));
        txtSenha.addActionListener(e -> realizarLogin());
        painelPrincipal.add(txtSenha, gbc);

        // Botão de Entrar (com a cor azul do dashboard)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 8, 8, 8);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(52, 152, 219).darker()),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnEntrar.setContentAreaFilled(true);
        btnEntrar.setOpaque(true);
        btnEntrar.setForeground(Color.WHITE);                 // Texto branco
        btnEntrar.setBackground(new Color(52, 152, 219));    // Fundo azul vibrante (cor do dashboard)
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEntrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEntrar.setBackground(new Color(52, 152, 219).brighter()); // Fundo mais claro ao passar o mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEntrar.setBackground(new Color(52, 152, 219));
            }
        });

        btnEntrar.addActionListener(e -> realizarLogin());
        painelPrincipal.add(btnEntrar, gbc);

        add(painelPrincipal);

        pack();
    }

    private void realizarLogin() {
    String login = txtLogin.getText();
    String senha = new String(txtSenha.getPassword());

    if (autenticacao.autenticar(login, senha)) {
        TelaPDV telaPdv = new TelaPDV();
        // Passa a tela para o controlador gerenciar as ações e escutar os botões
            new controller.VendaController(telaPdv);
            telaPdv.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText(""); // limpa a senha para nova tentativa
        }
    }

    public static void main(String[] args) {
        // Tenta usar o visual do sistema operacional
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}

// Classe auxiliar para criar uma borda arredondada
class RoundedBorder implements Border {
    private int radius;
    private Color color;

    public RoundedBorder(int radius) {
        this(radius, Color.WHITE);
    }

    public RoundedBorder(int radius, Color color) {
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
        return new Insets(radius / 2 + 1, radius / 2 + 1, radius / 2 + 2, radius / 2 + 1);
    }
}
