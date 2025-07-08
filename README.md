# zenith_API
# 🛠 Backend – App de Serviços Residenciais

Este é o backend do MVP para um aplicativo de serviços residenciais, onde clientes podem buscar prestadores de serviço (como encanadores, eletricistas, etc.) e solicitar atendimentos diretamente pelo app. O sistema garante segurança com autenticação via JWT, verificação de documentos e controle de atendimentos.

---

## 🔧 Tecnologias Utilizadas

- Java 24
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- PostgreSQL
- Lombok
- Docker & Docker Compose

---

## 📂 Estrutura do Projeto

- `model/` – Entidades JPA (User, Documento, Servico, Atendimento)
- `controller/` – Controllers REST
- `service/` – Regras de negócio
- `repository/` – Repositórios JPA
- `security/` – Autenticação JWT
- `resources/` – Configurações (application.properties)

---

## 🔐 Funcionalidades Principais

- Cadastro e login com autenticação JWT
- Upload e verificação de documentos
- Cadastro e listagem de prestadores por categoria
- Solicitação e gerenciamento de atendimentos
- Perfil do usuário

---

## 🚀 Como rodar localmente

```bash
# Clonar o projeto
git clone https://github.com/seu-usuario/backend-service-app

# Construir com Maven (ou usar sua IDE)
./mvnw clean install

# Rodar com Docker
docker-compose up --build
