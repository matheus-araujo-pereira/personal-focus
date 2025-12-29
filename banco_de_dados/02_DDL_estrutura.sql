-- DDL - DATA DEFINITION LANGUAGE
-- Objetivo: Criar a estrutura física das tabelas e relacionamentos.

-- Limpeza (Caso precise refazer o banco)
DROP TABLE IF EXISTS tb_historico_treino;
DROP TABLE IF EXISTS tb_exercicio;
DROP TABLE IF EXISTS tb_treino;
DROP TABLE IF EXISTS tb_usuario;

CREATE TABLE tb_usuario (
    id_usuario SERIAL PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE, -- Login único
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('PERSONAL', 'ALUNO')),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_treino (
    id_treino SERIAL PRIMARY KEY,
    dia_semana VARCHAR(15) NOT NULL CHECK (dia_semana IN ('DOMINGO', 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO')),
    nome_treino VARCHAR(100) NOT NULL,
    descricao TEXT,
    id_aluno INTEGER NOT NULL,
    
    CONSTRAINT fk_treino_aluno FOREIGN KEY (id_aluno) REFERENCES tb_usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT unq_treino_dia_aluno UNIQUE (id_aluno, dia_semana)
);

CREATE TABLE tb_exercicio (
    id_exercicio SERIAL PRIMARY KEY,
    nome_exercicio VARCHAR(100) NOT NULL,
    repeticoes VARCHAR(50) NOT NULL, 
    carga_kg DECIMAL(5,2),
    id_treino INTEGER NOT NULL,

    CONSTRAINT fk_exercicio_treino FOREIGN KEY (id_treino) REFERENCES tb_treino(id_treino) ON DELETE CASCADE
);

CREATE TABLE tb_historico_treino (
    id_historico SERIAL PRIMARY KEY,
    data_finalizacao DATE NOT NULL DEFAULT CURRENT_DATE,
    id_aluno INTEGER NOT NULL,
    id_treino INTEGER NOT NULL,
    
    CONSTRAINT fk_historico_aluno FOREIGN KEY (id_aluno) REFERENCES tb_usuario(id_usuario),
    CONSTRAINT fk_historico_treino FOREIGN KEY (id_treino) REFERENCES tb_treino(id_treino)
);