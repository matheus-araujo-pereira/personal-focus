-- DCL - DATA CONTROL LANGUAGE
-- Objetivo: Criar um usuário dedicado para a aplicação e dar permissões.

-- 1. Cria o Role (Usuário) do sistema
CREATE USER app_personal_focus WITH PASSWORD 'senha_segura_123';

-- 2. Cria o Banco de Dados (Se ainda não existir)
-- Nota: Após rodar este comando, você deve desconectar e conectar no banco 'personal_focus_db'
CREATE DATABASE personal_focus_db;

-- 3. Concede permissões (Executar conectado no banco personal_focus_db)
GRANT ALL PRIVILEGES ON DATABASE personal_focus_db TO app_personal_focus;
GRANT ALL ON ALL TABLES IN SCHEMA public TO app_personal_focus;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO app_personal_focus;