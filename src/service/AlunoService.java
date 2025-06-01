package src.service;

import src.dao.AlunoDAO;
import src.model.Aluno;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de serviço para operações relacionadas a alunos.
 */
public class AlunoService {
    private AlunoDAO alunoDAO;

    public AlunoService() {
        try {
            this.alunoDAO = new AlunoDAO();
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar AlunoService: " + e.getMessage());
            throw new RuntimeException("Falha ao conectar ao banco de dados", e);
        }
    }

    /**
     * Cadastra um novo aluno.
     * @param aluno o aluno a ser cadastrado
     * @return o aluno cadastrado com ID gerado
     * @throws Exception se ocorrer um erro durante o cadastro
     */
    public Aluno cadastrarAluno(Aluno aluno) throws Exception {
        validarAluno(aluno);
        
        try {
            // Verifica se já existe um aluno com a mesma matrícula
            Aluno alunoExistente = alunoDAO.buscarPorMatricula(aluno.getMatricula());
            if (alunoExistente != null) {
                throw new Exception("Já existe um aluno cadastrado com a matrícula " + aluno.getMatricula());
            }
            
            return alunoDAO.inserir(aluno);
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
            throw new Exception("Falha ao cadastrar aluno: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um aluno existente.
     * @param aluno o aluno com os dados atualizados
     * @return true se a atualização foi bem-sucedida, false caso contrário
     * @throws Exception se ocorrer um erro durante a atualização
     */
    public boolean atualizarAluno(Aluno aluno) throws Exception {
        validarAluno(aluno);
        
        if (aluno.getId() <= 0) {
            throw new Exception("ID do aluno inválido para atualização");
        }
        
        try {
            // Verifica se o aluno existe
            Aluno alunoExistente = alunoDAO.buscarPorId(aluno.getId());
            if (alunoExistente == null) {
                throw new Exception("Aluno não encontrado com o ID " + aluno.getId());
            }
            
            // Verifica se a nova matrícula já está em uso por outro aluno
            if (!alunoExistente.getMatricula().equals(aluno.getMatricula())) {
                Aluno alunoComMesmaMatricula = alunoDAO.buscarPorMatricula(aluno.getMatricula());
                if (alunoComMesmaMatricula != null && alunoComMesmaMatricula.getId() != aluno.getId()) {
                    throw new Exception("Já existe outro aluno cadastrado com a matrícula " + aluno.getMatricula());
                }
            }
            
            return alunoDAO.atualizar(aluno);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
            throw new Exception("Falha ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um aluno pelo ID.
     * @param id o ID do aluno a ser removido
     * @return true se a remoção foi bem-sucedida, false caso contrário
     * @throws Exception se ocorrer um erro durante a remoção
     */
    public boolean removerAluno(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID do aluno inválido para remoção");
        }
        
        try {
            // Verifica se o aluno existe
            Aluno aluno = alunoDAO.buscarPorId(id);
            if (aluno == null) {
                throw new Exception("Aluno não encontrado com o ID " + id);
            }
            
            // TODO: Verificar se o aluno possui empréstimos ativos antes de remover
            
            return alunoDAO.remover(id);
        } catch (SQLException e) {
            System.err.println("Erro ao remover aluno: " + e.getMessage());
            throw new Exception("Falha ao remover aluno: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um aluno pelo ID.
     * @param id o ID do aluno a ser buscado
     * @return o aluno encontrado ou null se não existir
     * @throws Exception se ocorrer um erro durante a busca
     */
    public Aluno buscarAlunoPorId(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID do aluno inválido para busca");
        }
        
        try {
            return alunoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
            throw new Exception("Falha ao buscar aluno: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um aluno pela matrícula.
     * @param matricula a matrícula do aluno a ser buscado
     * @return o aluno encontrado ou null se não existir
     * @throws Exception se ocorrer um erro durante a busca
     */
    public Aluno buscarAlunoPorMatricula(String matricula) throws Exception {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new Exception("Matrícula inválida para busca");
        }
        
        try {
            return alunoDAO.buscarPorMatricula(matricula);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por matrícula: " + e.getMessage());
            throw new Exception("Falha ao buscar aluno: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os alunos cadastrados.
     * @return lista de alunos
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Aluno> listarTodosAlunos() throws Exception {
        try {
            return alunoDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
            throw new Exception("Falha ao listar alunos: " + e.getMessage(), e);
        }
    }

    /**
     * Busca alunos pelo nome (busca parcial).
     * @param nome parte do nome a ser buscado
     * @return lista de alunos que contêm o nome buscado
     * @throws Exception se ocorrer um erro durante a busca
     */
    public List<Aluno> buscarAlunosPorNome(String nome) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome inválido para busca");
        }
        
        try {
            return alunoDAO.buscarPorNome(nome);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
            throw new Exception("Falha ao buscar alunos: " + e.getMessage(), e);
        }
    }

    /**
     * Valida os dados de um aluno.
     * @param aluno o aluno a ser validado
     * @throws Exception se os dados do aluno forem inválidos
     */
    private void validarAluno(Aluno aluno) throws Exception {
        if (aluno == null) {
            throw new Exception("Aluno não pode ser nulo");
        }
        
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new Exception("Nome do aluno é obrigatório");
        }
        
        if (aluno.getMatricula() == null || aluno.getMatricula().trim().isEmpty()) {
            throw new Exception("Matrícula do aluno é obrigatória");
        }
        
        // Validações adicionais podem ser implementadas conforme necessário
    }
}