import java.util.Scanner;

public class HistoricoExibido {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HistoricoMensagens historico = new HistoricoMensagens();

        while (true) {
            System.out.println("\n MENU:");
            System.out.println("1 - Adicionar nova mensagem");
            System.out.println("2 - Exibir histórico de mensagens");
            System.out.println("3 - Limpar histórico");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print(" Digite sua mensagem: ");
                    String mensagem = scanner.nextLine();
                    historico.adicionarMensagem(mensagem);
                    break;
                case 2:
                    historico.obterHistorico();
                    break;
                case 3:
                    System.out.print(" Tem certeza que deseja limpar o histórico? (S/N): ");
                    String confirmacao = scanner.nextLine();
                    if (confirmacao.equalsIgnoreCase("S")) {
                        historico.limparHistorico();
                    } else {
                        System.out.println(" O histórico foi mantido.");
                    }
                    break;
                case 4:
                    System.out.println(" Saindo... Obrigado!");
                    scanner.close();
                    return;
                default:
                    System.out.println(" Opção inválida. Tente novamente.");
            }
        }
    }
}
