# Sistema de Gerenciamento de Pedidos (OMS)

Projeto acadêmico para a disciplina **Serviços em Nuvem**.  
CRUD completo de Pedidos executado inteiramente na AWS com API Gateway, EC2 (Docker), RDS PostgreSQL e Lambda.

---

## Grupo

| RA | Nome | Responsabilidade |
|----|------|-----------------|
| 10439165 | Guilherme Shinohara | Backend Spring Boot + testes locais com PostgreSQL |
| 10438845 | Kauã de Castro Alencar | Frontend + Dockerfile Nginx + deploy EC2 (frontend) |
| 10428496 | Alan Ribeiro do Carmo | Dockerfile do backend + deploy na EC2 (backend) |
| 10436944 | Ricardo Kawamuro | VPC, sub-redes, security groups, Amazon RDS, API Gateway e AWS Lambda |
| 10427235 | Kauan Sarzi da Rocha | VPC, sub-redes, security groups, Amazon RDS, API Gateway e AWS Lambda |

---

## Visão geral

O domínio escolhido foi **gerenciamento de pedidos** — um cenário central em qualquer negócio 
que envolva vendas, logística ou controle de estoque. A entidade principal é o **Pedido (Order)**, 
que representa uma transação entre um cliente e um produto.

O sistema expõe um CRUD completo com as seguintes operações:
- **Criar** um pedido com cliente, produto, quantidade, status e valor total
- **Listar** todos os pedidos cadastrados
- **Atualizar** os dados de um pedido existente
- **Remover** um pedido do sistema

Além do CRUD, o sistema conta com um endpoint `/report` atendido por uma função **AWS Lambda** 
que calcula estatísticas em tempo real — total de pedidos, contagem por status e ticket médio — 
consumindo a própria API via HTTP, sem acesso direto ao banco de dados.

---

## Arquitetura

```
Navegador
  └── Frontend (EC2 + Docker + Nginx) :80
          └── todas as chamadas → API Gateway
                  ├── /orders*  → Backend Spring Boot (EC2 + Docker) :8080  → RDS PostgreSQL (subnet privada)
                  └── /report   → Lambda Node.js 22 (consome GET /orders via HTTP, não acessa o RDS)
```

<img width="1443" height="444" alt="Diagrama de Arquitetura OMS" src="https://github.com/user-attachments/assets/4924771e-f823-4c9d-99bc-a2faba110353" />

[Link do Miro](https://miro.com/app/board/uXjVJvNcYeA=/?moveToWidget=3458764673469293767)

---

## Serviços AWS Utilizados

| Camada | Serviço AWS | Descrição |
|--------|-------------|-----------|
| Back-end | EC2 + Docker | API REST Spring Boot conectada ao RDS |
| Banco de dados | Amazon RDS (PostgreSQL 16) | Instância em subnet privada, porta não exposta à internet |
| Gateway | Amazon API Gateway (HTTP API) | Roteia /orders → backend e /report → Lambda |
| Serverless | AWS Lambda (Node.js 22) | Gera estatísticas consumindo a API via HTTP |
| Front-end | EC2 + Docker + Nginx | Interface web que consome todas as rotas via API Gateway |

---

## Pré-requisitos (execução local)

| Ferramenta | Versão |
|------------|--------|
| Docker + Docker Compose | v2+ |
| Java (build Maven local) | 21 |
| Maven | 3.9+ |
| AWS CLI | v2 |

---

## Executando Localmente com Docker Compose

```bash
# 1. Compilar o JAR do Spring Boot
cd backend
mvn clean package -DskipTests
cd ..

# 2. Subir PostgreSQL + Backend
docker compose up --build

# 3. Abrir o frontend
#    Abra frontend/src/index.html no navegador
#    (ou sirva com: npx serve frontend/src)

# 4. Testar a API
curl http://localhost:8080/orders
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"customerName":"Alice","product":"Widget","quantity":2,"status":"PENDING","total":49.90}'
```

> O banco de dados é inicializado automaticamente a partir de `infra/schema.sql` na primeira execução.

---

## Endpoints da API

| Método | Caminho | Descrição |
|--------|---------|-----------|
| GET    | `/orders`      | Lista todos os pedidos |
| GET    | `/orders/{id}` | Busca pedido por ID |
| POST   | `/orders`      | Cria novo pedido |
| PUT    | `/orders/{id}` | Atualiza pedido (parcial) |
| DELETE | `/orders/{id}` | Remove pedido |
| GET    | `/report`      | Lambda — estatísticas dos pedidos |

### Payload do pedido
```json
{
  "customerName": "Alice",
  "product": "Widget",
  "quantity": 2,
  "status": "PENDING",
  "total": 49.90
}
```

Valores de status: `PENDING` | `PROCESSING` | `DONE` | `CANCELLED`

---

## Variáveis de Ambiente

### Backend (Spring Boot)
| Variável | Exemplo | Descrição |
|----------|---------|-----------|
| `DB_HOST` | `db` / endpoint RDS | Host do PostgreSQL |
| `DB_PORT` | `5432` | Porta do PostgreSQL |
| `DB_NAME` | `omsdb` | Nome do banco de dados |
| `DB_USER` | `omsuser` | Usuário do banco |
| `DB_PASSWORD` | `omspass123` | Senha do banco |

### Lambda
| Variável | Exemplo | Descrição |
|----------|---------|-----------|
| `API_URL` | `https://xxxx.execute-api.us-east-2.amazonaws.com` | URL base do API Gateway |

### Frontend
Edite a constante em [frontend/src/index.html](frontend/src/index.html):
```js
const API_BASE_URL = 'https://SUA_URL_DO_API_GATEWAY';
```

---

## Deploy na AWS (passo a passo)

1. **VPC** — Criar VPC com subnet pública (EC2) + subnet privada (RDS)
2. **Security Groups** — `sg-frontend` (porta 80), `sg-backend` (porta 8080), `sg-rds` (porta 5432 apenas do sg-backend)
3. **RDS** — PostgreSQL 16 na subnet privada; executar `infra/schema.sql` para inicializar
4. **EC2 Backend** — Instância t3.micro, instalar Docker, build da imagem, executar container com variáveis do RDS
5. **EC2 Frontend** — Instância t3.micro, instalar Docker, build da imagem, executar container
6. **Lambda** — Criar função (Node.js 22), fazer upload do `lambda/handler.js`, definir variável `API_URL`
7. **API Gateway** — HTTP API:
   - `ANY /orders` → Backend EC2
   - `ANY /orders/{proxy+}` → Backend EC2
   - `GET /report` → Função Lambda

---

## Estrutura do Projeto

```
.
├── backend/
│   ├── src/main/java/com/oms/
│   │   ├── OmsApplication.java
│   │   ├── controller/OrderController.java
│   │   ├── entity/Order.java
│   │   ├── repository/OrderRepository.java
│   │   └── service/OrderService.java
│   ├── src/main/resources/application.properties
│   ├── pom.xml
│   └── Dockerfile
├── frontend/
│   ├── src/index.html
│   ├── package.json
│   └── Dockerfile
├── lambda/
│   └── handler.js
├── infra/
│   └── schema.sql
├── docs/
│   └── architecture.png
├── docker-compose.yml
└── README.md
```
