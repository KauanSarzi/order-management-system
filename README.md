# Order Management System (OMS) — AWS Academy

Academic project for **AWS Academy – Cloud Developing**.  
A full REST CRUD for Orders running entirely on AWS with API Gateway, EC2 (Docker), RDS PostgreSQL, and Lambda.

---

## Architecture

```
Browser
  └── Frontend (EC2 + Docker + Nginx) :80
          └── all API calls → API Gateway
                  ├── /orders*  → Backend Spring Boot (EC2 + Docker) :8080  → RDS PostgreSQL (private subnet)
                  └── /report   → Lambda Node.js 20 (calls GET /orders via HTTP, never touches RDS)
```

---

## Prerequisites

| Tool | Version |
|------|---------|
| Docker + Docker Compose | v2+ |
| Java (for local Maven build) | 21 |
| Maven | 3.9+ |
| AWS CLI | v2 |

---

## Running Locally with Docker Compose

```bash
# 1. Build the Spring Boot JAR first
cd backend
mvn clean package -DskipTests
cd ..

# 2. Start PostgreSQL + Backend
docker compose up --build

# 3. Open the frontend
#    Open frontend/src/index.html in your browser
#    (or serve it: npx serve frontend/src)

# 4. Test the API
curl http://localhost:8080/orders
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"customerName":"Alice","product":"Widget","quantity":2,"status":"PENDING","total":49.90}'
```

> The database is initialised automatically from `infra/schema.sql` on first run.

---

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET    | `/orders`      | List all orders |
| GET    | `/orders/{id}` | Get order by ID |
| POST   | `/orders`      | Create new order |
| PUT    | `/orders/{id}` | Update order (partial) |
| DELETE | `/orders/{id}` | Delete order |
| GET    | `/report`      | Lambda — order statistics |

### Order payload
```json
{
  "customerName": "Alice",
  "product": "Widget",
  "quantity": 2,
  "status": "PENDING",
  "total": 49.90
}
```

Status values: `PENDING` | `PROCESSING` | `DONE` | `CANCELLED`

---

## Environment Variables

### Backend (Spring Boot)
| Variable | Example | Description |
|----------|---------|-------------|
| `DB_HOST` | `db` / RDS endpoint | PostgreSQL host |
| `DB_PORT` | `5432` | PostgreSQL port |
| `DB_NAME` | `omsdb` | Database name |
| `DB_USER` | `omsuser` | Database user |
| `DB_PASSWORD` | `omspass` | Database password |

### Lambda
| Variable | Example | Description |
|----------|---------|-------------|
| `API_URL` | `https://xxxx.execute-api.us-east-1.amazonaws.com/prod` | API Gateway base URL |

### Frontend
Edit the constant in [frontend/src/index.html](frontend/src/index.html):
```js
const API_BASE_URL = 'https://YOUR_API_GATEWAY_URL';
```

---

## AWS Deployment Summary

1. **VPC** — Create VPC with public subnet (EC2) + private subnet (RDS)
2. **RDS** — PostgreSQL 16 in private subnet; run `infra/schema.sql` to initialise
3. **Backend EC2** — Launch instance, install Docker, build image, run container with RDS env vars
4. **Frontend EC2** — Launch instance, install Docker, build image, run container
5. **Lambda** — Create function (Node.js 20), upload `lambda/handler.js`, set `API_URL` env var
6. **API Gateway** — HTTP API:
   - `ANY /orders/{proxy+}` → Backend EC2 (private IP or internal LB)
   - `GET /report` → Lambda function

---

## Project Structure

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
│   └── architecture.png   (placeholder)
├── docker-compose.yml
└── README.md
```
