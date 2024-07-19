# EntryControl

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

## Descrição

**EntryControl** é uma aplicação backend de gerenciamento de usuários que realiza o controle de acesso aos recursos (endpoints) através de tecnologias de **autenticação** e **autorização**.

### Tecnologias
- Java
- Spring Boot
- Spring Data JPA
- Spring WEB
- Spring Security
- JWT
- MySQL

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
> Qualquer dúvida, mande uma mensagem no meu e-mail: douglaslima-pro@outlook.com

---

### Endpoints

- [/auth/register](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#post---authregister)
- [/auth/login](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#post---authlogin)
- [/user/search/me](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#get---searchme)
- [/user/search/{id}](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#get---searchid)
- [/user/search/all](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#get---searchall)
- [/user/update/me](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#put---updateme)
- [/user/update/{id}](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#put---updateid)
- [/user/delete/{id}](https://github.com/douglaslima-pro/entrycontrol/tree/main?tab=readme-ov-file#delete---deleteid)

---

### **POST - /auth/register**

- **Descrição**: Realiza o cadastro de novos usuários
- **Quem pode acessar**: Qualquer usuário

**CORPO**
```json
{
    "nome": "Douglas Souza de Lima",
    "bio": "Criador do EntryControl",
    "dataNascimento": "2003-01-11",
    "sexo": "M",
    "usuario": "douglaslima",
    "email": "douglaslima-pro@outlook.com",
    "senha": "123456",
    "telefones": [
        {
            "ddd": "61",
            "prefixo": "98549",
            "sufixo": "7046",
            "tipo": "celular"
        }
    ],
    "endereco": {
        "cep": "72320000",
        "estado": "DF",
        "cidade": "Brasília",
        "bairro": "Samambaia Norte",
        "logradouro": "Quadra 410",
        "complemento": "Casa",
        "numero": "0"
    }
}
```

> [!IMPORTANT]
> É obrigatório informar os seguintes atributos: `nome`, `usuario`, `email`, `senha`, `cep`, `estado`, `cidade` e `logradouro`.

**RESPOSTA (STATUS 201)**
```
Usuário cadastrado com sucesso!
```

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

---

### **POST - /auth/login**

- **Descrição**: Realiza a autenticação do usuário através dos atributos **usuario** e **senha**
- **Quem pode acessar**: Qualquer usuário

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

---

### **GET - /search/me**

- **Descrição**: Retorna as informações do usuário autenticado
- **Quem pode acessar**: Usuários autenticados

> É necessário inserir o **token Bearer** no cabeçalho "Authorization" antes de enviar a solicitação.

**RESPOSTA (STATUS 200)**

```json
{
    "id": 1,
    "nome": "Douglas Souza de Lima",
    "bio": "Criador do EntryControl",
    "dataNascimento": "11/01/2003",
    "sexo": "M",
    "usuario": "douglaslima",
    "email": "douglaslima-pro@outlook.com",
    "senha": "$2a$10$O2y66.J8ndLwKtuEQqCO8ephM3oaNIuvgpuZ68RhL3.DYXMryZFAe",
    "telefones": [
        {
            "id": 1,
            "ddd": "61",
            "prefixo": "98549",
            "sufixo": "7046",
            "tipo": "celular"
        }
    ],
    "endereco": {
        "cep": "72320000",
        "estado": "DF",
        "cidade": "Brasília",
        "bairro": "Samambaia Norte",
        "logradouro": "Quadra 410",
        "complemento": "Casa",
        "numero": 0
    },
    "perfis": [
        {
            "nome": "USER"
        }
    ]
}
```

**RESPOSTAS DE ERRO**

Token expirado

```json
{
    "timestamp": 1721343755326,
    "status": 401,
    "error": "error-token-expirado",
    "message": "O token expirou há 173099326 milissegundos atrás em 2024-07-16T19:57:36.",
    "detail": "Faça login novamente e utilize o token gerado para autenticar o acesso.",
    "help": null,
    "path": "http://localhost:8080/user/search/me"
}
```

Token inválido

```json
{
    "timestamp": 1721343790695,
    "status": 400,
    "error": "error-token-invalido",
    "message": "Não foi possível ler o token informado no cabeçalho 'Authorization'. O token é inválido ou está em um formato incorreto.",
    "detail": "Verifique se o token informado no cabeçalho 'Authorization' é um token Bearer válido.",
    "help": null,
    "path": "http://localhost:8080/user/search/me"
}
```

Cabeçalho "Authorization" vazio

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

---

### **GET - /search/{id}**

- **Descrição**: Retorna as informações do usuário cujo ID foi informado no parâmetro da URI
- **Quem pode acessar**: Usuários autenticados e com o perfil "ADMIN"

> É necessário inserir o **token Bearer** no cabeçalho "Authorization" antes de enviar a solicitação.

**RESPOSTA (STATUS 200)**

```json
{
    "id": 1,
    "nome": "Douglas Souza de Lima",
    "bio": "Criador do EntryControl",
    "dataNascimento": "11/01/2003",
    "sexo": "M",
    "usuario": "douglaslima",
    "email": "douglaslima-pro@outlook.com",
    "senha": "$2a$10$O2y66.J8ndLwKtuEQqCO8ephM3oaNIuvgpuZ68RhL3.DYXMryZFAe",
    "telefones": [
        {
            "id": 1,
            "ddd": "61",
            "prefixo": "98549",
            "sufixo": "7046",
            "tipo": "celular"
        }
    ],
    "endereco": {
        "cep": "72320000",
        "estado": "DF",
        "cidade": "Brasília",
        "bairro": "Samambaia Norte",
        "logradouro": "Quadra 410",
        "complemento": "Casa",
        "numero": 0
    },
    "perfis": [
        {
            "nome": "USER"
        }
    ]
}
```

**RESPOSTAS DE ERRO**

Usuário não foi encontrado

```json
{
    "timestamp": 1721346566648,
    "status": 404,
    "error": "error-objeto-nao-encontrado",
    "message": "Nenhum usuário foi encontrado com o ID 3",
    "detail": null,
    "help": null,
    "path": "http://localhost:8080/user/search/3"
}
```

Token expirado

```json
{
    "timestamp": 1721343755326,
    "status": 401,
    "error": "error-token-expirado",
    "message": "O token expirou há 173099326 milissegundos atrás em 2024-07-16T19:57:36.",
    "detail": "Faça login novamente e utilize o token gerado para autenticar o acesso.",
    "help": null,
    "path": "http://localhost:8080/user/search/1"
}
```

Token inválido

```json
{
    "timestamp": 1721343790695,
    "status": 400,
    "error": "error-token-invalido",
    "message": "Não foi possível ler o token informado no cabeçalho 'Authorization'. O token é inválido ou está em um formato incorreto.",
    "detail": "Verifique se o token informado no cabeçalho 'Authorization' é um token Bearer válido.",
    "help": null,
    "path": "http://localhost:8080/user/search/1"
}
```

Cabeçalho "Authorization" vazio

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

Usuário não possui o perfil "ADMIN"

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

---

### **GET - /search/all**

- **Descrição**: Retorna as informações de todos os usuários cadastrados na API
- **Quem pode acessar**: Usuários autenticados e com o perfil "ADMIN"

> É necessário inserir o **token Bearer** no cabeçalho "Authorization" antes de enviar a solicitação.

**RESPOSTA (STATUS 200)**

```json
{
    "id": 1,
    "nome": "Douglas Souza de Lima",
    "bio": "Criador do EntryControl",
    "dataNascimento": "11/01/2003",
    "sexo": "M",
    "usuario": "douglaslima",
    "email": "douglaslima-pro@outlook.com",
    "senha": "$2a$10$O2y66.J8ndLwKtuEQqCO8ephM3oaNIuvgpuZ68RhL3.DYXMryZFAe",
    "telefones": [
        {
            "id": 1,
            "ddd": "61",
            "prefixo": "98549",
            "sufixo": "7046",
            "tipo": "celular"
        }
    ],
    "endereco": {
        "cep": "72320000",
        "estado": "DF",
        "cidade": "Brasília",
        "bairro": "Samambaia Norte",
        "logradouro": "Quadra 410",
        "complemento": "Casa",
        "numero": 0
    },
    "perfis": [
        {
            "nome": "USER"
        }
    ]
},
{
    Outros usuários...
}
```

**RESPOSTAS DE ERRO**

Token expirado

```json
{
    "timestamp": 1721343755326,
    "status": 401,
    "error": "error-token-expirado",
    "message": "O token expirou há 173099326 milissegundos atrás em 2024-07-16T19:57:36.",
    "detail": "Faça login novamente e utilize o token gerado para autenticar o acesso.",
    "help": null,
    "path": "http://localhost:8080/user/search/all"
}
```

Token inválido

```json
{
    "timestamp": 1721343790695,
    "status": 400,
    "error": "error-token-invalido",
    "message": "Não foi possível ler o token informado no cabeçalho 'Authorization'. O token é inválido ou está em um formato incorreto.",
    "detail": "Verifique se o token informado no cabeçalho 'Authorization' é um token Bearer válido.",
    "help": null,
    "path": "http://localhost:8080/user/search/all"
}
```

Cabeçalho "Authorization" vazio

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

Usuário não possui o perfil "ADMIN"

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

---

### **PUT - /update/me**

- **Descrição**: Atualiza as informações do usuário autenticado
- **Quem pode acessar**: Usuários autenticados

> É necessário inserir o **token Bearer** no cabeçalho "Authorization" antes de enviar a solicitação.

**CORPO**
```json
{
    "nome": "Douglas",
    "bio": "Estudante de Análise e Desenvolvimento de Sistemas",
    "senha": "douglas@2024",
    "telefones": [
        {
            "ddd": "61",
            "prefixo": "4402",
            "sufixo": "8922",
            "tipo": "trabalho"
        }
    ]
}
```

**RESPOSTA (STATUS 200)**

```json
{
    "id": 1,
    "nome": "Douglas",
    "bio": "Estudante de Análise e Desenvolvimento de Sistemas",
    "dataNascimento": "11/01/2003",
    "sexo":"",
    "usuario": "douglaslima",
    "email": "douglaslima-pro@outlook.com",
    "senha": "$2a$10$Z13ofifAgRyYsHmPjcqpEOwXSrsw9OlW5MTicLTDAQhuMgaFXbsC2",
    "telefones": [
        {
            "id": 2,
            "ddd": "61",
            "prefixo": "4402",
            "sufixo": "8922",
            "tipo": "trabalho"
        }
    ],
    "endereco": {
        "cep": "72320000",
        "estado": "DF",
        "cidade": "Brasília",
        "bairro": "Samambaia Norte",
        "logradouro": "Quadra 410",
        "complemento": "Casa",
        "numero": 0
    },
    "perfis": [
        {
            "nome": "USER"
        }
    ]
}
```

> [!NOTE]
> Depois que atualiza os dados do usuário, o atributo `sexo` é apagado. Esse erro será corrigido em versões futuras do projeto.


**RESPOSTAS DE ERRO**

Token expirado

```json
{
    "timestamp": 1721343755326,
    "status": 401,
    "error": "error-token-expirado",
    "message": "O token expirou há 173099326 milissegundos atrás em 2024-07-16T19:57:36.",
    "detail": "Faça login novamente e utilize o token gerado para autenticar o acesso.",
    "help": null,
    "path": "http://localhost:8080/user/update/me"
}
```

Token inválido

```json
{
    "timestamp": 1721343790695,
    "status": 400,
    "error": "error-token-invalido",
    "message": "Não foi possível ler o token informado no cabeçalho 'Authorization'. O token é inválido ou está em um formato incorreto.",
    "detail": "Verifique se o token informado no cabeçalho 'Authorization' é um token Bearer válido.",
    "help": null,
    "path": "http://localhost:8080/user/update/me"
}
```

Cabeçalho "Authorization" vazio

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

---

### **PUT - /update/{id}**

- **Descrição**: Atualiza as informações do usuário cujo ID foi informado no parâmetro da URI
- **Quem pode acessar**: Usuários autenticados e com o perfil "ADMIN"

> É necessário inserir o **token Bearer** no cabeçalho "Authorization" antes de enviar a solicitação.

**CORPO**
```json
{
    "nome": "Douglas",
    "bio": "Estudante de Análise e Desenvolvimento de Sistemas",
    "senha": "douglas@2024",
    "telefones": [
        {
            "ddd": "61",
            "prefixo": "4402",
            "sufixo": "8922",
            "tipo": "trabalho"
        }
    ]
}
```

**RESPOSTA (STATUS 200)**

```json
{
    "id": 1,
    "nome": "Douglas",
    "bio": "Estudante de Análise e Desenvolvimento de Sistemas",
    "dataNascimento": "11/01/2003",
    "sexo":"",
    "usuario": "douglaslima",
    "email": "douglaslima-pro@outlook.com",
    "senha": "$2a$10$Z13ofifAgRyYsHmPjcqpEOwXSrsw9OlW5MTicLTDAQhuMgaFXbsC2",
    "telefones": [
        {
            "id": 2,
            "ddd": "61",
            "prefixo": "4402",
            "sufixo": "8922",
            "tipo": "trabalho"
        }
    ],
    "endereco": {
        "cep": "72320000",
        "estado": "DF",
        "cidade": "Brasília",
        "bairro": "Samambaia Norte",
        "logradouro": "Quadra 410",
        "complemento": "Casa",
        "numero": 0
    },
    "perfis": [
        {
            "nome": "USER"
        }
    ]
}
```

> [!NOTE]
> Depois que atualiza os dados do usuário, o atributo `sexo` é apagado. Esse erro será corrigido em versões futuras do projeto.


**RESPOSTAS DE ERRO**

Usuário não foi encontrado

```json
{
    "timestamp": 1721346566648,
    "status": 404,
    "error": "error-objeto-nao-encontrado",
    "message": "Nenhum usuário foi encontrado com o ID 3",
    "detail": null,
    "help": null,
    "path": "http://localhost:8080/user/update/3"
}
```

Token expirado

```json
{
    "timestamp": 1721343755326,
    "status": 401,
    "error": "error-token-expirado",
    "message": "O token expirou há 173099326 milissegundos atrás em 2024-07-16T19:57:36.",
    "detail": "Faça login novamente e utilize o token gerado para autenticar o acesso.",
    "help": null,
    "path": "http://localhost:8080/user/update/1"
}
```

Token inválido

```json
{
    "timestamp": 1721343790695,
    "status": 400,
    "error": "error-token-invalido",
    "message": "Não foi possível ler o token informado no cabeçalho 'Authorization'. O token é inválido ou está em um formato incorreto.",
    "detail": "Verifique se o token informado no cabeçalho 'Authorization' é um token Bearer válido.",
    "help": null,
    "path": "http://localhost:8080/user/update/1"
}
```

Cabeçalho "Authorization" vazio

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

Usuário não possui o perfil "ADMIN"

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

---

### **DELETE - /delete/{id}**

- **Descrição**: Exclui de forma permanente as informações do usuário cujo ID foi informado no parâmetro da URI
- **Quem pode acessar**: Usuários autenticados e com o perfil "ADMIN"

> É necessário inserir o **token Bearer** no cabeçalho "Authorization" antes de enviar a solicitação.

**RESPOSTA (STATUS 204)**

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 204 (No content).

**RESPOSTAS DE ERRO**

Usuário não foi encontrado

```json
{
    "timestamp": 1721346566648,
    "status": 404,
    "error": "error-objeto-nao-encontrado",
    "message": "Nenhum usuário foi encontrado com o ID 3",
    "detail": null,
    "help": null,
    "path": "http://localhost:8080/user/delete/3"
}
```

Token expirado

```json
{
    "timestamp": 1721343755326,
    "status": 401,
    "error": "error-token-expirado",
    "message": "O token expirou há 173099326 milissegundos atrás em 2024-07-16T19:57:36.",
    "detail": "Faça login novamente e utilize o token gerado para autenticar o acesso.",
    "help": null,
    "path": "http://localhost:8080/user/delete/1"
}
```

Token inválido

```json
{
    "timestamp": 1721343790695,
    "status": 400,
    "error": "error-token-invalido",
    "message": "Não foi possível ler o token informado no cabeçalho 'Authorization'. O token é inválido ou está em um formato incorreto.",
    "detail": "Verifique se o token informado no cabeçalho 'Authorization' é um token Bearer válido.",
    "help": null,
    "path": "http://localhost:8080/user/delete/1"
}
```

Cabeçalho "Authorization" vazio

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

Usuário não possui o perfil "ADMIN"

```

```

> [!NOTE]
> Retorna apenas o cabeçalho com o status 403 (Forbidden).

---

## Contribuição
Contribuições são bem-vindas! Se você tiver qualquer correção ou sugestões de melhorias, por favor, abra uma issue ou envie um pedido de pull request no repositório.

Ao contribuir com este projeto, por favor, mantenha o estilo de escrita dos códigos e envie as mudanças em um branch separado.
