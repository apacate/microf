/*----------------------------------------------------------*/
/* Criacao de banco de dados */
CREATE DATABASE microf;

USE microf;

/*----------------------------------------------------------*/
/* Criacao de tabelas */

CREATE TABLE clientes (
    cliente_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    sobrenome VARCHAR(100),
    data_nascimento DATE,
    NUIT VARCHAR(14) UNIQUE,
    endereco VARCHAR(255),
    telefone VARCHAR(20),
    email VARCHAR(100),
    documento_de_ident VARCHAR(200),
    obs VARCHAR(500)
);

CREATE TABLE funcionarios (
    funcionario_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    sobrenome VARCHAR(100),
    cargo VARCHAR(100),
    salario DECIMAL(10,2),
    data_contratacao DATE,
    usuario VARCHAR(25),
    senha VARCHAR(25)
);

CREATE TABLE emprestimos (
    emprestimo_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    valor_emprestimo DECIMAL(10,2),
    data_contratacao DATE,
    FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id)
);

CREATE TABLE parcelas (
    parcela_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    emprestimo_id INT,
    numero_parcela INT,
    valor_parcela DECIMAL(10,2),
    data_vencimento DATE,
    STATUS ENUM('A TEMPO', 'PENDENTE', 'VENCIDO', 'PAGO') DEFAULT 'A TEMPO',
    valor_juro DECIMAL(10,2),
    valor_PagarPrestacao DECIMAL(10,2),
    FOREIGN KEY (emprestimo_id) REFERENCES emprestimos(emprestimo_id)
);
