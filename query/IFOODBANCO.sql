CREATE DATABASE IF NOT EXISTS Ifood;
USE Ifood;


-- Tabela de Cliente
CREATE TABLE Cliente (
 ID_CLiente INT AUTO_INCREMENT PRIMARY KEY,
 Nome VARCHAR(100) NOT NULL,
 Telefone VARCHAR(15) NOT NULL,
 Endereço VARCHAR(100) NOT NULL
);

-- Tabela de Restaurante
CREATE TABLE Restaurante (
 ID_Restaurante INT AUTO_INCREMENT PRIMARY KEY,
 Nome VARCHAR(50) NOT NULL,
 TipoCozinha VARCHAR(50) NOT NULL,
 Telefone VARCHAR(15) NOT NULL
);

-- Tabela de Pedido
CREATE TABLE Pedido (
 ID_Pedido INT AUTO_INCREMENT PRIMARY KEY,
 Cliente int NOT NULL,
 Restaurante int NOT NULL,
 DataHora varchar(19) NOT NULL,
 Stats enum("Em Preparo" , "A Caminho" , "Entregue") NOT NULL,
 FOREIGN KEY (Cliente) REFERENCES Cliente(ID_Cliente) ON DELETE CASCADE,
 FOREIGN KEY (Restaurante) REFERENCES Restaurante(ID_Restaurante) ON DELETE CASCADE
);

-- Tabela de ItemPedido
CREATE TABLE ItemPedido (
 ID_Item INT AUTO_INCREMENT PRIMARY KEY,
 Pedido INT NOT NULL,
 Descrição VARCHAR(100),
 Quantidade INT NOT NULL,
 Valor DECIMAL(10,2) NOT NULL,
 FOREIGN KEY (Pedido) REFERENCES Pedido(ID_Pedido) ON DELETE CASCADE
);

-- Tabela de Produto
CREATE TABLE Produto (
ID_Produto INT AUTO_INCREMENT PRIMARY KEY,
Nome VARCHAR(100) NOT NULL,
Valor DECIMAL(10, 2) NOT NULL,
Restaurante INT NOT NULL,
Disponivel boolean not null,
Descricao varchar(100),
FOREIGN KEY (Restaurante) REFERENCES Restaurante(ID_Restaurante) ON DELETE CASCADE
);

-- Tabela de Login
CREATE TABLE Login (
ID_Login INT AUTO_INCREMENT PRIMARY KEY,
Email VARCHAR(100) NOT NULL,
Senha VARCHAR(100) NOT NULL,
TipoUsuario ENUM("Cliente", "Restaurante") NOT NULL,
Referencia INT NOT NULL -- ID da tabela Cliente ou Restaurante
);


-- INSERTS NA TABELA CLIENTE
INSERT INTO Cliente (Nome, Telefone, Endereço) VALUES
('Ana Silva', '(11) 98765-4321', 'Rua das Flores, 100, São Paulo'),
('Bruno Costa', '(21) 99887-7665', 'Av. Atlântica, 500, Rio de Janeiro'),
('Carla Souza', '(31) 97654-3210', 'Rua da Paz, 30, Belo Horizonte');

-- INSERTS NA TABELA RESTAURANTE
INSERT INTO Restaurante (Nome, TipoCozinha, Telefone) VALUES
('Pizzaria Fantástica', 'Italiana', '(11) 5555-1000'),    -- ID 1
('Churrascaria Grelha Quente', 'Churrasco', '(21) 4444-2000'), -- ID 2
('Temaki Lovers', 'Japonesa', '(31) 3333-3000'),           -- ID 3
('Sabor da Terra', 'Brasileira', '(11) 2222-4000');       -- ID 4

