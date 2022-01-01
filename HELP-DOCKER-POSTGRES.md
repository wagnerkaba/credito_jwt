# DOCKER UTILIZADO PARA POSTGRES LOCAL

## Criar container docker do Postgres

```shell script
docker run --name emprestimo-db -d -p 5432:5432 -e POSTGRES_USER=p_user -e POSTGRES_PASSWORD=pwd -e POSTGRES_DB=emprestimo postgres
```

## Entrar no shell do container
```shell script
docker run -it --rm --net=host -v $PWD:/tmp postgres /bin/bash

```

## Entrar no Postgres Shell
```shell script
psql -h localhost -U p_user emprestimo
```

## Criar usuários

Após iniciar o programa, você pode criar usuários manualmente no psql:

```shell script
INSERT INTO person (id, cpf, first_name, last_name, password, user_role, email) VALUES (1, '369.333.878-79', 'Rodrigo', 'Melo', 'pass', 1, 'admin@gmail.com');

INSERT INTO person (id, cpf, first_name, last_name, password, user_role, email) VALUES (2, '165.086.320-94', 'Alexandre', 'Souza', 'pass', 2, 'trainee@gmail.com');

INSERT INTO person (id, cpf, first_name, last_name, password, user_role, email) VALUES (3, '954.819.040-04', 'Renata', 'Bueno', 'pass', 0, 'cliente@gmail.com');


```