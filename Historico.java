import java.util.ArrayList;
import java.util.List;

class Mensagem {
    private String conteudo;

    public Mensagem(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }
}

// Classe que gerencia o histórico de mensagens
class HistoricoMensagens {
    private List<Mensagem> mensagens = new ArrayList<>();

    public void adicionarMensagem(String conteudo) {
        mensagens.add(new Mensagem(conteudo));
        System.out.println("Mensagem adicionada ao histórico.");
    }

    public String obterHistorico() {
        if (mensagens.isEmpty()) {
            return "O histórico está vazio.";
        }
        StringBuilder historico = new StringBuilder("📜 Histórico de Mensagens:\n");
        for (Mensagem msg : mensagens) {
            historico.append("- ").append(msg.getConteudo()).append("\n");
        }
        return historico.toString();
    }

    public void limparHistorico() {
        mensagens.clear();
        System.out.println("O histórico foi apagado.");
    }
}