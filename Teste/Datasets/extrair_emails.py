import mailbox

# Caminho correto do arquivo .mbox
#mbox_path = "C:\\Users\\nando\\OneDrive\\Documentos\\Fernando\\Sentinel-Anti-Fraude\\Teste\\Datasets\\mbox\\Caixa de entrada.mbox"
mbox_path = "C:\\Users\\nando\\OneDrive\\Documentos\\Fernando\\Sentinel-Anti-Fraude\\Teste\\Datasets\\mbox\\Importante.mbox"

# Abre o arquivo .mbox corretamente
mbox = mailbox.mbox(mbox_path)

# Percorre os e-mails e salva cada um como um arquivo .txt
for idx, message in enumerate(mbox):
    with open(f"C:\\Users\\nando\\OneDrive\\Documentos\\Fernando\\Sentinel-Anti-Fraude\\Teste\\Datasets\\emails/email_{idx + 995}.txt", "w", encoding="utf-8") as f:
        f.write(message.as_string())