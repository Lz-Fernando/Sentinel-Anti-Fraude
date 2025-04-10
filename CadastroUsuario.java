import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CadastroUsuario {
    private List<Usuario> usuarios = new ArrayList<>();
    private int idCounter = 1;
    private static final String ARQUIVO_CSV = "usuarios.csv";
    private Map<String, String> tokensRecuperacao = new HashMap<>(); // <email, token>

    public CadastroUsuario() {
        carregarUsuarios();
    }

    public void cadastrarUsuario(String nome, String email, String telefone, String senha) {
        Usuario novoUsuario = new Usuario(idCounter++, nome, email, telefone, senha);

        String token = gerarTokenConfirmacao();
        novoUsuario.setTokenConfirmacao(token);
        novoUsuario.setEmailConfirmado(false);

        usuarios.add(novoUsuario);
        salvarUsuarios();
        System.out.println("Usuário cadastrado com sucesso.");

        enviarEmailConfirmacao(novoUsuario);
    }

    // Validações públicas
    public boolean validarEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }

    public boolean validarTelefone(String telefone) {
        String regex = "^\\d{10,11}$";
        return telefone.matches(regex);
    }

    public boolean validarSenha(String senha) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return senha.matches(regex);
    }

    public boolean emailJaCadastrado(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean telefoneJaCadastrado(String telefone) {
        for (Usuario usuario : usuarios) {
            if (usuario.getTelefone().equals(telefone)) {
                return true;
            }
        }
        return false;
    }

    // Recuperação de senha
    public boolean solicitarRecuperacaoSenha(String email) {
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario != null) {
            String tokenRecuperacao = UUID.randomUUID().toString();
            tokensRecuperacao.put(email.toLowerCase(), tokenRecuperacao);

            System.out.println("\nSolicitação de recuperação de senha:");
            System.out.println("E-mail: " + email);
            System.out.println("Token de recuperação: " + tokenRecuperacao);
            System.out.println("Use este token para redefinir sua senha.\n");

            return true;
        }
        System.out.println("E-mail não encontrado no sistema.");
        return false;
    }

    public boolean redefinirSenha(String email, String tokenRecebido, String novaSenha) {
        if (tokensRecuperacao.containsKey(email.toLowerCase())) {
            String tokenCorreto = tokensRecuperacao.get(email.toLowerCase());
            if (tokenCorreto.equals(tokenRecebido)) {
                Usuario usuario = buscarUsuarioPorEmail(email);
                if (usuario != null) {
                    usuario.setSenha(novaSenha);
                    salvarUsuarios();
                    tokensRecuperacao.remove(email.toLowerCase());
                    System.out.println("Senha redefinida com sucesso.");
                    return true;
                }
            } else {
                System.out.println("Token inválido para este e-mail.");
                return false;
            }
        }
        System.out.println("Nenhuma solicitação de recuperação encontrada para este e-mail.");
        return false;
    }

    private String gerarTokenConfirmacao() {
        return UUID.randomUUID().toString();
    }

    private void enviarEmailConfirmacao(Usuario usuario) {
        System.out.println("\nSimulando envio de e-mail para: " + usuario.getEmail());
        System.out.println("Link de confirmação: http://localhost/confirmar?token=" + usuario.getTokenConfirmacao());
    }

    public boolean confirmarEmail(String tokenRecebido) {
        for (Usuario usuario : usuarios) {
            if (usuario.getTokenConfirmacao().equals(tokenRecebido)) {
                usuario.setEmailConfirmado(true);
                salvarUsuarios();
                System.out.println("E-mail confirmado com sucesso para o usuário: " + usuario.getNome());
                return true;
            }
        }
        System.out.println("Token inválido. E-mail não confirmado.");
        return false;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    public void listarUsuarios() {
        System.out.println("\nLista de Usuários:");
        for (Usuario u : usuarios) {
            String status = u.isEmailConfirmado() ? "Confirmado" : "Não Confirmado";
            System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome() + " | Email: " + u.getEmail() + " | Status: " + status);
        }
    }

    private void salvarUsuarios() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_CSV))) {
            for (Usuario usuario : usuarios) {
                writer.println(usuario.getId() + "," +
                        usuario.getNome() + "," +
                        usuario.getEmail() + "," +
                        usuario.getTelefone() + "," +
                        usuario.getSenha() + "," +
                        usuario.isEmailConfirmado() + "," +
                        usuario.getTokenConfirmacao());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    private void carregarUsuarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CSV))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 7) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String email = partes[2];
                    String telefone = partes[3];
                    String senha = partes[4];
                    boolean emailConfirmado = Boolean.parseBoolean(partes[5]);
                    String tokenConfirmacao = partes[6];

                    Usuario usuario = new Usuario(id, nome, email, telefone, senha);
                    usuario.setEmailConfirmado(emailConfirmado);
                    usuario.setTokenConfirmacao(tokenConfirmacao);

                    usuarios.add(usuario);
                    idCounter = Math.max(idCounter, id + 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de usuários não encontrado. Um novo será criado.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
}
