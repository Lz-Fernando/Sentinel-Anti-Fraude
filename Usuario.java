public class Usuario {
    private String email;
    private String telefone;
    private String nome;
    private int id;
    private String senha;
    private HistoricoMensagens historicoMensagens;

    public Usuario() {}

    public Usuario(int id, String nome, String email, String telefone, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.historicoMensagens = new HistoricoMensagens();
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

    public HistoricoMensagens gHistoricoMensagens(){
        return historicoMensagens;
    }

    public void adicionarMensagem(String mensagem){
        historicoMensagens.adicionarMensagem(mensagem);
    }
}
