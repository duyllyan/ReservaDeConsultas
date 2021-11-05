## Primeira avaliação de Back End

A partir dos critérios de avaliação que deixamos no Playground, lembre-se:
* A duração da avaliação é de 110 minutos. Você receberá uma notificação
do professor 10 minutos antes do término do tempo estabelecido;
* A avaliação é individual;
* O código em Java em sua IDE deverá ser exportado para .ZIP, o qual
deverá ser submetido ao formulário que iremos fornecer. O nome do
arquivo .ZIP deverá ter o seguinte formato:
**NOME-SOBRENOME-TURMA.ZIP**

### Enunciado:

A clínica odontológica está solicitando para desenvolvermos a funcionalidade de
gerenciarmos os dentistas. Em outras palavras, o sistema deverá permitir que os
dentistas sejam cadastrados e listados.<br>
Após o levantamento realizado, obtivemos as seguintes informações que um
dentista deve ter:

* Número de matrícula;
* Nome;
* Sobrenome.

Nosso líder de arquitetura nos orienta para:

* Usar o H2 como banco de dados aplicando o padrão DAO para acessar os
dados e executar os métodos necessários para que nos permita salvar um
dentista e listar todos os dentistas;
* Registrar a execução de cada operação utilizando Log4j;
* Disponibilizar um script para criar as tabelas com os seus respectivos
atributos;
* Realizar o teste utilizando o JUnit. No teste devemos listar todos os
dentistas. Lembre-se de salvar alguns dentistas antes de executá-lo.