package controller;
import java.util.ArrayList;
import model.Operador;

public class Autenticacao {

    private ArrayList<Operador> operadors;

    public Autenticacao() {
        operadors = new ArrayList<>();

        // Usuários padrão do sistema
        operadors.add(new Operador("admin", "123"));
    }

    public boolean autenticar(String login, String senha) {
        for (Operador usuario : operadors) {
            if (usuario.getLogin().equals(login) &&
                    usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return ArrayList<Usuario> return the operadors
     */
    public ArrayList<Operador> getoperadors() {
        return operadors;
    }

    /**
     * @param operadors the operadors to set
     */
    public void setoperadors(ArrayList<Operador> operadors) {
        this.operadors = operadors;
    }

}
