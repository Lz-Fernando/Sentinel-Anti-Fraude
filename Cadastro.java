import java.util.Scanner;

public class Cadastro {
    public static void main(String[] args) {
        CadastroUsuario service = new CadastroUsuario();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u2709 Cadastro de Usuários\n");

        while (true) {
            System.out.println("1. Cadastrar Novo Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (opcao == 1) {
                System.out.print("Digite o Nome: ");
                String nome = scanner.nextLine();

                System.out.print("Digite o Email: ");
                String email = scanner.nextLine();

                System.out.print("Digite o Telefone: ");
                String telefone = scanner.nextLine();

                System.out.print("Digite a Senha (6+ caracteres): ");
                String senha = scanner.nextLine();

                service.cadastrarUsuario(nome, email, telefone, senha);

            } else if (opcao == 2) {
                service.listarUsuarios();

            } else if (opcao == 3) {
                System.out.println("\ud83d\udc4b Saindo... Obrigado por usar o sistema!");
                break;

            } else {
                System.out.println("\u26a0\ufe0f Opção inválida. Tente novamente.");
            }

            System.out.println(); // Linha em branco para espaço
        }

        scanner.close();
    }
}
