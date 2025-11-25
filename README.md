# 🍕 Delivery App - Sistema de Pedidos Online

Um sistema completo de delivery desenvolvido em **Java** que permite clientes fazer pedidos em restaurantes e acompanhar suas entregas. O projeto utiliza **JavaFX** para a interface gráfica e **MySQL** como banco de dados.

---

## 📋 Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Requisitos do Sistema](#requisitos-do-sistema)
- [Instalação](#instalação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Usar](#como-usar)
- [Arquitetura](#arquitetura)
- [Banco de Dados](#banco-de-dados)

---

## 📱 Sobre o Projeto

O **Delivery App** é uma aplicação desktop que simula um sistema de delivery similar ao iFood. O sistema foi desenvolvido seguindo o padrão **MVC (Model-View-Controller)** e possui:

- **Autenticação de usuários** (clientes e restaurantes)
- **Gerenciamento de produtos** por restaurante
- **Criação e acompanhamento de pedidos**
- **Interface gráfica intuitiva** com JavaFX

---

## ⚡ Funcionalidades

### Para Clientes
✅ Cadastro e login de clientes  
✅ Visualizar restaurantes disponíveis  
✅ Visualizar cardápio de produtos  
✅ Adicionar produtos ao carrinho  
✅ Criar pedidos  
✅ Acompanhar status do pedido em tempo real  

### Para Restaurantes
✅ Cadastro de restaurante  
✅ Gerenciar cardápio (adicionar/editar/remover produtos)  
✅ Visualizar pedidos recebidos  
✅ Atualizar status dos pedidos (Recebido → Preparando → Pronto → Entregue)  

### Geral
✅ Persistência de dados em banco de dados MySQL  
✅ Tratamento robusto de erros  
✅ Interface responsiva e amigável  

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Java** | 23 | Linguagem de programação principal |
| **JavaFX** | - | Framework para interface gráfica |
| **MySQL** | 5.7+ | Banco de dados relacional |
| **Maven** | 3.6+ | Gerenciador de dependências e build |
| **JDBC** | - | Acesso ao banco de dados |

---

## 🔧 Requisitos do Sistema

- ☕ **Java 23** ou superior instalado
- 🗄️ **MySQL 5.7** ou superior
- 📦 **Maven 3.6** ou superior
- 💾 Mínimo 500 MB de espaço em disco

---

## 📥 Instalação

### 1. Clonar o Repositório
```bash
git clone https://github.com/naok1m/delivery-app.git
cd delivery-app
```

### 2. Configurar o Banco de Dados
Execute o script SQL para criar as tabelas:
```bash
mysql -u root -p < query/IFOODBANCO.sql
```

Edite `src/dao/ConnectionFactory.java` e configure as credenciais do MySQL:
```java
private static final String URL = "jdbc:mysql://localhost:3306/delivery_db";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 3. Compilar o Projeto
```bash
mvn clean compile
```

### 4. Executar a Aplicação
```bash
mvn javafx:run
```

---

## 📂 Estrutura do Projeto

```
delivery-app/
├── src/
│   ├── Main.java                          # Ponto de entrada da aplicação
│   ├── TestConnection.java                # Teste de conexão com BD
│   ├── dao/                               # Data Access Objects
│   │   ├── ClienteDAO.java
│   │   ├── RestauranteDAO.java
│   │   ├── ProdutoDAO.java
│   │   ├── PedidoDAO.java
│   │   ├── ItemPedidoDAO.java
│   │   ├── LoginDAO.java
│   │   └── ConnectionFactory.java         # Gerenciador de conexões
│   ├── model/                             # Classes de modelo
│   │   ├── Cliente.java
│   │   ├── Restaurante.java
│   │   ├── Produto.java
│   │   ├── Pedido.java
│   │   ├── ItemPedido.java
│   │   └── Login.java
│   ├── service/                           # Lógica de negócio
│   │   ├── ClienteService.java
│   │   ├── RestauranteService.java
│   │   ├── ProdutoService.java
│   │   ├── PedidoService.java
│   │   └── LoginService.java
│   └── ui/                                # Interface gráfica (JavaFX)
│       ├── MainApp.java                   # Aplicação principal JavaFX
│       ├── LoginController.java
│       ├── CadastroController.java
│       ├── CadastroRestauranteController.java
│       ├── DeliveryUIController.java
│       ├── RestauranteController.java
│       ├── RestaurantePrincipalController.java
│       ├── LoginView.fxml
│       ├── CadastroView.fxml
│       ├── CadastroRestaurante.fxml
│       ├── DeliveryView.fxml
│       ├── RestauranteView.fxml
│       └── RestaurantePrincipalView.fxml
├── query/
│   └── IFOODBANCO.sql                     # Script de criação do banco
├── pom.xml                                # Configuração do Maven
└── README.md                              # Este arquivo
```

---

## 🚀 Como Usar

### 1. Primeira Execução
Ao abrir a aplicação, você será direcionado para a tela de login:

**Cliente:**
- Clique em "Novo Cliente"
- Preencha os dados (nome, endereço, telefone)
- Faça login com suas credenciais

**Restaurante:**
- Clique em "Restaurante"
- Registre seu restaurante com nome, telefone e tipo de culinária
- Gerencie seu cardápio

### 2. Fazer um Pedido (Cliente)
1. Login como cliente
2. Selecione um restaurante
3. Escolha produtos do cardápio
4. Adicione ao carrinho
5. Finalize o pedido
6. Acompanhe o status em tempo real

### 3. Gerenciar Restaurante
1. Login como restaurante
2. Visualize e atualize produtos
3. Veja pedidos recebidos
4. Altere status dos pedidos (Recebido → Preparando → Pronto → Entregue)

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)**:

```
┌─────────────────────────────────────────┐
│           Camada de Apresentação        │
│    (UI - JavaFX - Controllers + FXML)   │
├─────────────────────────────────────────┤
│          Camada de Negócio              │
│      (Service Classes - Lógica)         │
├─────────────────────────────────────────┤
│       Camada de Persistência            │
│      (DAO - Acesso ao Banco)            │
├─────────────────────────────────────────┤
│          Camada de Modelo               │
│     (Model Classes - Entidades)         │
├─────────────────────────────────────────┤
│          Banco de Dados                 │
│            (MySQL)                      │
└─────────────────────────────────────────┘
```

**Fluxo de Dados:**
1. Usuário interage com a UI (FXML)
2. Controller captura a ação
3. Service processa a lógica de negócio
4. DAO executa operações no banco
5. Dados retornam à UI

---

## 🗄️ Banco de Dados

### Tabelas Principais

**clientes**
- id_cliente
- nome
- endereco
- telefone

**restaurantes**
- id_restaurante
- nome
- telefone
- tipo_culinaria

**produtos**
- id_produto
- id_restaurante
- nome
- descricao
- preco
- ativo

**pedidos**
- id_pedido
- id_cliente
- id_restaurante
- data_hora
- status
- valor_total

**itens_pedido**
- id_item
- id_pedido
- descricao
- quantidade
- subtotal

Execute `query/IFOODBANCO.sql` para criar todas as tabelas com suas relações.

---

## 📝 Status do Pedido

Os pedidos passam pelos seguintes estados:

| Status | Descrição |
|--------|-----------|
| 🔴 RECEBIDO | Pedido foi recebido pelo restaurante |
| 🟠 PREPARANDO | Restaurante está preparando o pedido |
| 🟡 PRONTO | Pedido pronto para retirada/entrega |
| 🟢 ENTREGUE | Pedido entregue ao cliente |

---

## 🐛 Solução de Problemas

### Erro de Conexão com Banco
- Verifique se MySQL está rodando
- Confirme as credenciais em `ConnectionFactory.java`
- Verifique se o banco `delivery_db` foi criado

### Erro de Compilação
```bash
mvn clean compile
```

### Interface não carrega
- Certifique-se que JavaFX está instalado
- Atualize as dependências: `mvn dependency:resolve`

---

## 👨‍💻 Autores

- **Naok1m** ([@Naok1m](https://github.com/naok1m)) - Desenvolvedor principal
- **MauricioOliveiraAmorim** ([@MauricioOliveiraAmorim](https://github.com/MauricioOliveiraAmorim)) - Colaborador

---

## 🔗 Repositórios Relacionados

Este projeto é parte de uma série de projetos desenvolvidos para a disciplina:

- 📦 **Delivery App** - [github.com/naok1m/delivery-app](https://github.com/naok1m/delivery-app) ← Você está aqui
- 🏫 **Academia** - [github.com/MauricioOliveiraAmorim/Academia](https://github.com/MauricioOliveiraAmorim/Academia)

---

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE)

---

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para:
- Reportar bugs
- Sugerir novas funcionalidades
- Fazer pull requests

---

**Última atualização:** Novembro 2025
