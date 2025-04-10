
import java.util.ArrayList;
import java.util.List;

public class CadastroUsuario {
    private List<Usuario> usuarios = new ArrayList<>();
    private int idCounter = 1;

    public void cadastrarUsuario(String nome, String email, String telefone, String senha) {
        if (validarDados(email, senha)) {
            Usuario novoUsuario = new Usuario(idCounter++, nome, email, telefone, senha);
            usuarios.add(novoUsuario);
            System.out.println(" Usuário cadastrado com sucesso!");
        } else {
            System.out.println(" Dados inválidos. Cadastro não realizado.");
        }
    }

    private boolean validarDados(String email, String senha) {
        return email.contains("@") && validarSenha(senha) && !emailJaCadastrado(email);
    }

    private boolean validarSenha(String senha) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return senha.matches(regex);
    }

    public void listarUsuarios() {
        System.out.println(" Lista de Usuários:");
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome());
        }
    }

    private boolean emailJaCadastrado(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
