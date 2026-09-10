# OrderFlow API

API REST para gerenciamento de pedidos, clientes, produtos, categorias e
pagamentos, desenvolvida com Java e Spring Boot.

O projeto foi criado como projeto de portfólio e estudo de **Design
Patterns**, com foco em organização em camadas, regras de negócio,
autenticação JWT e autorização baseada em roles.

## Tecnologias

-   Java 21
-   Spring Boot 4.1.0
-   Spring Web MVC
-   Spring Data JPA
-   Spring Security
-   JWT com JJWT 0.12.6
-   MySQL
-   Maven
-   Lombok
-   Bean Validation
-   Springdoc OpenAPI / Swagger UI
-   JUnit 5
-   Mockito

## Funcionalidades

### Autenticação e autorização

-   Cadastro de usuários
-   Login com email e senha
-   Senhas protegidas com BCrypt
-   Autenticação baseada em JWT
-   Controle de acesso por roles:
    -   `USER`
    -   `ADMIN`
-   Retorno `401 Unauthorized` quando não há autenticação válida
-   Retorno `403 Forbidden` quando o usuário autenticado não possui
    permissão

### Produtos

-   Cadastro
-   Consulta por ID
-   Listagem paginada
-   Filtros por nome, categoria e faixa de preço
-   Atualização
-   Exclusão lógica utilizando o campo `ativo`
-   Controle de estoque

### Clientes

-   Cadastro
-   Consulta por ID
-   Listagem paginada
-   Filtros por nome, CPF e email
-   Atualização
-   Exclusão lógica utilizando o campo `ativo`
-   Data de cadastro definida automaticamente pela aplicação

### Categorias

-   Cadastro
-   Consulta por ID
-   Listagem
-   Atualização
-   Exclusão

### Pedidos

-   Criação de pedidos
-   Consulta por ID
-   Listagem paginada
-   Filtros por cliente, período, valor e status
-   Processamento de itens
-   Validação de estoque
-   Cálculo do valor total
-   Processamento do pagamento
-   Atualização do status do pedido
-   Baixa automática do estoque quando o pagamento é aprovado
-   Exclusão de pedidos

### Pagamentos

-   Consulta de pagamentos
-   Consulta por ID
-   Associação com pedido
-   Controle de forma e status de pagamento

## Design Patterns

O projeto utiliza diferentes padrões para organizar responsabilidades e
demonstrar conceitos de Design Patterns.

### Strategy

Utilizado para representar diferentes formas de pagamento.

``` text
EstrategiaPagamento
├── PagamentoPix
├── PagamentoCartao
└── PagamentoBoleto
```

A `PedidoFacade` não precisa conhecer os detalhes de cada forma de
pagamento.

### Factory

A `PagamentoFactory` é responsável por criar a estratégia correspondente
à forma de pagamento escolhida.

``` text
FormaPagamento
      ↓
PagamentoFactory
      ↓
EstrategiaPagamento
      ↓
Pix / Cartão / Boleto
```

### Facade

A `PedidoFacade` centraliza o processo de criação de um pedido.

Ela coordena:

-   montagem do pedido;
-   validação dos produtos;
-   verificação de estoque;
-   cálculo de subtotal;
-   cálculo do valor total;
-   criação da estratégia de pagamento;
-   processamento do pagamento;
-   baixa do estoque;
-   atualização dos status.

### Singleton

O `EstoqueManager` é um componente gerenciado pelo Spring e utilizado
como ponto central para as operações de estoque.

O Spring gerencia seus beans como singleton por padrão, permitindo que o
gerenciamento do estoque seja centralizado na aplicação.

## Arquitetura

O projeto segue uma organização baseada em camadas:

``` text
com.example.orderflowapi
│
├── controller
│   └── Endpoints REST
│
├── service
│   └── Regras de negócio
│
├── repository
│   └── Acesso ao banco de dados
│
├── model
│   └── Entidades JPA
│
├── dto
│   ├── request
│   └── response
│
├── mapper
│   └── Conversão entre DTOs e entidades
│
├── security
│   └── JWT, autenticação e autorização
│
├── facade
│   └── Orquestração do processamento de pedidos
│
├── factory
│   └── Criação das estratégias de pagamento
│
├── strategy
│   └── Estratégias de pagamento
│
├── singleton
│   └── Gerenciamento centralizado de estoque
│
├── enums
│   └── Roles e status
│
└── exception
    └── Exceções e tratamento global
```

## Principais endpoints

### Autenticação

  Método   Endpoint           Acesso
  -------- ------------------ ---------
  POST     `/auth/registro`   Público
  POST     `/auth/login`      Público

### Produtos

  Método   Endpoint          Acesso
  -------- ----------------- --------------
  GET      `/produto`        USER / ADMIN
  GET      `/produto/{id}`   USER / ADMIN
  POST     `/produto`        ADMIN
  PUT      `/produto/{id}`   ADMIN
  DELETE   `/produto/{id}`   ADMIN

