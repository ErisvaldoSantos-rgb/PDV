package controller;
import model.Usuario;
import java.util.ArrayList;

public class Autenticacao {

    private ArrayList<Usuario> usuarios;

    public Autenticacao() {
        usuarios = new ArrayList<>();

        // Usuários padrão do sistema
        usuarios.add(new Usuario("admin", "123"));
    }

    public boolean autenticar(String login, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login) &&
                    usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}
