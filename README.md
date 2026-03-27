---

# 🏋️‍♂️ TreinoDex Backend API

O **TreinoDex** é uma API RESTful desenvolvida em **Spring Boot** voltada para o gerenciamento de treinos. A plataforma permite que **Personal Trainers** se cadastrem, gerenciem seus alunos, montem rotinas de treino e registrem exercícios detalhados para cada sessão.

---

## 🚀 Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias e bibliotecas:

* **Java 21**
* **Spring Boot**

  * Web
  * Data JPA
  * Security
  * Validation
* **PostgreSQL** (Banco de Dados Relacional)
* **Spring Security + JWT (Auth0)** (Autenticação e Autorização)
* **Flyway** (Migração de Banco de Dados)
* **Lombok** (Redução de boilerplate)
* **Maven** (Gerenciamento de dependências)

---

## ⚙️ Arquitetura e Funcionalidades

A API foi projetada para atender aos seguintes casos de uso:

### 🔐 Autenticação

* Cadastro de novos usuários (Personal Trainers)
* Login com geração de Token JWT

### 🧑‍🎓 Alunos

* CRUD completo de alunos:

  * Criar
  * Listar
  * Atualizar
  * Excluir
* Cada aluno é vinculado ao Personal Trainer que o cadastrou

### 🏋️ Treinos (Workouts)

* Criação de fichas de treino
* Listagem de treinos por aluno

### 🏃 Exercícios

* Registro de exercícios contendo:

  * Nome
  * Séries
  * Repetições
  * Tempo de descanso
  * Peso
* Associação com treinos específicos

---

## 🛠️ Como Executar o Projeto Localmente

### ✅ Pré-requisitos

* Java 21 instalado
* Maven instalado
* PostgreSQL rodando localmente (ou via Docker)

---

### 📥 Passo a Passo

#### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/treinodex.git
cd treinodex
```

---

#### 2. Configure o Banco de Dados

Crie um banco no PostgreSQL com o nome:

```
treinodex_db
```

**Configurações padrão (`application.yml`):**

* URL: `jdbc:postgresql://localhost:5432/treinodex_db`
* Usuário: `postgres`
* Senha: `postgres`

💡 Você pode alterar usando variáveis de ambiente:

* `DB_URL`
* `DB_USER`
* `DB_PASSWORD`

---

### 🔑 Variáveis de Ambiente (Opcional)

Para assinatura do token JWT:

```
JWT_SECRET=chave_secreta_local_treinodex_123456789
```

---

### ▶️ Executando a aplicação

Na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080
```

---

## 📚 Principais Endpoints da API

⚠️ A maioria dos endpoints requer autenticação via JWT:

```
Authorization: Bearer <seu_token>
```

---

### 🔐 Autenticação (`/auth`)

| Método | Endpoint         | Descrição                 |
| ------ | ---------------- | ------------------------- |
| POST   | `/auth/register` | Registra um novo usuário  |
| POST   | `/auth/login`    | Autentica e retorna o JWT |

---

### 🧑‍🎓 Alunos (`/students`)

| Método | Endpoint         | Descrição                           |
| ------ | ---------------- | ----------------------------------- |
| POST   | `/students`      | Cria um novo aluno                  |
| GET    | `/students`      | Lista alunos do usuário autenticado |
| PUT    | `/students/{id}` | Atualiza um aluno                   |
| DELETE | `/students/{id}` | Remove um aluno                     |

---

### 🏋️ Treinos (`/workouts`)

| Método | Endpoint                        | Descrição                 |
| ------ | ------------------------------- | ------------------------- |
| POST   | `/workouts/student/{studentId}` | Cria treino para um aluno |
| GET    | `/workouts/student/{studentId}` | Lista treinos do aluno    |

---

### 🏃 Exercícios (`/exercises`)

| Método | Endpoint                         | Descrição                    |
| ------ | -------------------------------- | ---------------------------- |
| POST   | `/exercises/workout/{workoutId}` | Adiciona exercício ao treino |
| GET    | `/exercises/workout/{workoutId}` | Lista exercícios do treino   |

---

## 🗄️ Estrutura do Banco de Dados

O sistema é composto pelas seguintes entidades principais:

* **User (Usuários)**
  Representa o Personal Trainer

* **Student (Alunos)**
  Vinculados a um User

* **Workout (Treinos)**
  Vinculados a um Student

* **Exercise (Exercícios)**
  Vinculados a um Workout

---

## 👨‍💻 Autor

Desenvolvido por **Gabriel Augusto Panucci**

---
