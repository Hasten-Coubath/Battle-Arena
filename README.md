# Battle Arena - Arquitetura de Microsserviços

Este projeto consiste em uma implementação de arquitetura de software distribuída aplicada a um contexto de jogo RPG (Role-Playing Game). O objetivo principal é demonstrar a aplicação prática de microsserviços, padrões de projeto (GoF), comunicação síncrona e assíncrona, e persistência de dados isolada.

## Visão Geral da Arquitetura

O sistema foi desenvolvido utilizando **Java 17** e **Spring Boot 3.4.0**, estruturado em quatro componentes principais operando em containers Docker:

1.  **API Gateway (Porta 8080):** Ponto único de entrada, responsável pelo roteamento de requisições para os serviços backend, utilizando Spring Cloud Gateway.
2.  **Auth Service (Porta 8081):** Microserviço responsável pela gestão de usuários e autenticação.
3.  **Character Service (Porta 8082):** Microserviço responsável pela criação de personagens (Factory Method) e persistência de dados.
4.  **Combat Service (Porta 8083):** Microserviço responsável pela lógica de negócios das batalhas, cálculo de dano (Strategy) e logs de auditoria (AOP).

### Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.4.0
* **Banco de Dados:** PostgreSQL (Instâncias isoladas por serviço)
* **Mensageria:** RabbitMQ
* **Containerização:** Docker & Docker Compose
* **Comunicação entre Serviços:** OpenFeign (REST) e AMQP
* **Monitoramento/Logs:** Spring AOP (AspectJ)

## Padrões de Projeto Implementados

O sistema implementa rigorosamente os seguintes padrões de engenharia de software:

* **Factory Method:** Utilizado no *Character Service* para encapsular a lógica de instanciação das classes `Mage` e `Warrior`.
* **Strategy:** Utilizado no *Combat Service* para permitir a variação do algoritmo de cálculo de dano conforme a classe do personagem.
* **Observer:** Implementado através do RabbitMQ para notificação assíncrona de eventos de "Fim de Batalha", desacoplando o processamento de ranking/logs.
* **Facade:** Implementado nos Controllers REST para simplificar a interface de consumo dos serviços complexos.
* **Decorator/Proxy:** Utilizado via Programação Orientada a Aspectos (AOP) para injeção transparente de logs em tempo de execução.

---

## Instruções de Execução

### 1. Pré-requisitos
Certifique-se de ter instalado em sua máquina:
* Docker e Docker Desktop
* Java JDK 17
* Git

### 2. Inicialização da Infraestrutura
Na raiz do projeto, execute o comando para iniciar os bancos de dados e o servidor de mensageria:

```bash
docker compose up -d
```

### 3. Inicialização dos Microsserviços
## Terminal 1 - Auth Service:
```bash
cd auth-service
./mvnw spring-boot:run
```
## Terminal 2 - Character Service:
```bash
cd character-service
./mvnw spring-boot:run
```
## Terminal 3 - Combat Service:
```bash
cd combat-service
./mvnw spring-boot:run
```
## Terminal 4 - API Gateway:
```bash
cd api-gateway
./mvnw spring-boot:run
```

### Utilização da API
Todas as requisições devem ser enviadas para o API Gateway (Porta 8080). Abaixo estão exemplos de comandos prontos para copiar e colar no PowerShell.

## 1. Criação de Personagens
## Criar Guerreiro:
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/characters" -ContentType "application/json" -Body '{"name": "Conan", "role": "WARRIOR"}'
## Criar Mago:
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/characters" -ContentType "application/json" -Body '{"name": "Gandalf", "role": "MAGE"}'

## 2. Execução de Batalha
Para iniciar um turno de combate, utilize o comando abaixo. Substitua os parâmetros attackerId e defenderId pelos IDs retornados na criação dos personagens (geralmente 1 e 2).
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/combat/attack?attackerId=1&defenderId=2"

O retorno apresentará o log gráfico da batalha, a redução de pontos de vida (HP) e, caso o HP chegue a zero, a mensagem de finalização do combate.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Autor: Hasten Versão: 1.0.0
