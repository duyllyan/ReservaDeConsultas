# Back End I

## Exercício para mesas de trabalho

## Clínica Odontológica

### Objetivo

O objetivo desta atividade é que você possa integrar os conhecimentos adquiridos até o
momento. Sugerimos que o realize individualmente, mas lembrando que poderá consultar
os seus colegas nas mesas de trabalho, e então, poderão tirar as dúvidas com o professor.<br>
Considere:

* Exercício individual
* Nível de complexidade: Intermediário 🔥🔥

### Desafio

Uma clínica odontológica precisa informatizar suas operações. Por isso, nos
pedem um sistema para gerenciamento de seus pacientes, onde possam
cadastrá-los, buscá-los, excluí-los e modificá-los. Ao cadastrar um paciente, os
seguintes dados serão solicitados:

* Nome 
* Sobrenome 
* RG 
* Data de cadastro 
* Endereço 
* Rua 
* Número 
* Cidade 
* Estado

Além disso, adicionaremos um ID com auto-incremento para pacientes e
endereços.<br>
Utilize o H2 como banco de dados, aplicando o padrão DAO e testes com o JUnit.
Lembre que a modelagem dos dados deve ter no mínimo 2 classes, **Paciente** e
**Endereço**.

#### Testes unitários

Antes de iniciar os testes, salve no banco de dados 2 ou mais pacientes com
seus respectivos endereços.

Utilizando a camada de serviço:

1. Adicione um paciente com endereço e pesquise no banco de dados pelo
   ID;
2. Exclua o paciente criado anteriormente e procure-o;
3. Pesquise e imprima no console todos os pacientes com seus endereços,
   serão listados todos os pacientes que incluímos antes de realizar o
   primeiro teste.