# REST API para sistema de análise de crédito e cadastro de pessoas com autenticação por meio de *tokens JWT*

## Principais características

### 1.Segurança

- A autenticação do usuário é realizada por e-mail e senha.
- A segurança de cada *http request* é fornecida por meio de *tokens JWT.*
- O *token JWT* gerado pela API está de acordo com o padrão RFC 7519. Dessa forma, o *token* gerado pode ser decodificado através do *site*: https://jwt.io/
- Se um usuário não possuir um *token JWT* válido, ele não tem acesso ao sistema. Mas qualquer pessoa pode fazer seu cadastro para conseguir acesso.
- Cada usuário pode ter um dos três papéis (*ROLES*) a seguir: ADMIN, ADMIN TRAINEE e CUSTOMER. O papel de CUSTOMER é fornecido por padrão aos clientes da empresa de crédito. O papel de ADMIN e ADMIN TRAINEE deve ser fornecido às pessoas que gerenciam o sistema.
- Cada papel (*ROLE*) possui autorizações diversas para acesso ao sistema. Tais autorizações são descritas no item a seguir.



#### 1.1 Autorizações concedidas a cada papel (*ROLE*)

|                                                         | **CUSTOMER**                                                 | **ADMIN TRAINEE** | **ADMIN** |
| ------------------------------------------------------- | ------------------------------------------------------------ | ----------------- | --------- |
| **Criar usuário**                                       | Pode criar usuário se não estiver anteriormente cadastrado. O usuário criado somente pode ter o papel (*ROLE*) de CUSTOMER |                   | SIM       |
| **Fazer listagem de usuários**                          |                                                              | SIM               | SIM       |
| **Ver o cadastro de um usuário específico**             |                                                              | SIM               | SIM       |
| **Remover usuário**                                     |                                                              |                   | SIM       |
| **Alterar usuário**                                     |                                                              |                   | SIM       |
| **Solicitar empréstimo**                                | SIM                                                          | SIM               | SIM       |
| **Ver lista de todos os empréstimos do sistema**        |                                                              | SIM               | SIM       |
| **Ver o empréstimo que solicitou**                      | SIM                                                          | SIM               | SIM       |
| **Ver, de forma detalhada, o empréstimo que solicitou** | SIM                                                          | SIM               | SIM       |



### **2. Cadastro de clientes**

- O cliente (CUSTOMER) pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.

- Além disso, cada cliente também pode cadastrar vários telefones.

- Usuários que tiverem o papel de ADMIN ou de ADMIN TRAINEE podem fazer a listagem de todos os usuários.

- Usuários que tiverem o papel de ADMIN podem livremente criar, remover e alterar o cadastro de usuários.

  

#### **2.1. Validação dos dados do cliente**

Na criação de um cadastro, a API faz a validação dos seguintes dados:

| **CAMPO**     | **TIPO DE VALIDAÇÃO**                                        |
| ------------- | ------------------------------------------------------------ |
| **Nome**      | No mínimo 2 e no máximo 100 caracteres                       |
| **Sobrenome** | No mínimo 2 e no máximo 100 caracteres                       |
| **CPF**       | Validação feita pelo *Hibernate Validator* através da anotação @CPF |
| **E-mail**    | Validação feita pelo *Hibernate Validator* através da anotação @Email |
| **CEP**       | Deve ter cinco dígitos, hífen e terminar com três digitos    |
| **Telefone**  | Tamanho de 13 a 14 dígitos                                   |



### **3.  Solicitação de empréstimo**

- Para solicitar um empréstimo, o cliente precisa fornecer o valor do empréstimo, data da primeira parcela e quantidade de parcelas.

- O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.

- A API somente faz o registro do pedido de empréstimo se este estiver de acordo com as mencionadas regras de negócio.

  

### **4.  Acompanhamento das solicitações de empréstimo**

- O cliente (CUSTOMER) pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.
  - Na listagem, é retornado o código do empréstimo, o valor e a quantidade de parcelas.
  - No detalhe do empréstimo, é retornado: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.

- Usuários que tiverem o papel de ADMIN ou de ADMIN TRAINEE podem fazer a listagem completa de todos os  pedidos de empréstimos do sistema. Por outro lado, o usuário comum (CUSTOMER) pode ver apenas suas próprias solicitações.

  

### 5. Testes unitários

Foram criados testes unitários para algumas classes do sistema.




## **Documentação no POSTMAN**

Foi criado documentação completa no POSTMAN:

https://www.postman.com/wagnerkaba/workspace/public/documentation/19070115-f0da9d0e-3935-459b-8382-3d828bfde3d3


## *Deploy* no HEROKU

Foi feito o *deploy* da API no Heroku no seguinte endereço:

https://loanwk.herokuapp.com/



## **Tecnologias utilizadas**

- Java 11
- Spring Web
- Lombok
- Spring Security
- Swagger - Documentação produzida pelo Swagger disponível em: https://loanwk.herokuapp.com/swagger-ui.html
- Spring Boot Actuator - *Endpoint* disponível em: https://loanwk.herokuapp.com/actuator/health
- PostgreSQL
- Spring Data JPA
- Hibernate Validator
- Autenticação por token JWT

