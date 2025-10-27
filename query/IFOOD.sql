-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS Ifood;
USE Ifood;

-- Criar a tabela de cliente
CREATE TABLE Cliente (
 ID_CLiente INT AUTO_INCREMENT PRIMARY KEY,  -- KEY
 Nome VARCHAR(100) NOT NULL,
 Telefone VARCHAR(15) NOT NULL,
 Endereço VARCHAR(100) NOT NULL
);

-- Criar a tabela de Restaurante
CREATE TABLE Restaurante (
 ID_Restaurante INT AUTO_INCREMENT PRIMARY KEY,  -- KEY
 Nome VARCHAR(50) NOT NULL,
 Tipo_cozinha VARCHAR(50) NOT NULL,
 Telefone VARCHAR(15) NOT NULL
);

-- Criar a tabela de Pedido
CREATE TABLE Pedido (
 ID_Pedido INT AUTO_INCREMENT PRIMARY KEY,  -- KEY
 Cliente int NOT NULL,	 -- id cliente
 Restaurante int NOT NULL,  -- id restaurante
 DataHora datetime NOT NULL,
 Stats enum("Em Preparo" , "A Caminho" , "Entregue") NOT NULL,
 FOREIGN KEY (Cliente) REFERENCES Cliente(ID_Cliente) ON DELETE CASCADE,  -- REFERENCIA A OUTRA KEY
 FOREIGN KEY (Restaurante) REFERENCES Restaurante(ID_Restaurante) ON DELETE CASCADE  -- REFERENCIA A OUTRA KEY
);

-- Criar a tabela de ItemPedido
CREATE TABLE ItemPedido (
 ID_Item INT AUTO_INCREMENT PRIMARY KEY, -- KEY
 Pedido INT NOT NULL,  -- id pedido
 Descrição VARCHAR(100),
 Quantidade INT NOT NULL,
 Valor DECIMAL(10,2) NOT NULL,
 FOREIGN KEY (Pedido) REFERENCES Pedido(ID_Pedido) ON DELETE CASCADE  -- REFERENCIA A OUTRA KEY
);

USE Ifood;

CREATE TABLE Produto (
                         ID INT AUTO_INCREMENT PRIMARY KEY,
                         Nome VARCHAR(100) NOT NULL,
                         Valor DECIMAL(10, 2) NOT NULL,
                         Restaurante INT NOT NULL,
                         FOREIGN KEY (Restaurante) REFERENCES Restaurante(ID_Restaurante) ON DELETE CASCADE
);
