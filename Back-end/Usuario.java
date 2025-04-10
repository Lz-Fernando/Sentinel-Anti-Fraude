import java.io.*;

public class Usuario {
    private String email;
    private String telefone;
    private String nome;
    private int id;
    private String senha;
    private boolean emailConfirmado;
    private String tokenConfirmacao;

    public Usuario() {}

    public Usuario(int id, String nome, String email, String telefone, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isEmailConfirmado() { return emailConfirmado; }
    public void setEmailConfirmado(boolean emailConfirmado) { this.emailConfirmado = emailConfirmado; }

    public String getTokenConfirmacao() { return tokenConfirmacao; }
    public void setTokenConfirmacao(String tokenConfirmacao) { this.tokenConfirmacao = tokenConfirmacao; }


    private String nomeArquivoHistorico() {
        return "historico_usuario_" + id + ".txt"; // Nome do arquivo
    }

    public void adicionarMensagem(String mensagem) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivoHistorico(), true))) {
            writer.println(mensagem);
        } catch (IOException e) {
            System.out.println("Erro ao salvar mensagem no histórico: " + e.getMessage());
        }
    }

    public void listarMensagens() {
        File arquivo = new File(nomeArquivoHistorico());
        if (!arquivo.exists()) {
            System.out.println("Nenhuma mensagem encontrada para este usuário.");
            return;
        }

        System.out.println("\n Histórico de Mensagens:");
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println("- " + linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler histórico: " + e.getMessage());
        }
    }
}
