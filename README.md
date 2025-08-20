# 🏦 Bank System API com Spring Boot

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-3.9.9-C71A36.svg)
![JPA](https://img.shields.io/badge/JPA-Hibernate-orange.svg)
![Lombok](https://img.shields.io/badge/Lombok-%E2%9C%94-green.svg)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.3.1-6DB33F.svg)
![Docker](https://img.shields.io/badge/Docker-26.1.4-2496ED.svg)
![JWT](https://img.shields.io/badge/JWT-JSON%20Web%20Token-yellow.svg)

API REST em Java com Spring Boot para simular operações bancárias entre usuários.

## 📌 Status do Projeto
✅ Finalizado!

## 🚀 Funcionalidades
- Cadastro e autenticação de usuários (JWT)
- Criação e gerenciamento de contas bancárias
- Transações: depósito, saque e transferência entre contas
- Consulta de saldo e extrato de transações
- Proteção de endpoints sensíveis com autenticação
- Documentação automática dos endpoints via Swagger
- Estrutura modular com separação em camadas (controller, service, repository, DTOs, mappers)

## 🛠️ Tecnologias Utilizadas
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL (Docker)
- Swagger

## 📦 Como rodar o projeto

### 1. Clone o repositório
```bash
git clone https://github.com/GabSkyDev/bank-system-api
cd bank-system-api
```

### 2. Suba o banco de dados PostgreSQL com Docker (Certifique-se de ter o Docker instalado)
```bash
docker-compose up -d
```
Isso criará um container PostgreSQL acessível em ```localhost:5432``` com usuário ```user```, senha ```root``` e banco bankapi.

### 3. Configure o ```application.yml```
Verifique se as configurações de acesso ao banco em ```application.yml``` estão de acordo com o serviço do Docker

### 4. Execute a aplicação
Com o banco rodando, execute o projeto com Maven:
```bash
./mvnw spring-boot:run
```
Ou, no Windows:
```bash
mvnw.cmd spring-boot:run
```

### 5. Acesse a API
A aplicação estará disponível em:
```http://localhost:8080```

A documentação Swagger pode ser acessada em:
```http://localhost:8080/swagger-ui.html```

## 👨‍💻 Desenvolvedor
- **LinkedIn:** [Gabriel Lima de Sousa](https://www.linkedin.com/in/gabriel-lima-de-sousa-31a358287/)


## ⚠️ Aviso Legal

Este projeto é de autoria de Gabriel Lima de Sousa e está licenciado sob os termos da [Licença MIT](./LICENSE).
Este é um projeto de código aberto com fins **educacionais** e **não possui garantias comerciais ou suporte oficial**.

Para mais detalhes, consulte o arquivo [LICENSE](./LICENSE).
