package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.ItemVenda;
import model.Produto;
import model.Venda;
import service.Caixa;
import view.TelaPDV;

public class VendaController {

    private TelaPDV view;
    private Venda modelVenda;
    private Caixa caixa;
    
    // Armazenamento em memória (Simulando o Banco de Dados)
    private ArrayList<Produto> listaProdutos;
    private ArrayList<Venda> historicoVendas;

    public VendaController(TelaPDV view) {
        this.view = view;
        this.caixa = new Caixa();
        this.modelVenda = new Venda();
        this.listaProdutos = new ArrayList<>();
        this.historicoVendas = new ArrayList<>();


        // Produtos iniciais padrão
        inicializarProdutosPadrao();

        // Vinculando ações da interface aos métodos do Controller
        this.view.addAdicionarItemListener(new AdicionarItemListener());
        this.view.addNovaVendaListener(new NovaVendaListener());
        this.view.addCancelarVendaListener(new CancelarVendaListener());
        this.view.addFinalizarVendaListener(new FinalizarVendaListener());
        this.view.associarController(this);
    }

    private void inicializarProdutosPadrao() {
        listaProdutos.add(new Produto(1, "Refrigerante", 7.50, 20));
        listaProdutos.add(new Produto(2, "Chocolate", 4.00, 35));
        listaProdutos.add(new Produto(3, "Água", 2.50, 50));
        listaProdutos.add(new Produto(4, "Cachaça", 10.00, 5));
        listaProdutos.add(new Produto(5, "Queijo ralado", 6.50, 40));
        listaProdutos.add(new Produto(6, "Feijão", 8.50, 30));
        listaProdutos.add(new Produto(7, "Farinha", 6.50, 91));
        listaProdutos.add(new Produto(8, "Arroz", 4.50, 30));
    }

    // --- NOVAS FUNCIONALIDADES DE GERENCIAMENTO ---

