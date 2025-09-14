# 🩺 PacienteApp

Aplicação web desenvolvida com **Spring Boot 3.5.5**, **Java 21** e **MongoDB**, utilizando o padrão **MVC**. O sistema permite o cadastro, busca, atualização e visualização de prontuários médicos de pacientes, com interface web simples em HTML, CSS e JavaScript.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data MongoDB**
- **MongoDB**
- **HTML, CSS, JS** (frontend estático em `/static`)
- **Maven**

---

## 📁 Estrutura do Projeto

src/
└── main/
├── java/
│   └── com/
│       └── seuprojeto/
│           ├── controller/
│           ├── service/
│           ├── model/
│           ├── dto/
│           └── repository/
└── resources/
├── static/
│   ├── css/
│   ├── js/
│   └── html/
└── application.properties

---

## 🧠 Funcionalidades

- 🔍 **Buscar paciente** por ID ou parte do nome
- 📝 **Visualizar prontuário** e data/hora da última modificação
- ✏️ **Atualizar prontuário**
- ➕ **Cadastrar novo paciente**

---

## 🗃️ Modelo de Dados

**Paciente**
- `id` (String, tamanho 3)
- `nome` (String, até 50 caracteres)
- `prontuario` (String, até 5000 caracteres)
- `ultimaAtualizacao` (Data e hora da última modificação)

---

## 📦 Requisitos

- Java 21 
- MongoDB em execução local (`mongodb://localhost:27017/seubanco`)
- Maven

---

## ▶️ Como Executar

1. Clone o repositório:
   via bash
   git clone https://github.com/seuusuario/paciente-app.git
   cd paciente-app
   
2. Execute o projeto:
   mvn spring-boot:run

3. Acessa a interface web:
   http://localhost:8080/html/home.html

📄 API REST
POST /api/pacientes
Cria um novo paciente.
GET /api/pacientes/{termo}
Busca paciente por ID ou parte do nome.
PUT /api/pacientes/{id}
Atualiza os dados de um paciente existente.

💡 Observações

- A interface web está localizada em src/main/resources/static/html/index.html.
- A aplicação não utiliza frameworks JS externos, apenas JavaScript puro.


🛠️ Autor
Projeto desenvolvido por Uverlanio Mauricio — sinta-se à vontade para contribuir ou sugerir melhorias!
