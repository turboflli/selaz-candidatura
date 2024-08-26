# Teste Candidatura

``
(JAVA + SpringBoot + prova)
made to avaliation prove ...
Feito para prova de teste ...
``

para ler em portugues vá para [meio do arqivo](#portugues)

## ENGLISH

This website is used to manage User and their Tasks
 - CRUD from tasks
 - CRUD from user
 - Has normal users and admin users
 - Has security to transmit date, like password while on webbroswer
 >Doesn't has security when using the requested api, only when by the screen

### Config
In order to be able to run, you need just to configure the database acess,
Go to file \src\main\resources\application.properties , and edit acording to your data base.

Its default uses postgres database,

| credential | value |
| ------ | ------ |
| username | postgres |
| password | postgres |

``
spring.datasource.driverClassName=<your DB driver>
spring.datasource.url=<your jdbc connection url>
spring.datasource.username=<your DB user>
spring.datasource.password=<your DB user password>
spring.jpa.database-platform=<your DB dialect>
``


### running
To run te program just type on the console, on the root folder

``
mvn spring-boot:run
``

It should open an web broswer window, but if not [Click here](https://localhost:8080/listagem)
It comes with a default admin user, with username: 'userAdmin' and password: '@P!$#23'

### Documentation
This program was documented using swagger, to acess [Click here](http://localhost:8080/listagem/swagger-ui/index.html#/) after running of course,

### Aditional config
By default it runs on port 8080, so make sure it's avaliable when running, otherwise it won't work, and has a default path of 'listagem'
To change edit the file \src\main\resources\application.properties.

``
server.port=<new port>
server.servlet.context-path=<new path> 
``

>Note that this will affect the acess link on this document

### rules
 - Normal users can create tasks and associate to themself, also edit and delete them, but not from another users
 - Admin users can create tasks and associate to any user, and edit and delete tasks from another user
 - Only Admin users can create, edit or delete, another user
 - All users can consult tasks 

## PORTUGUES

Esse site é usado para gerenciar usuários e suas tarefas
 - CRUD para tarefas
 - CRUD para usuários
 - Tem usuários normais e administradores
 - Tem segurança para trnasmitir dados, como senha, quando estiver no navegador
 > Não tem segurança quando acessado pela api requisitada, apenas pela tela própria no navegador

### Configuração
Para poder rodar o projeto, apenas precisa configurar o acesso ao banco de dados
Vá para o arquivo \src\main\resources\application.properties , e edite de acordo com seu acesso.

Ele usa por padrão banco postgres.

| credencial | valor |
| ------ | ------ |
| username | postgres |
| password | postgres |

``
spring.datasource.driverClassName=<seu driver do BD>
spring.datasource.url=<sua url de conexão jdbc>
spring.datasource.username=<seu usuário do BD>
spring.datasource.password=<senha do seu usuário do BD>
spring.jpa.database-platform=<dialeto do seu BD>
``


### rodando
Para rodar o programa, apenas dinite no seu terninal, na pasta raiz

``
mvn spring-boot:run
``

Deverá abrir uma janela no navegador, senão [Click aqui](https://localhost:8080/listagem)
Ele vem com um usuário administrador padrão, com username: 'userAdmin' e senha: '@P!$#23'

### Documentação
Esse programa foi documentado usando swagger, para acessar [Click aqui](http://localhost:8080/listagem/swagger-ui/index.html#/) after running of course,

### conficuração adicional
Por padrão ele roda na porta 8080, então tenha certeza que a porta está disponível quando rodar, senão não irá funcionar, e tem o caminho padrão como 'listagem'
para editar, altere o arquivo \src\main\resources\application.properties.

``
server.port=<nova porta>
server.servlet.context-path=<novo caminho> 
``

> Note que isso afetará os links de acesso presentes neste documento

### Regras
 - Usuário normal pode criar tarefas e associá-las a si mesmo, também editá-las e deleta-las, mas não de outro usuário
 - Usuários administradore podem criar tarefas e associá-las a outro usuário, bem como editar e deletar
 - Apenas usuários administradores podem criar, editar ou deletar outro usuário
 - Todos os usuários podem consultar as tarefas 
