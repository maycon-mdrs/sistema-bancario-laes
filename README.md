# Sistema Bancário

Sistema bancário simples desenvolvido como projeto da disciplina **Lógica Aplicada à Engenharia de Software**, com **arquitetura em camadas** e API REST em Spring Boot.

O comportamento das regras de negócio é especificado formalmente com **Java Modeling Language (JML)** via [OpenJML](https://www.openjml.org), aplicada nas camadas de `models/`, `services/` e `services/exceptions/`.

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

O sistema expõe apenas uma API REST (sem interface gráfica). Os DTOs definem o contrato JSON de entrada e saída — não constituem uma camada de apresentação no sentido do MVC clássico.

| Camada | Pacote | Responsabilidade |
|--------|--------|------------------|
| **Apresentação** | `controller/` | Recebe requisições HTTP, valida entrada (`@Valid`), delega ao Service e retorna respostas |
| **Contrato da API** | `dto/` | Estruturas de requisição e resposta serializadas em JSON |
| **Serviço** | `services/` | Orquestra operações, validações de negócio e persistência em memória (`HashMap`) |
| **Domínio** | `models/` | Entidade `Conta` com regras de crédito, débito e invariantes |
| **Exceções** | `services/exceptions/` | Falhas de negócio mapeadas para respostas HTTP pelo `SistemaBancarioExceptionHandler` |

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

4. A API fica disponível em `http://localhost:8080`. Use os endpoints abaixo (Postman, curl, etc.) — por exemplo: `GET http://localhost:8080/api/conta/12345/saldo`.

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

## Verificação JML (OpenJML)

Com o [OpenJML](https://www.openjml.org) instalado, é possível validar as especificações:

```bash
openjml -check src/main/java/ufrn/imd/sistema_bancario/models/Conta.java
openjml --esc src/main/java/ufrn/imd/sistema_bancario/models/Conta.java
```

Mais detalhes sobre contratos e limitações da verificação estão em `RELATORIO_IMPLEMENTACAO.md`.

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
        <td align="center" width="80">
            <a href="https://github.com/Erigeo">
                <img src="https://avatars.githubusercontent.com/u/79608648?v=4" width="50" style="border-radius: 50%;"/>
            </a>
        </td>
        <td>
            <strong>Georg Herison</strong><br/>
            <a href="https://github.com/Erigeo">Erigeo</a>
        </td>
    </tr>
    <tr>
        <td align="center" width="80">
            <a href="https://github.com/Gaplima">
                <img src="https://avatars.githubusercontent.com/u/53875638?v=4" width="50" style="border-radius: 50%;"/>
            </a>
        </td>
        <td>
            <strong>Gabriel Alves Pinheiro Lima</strong><br/>
            <a href="https://github.com/Gaplima">Gaplima</a>
        </td>
    </tr>
    <tr>
        <td align="center" width="80">
            <a href="https://github.com/MarcosBB">
                <img src="https://avatars.githubusercontent.com/u/50207805?v=4" width="50" style="border-radius: 50%;"/>
            </a>
        </td>
        <td>
            <strong>Marcos Beraldo Barros</strong><br/>
            <a href="https://github.com/MarcosBB">MarcosBB</a>
        </td>
    </tr>
    <tr>
        <td align="center" width="80">
            <a href="https://github.com/maycon-mdrs">
                <img src="https://avatars.githubusercontent.com/u/81583731?v=4" width="50" style="border-radius: 50%;"/>
            </a>
        </td>
        <td>
            <strong>Maycon Douglas Rêgo Santos</strong><br/>
            <a href="https://github.com/maycon-mdrs">maycon-mdrs</a>
        </td>
    </tr>
    <tr>
        <td align="center" width="80">
            <a href="https://github.com/Vanessa-Maria2">
                <img src="https://avatars.githubusercontent.com/u/81782508?v=4" width="50" style="border-radius: 50%;"/>
            </a>
        </td>
        <td>
            <strong>Vanessa Maria</strong><br/>
            <a href="https://github.com/Vanessa-Maria2">Vanessa-Maria2</a>
        </td>
    </tr>
</table>

## Stack

- Spring Boot 3.4.5
- Java 21
- Maven
- Lombok
- Jakarta Validation
- OpenJML (verificação formal)