    /**
     * Cadastro Manual de Produtos
     */
    public void cadastrarProduto(int codigo, String nome, double preco, int estoque) {
        if (buscarProdutoPorCodigo(codigo) != null) {
            JOptionPane.showMessageDialog(view, "Erro: Já existe um produto com o código " + codigo, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        listaProdutos.add(new Produto(codigo, nome, preco, estoque));
        JOptionPane.showMessageDialog(view, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pesquisa de Produtos (Por Código ou por Nome)
     */
    public Produto buscarProdutoPorCodigo(int codigo) {
        for (Produto p : listaProdutos) {
            if (p.getCodigo() == codigo) return p;
        }
        return null;
    }

    public ArrayList<Produto> buscarProdutosPorNome(String termo) {
        ArrayList<Produto> encontrados = new ArrayList<>();
        for (Produto p : listaProdutos) {
            if (p.getNome().toLowerCase().contains(termo.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    /**
     * Exibe o Histórico de Vendas Realizadas
     */
    public void exibirHistoricoVendas() {
        if (historicoVendas.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhuma venda registrada no histórico.", "Histórico", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder relatorio = new StringBuilder("=== HISTÓRICO DE VENDAS ===\n\n");
        double faturamentoTotal = 0;
        int contador = 1;

        for (Venda v : historicoVendas) {
            relatorio.append(String.format("Venda nº %d\n", contador++));
            for (ItemVenda item : v.getItens()) {
                relatorio.append(String.format("  - %s (x%d): R$ %.2f\n", 
                        item.getProduto().getNome(), item.getQuantidade(), item.getSubtotal()));
            }
            double totalComDesconto = v.calcularTotal(); // Se houver desconto armazenado na venda
            relatorio.append(String.format("  Total da Venda: R$ %.2f\n", totalComDesconto));
            relatorio.append("--------------------------------------\n");
            faturamentoTotal += totalComDesconto;
        }
        relatorio.append(String.format("\nFaturamento Bruto Total: R$ %.2f", faturamentoTotal));

        JOptionPane.showMessageDialog(view, relatorio.toString(), "Histórico de Vendas", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- LISTENERS DE INTERFACE ---

    private class AdicionarItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codText = view.getCodigoInput().trim();
                String qtdText = view.getQuantidadeInput().trim();

                if (codText.isEmpty() || qtdText.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Preencha Código e Quantidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int codigo = Integer.parseInt(codText);
                int quantidade = Integer.parseInt(qtdText);

                Produto produto = buscarProdutoPorCodigo(codigo);
                if (produto == null) {
                    JOptionPane.showMessageDialog(view, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (produto.getEstoque() < quantidade) {
                    JOptionPane.showMessageDialog(view, "Estoque insuficiente! Disponível: " + produto.getEstoque(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                view.setExibicaoProduto(
                    String.valueOf(produto.getCodigo()), 
                    produto.getNome(), 
                    String.format("%.2f", produto.getPreco()), 
                    String.valueOf(produto.getEstoque())
                );

                ItemVenda item = new ItemVenda(produto, quantidade);
                modelVenda.adicionarItem(item);

                // Atualiza o subtotal na tela
                view.setTotal(String.format("%.2f", modelVenda.calcularTotal()));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class NovaVendaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modelVenda = new Venda();
            view.limparCampos();
            JOptionPane.showMessageDialog(view, "Nova venda pronta.");
        }
    }

    private class CancelarVendaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modelVenda = new Venda();
            view.limparCampos();
            JOptionPane.showMessageDialog(view, "Venda cancelada.");
        }
    }

    private class FinalizarVendaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (modelVenda.getItens().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Carrinho vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double subtotal = modelVenda.calcularTotal();

                // SOLICITAÇÃO DO DESCONTO POR PERCENTUAL
                String stringDesconto = JOptionPane.showInputDialog(view, "Digite a porcentagem de desconto (0 para nenhum):", "Desconto (%)", JOptionPane.QUESTION_MESSAGE);
                double porcentagemDesconto = (stringDesconto == null || stringDesconto.trim().isEmpty()) ? 0 : Double.parseDouble(stringDesconto);
                
                if (porcentagemDesconto < 0 || porcentagemDesconto > 100) {
                    JOptionPane.showMessageDialog(view, "Desconto inválido! Deve ser entre 0 e 100%.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double valorDesconto = subtotal * (porcentagemDesconto / 100.0);
                double totalComDesconto = subtotal - valorDesconto;

                // Atualiza o total com desconto na tela antes do pagamento
                view.setTotal(String.format("%.2f", totalComDesconto));

                // Recebendo o pagamento
                String valorPagoText = view.getValorPagoInput().trim();
                if (valorPagoText.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Digite o valor pago pelo cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double valorPago = Double.parseDouble(valorPagoText.replace(",", "."));
                if (valorPago < totalComDesconto) {
                    JOptionPane.showMessageDialog(view, "Valor insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cálculo do troco
                double troco = caixa.calcularTroco(valorPago, totalComDesconto);
                view.setTroco(String.format("%.2f", troco));

                // Baixa de estoque
                for (ItemVenda item : modelVenda.getItens()) {
                    item.getProduto().baixarEstoque(item.getQuantidade());
                }

                // Salva cópia da venda concluída no histórico antes de limpar o modelo
                historicoVendas.add(modelVenda);

                // Exibe cupom fiscal simplificado
                emitirComprovante(subtotal, valorDesconto, totalComDesconto, valorPago, troco);

                JOptionPane.showMessageDialog(view, "Venda concluída com sucesso!");
                modelVenda = new Venda(); // Reseta para a próxima venda

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Insira valores numéricos coerentes.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void emitirComprovante(double subtotal, double desconto, double total, double pago, double troco) {
        StringBuilder cupom = new StringBuilder("===== CUPOM FISCAL SIMPLIFICADO =====\n\n");
        for (ItemVenda item : modelVenda.getItens()) {
            cupom.append(String.format("%-15s x%d  R$ %7.2f\n", 
                item.getProduto().getNome(), item.getQuantidade(), item.getSubtotal()));
        }
        cupom.append("-------------------------------------\n");
        cupom.append(String.format("Subtotal:         R$ %7.2f\n", subtotal));
        cupom.append(String.format("Desconto:        -R$ %7.2f\n", desconto));
        cupom.append(String.format("TOTAL:            R$ %7.2f\n", total));
        cupom.append(String.format("Valor Pago:       R$ %7.2f\n", pago));
        cupom.append(String.format("Troco:            R$ %7.2f\n", troco));
        
        JOptionPane.showMessageDialog(view, cupom.toString(), "Comprovante", JOptionPane.INFORMATION_MESSAGE);
    }
}