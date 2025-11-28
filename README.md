# Tools Java Challenge [C]
### Prova Prática - Processo Seletivo Tools


##### ToolsChallenge – API de Pagamentos

A aplicação permite criar pagamentos, gerar estornos e listar transações

<br>

######Tecnologias utilizadas

    Java 21
    
    Spring Boot 3 (Web, JPA)
    
    PostgreSQL
    
    Flyway (migrations)
    
    H2 (teste & desenvolvimento local)
    
    Docker + Docker Compose
    
    OpenAPI / Swagger UI


<br>

###Como rodar a aplicação

Clone o repositório ou baixe o .zip em sua máquina. 

######A aplicação está preparada para rodar via Docker utilizando o perfil postgres.

No diretório clonado, rode o comando:

> docker-compose up --build


Esse comando irá subir:
<ul>

    PostgreSQL 15 na porta 5433
    
    API ToolsChallenge na porta 8080
    
    A API iniciará já conectada ao banco, usando application-postegres.properties.
</ul>


######A aplicação tambem pode rodar localmente com H2 sem Docker

O projeto possui um perfil h2 para desenvolvimento, ativado por padrão: spring.profiles.active=h2


Basta rodar pela própria IDE ou via Maven:

> mvn spring-boot:run

A porta tambem será :8080.

<br>

######Para facilitar o consumo da API, foi incluído o Swagger UI (OpenAPI).

Após subir a aplicação, acesse: http://localhost:8080/swagger-ui.html

Aqui você poderá testar todas as rotas da API sem precisar do Insomnia/Postman.


<br>

###Endpoints da API

Base URL
http://localhost:8080/api-pagamentos

<br>

######Realizar um pagamento

POST /api-pagamentos

body request:

```json
{
    "transacao": {
        "cartao": "4444********1234",
        "id": "1000235689000001",
        "descricao": {
            "valor": "70.50",
            "dataHora": "01/05/2021 18:30:00",
            "estabelecimento": "PetShop Mundo cão"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": "1"
        }
    }
}

````

body response:
```json
{
    "transacao": {
        "cartao": "4444********1234",
        "id": "1000235689000001",
        "descricao": {
            "valor": "70.50",
            "dataHora": "01/05/2021 18:30:00",
            "estabelecimento": "PetShop Mundo cão",
            "nsu": "402601112",
            "codigoAutorizacao": "45999159",
            "status": "AUTORIZADO"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": "1"
        }
    }
}

````

<br>

######Realizar estorno

POST /api-pagamentos/estorno

body request:

```json
{
  "id": "1000235689000001"
}
```

O body response segue o mesmo padrão do pagamento, alterando o descricao.status para "CANCELADO" conforme regra.

<br>

######Listar todas as transações

GET /api-pagamentos

<br>

######Buscar transação por ID

GET /api-pagamentos/{id}


<br>

####Regras de erro

Para qualquer erro (campos inválidos, forma de pagamento desconhecida, ID já existente, JSON mal formatado):

O body sempre retorna o JSON modelo padrão do body response, porém com descricao.status "NEGADO".

Os motivos geradores do erro so enviados aos  HEADERS da response e podem ser identificados no value do campo name 'Erro encontrado'.

Apenas para casos de ID não encontrado ou de ID já estornado, o body response virá com um aviso e sem o JSON padrão.

<br>


###Informacoes adicionais 

O docker-compose.yml utiliza:

    DB: toolschallenge_db
    
    User: postgres
    
    Password: postgres
    
    Host interno: postgres (nome do serviço)
    
    Porta externa: 5433
    
    Porta interna: 5432

Flyway executa automaticamente o script:

    src/main/resources/db/migration/V1__create_tb_transacoes.sql


Dockerfile (multi-stage)

    Primeiro estágio compila o JAR
    
    Segundo estágio roda o JRE leve com o app
    
    docker-compose.yml
    
    Sobe Postgres + API
    
    Injeta variáveis via environment
    
    Usa perfil postgres
