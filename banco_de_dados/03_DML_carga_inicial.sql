-- DML - DATA MANIPULATION LANGUAGE
-- Objetivo: Popular o banco com dados de teste.

INSERT INTO tb_usuario (cpf, senha, nome, data_nascimento, perfil) 
VALUES ('000.000.000-00', 'admin123', 'Carlos Personal', '1985-05-20', 'PERSONAL');

INSERT INTO tb_usuario (cpf, senha, nome, data_nascimento, perfil) 
VALUES 
('111.111.111-11', 'aluno123', 'João Frango', '1998-01-10', 'ALUNO'),
('222.222.222-22', 'aluno123', 'Maria Fitness', '1995-07-15', 'ALUNO');

INSERT INTO tb_treino (dia_semana, nome_treino, descricao, id_aluno)
VALUES 
('SEGUNDA', 'Treino A - Peito e Tríceps', 'Foco em hipertrofia', (SELECT id_usuario FROM tb_usuario WHERE cpf='111.111.111-11')),
('QUARTA', 'Treino B - Costas e Bíceps', 'Foco em força', (SELECT id_usuario FROM tb_usuario WHERE cpf='111.111.111-11'));

INSERT INTO tb_exercicio (nome_exercicio, repeticoes, carga_kg, id_treino)
VALUES 
('Supino Reto', '4x10', 60.0, (SELECT id_treino FROM tb_treino WHERE nome_treino='Treino A - Peito e Tríceps')),
('Crucifixo', '3x12', 14.0, (SELECT id_treino FROM tb_treino WHERE nome_treino='Treino A - Peito e Tríceps')),
('Corrida Esteira', '15min', 0.0, (SELECT id_treino FROM tb_treino WHERE nome_treino='Treino A - Peito e Tríceps'));

INSERT INTO tb_historico_treino (data_finalizacao, id_aluno, id_treino)
VALUES 
('2023-10-25', 
 (SELECT id_usuario FROM tb_usuario WHERE cpf='111.111.111-11'),
 (SELECT id_treino FROM tb_treino WHERE nome_treino='Treino A - Peito e Tríceps')
);