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

## Interface e foto de perfil

A interface fica em `src/main/resources/static`, organizada por responsabilidade:

- `html/`: páginas do sistema.
- `css/tokens.css`: cores e valores reutilizáveis; `base.css`, `components.css` e `pages.css` concentram estilos globais, componentes e layouts de página.
- `js/`: scripts das páginas e funcionalidades compartilhadas, como autenticação e perfil.

Após entrar, o profissional pode selecionar uma foto JPEG ou PNG de até 5 MB pelo botão **Alterar foto**. A imagem é armazenada na tabela `perfil_profissional`, separada dos dados de autenticação, e aparece nas páginas autenticadas; até o primeiro envio, o avatar exibe as iniciais.

## API REST

- `POST /api/pacientes`: cria um paciente.
- `GET /api/pacientes/{termo}`: busca paciente.
- `PUT /api/pacientes/{id}`: atualiza um paciente.
- `POST /api/perfil/foto`: envia a foto do perfil da sessão autenticada.
- `GET /api/perfil/foto`: retorna a foto do perfil da sessão autenticada.
- `POST /api/perfil/logout`: encerra a sessão.

Os arquivos da interface ficam em `src/main/resources/static`.

## Docker
#docker login
#docker build -t prontuario-app-2.1.2 .
#docker run -d -p 8080:8080 --name prontuario-app prontuario-app-2.1.2
#docker tag prontuario-app-2.1.2:latest uverdocker/prontuario-app:2.1.2
#docker push uverdocker/prontuario-app:2.1.2

#docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name ci-cd-jenkins jenkins/jenkins:lts

#gerar token de acesso pessoal no github: https://hub.docker.com/settings/security
