# PacienteApp

Aplicação web para cadastro e gerenciamento de prontuários de pacientes. O projeto oferece uma interface simples para profissionais e uma API REST, com dados persistidos em MySQL.

## Funcionalidades

- Login e encerramento de sessão.
- Cadastro de pacientes.
- Busca de prontuários por identificador ou parte do nome.
- Edição e exclusão de prontuários.
- Atualização e exibição da foto de perfil profissional.
- Fluxo de redefinição de senha.

## Tecnologias

- Java 21 e Spring Boot 3.5.5.
- Spring Web para páginas estáticas e API REST.
- Spring Data JPA e Hibernate para persistência.
- MySQL 8.
- Maven para dependências, build e testes.
- HTML, CSS e JavaScript para a interface.
- Docker para empacotamento e execução.
- GitHub Actions para build e publicação da imagem Docker.

## Requisitos

- JDK 21.
- MySQL 8.
- Docker (opcional, para executar em container).

## Configuração e execução local

Crie o banco de dados (ou deixe que a configuração `createDatabaseIfNotExist` o crie):

```sql
CREATE DATABASE db_prontuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

Configure as variáveis de ambiente para conexão. Os valores padrão são `localhost:3306`, banco `db_prontuarios`, usuário `root` e senha `admin`; recomenda-se definir credenciais próprias:

| Variável | Padrão |
|---|---|
| `DB_HOST` | `localhost` |
| `DB_PORT` | `3306` |
| `DB_NAME` | `db_prontuarios` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | `admin` |

Execute a aplicação usando o Maven Wrapper:

```sh
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação fica disponível em `http://localhost:8080/html/home.html`. O Hibernate está configurado com `ddl-auto: update` para atualizar o esquema do banco.

## API REST

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/pacientes` | Cadastra um paciente. |
| `GET` | `/api/pacientes/{termo}` | Busca por ID ou nome. |
| `PUT` | `/api/pacientes/{id}` | Atualiza nome e prontuário. |
| `DELETE` | `/api/pacientes/{id}` | Exclui um paciente. |
| `POST` | `/api` | Inicia sessão. |
| `PUT` | `/api/reset` | Redefine a senha. |
| `GET` | `/api/perfil/sessao` | Retorna os dados do profissional autenticado. |
| `GET` | `/api/perfil/foto` | Retorna a foto do perfil autenticado. |
| `POST` | `/api/perfil/foto` | Atualiza a foto do perfil autenticado. |
| `POST` | `/api/perfil/logout` | Encerra a sessão. |

A foto deve ser JPEG ou PNG válida, com até 5 MB e dimensões máximas de 4096 × 4096 pixels.

## Docker

#docker login
#docker build -t prontuario-app-2.2.2 .
#docker run -d -p 8080:8080 --name prontuario-app prontuario-app-2.2.2
#docker tag prontuario-app-2.2.2:latest uverdocker/prontuario-app:2.2.2
#docker push uverdocker/prontuario-app:2.2.2

#docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name ci-cd-jenkins jenkins/jenkins:lts

#gerar token de acesso pessoal no github: https://hub.docker.com/settings/security

O `Dockerfile` usa uma imagem Eclipse Temurin 21 e expõe a porta `8080`. Para gerar e executar a imagem localmente:

```sh
./mvnw clean package -DskipTests
docker build -t prontuario-app:local .
docker run --rm -p 8080:8080 -e DB_HOST=host.docker.internal -e DB_NAME=db_prontuarios -e DB_USER=root -e DB_PASSWORD=sua-senha prontuario-app:local
```

Ajuste `DB_HOST` e as credenciais conforme o ambiente do MySQL.

## CI/CD com GitHub Actions

O workflow [`.github/workflows/build.yml`](.github/workflows/build.yml) é executado em pushes e pull requests direcionados às branches `main` e `master`. Ele:

1. Obtém o código e configura o JDK 21 com cache do Maven.
2. Executa `./mvnw clean package -DskipTests`.
3. Configura Docker Buildx, autentica no Docker Hub e constrói/publica a imagem `uverdocker/prontuario-app:2.1.2`.

Configure os secrets `DOCKERHUB_USERNAME` e `DOCKERHUB_TOKEN` no repositório GitHub para autenticação no Docker Hub. O build do workflow pula os testes.

## Estrutura do projeto

```text
.
├── .github/workflows/build.yml   # Pipeline de CI/CD
├── Dockerfile                    # Imagem da aplicação
├── pom.xml                       # Dependências e configuração Maven
├── mvnw, mvnw.cmd                # Maven Wrapper
└── src
    ├── main
    │   ├── java/...              # Aplicação Spring Boot
    │   │   ├── controller/       # Endpoints REST
    │   │   ├── service/          # Regras de negócio
    │   │   ├── repository/      # Acesso a dados com JPA
    │   │   ├── model/            # Entidades persistidas
    │   │   └── dto/              # Objetos de requisição e resposta
    │   └── resources
    │       ├── application.yml   # Configuração da aplicação e banco
    │       └── static
    │           ├── html/         # Páginas
    │           ├── css/          # Tokens, estilos base, componentes e páginas
    │           └── js/           # Scripts da interface
    └── test/java/                # Testes automatizados
```

## Testes

Execute os testes automatizados com:

```sh
./mvnw test
```
