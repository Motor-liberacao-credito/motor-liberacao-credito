# motor-liberacao-credito

## Visão Geral

O `motor-liberacao-credito` é um API para avaliação de crédito e consulta de saldo/score de clientes. 

Ele é projetado para ser integrado em sistemas de crédito, permitindo que as empresas avaliem o risco de crédito dos clientes e tomem decisões informadas sobre a concessão de crédito.

O projeto é construído usando Spring Boot e segue as melhores práticas de desenvolvimento, incluindo testes unitários com Mockito para garantir a qualidade e confiabilidade do código.

## Arquitetura

O projeto segue uma arquitetura em camadas, separando responsabilidades em:

- **application/**: Casos de uso e orquestração de fluxos de negócio.
- **config/**: Configurações automáticas, propriedades e health checks da API.
- **domain/**: Interfaces e contratos de negócio (ex: mappers, modelos de domínio, interfaces de serviços e de borda como ports).
- **infrastructure/**: Implementações concretas dos adaptadores, integrações com serviços externos (ex: database, endpoints da API, etc).

## Banco de Dados H2 em Memória

O projeto utiliza o banco de dados H2 em memória para armazenar os dados dos clientes, incluindo informações de saldo e score de crédito.

Essa escolha facilita o desenvolvimento e os testes, permitindo que o banco de dados seja facilmente configurado e resetado a cada execução do projeto.

As configurações do H2 estão definidas no arquivo `application.yml`, onde é possível ajustar as credenciais, o URL de conexão e outras propriedades relacionadas ao banco de dados.

Para acessar o console do H2, basta navegar para [http://localhost:8080/h2-console](http://localhost:8080/h2-console) após iniciar a aplicação, usando as credenciais configuradas no `application.yml`.

### Queries de Suporte

Queries para auxilio nos testes e validacoes de resultados

```
-- tabela clientes
SELECT * FROM CLIENTES; 

-- tabela transacoes
-- ID, CLIENTE_ID, DATA, POSITIVA, VALOR
SELECT * FROM TRANSACOES 
WHERE 1=1 
AND CLIENTE_ID = 1;

-- busca transacoes positivas
SELECT 
    CLIENTE_ID,
    COUNT(ID) AS TRANSACOES,  
    SUM(VALOR) AS SALDO  
FROM TRANSACOES
WHERE 1=1
AND POSITIVA = true
AND CLIENTE_ID = 1
GROUP BY CLIENTE_ID;

-- busca clientes com score >= 50
SELECT 
    CLIENTE_ID,
    COUNT(CASE WHEN POSITIVA = TRUE THEN 1 END) AS positivas,
    COUNT(*) AS transacoes,
    CAST(COUNT(CASE WHEN POSITIVA = TRUE THEN 1 END) AS DECIMAL(10,2)) 
        / CAST(COUNT(*) AS DECIMAL(10,2)) AS proporcao,
    ROUND(
        (CAST(COUNT(CASE WHEN POSITIVA = TRUE THEN 1 END) AS DECIMAL(10,2)) 
         / CAST(COUNT(*) AS DECIMAL(10,2)) * 100), 
        2
    ) AS score
FROM TRANSACOES
GROUP BY CLIENTE_ID
HAVING ROUND(
        (CAST(COUNT(CASE WHEN POSITIVA = TRUE THEN 1 END) AS DECIMAL(10,2)) 
         / CAST(COUNT(*) AS DECIMAL(10,2)) * 100), 
        2
    ) >= 50;
```

**Importante**: O H2 em memória é adequado para desenvolvimento e testes, mas para ambientes de produção, recomenda-se o uso de um banco de dados mais robusto e persistente, como PostgreSQL ou MySQL.

## API Contract First

A API é definida usando OpenAPI (Swagger), garantindo que a documentação esteja sempre atualizada e que os contratos sejam claros para os consumidores da API. 

O arquivo de definição da API está localizado em `src/main/resources/openapi/openapi.yaml`.

Através do plugin openapitools, o código dos controllers e modelos é gerado automaticamente a partir do contrato definido no OpenAPI, garantindo consistência entre a implementação e a documentação.

Esse approach facilita a manutenção e evolução da API, permitindo que as mudanças sejam feitas de forma controlada e transparente para os consumidores.

As definições de endpoints, parâmetros, respostas e modelos de dados estão todas centralizadas no arquivo OpenAPI, tornando a API fácil de entender e consumir por outros desenvolvedores e sistemas.

As configurações do plugin openapitools estão definidas no `pom.xml`, onde é especificado o caminho para o arquivo OpenAPI e as opções de geração de código, como o pacote base para os controllers e modelos gerados.

### Configuração do OpenAPI Generator

Exemplo de configuração do plugin no `pom.xml`:

```xml
<!-- Plugin OpenAPI Generator -->
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>${openapi.generator.version}</version>
    <executions>
        <execution>
            <id>generate-api</id>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/openapi/openapi.yml</inputSpec>
                <generatorName>spring</generatorName>
                <library>spring-boot</library>
                <apiPackage>com.motorliberacaocredito.infrastructure.entrypoint</apiPackage>
                <modelPackage>com.motorliberacaocredito.domain.model</modelPackage>
                <output>${project.build.directory}/generated-sources/openapi</output>
                <configOptions>
                    <useLombok>true</useLombok>
                    <delegatePattern>true</delegatePattern>
                    <interfaceOnly>true</interfaceOnly>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### Contrato da API

A API expõe os seguintes endpoints: 
- `POST /credito/avaliar/{id}`: Avalia o crédito de um cliente com base em seu ID e retorna uma resposta indicando se o crédito foi aprovado ou não, juntamente com detalhes relevantes.
- `GET /credito/cliente/{id}`: Recupera informações sobre o cliente, incluindo seu saldo e score de crédito, com base em seu ID.

Para detalhes completos sobre os parâmetros, respostas e modelos de dados, consulte o arquivo `openapi.yaml` localizado em `src/main/resources/openapi/`.

## Regras de Negócio do Motor de Liberação de Crédito

O motor de liberação de crédito avalia o risco de crédito dos clientes com base em uma série de regras de negócio, que podem incluir:

- **AvaliarCreditoUseCase**: avalia se o cliente é elegível para crédito com base em seu score e saldo. Se o score for maior que 60 e o saldo for positivo, o crédito é aprovado; caso contrário, é negado.
- **ConsultarSaldoScoreUseCase**: recupera o saldo e o score de crédito do cliente, que são usados para avaliar o risco de crédito.

## Como Rodar o Projeto

1. Clone o repositório: `git clone` ou baixe o código-fonte do projeto.
2. Navegue até o diretório do projeto: `cd motor-liberacao-credito`
3. Execute o projeto usando Maven: `mvn spring-boot:run` ou configure a classe `Application` para rodar em sua IDE.
4. A API estará disponível em [http://localhost:8080](http://localhost:8080).
5. Use o Swagger UI para explorar a API: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) ou use ferramentas como Postman para testar os endpoints.

## Considerações Finais

Este projeto é um exemplo de como construir uma API de avaliação de crédito usando Spring Boot, seguindo boas práticas de desenvolvimento e arquitetura.

Ele é projetado para ser facilmente extensível e adaptável a diferentes regras de negócio e integrações com serviços externos, como bancos de dados ou sistemas de terceiros para consulta de crédito.

Para suporte, dúvidas ou contribuições, consulte a documentação oficial do projeto ou entre em contato com os mantenedores.

## Referências
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Mockito](https://site.mockito.org/)



