package model;
public class ItemVenda {

   private Produto produto;
   private int quantidade;

   public ItemVenda(Produto produto, int quantidade) {
       this.produto = produto;
       this.quantidade = quantidade;
   }

   public double getSubtotal() {
       return produto.getPreco() * quantidade;
   }

   public Produto getProduto() {
       return produto;
   }

   public int getQuantidade() {
       return quantidade;
   }

    /**
     * @param produto the produto to set
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
