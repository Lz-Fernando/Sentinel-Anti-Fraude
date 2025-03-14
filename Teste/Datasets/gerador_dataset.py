import os
import re
import csv

from pathlib import Path
from langdetect import detect


def gera_dataset():

  # nome das colunas para setar no cvs
  csv_columns = ['conteudo', 'fraude']

  # dicionário contento as chaves e valores que farão parter do csv
  dict_emails = {'conteudo': '', 'fraude': 0}

  # Lista todos os arquivos no diretóro usando o pathlib
  basepath = Path('C:\\Users\\nando\\OneDrive\\Documentos\\Fernando\\Sentinel-Anti-Fraude\\Teste\\Datasets\\emails/')  # Diretório dos arquivos

  # Retorna a lista de arquivos
  files_in_basepath = (file for file in basepath.iterdir() if file.is_file())

  # Abre/cria o arquivo dataset em modo escrita
  data_file = open('dataset_emails.csv', 'w')

  # prepara o csv para receber os valores do dicionário
  # com o nome das colunas
  writer = csv.DictWriter(data_file, fieldnames=csv_columns)
  writer.writeheader()

  count_valido = 0
  count_descartado = 0
  # Percorre a lista de arquivos
  for item in files_in_basepath:

    # Pega o caminho e o nome de cada aquivo
    arquivo = 'C:\\Users\\nando\\OneDrive\\Documentos\\Fernando\\Sentinel-Anti-Fraude\\Teste\\Datasets\\emails/' + item.name
    print('Lendo arquivo' + arquivo)

    # Abre o arquivo atual
    f = open(arquivo)

    # Lê o arquivo atual e
    # pegando somemente o conteúdo
    # remove o cabeçalho que não será utilizado
    fr = re.sub('.*:.*', '', f.read())

    try:
      # Detecta a lingua do texto
      lang = detect(fr)

    except:
      pass

    # Se o texto estiver em português continua o processo
    if lang == "pt":
      # Você deve criar um regex para retirar os seus dados pessoais
      fr = re.sub(r'.Luiz\w*', '', fr)
      # Remove o nome "Fernando"
      fr = re.sub(r'.Fernando\w*', '', fr)
      # Remove o sobrenome "Brito"
      fr = re.sub(r'.Brito\w*', '', fr)
      # Remove o sobrenome "Ferreira"
      fr = re.sub(r'.Ferreira\w*', '', fr)

      fr = re.sub(r'\S+@\S+', '', fr)    # remove qualquer email

      # seta os valores (as mensagens) no campo conteúdo
      dict_emails['conteudo'] = fr

      # escreve no arquivo dataset.csv com os dados do dicionário
      writer.writerow(dict_emails)
      count_valido = count_valido + 1

      # Se o texto não estiver em português o texto é descartado
    else:
      count_descartado = count_descartado + 1

  # fecha o arquivo dataset.csv
  data_file.close()

  print("Emails validados: %d   \r" % (count_valido))
  print("Emails descartados: %d   \r" % (count_descartado))


gera_dataset()