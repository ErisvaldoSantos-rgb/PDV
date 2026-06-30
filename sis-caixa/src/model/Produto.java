package model;
public class Produto {

   private int codigo;
   private String nome;
   private double preco;
   private int estoque;

   public Produto(int codigo, String nome, double preco, int estoque) {

       this.codigo = codigo;
       this.nome = nome;
       this.preco = preco;
       this.estoque = estoque;
   }

   public int getCodigo() {
       return codigo;
   }

   public String getNome() {
       return nome;
   }

   public double getPreco() {
       return preco;
   }

   public int getEstoque() {
       return estoque;
   }

   public void baixarEstoque(int quantidade) {
       estoque -= quantidade;
   }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @param preco the preco to set
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * @param estoque the estoque to set
     */
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

}
