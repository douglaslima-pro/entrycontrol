# EntryControl

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-000?style=for-the-badge&logo=postgresql)

## Descrição

**EntryControl** é uma aplicação backend de gerenciamento de usuários que realiza o controle de acesso aos recursos (endpoints) através de tecnologias de **autenticação** e **autorização**.

### Tecnologias
- Java
- Spring Boot
- Spring Data JPA
- Spring WEB
- Spring Security
- JWT

## Diagrama de Classes
![Diagrama de classes da aplicação backend](doc/diagram/Sistema%20de%20Gerenciamento%20de%20Usuários%20-%20Diagrama%20de%20Classes.png)

## Instruções de instalação
### Pré-requisitos:
- Java instalado: [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/) 
- Maven instalado: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

### Passos:

1. Clone o repositório na sua máquina:
```
git clone https://github.com/douglaslima-pro/entrycontrol
```
2. Execute o seu **banco de dados local** e altere as variáveis de conexão do banco de dados no arquivo de configuração do projeto: `application.properties`.

Por exemplo:
```
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/entrycontrol
spring.datasource.username=root
spring.datasource.password=
```

> [!NOTE]
> Em caso de dúvidas sobre o arquivo de configuração, veja: [https://docs.spring.io/spring-boot/appendix/application-properties/index.html](https://docs.spring.io/spring-boot/appendix/application-properties/index.html)

## Instruções de uso

Execute o projeto através da classe **Application.java**
    
- Em caso de erros, abra o CMD (Prompt de Comando) como administrador

- Execute os seguintes comandos **dentro da pasta raiz do projeto**

```
mvn clean
mvn install
```

> [!NOTE]
> Qualquer dúvida, mande-me uma mensagem no meu e-mail: douglaslima-pro@outlook.com

### **POST - /auth/register**

- **Descrição**: Realiza o cadastrado de novos usuários
- **Quem pode acessar**: Usuários não autenticados

**CORPO**
```json
{
    "nome": "Douglas Souza de Lima", // Obrigatório
    "bio": "Criador do EntryControl",
    "dataNascimento": "2003-01-11",
    "sexo": "M",
    "usuario": "douglaslima", // Obrigatório
    "email": "douglaslima-pro@outlook.com", // Obrigatório
    "senha": "123456", // Obrigatório
    "telefones": [
        {
            "ddd": "61",
            "prefixo": "98549",
            "sufixo": "7046",
            "tipo": "celular"
        }
    ],
    "endereco": {
        "cep": "72320000", // Obrigatório
        "estado": "DF", // Obrigatório
        "cidade": "Brasília", // Obrigatório
        "bairro": "Samambaia Norte",
        "logradouro": "Quadra 410", // Obrigatório
        "complemento": "Casa",
        "numero": "0"
    }
}
```

**RESPOSTA (STATUS 201)**
```
Usuário cadastrado com sucesso!
```

---

**RESPOSTAS DE ERRO**

Nome de usuário já existe

```json
{
    "timestamp": 1721332306432,
    "status": 400,
    "error": "error-argumentos-invalidos",
    "message": "O nome de usuário 'douglaslima' já existe",
    "detail": null,
    "help": null,
    "path": "http://localhost:8080/auth/register"
}
```

E-mail já existe

```json
{
    "timestamp": 1721332345593,
    "status": 400,
    "error": "error-argumentos-invalidos",
    "message": "O e-mail 'douglaslima-pro@outlook.com' já existe",
    "detail": null,
    "help": null,
    "path": "http://localhost:8080/auth/register"
}
```

Atributo obrigatório não foi informado

```json
{
    "timestamp": 1721332396783,
    "status": 400,
    "error": "error-argumentos-invalidos",
    "message": "O atributo 'nome' é obrigatório!",
    "detail": null,
    "help": null,
    "path": "http://localhost:8080/auth/register"
}
```

### **POST - /auth/login**

- **Descrição**: Realiza a autenticação do usuário através dos atributos **usuario** e **senha**
- **Quem pode acessar**: Usuários não autenticados

**CORPO**
```json
{
    "username": "douglaslima",
    "password": "123456"
}
```

**RESPOSTA (STATUS 200)**
```json
{
    "username": "douglaslima",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkb3VnbGFzbGltYSIsImlzcyI6IkVudHJ5Q29udHJvbCIsImlhdCI6MTcyMTMzMjUyNCwiZXhwIjoxNzIxMzM2MTI0fQ.JSbPRXq0B3nMM9zDNV6iTAmoY-zkdgRT4CybgoOzU2wu9A3nPNj-bGbiIpHiKwuyBNLQ7O271H9LFyOYUc_nKw",
    "expiration": 1721336124000
}
```

---

**RESPOSTAS DE ERRO**

Nome de usuário ou senha incorretos

```json
{
    "timestamp": 1721333439532,
    "status": 401,
    "error": "error-credenciais-incorretas",
    "message": "Nome de usuário ou senha incorretos!",
    "detail": "Solicitar a redefinição da senha caso tenha esquecido.",
    "help": null,
    "path": "http://localhost:8080/auth/login"
}
```

> [!NOTE]
> Caso mande um JSON sem atributos "{ }" ou informe apenas um dos atributos (usuario ou senha) no corpo da solicitação, receberá a mesma resposta de erro: "error-credenciais-incorretas".

## **GET - /search/me**

- **Descrição**: 
- **Quem pode acessar**:


## Contribuição
Contribuições são bem-vindas! Se você tiver qualquer correção ou sugestões de melhorias, por favor, abra uma issue ou envie um pedido de pull request no repositório.

Ao contribuir com este projeto, por favor, mantenha o estilo de escrita dos códigos e envie as mudanças em um branch separado.