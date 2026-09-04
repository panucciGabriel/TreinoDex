# 🏋️‍♂️ TreinoDex — Backend API

API RESTful desenvolvida em **Spring Boot** para o gerenciamento de treinos. A plataforma permite que Personal Trainers se cadastrem, gerenciem seus alunos, montem rotinas de treino e registrem exercícios detalhados para cada sessão.

> **Status:** projeto pessoal em desenvolvimento ativo. Construído para estudo aprofundado de Spring Boot, autenticação com JWT e modelagem de dados relacional.

---

## 🚀 Tecnologias

| Camada | Tecnologias |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 4 (Web MVC, Data JPA, Security, Validation) |
| Banco de dados | PostgreSQL |
| Autenticação | Spring Security + JWT (`com.auth0:java-jwt`) |
| Migrações | Flyway |
| Documentação | springdoc-openapi (Swagger UI) |
| Testes | JUnit + Mockito |
| Build | Maven |
| Utilitários | Lombok |

---

## 🧱 Arquitetura

O código é organizado por **funcionalidade**, e não por tipo técnico — cada recurso concentra seu controller e seus DTOs no mesmo pacote:

```
com.treinodex.backend
├── api/                 # Camada de entrada: controllers e DTOs de request/response
│   ├── auth/            # Registro e login
│   ├── student/         # Alunos
│   ├── workout/         # Treinos
│   └── exercise/        # Exercícios
├── core/security/       # Configuração do Spring Security, filtro e serviço de JWT
├── domain/              # Entidades de domínio e repositórios
└── infra/               # Configurações de infraestrutura
```

**Decisões de projeto:**

- **DTOs como `record`** — objetos de transferência imutáveis, sem boilerplate, separando o contrato da API das entidades de domínio.
- **Identificadores em `UUID`** — evita expor a sequência de crescimento da base e facilita geração distribuída de IDs.
- **Proteção contra IDOR** — todo acesso a aluno, treino ou exercício é validado contra o usuário do token. Conhecer o `UUID` de um recurso não basta: se ele não pertence a quem está autenticado, a requisição é recusada.
- **Tratamento global de erros** — as exceções são centralizadas em `infra/exception`, devolvendo resposta padronizada e status HTTP adequado em vez de stack trace.
- **Schema versionado com Flyway** — a estrutura do banco é código, versionada junto com a aplicação.
- **Listagens paginadas** — os endpoints de listagem de alunos e exercícios retornam páginas, não coleções inteiras.

---

## 📖 Documentação interativa

Com a aplicação em execução, a documentação gerada pelo springdoc fica disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## ✅ Testes

Testes unitários do `StudentController` com **JUnit** e **Mockito**:

```bash
./mvnw test
```

---

## 🗄️ Modelo de dados

```
User (Personal Trainer)
  └── Student (Aluno)          1:N — cada aluno pertence a um User
        └── Workout (Treino)   1:N — cada treino pertence a um Student
              └── Exercise     1:N — cada exercício pertence a um Workout
```

| Entidade | Descrição |
|---|---|
| `User` | Personal Trainer autenticado no sistema |
| `Student` | Aluno vinculado ao Personal Trainer que o cadastrou |
| `Workout` | Ficha de treino montada para um aluno |
| `Exercise` | Exercício de um treino, com séries, repetições, carga e descanso |

---

## 🛠️ Executando localmente

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL em execução (local ou via Docker)

### 1. Clone o repositório

```bash
git clone https://github.com/panucciGabriel/TreinoDex.git
cd TreinoDex
```

### 2. Suba o banco de dados

Crie um banco chamado `treinodex_db`. Se preferir usar Docker:

```bash
docker run --name treinodex-db \
  -e POSTGRES_DB=treinodex_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 -d postgres:16
```

As migrações do Flyway são aplicadas automaticamente na inicialização da aplicação.

### 3. Configure as variáveis de ambiente

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/treinodex_db` | URL de conexão |
| `DB_USER` | `postgres` | Usuário do banco |
| `DB_PASSWORD` | `postgres` | Senha do banco |
| `JWT_SECRET` | — | Chave usada para assinar os tokens |

```bash
export JWT_SECRET="defina-uma-chave-secreta-local"
```

### 4. Execute

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

---

## 📚 Endpoints

Todos os endpoints, exceto os de autenticação, exigem o cabeçalho:

```
Authorization: Bearer <token>
```

### 🔐 Autenticação

<details open>
<summary><code>POST /auth/register</code> — registra um novo Personal Trainer</summary>

```json
{
  "name": "Gabriel Panucci",
  "email": "gabriel@exemplo.com",
  "password": "senha-segura",
  "role": "USER"
}
```

Valores aceitos em `role`: `ADMIN`, `USER`, `STUDENT`.

**200 OK** → `User registered successfully!`
**400 Bad Request** → `Email already exists!`
</details>

<details open>
<summary><code>POST /auth/login</code> — autentica e devolve o token</summary>

```json
{
  "email": "gabriel@exemplo.com",
  "password": "senha-segura"
}
```

**200 OK**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**401 Unauthorized** → `Invalid credentials!`
</details>

### 🧑‍🎓 Alunos

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/students` | Cadastra um aluno |
| `GET` | `/students` | Lista paginada dos alunos do usuário autenticado |
| `PUT` | `/students/{id}` | Atualiza um aluno |
| `DELETE` | `/students/{id}` | Remove um aluno |

<details>
<summary>Exemplo de requisição e resposta</summary>

`POST /students`

```json
{
  "name": "Maria Souza",
  "email": "maria@exemplo.com",
  "phone": "18999999999"
}
```

**200 OK**

```json
{
  "id": "3f2a1c9e-8b4d-4f27-9a11-5c8e2d7b6a03",
  "name": "Maria Souza",
  "email": "maria@exemplo.com",
  "phone": "18999999999"
}
```
</details>

### 🏋️ Treinos

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/workouts/student/{studentId}` | Cria uma ficha de treino para o aluno |
| `GET` | `/workouts/student/{studentId}` | Lista os treinos do aluno |

<details>
<summary>Exemplo de requisição</summary>

`POST /workouts/student/{studentId}`

```json
{
  "name": "Treino A — Peito e Tríceps",
  "description": "Foco em hipertrofia, 3x por semana",
  "active": true
}
```
</details>

### 🏃 Exercícios

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/exercises/workout/{workoutId}` | Adiciona um exercício ao treino |
| `GET` | `/exercises/workout/{workoutId}` | Lista paginada dos exercícios do treino |

<details>
<summary>Exemplo de requisição</summary>

`POST /exercises/workout/{workoutId}`

```json
{
  "name": "Supino reto com barra",
  "sets": 4,
  "reps": "8-10",
  "rest": "90s",
  "weight": "60kg"
}
```

`name`, `sets` e `reps` são obrigatórios. `rest` e `weight` são opcionais.
</details>

---

## 🗺️ Próximos passos

- [x] Tratamento centralizado de exceções
- [x] Documentação interativa com Swagger / OpenAPI
- [x] Paginação nas listagens
- [x] Testes unitários do `StudentController`
- [ ] Validação de entrada (`@Valid` + Bean Validation) em todos os endpoints
- [ ] Atualização e remoção de treinos e exercícios
- [ ] Ampliar a cobertura de testes para os demais controllers
- [ ] Containerização da aplicação com Docker

---

## 👨‍💻 Autor

**Gabriel Augusto Panucci**

[GitHub](https://github.com/panucciGabriel) · [LinkedIn](https://www.linkedin.com/in/gabriel-panucci)
