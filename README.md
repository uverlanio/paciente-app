# PacienteApp

Aplicação web desenvolvida com **Spring Boot 3.5.5**, **Java 21** e **MySQL**, utilizando Spring Data JPA e o padrão MVC. O sistema permite cadastrar, buscar, atualizar e visualizar prontuários de pacientes, com interface em HTML, CSS e JavaScript.

## Tecnologias

- Java 21 e Spring Boot 3.5.5
- Spring Data JPA
- MySQL Server 8
- MySQL Workbench 8.0.47 (opcional, para administrar o banco)
- Maven

## Requisitos

- JDK 21
- MySQL Server 8 em execução
- MySQL Workbench 8.0.47 (opcional)

## Configuração e execução

1. No MySQL Workbench, conecte ao servidor e crie o banco:

   ```sql
   CREATE DATABASE db_prontuarios
       CHARACTER SET utf8mb4
       COLLATE utf8mb4_unicode_ci;
   ```

2. Configure as credenciais do MySQL. Por padrão, a aplicação usa `localhost:3306`, o banco `db_prontuarios` e o usuário `root`, sem senha. É possível sobrescrever os valores com as variáveis de ambiente `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER` e `DB_PASSWORD`.

3. Execute a aplicação:

   ```sh
   mvn spring-boot:run
   ```

   O Hibernate cria ou atualiza as tabelas `pacientes` e `usuario` no banco configurado.

4. Acesse a interface em `http://localhost:8080/html/home.html`.

Os registros existentes no MongoDB não são copiados automaticamente para o MySQL; a migração desses dados deve ser feita separadamente antes de desativar o banco antigo.

## API REST

- `POST /api/pacientes`: cria um paciente.
- `GET /api/pacientes/{termo}`: busca paciente.
- `PUT /api/pacientes/{id}`: atualiza um paciente.

Os arquivos da interface ficam em `src/main/resources/static`.

## Docker
#docker build -t prontuario-app-2.0.0 .
#docker run -d -p 8080:8080 --name prontuario-app prontuario-app-2.0.0
#docker login
#docker tag prontuario-app-2.0.0:latest uverdocker/prontuario-app:2.0.0
#docker push uverdocker/prontuario-app:latest

#docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name ci-cd-jenkins jenkins/jenkins:lts
