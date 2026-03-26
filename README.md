# TreinoDex Backend API 🏋️‍♂️

O **TreinoDex** é uma API RESTful desenvolvida em Spring Boot voltada para o gerenciamento de treinos. A plataforma permite que Personal Trainers se cadastrem, gerenciem seus alunos, montem rotinas de treinos e registrem os exercícios detalhados para cada sessão.

## 🚀 Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias e bibliotecas:

* **Java 21**
* **Spring Boot** (Web, Data JPA, Security, Validation)
* **PostgreSQL** (Banco de Dados Relacional)
* **Spring Security & JWT (Auth0)** (Autenticação e Autorização)
* **Flyway** (Migração de Banco de Dados)
* **Lombok** (Redução de Boilerplate)
* **Maven** (Gerenciamento de Dependências)

## ⚙️ Arquitetura e Funcionalidades

A API foi projetada para atender aos seguintes casos de uso:

* **Autenticação:** Cadastro de novos usuários (Personal Trainers) e Login com geração de Token JWT.
* **Alunos:** Operações de CRUD (Criação, Leitura, Atualização e Exclusão) de alunos. Cada aluno é vinculado estritamente ao Personal Trainer que o cadastrou.
* **Treinos (Workouts):** Criação e listagem de fichas de treinos vinculadas a um aluno específico.
* **Exercícios:** Registro de exercícios (nome, séries, repetições, descanso e peso) associados a um treino.

## 🛠️ Como executar o projeto localmente

### Pré-requisitos
* Java 21 instalado
* Maven instalado
* PostgreSQL rodando localmente (ou via Docker)

### Passo a Passo

1. **Clone o repositório**
   ```bash
   git clone [https://github.com/seu-usuario/treinodex.git](https://github.com/seu-usuario/treinodex.git)
   cd treinodex


2. **Configure o Banco de Dados**
Crie um banco de dados no PostgreSQL chamado treinodex_db. As configurações padrão no application.yml são:

   URL: jdbc:postgresql://localhost:5432/treinodex_db

   Utilizador: postgres

   Palavra-passe: postgres

   Obs: Pode alterar estas configurações passando as variáveis de ambiente DB_URL, DB_USER e DB_PASSWORD.

   Variáveis de Ambiente (Opcional)
   O projeto utiliza a seguinte variável para a assinatura do token JWT:

   JWT_SECRET (Valor padrão: chave_secreta_local_treinodex_123456789)

   Execute a aplicação
   Na raiz do projeto, execute o comando Maven:

   ```Bash
   ./mvnw spring-boot:run
   A API estará disponível em http://localhost:8080.`
   ```

   📚 Principais Endpoints da API
   Para aceder à maioria dos endpoints, é necessário enviar o Token JWT no header da requisição:
   Authorization: Bearer <seu_token>

   🔐 Autenticação (/auth)
   POST /auth/register - Regista um novo utilizador (Personal Trainer).

   POST /auth/login - Autentica o utilizador e retorna o Token JWT.

   🧑‍🎓 Alunos (/students)
   POST /students - Regista um novo aluno.

   GET /students - Lista todos os alunos vinculados ao Personal autenticado.

   PUT /students/{id} - Atualiza as informações de um aluno.

   DELETE /students/{id} - Remove um aluno.

   🏋️ Treinos (/workouts)
   POST /workouts/student/{studentId} - Cria uma nova ficha de treino para um aluno.

   GET /workouts/student/{studentId} - Lista todos os treinos de um aluno.

   🏃 Exercícios (/exercises)
   POST /exercises/workout/{workoutId} - Adiciona um exercício a um treino existente.

   GET /exercises/workout/{workoutId} - Lista os exercícios de um determinado treino.

   🗄️ Estrutura do Banco de Dados
   O banco de dados gere as seguintes entidades principais:

   User (Utilizadores): Representa o administrador ou Personal Trainer.

   Student (Alunos): Vinculados a um Personal Trainer (User).

   Workout (Treinos): Vinculados a um Aluno.

   Exercise (Exercícios): Vinculados a um Treino.

   Desenvolvido por Gabriel Panucci.
