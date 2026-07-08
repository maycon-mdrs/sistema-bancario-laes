# Sistema Bancário

Sistema bancário simples desenvolvido como projeto da disciplina **Lógica Aplicada à Engenharia de Software**, com arquitetura **Model–View–Controller (MVC)** e API REST em Spring Boot.

O objetivo do trabalho inclui a especificação formal do comportamento com **Java Modeling Language (JML)** via [OpenJML](https://www.openjml.org) — etapa prevista para as classes de `models/` e `services/`.

## Funcionalidades

- Criar conta
- Buscar conta
- Consultar saldo
- Creditar valor
- Debitar valor
- Realizar transferência

## Regras de negócio

- Existe apenas um tipo de conta (`Conta`)
- O saldo não pode ficar negativo
- Valores de operação devem ser maiores que zero
- Não é permitido cadastrar duas contas com o mesmo número
- Os dados são armazenados em memória (sem banco de dados)

---

## Arquitetura

| Camada | Pacote | Responsabilidade |
|--------|--------|------------------|
| **Model** | `models/` | Entidade `Conta` com regras de crédito e débito |
| **View** | `dto/` | DTOs de requisição e resposta da API REST |
| **Controller** | `controller/` | Recebe requisições HTTP, delega ao Service e retorna respostas |
| **Service** | `services/` | Orquestra operações, validações e persistência em memória |

---

## Executando a aplicação

### Requisitos

- Java JDK 21
- Maven instalado (ou usar o wrapper Maven fornecido)

### Como rodar

1. Clone o repositório:

```bash
git clone https://github.com/maycon-mdrs/sistema-bancario-laes.git
cd sistema-bancario-laes
```

2. Compile e execute os testes:

```bash
mvnw.cmd clean test        # Windows
./mvnw clean test          # Linux/Mac
```

3. Inicie a aplicação:

```bash
mvnw.cmd spring-boot:run     # Windows
./mvnw spring-boot:run       # Linux/Mac
```

4. Acesse em `http://localhost:8080`

---

## Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/conta/cadastrar` | Criar conta |
| `GET` | `/api/conta/{numeroConta}` | Buscar conta |
| `GET` | `/api/conta/{numeroConta}/saldo` | Consultar saldo |
| `POST` | `/api/conta/{numeroConta}/creditar` | Creditar valor |
| `POST` | `/api/conta/{numeroConta}/debitar` | Debitar valor |
| `POST` | `/api/conta/{origem}/transferir/{destino}` | Transferir valor |

### Exemplos com curl

**Criar conta**

```bash
curl -X POST http://localhost:8080/api/conta/cadastrar \
  -H "Content-Type: application/json" \
  -d '{"numeroConta": "12345", "saldoInicial": 100.0}'
```

**Consultar saldo**

```bash
curl http://localhost:8080/api/conta/12345/saldo
```

**Creditar**

```bash
curl -X POST http://localhost:8080/api/conta/12345/creditar \
  -H "Content-Type: application/json" \
  -d '{"valor": 100.0}'
```

**Debitar**

```bash
curl -X POST http://localhost:8080/api/conta/12345/debitar \
  -H "Content-Type: application/json" \
  -d '{"valor": 50.0}'
```

**Transferir**

```bash
curl -X POST http://localhost:8080/api/conta/12345/transferir/67890 \
  -H "Content-Type: application/json" \
  -d '{"valor": 25.0}'
```

### Tratamento de erros

| Situação | HTTP |
|----------|------|
| Conta não encontrada | 404 |
| Conta já cadastrada | 409 |
| Valor inválido (≤ 0) | 400 |
| Saldo insuficiente | 400 |
| Campos inválidos na requisição | 400 |

---

## Docker

```bash
mvnw.cmd clean package -DskipTests
docker build -t sistema-bancario .
docker run -p 8080:8080 sistema-bancario
```

---

## Integrantes da Equipe

<table>
    <tr>
        
    </tr>
    <tr>
        
    </tr>
    <tr>
        
    </tr>
</table>

## Stack

- Spring Boot 3.4.5
- Java 21
- Maven
- Lombok
- Jakarta Validation
