import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class TestaAPI {

    public static String testarAPI(String mensagem) {
        String respostaFinal = "";

        try {
            URL url = URI.create("https://sentinel-api-4i70.onrender.com/verificar").toURL(); // Forma moderna
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

            // Verifica se a resposta foi sucesso ou erro
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

                // Formatação "manual" básica do JSON
                System.out.println("\nResposta da API:");
                System.out.println(respostaFinal.replace(",", ",\n").replace("{", "{\n").replace("}", "\n}"));

                // Validação simples do status
                if (respostaFinal.toLowerCase().contains("\"fraude\"")) {
                    System.out.println("\n⚠️ Alerta: Possível fraude detectada!");
                } else if (respostaFinal.toLowerCase().contains("\"ok\"")) {
                    System.out.println("\n✅ Mensagem segura.");
                } else {
                    System.out.println("\nℹ️ Resposta inesperada da API.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return respostaFinal;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a mensagem que você quer analisar:");
        String mensagemUsuario = scanner.nextLine();

        testarAPI(mensagemUsuario);
        scanner.close();
    }
}
