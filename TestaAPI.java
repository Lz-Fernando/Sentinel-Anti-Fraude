import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class TestaAPI {

    // Cores ANSI para o terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static String testarAPI(String mensagem) {
        String respostaFinal = "";

        try {
            URL url = URI.create("https://sentinel-api-4i70.onrender.com/verificar").toURL();
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json; utf-8");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);

            // Corpo da requisição
            String jsonInputString = "{ \"descricao\": \"" + mensagem + "\" }";

            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            InputStream respostaStream = (conexao.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST)
                    ? conexao.getInputStream()
                    : conexao.getErrorStream();

            // Lê a resposta da API
            try (BufferedReader br = new BufferedReader(new InputStreamReader(respostaStream, "utf-8"))) {
                StringBuilder resposta = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    resposta.append(responseLine.trim());
                }

                respostaFinal = resposta.toString();

                // Exibe a resposta formatada
                System.out.println("\nResposta da API:");
                System.out.println(formatarJson(respostaFinal));

                // Análise da resposta
                if (respostaFinal.toLowerCase().contains("\"fraude\"")) {
                    System.out.println(ANSI_RED + "\nAlerta: Possível fraude detectada!" + ANSI_RESET);
                } else if (respostaFinal.toLowerCase().contains("\"ok\"")) {
                    System.out.println(ANSI_GREEN + "\nMensagem segura." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_BLUE + "\nResposta inesperada da API." + ANSI_RESET);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return respostaFinal;
    }

    // Método para formatar JSON de forma simples
    public static String formatarJson(String json) {
        return json
                .replace("{", "{\n  ")
                .replace("}", "\n}")
                .replace(",", ",\n  ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_BLUE + "=== Sentinel API Tester ===" + ANSI_RESET);
        System.out.println("Digite a mensagem para análise (ou 'sair' para encerrar):");

        while (true) {
            System.out.print("\nMensagem: ");
            String mensagemUsuario = scanner.nextLine();

            if (mensagemUsuario.equalsIgnoreCase("sair")) {
                System.out.println("\nEncerrando o programa. Até mais! ");
                break;
            }

            testarAPI(mensagemUsuario);
        }

        scanner.close();
    }
}