### Clientes

  Método   Endpoint          Acesso
  -------- ----------------- --------------
  GET      `/cliente`        USER / ADMIN
  GET      `/cliente/{id}`   USER / ADMIN
  POST     `/cliente`        ADMIN
  PUT      `/cliente/{id}`   ADMIN
  DELETE   `/cliente/{id}`   ADMIN

### Categorias

  Método   Endpoint            Acesso
  -------- ------------------- --------------
  GET      `/categoria`        USER / ADMIN
  GET      `/categoria/{id}`   USER / ADMIN
  POST     `/categoria`        ADMIN
  PUT      `/categoria/{id}`   ADMIN
  DELETE   `/categoria/{id}`   ADMIN

### Pedidos

  Método   Endpoint         Acesso
  -------- ---------------- --------------
  GET      `/pedido`        USER / ADMIN
  GET      `/pedido/{id}`   USER / ADMIN
  POST     `/pedido`        USER / ADMIN
  DELETE   `/pedido/{id}`   ADMIN

### Pagamentos

  Método   Endpoint            Acesso
  -------- ------------------- --------------
  GET      `/pagamento`        USER / ADMIN
  GET      `/pagamento/{id}`   USER / ADMIN

## Segurança

O fluxo de autenticação funciona da seguinte forma:

``` text
POST /auth/login
       ↓
AuthService
       ↓
AuthenticationManager
       ↓
Usuário autenticado
       ↓
JwtService
       ↓
JWT
       ↓
Authorization: Bearer <token>
       ↓
JwtAuthenticationFilter
       ↓
SecurityContext
       ↓
Endpoint protegido
```

As senhas dos usuários são armazenadas utilizando
`BCryptPasswordEncoder`.

A aplicação utiliza sessões `STATELESS`, pois a autenticação das
requisições protegidas é realizada através do JWT.

## Apagamento lógico

Produtos e clientes utilizam o campo:

``` java
private Boolean ativo;
```

Novos registros começam como ativos.

Quando ocorre uma exclusão lógica:

``` text
ativo = true
     ↓
DELETE
     ↓
ativo = false
```

O registro permanece no banco, mas deixa de participar das consultas
normais.

## Banco de dados

O projeto utiliza MySQL.

Crie um banco chamado:

``` sql
CREATE DATABASE orderflowdb;
```

Configure as credenciais no `application.properties` ou,
preferencialmente, por variáveis de ambiente.

Exemplo:

``` properties
spring.datasource.url=jdbc:mysql://localhost:3306/orderflowdb
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

> Não versionar senhas do banco ou a chave secreta do JWT no
> repositório.

## Executando o projeto

### Pré-requisitos

-   Java 21
-   Maven
-   MySQL
-   IDE de sua preferência

### Clonar o projeto

``` bash
git clone <URL_DO_REPOSITORIO>
cd "OrderFlow API"
```

### Executar

``` bash
mvn spring-boot:run
```

Ou execute a classe principal:

``` text
OrderFlowApiApplication
```

A aplicação será iniciada, por padrão, em:

``` text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicação em execução, a documentação da API pode ser acessada
através do Swagger UI:

``` text
http://localhost:8080/swagger-ui/index.html
```

## Exemplo de autenticação

### Cadastro

``` http
POST /auth/registro
Content-Type: application/json
```

``` json
{
  "email": "admin@email.com",
  "senha": "123456",
  "role": "ADMIN"
}
```

### Login

``` http
POST /auth/login
Content-Type: application/json
```

``` json
{
  "email": "admin@email.com",
  "senha": "123456"
}
```

Resposta:

``` json
{
  "token": "SEU_TOKEN_JWT"
}
```

Para acessar endpoints protegidos:

``` http
Authorization: Bearer SEU_TOKEN_JWT
```

## Testes

O projeto possui testes utilizando JUnit 5 e Mockito, incluindo testes
da `PedidoFacade` para cenários como:

-   processamento de pedido com sucesso;
-   estoque insuficiente;
-   pagamento aprovado;
-   pagamento recusado.

## Objetivo do projeto

O OrderFlow API foi desenvolvido para praticar e demonstrar:

-   desenvolvimento de APIs REST com Spring Boot;
-   arquitetura em camadas;
-   persistência com JPA;
-   DTOs e mapeamento de entidades;
-   validação de dados;
-   tratamento global de exceções;
-   autenticação e autorização com Spring Security;
-   JWT;
-   BCrypt;
-   Design Patterns;
-   testes unitários;
-   integração entre diferentes componentes de uma aplicação backend.

## Status

Projeto desenvolvido para fins de estudo e portfólio, com foco em
desenvolvimento backend Java e aplicação prática de Design Patterns.

## Autor

**Deyvison**

Projeto acadêmico e de portfólio em Java / Spring Boot.
