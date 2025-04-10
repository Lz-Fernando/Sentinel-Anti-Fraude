import java.util.Scanner;

public class Cadastro {
    public static void main(String[] args) {
        CadastroUsuario service = new CadastroUsuario();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nBem-vindo ao Sistema de Cadastro AntiGolpe\n");

        while (true) {
            System.out.println("\n====== MENU ======");
            System.out.println("1. Cadastrar Novo Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Confirmar E-mail");
            System.out.println("4. Fazer Login");
            System.out.println("5. Sair");
            System.out.println("6. Recuperar Senha");
            System.out.print("Escolha uma opção: ");

            int opcao = -1;

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Entrada inválida. Digite um número de 1 a 6.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\nCadastro de Novo Usuário");

                    System.out.print("Digite o Nome: ");
                    String nome = scanner.nextLine();

                    String email;
                    while (true) {
                        System.out.print("Digite o E-mail: ");
                        email = scanner.nextLine();
                        if (!service.validarEmail(email)) {
                            System.out.println("E-mail inválido. Tente novamente.");
                        } else if (service.emailJaCadastrado(email)) {
                            System.out.println("E-mail já cadastrado. Tente outro.");
                        } else {
                            break;
                        }
                    }

                    String telefone;
                    while (true) {
                        System.out.print("Digite o Telefone (apenas números): ");
                        telefone = scanner.nextLine();
                        if (!service.validarTelefone(telefone)) {
                            System.out.println("Telefone inválido. Tente novamente.");
                        } else if (service.telefoneJaCadastrado(telefone)) {
                            System.out.println("Telefone já cadastrado. Tente outro.");
                        } else {
                            break;
                        }
                    }

                    String senha;
                    while (true) {
                        System.out.print("Digite a Senha: ");
                        senha = scanner.nextLine();
                        if (!service.validarSenha(senha)) {
                            System.out.println("Senha fraca. Tente novamente.");
                        } else {
                            break;
                        }
                    }

                    service.cadastrarUsuario(nome, email, telefone, senha);
                    break;

                case 2:
                    service.listarUsuarios();
                    break;

                case 3:
                    System.out.print("\nDigite o Token de Confirmação: ");
                    String token = scanner.nextLine();
                    service.confirmarEmail(token);
                    break;

                case 4:
                    System.out.print("\nDigite seu E-mail: ");
                    String emailLogin = scanner.nextLine();

                    System.out.print("Digite sua Senha: ");
                    String senhaLogin = scanner.nextLine();

                    Usuario usuario = service.buscarUsuarioPorEmail(emailLogin);

                    if (usuario == null) {
                        System.out.println("Usuário não encontrado.");
                    } else if (!usuario.isEmailConfirmado()) {
                        System.out.println("E-mail não confirmado.");
                    } else if (!usuario.getSenha().equals(senhaLogin)) {
                        System.out.println("Senha incorreta.");
                    } else {
                        System.out.println("\nLogin realizado com sucesso. Bem-vindo, " + usuario.getNome() + "!");
                        usuario.adicionarMensagem("Login efetuado com sucesso.");

                        while (true) {
                            System.out.println("\nDigite a mensagem para análise ou 'sair' para encerrar:");
                            String mensagem = scanner.nextLine();

                            if (mensagem.equalsIgnoreCase("sair")) {
                                System.out.println("Encerrando envio de mensagens.");
                                break;
                            }

                            String resultado = TestaAPI.testarAPI(mensagem);
                            usuario.adicionarMensagem("Mensagem analisada: \"" + mensagem + "\" -> Resultado: " + resultado);
                            System.out.println("\nResultado da Análise: " + resultado);
                        }
                    }
                    break;

                case 5:
                    System.out.println("\nSaindo... Obrigado por usar o AntiGolpe.");
                    scanner.close();
                    return;

                case 6:
                    System.out.print("\nDigite seu E-mail para recuperação de senha: ");
                    String emailRecuperar = scanner.nextLine();

                    if (service.solicitarRecuperacaoSenha(emailRecuperar)) {
                        System.out.print("Digite o token recebido: ");
                        String tokenRecuperar = scanner.nextLine();

                        System.out.print("Digite a nova senha: ");
                        String novaSenha = scanner.nextLine();

                        if (!service.validarSenha(novaSenha)) {
                            System.out.println("Senha fraca. Recuperação cancelada.");
                            break;
                        }

                        service.redefinirSenha(emailRecuperar, tokenRecuperar, novaSenha);
                    }
                    break;

                default:
                    System.out.println("Opção inválida. Digite um número de 1 a 6.");
            }
        }
    }
}
