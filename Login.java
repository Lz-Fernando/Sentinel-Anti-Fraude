import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        CadastroUsuario cadastro = new CadastroUsuario(); // Você deve ter um cadastro já com usuários

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite seu e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = cadastro.buscarUsuarioPorEmail(email);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
        } else {
            if (!usuario.isEmailConfirmado()) {
                System.out.println("E-mail ainda não confirmado. Confirme antes de fazer login.");
            } else if (!usuario.getSenha().equals(senha)) {
                System.out.println("Senha incorreta.");
            } else {
                System.out.println("Login realizado com sucesso! Bem-vindo, " + usuario.getNome() + "!");
            }
        }

        scanner.close();
    }
}