-- INSERTS NA TABELA LOGIN
-- Cliente 1 (Ana Silva)
INSERT INTO Login (Email, Senha, TipoUsuario, Referencia) VALUES
('ana.silva@teste.com', 'senha123', 'Cliente', 1);
-- Cliente 2 (Bruno Costa)
INSERT INTO Login (Email, Senha, TipoUsuario, Referencia) VALUES
('bruno.costa@teste.com', 'senha123', 'Cliente', 2);
-- Restaurante 1 (Pizzaria Fantástica)
INSERT INTO Login (Email, Senha, TipoUsuario, Referencia) VALUES
('pizza.fantastica@teste.com', 'rest123', 'Restaurante', 1);
-- Restaurante 2 (Churrascaria Grelha Quente)
INSERT INTO Login (Email, Senha, TipoUsuario, Referencia) VALUES
('grelha.quente@teste.com', 'rest123', 'Restaurante', 2);


-- INSERTS NA TABELA PRODUTO
-- Produtos do Restaurante 1 (Pizzaria Fantástica)
INSERT INTO Produto (Restaurante, Nome, Descricao, Valor, Disponivel) VALUES
(1, 'Pizza Mussarela', 'Mussarela, tomate e orégano', 45.00, TRUE), -- ID 1
(1, 'Pizza Calabresa', 'Calabresa, cebola e azeitonas', 50.00, TRUE), -- ID 2
(1, 'Refrigerante 2L', 'Coca-Cola 2 Litros', 12.00, TRUE); -- ID 3

-- Produtos do Restaurante 2 (Churrascaria Grelha Quente)
INSERT INTO Produto (Restaurante, Nome, Descricao, Valor, Disponivel) VALUES
(2, 'Picanha na Grelha', 'Picanha fatiada com arroz e vinagrete', 89.90, TRUE), -- ID 4
(2, 'Cerveja Long Neck', 'Cerveja Heineken Long Neck', 9.50, FALSE); -- ID 5 (Indisponível)

-- Produtos do Restaurante 3 (Temaki Lovers)
INSERT INTO Produto (Restaurante, Nome, Descricao, Valor, Disponivel) VALUES
(3, 'Temaki Salmão Completo', 'Salmão, cream cheese e cebolinha', 32.50, TRUE), -- ID 6
(3, 'Hot Roll', 'Rolo empanado de salmão e kani', 25.00, TRUE); -- ID 7


-- INSERTS NA TABELA PEDIDO
-- Pedido 1: Cliente 1 (Ana) na Pizzaria (ID 1) - Entregue
INSERT INTO Pedido (Cliente, Restaurante, DataHora, Stats) VALUES
(1, 1, '2025-10-27 20:00:00', 'Entregue'); -- ID 1

-- Pedido 2: Cliente 2 (Bruno) na Churrascaria (ID 2) - Em Preparo
INSERT INTO Pedido (Cliente, Restaurante, DataHora, Stats) VALUES
(2, 2, '2025-10-28 13:15:00', 'Em Preparo'); -- ID 2

-- Pedido 3: Cliente 1 (Ana) no Temaki (ID 3) - A Caminho
INSERT INTO Pedido (Cliente, Restaurante, DataHora, Stats) VALUES
(1, 3, '2025-10-28 19:45:00', 'A Caminho'); -- ID 3


-- INSERTS NA TABELA ITEMPEDIDO
-- Itens para Pedido 1 (Pizza)
INSERT INTO ItemPedido (Pedido, Descrição, Quantidade, Valor) VALUES
(1, 'Pizza Mussarela', 1, 45.00),
(1, 'Refrigerante 2L', 1, 12.00);

-- Itens para Pedido 2 (Churrasco)
INSERT INTO ItemPedido (Pedido, Descrição, Quantidade, Valor) VALUES
(2, 'Picanha na Grelha', 1, 89.90);

-- Itens para Pedido 3 (Temaki)
INSERT INTO ItemPedido (Pedido, Descrição, Quantidade, Valor) VALUES
(3, 'Temaki Salmão Completo', 2, 32.50),
(3, 'Hot Roll', 1, 25.00);
