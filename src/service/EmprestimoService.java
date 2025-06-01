package src.service;

import src.dao.EmprestimoDAO;
import src.model.Aluno;
import src.model.Emprestimo;
import src.model.Livro;
import src.model.Emprestimo.StatusEmprestimo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Classe de serviço para operações relacionadas a empréstimos.
 */
public class EmprestimoService {
    private EmprestimoDAO emprestimoDAO;
    private AlunoService alunoService;
    private LivroService livroService;
    
    // Configurações de empréstimo
    private static final int DIAS_EMPRESTIMO_PADRAO = 14; // 2 semanas

    public EmprestimoService() {
        try {
            this.emprestimoDAO = new EmprestimoDAO();
            this.alunoService = new AlunoService();
            this.livroService = new LivroService();
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar EmprestimoService: " + e.getMessage());
            throw new RuntimeException("Falha ao conectar ao banco de dados", e);
        }
    }

    /**
     * Realiza um novo empréstimo.
     * @param alunoId o ID do aluno
     * @param livroId o ID do livro
     * @param diasEmprestimo número de dias para o empréstimo (opcional, usa o padrão se não informado)
     * @return o empréstimo realizado
     * @throws Exception se ocorrer um erro durante o empréstimo
     */
    public Emprestimo realizarEmprestimo(int alunoId, int livroId, Integer diasEmprestimo) throws Exception {
        if (alunoId <= 0) {
            throw new Exception("ID do aluno inválido");
        }
        
        if (livroId <= 0) {
            throw new Exception("ID do livro inválido");
        }
        
        // Se não informado, usa o período padrão
        int dias = (diasEmprestimo != null && diasEmprestimo > 0) ? diasEmprestimo : DIAS_EMPRESTIMO_PADRAO;
        
        try {
            // Verifica se o aluno existe
            Aluno aluno = alunoService.buscarAlunoPorId(alunoId);
            if (aluno == null) {
                throw new Exception("Aluno não encontrado com o ID " + alunoId);
            }
            
            // Verifica se o livro existe e está disponível
            Livro livro = livroService.buscarLivroPorId(livroId);
            if (livro == null) {
                throw new Exception("Livro não encontrado com o ID " + livroId);
            }
            
            if (!livro.isDisponivel()) {
                throw new Exception("Livro não disponível para empréstimo");
            }
            
            // Cria o empréstimo
            Timestamp dataEmprestimo = new Timestamp(System.currentTimeMillis());
            Timestamp dataDevolucaoPrevista = calcularDataDevolucao(dataEmprestimo, dias);
            
            Emprestimo emprestimo = new Emprestimo(alunoId, livroId, dataEmprestimo, dataDevolucaoPrevista);
            emprestimo.setStatus(StatusEmprestimo.ATIVO);
            
            // Salva o empréstimo no banco de dados
            return emprestimoDAO.inserir(emprestimo);
        } catch (SQLException e) {
            System.err.println("Erro ao realizar empréstimo: " + e.getMessage());
            throw new Exception("Falha ao realizar empréstimo: " + e.getMessage(), e);
        }
    }

    /**
     * Registra a devolução de um livro.
     * @param emprestimoId o ID do empréstimo
     * @return o empréstimo atualizado
     * @throws Exception se ocorrer um erro durante a devolução
     */
    public Emprestimo registrarDevolucao(int emprestimoId) throws Exception {
        if (emprestimoId <= 0) {
            throw new Exception("ID do empréstimo inválido");
        }
        
        try {
            // Verifica se o empréstimo existe
            Emprestimo emprestimo = emprestimoDAO.buscarPorId(emprestimoId);
            if (emprestimo == null) {
                throw new Exception("Empréstimo não encontrado com o ID " + emprestimoId);
            }
            
            // Verifica se o empréstimo já foi devolvido
            if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
                throw new Exception("Este livro já foi devolvido");
            }
            
            // Registra a devolução
            Timestamp dataDevolucao = new Timestamp(System.currentTimeMillis());
            return emprestimoDAO.registrarDevolucao(emprestimoId, dataDevolucao);
        } catch (SQLException e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
            throw new Exception("Falha ao registrar devolução: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um empréstimo pelo ID.
     * @param id o ID do empréstimo a ser buscado
     * @return o empréstimo encontrado ou null se não existir
     * @throws Exception se ocorrer um erro durante a busca
     */
    public Emprestimo buscarEmprestimoPorId(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID do empréstimo inválido para busca");
        }
        
        try {
            return emprestimoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empréstimo por ID: " + e.getMessage());
            throw new Exception("Falha ao buscar empréstimo: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os empréstimos.
     * @return lista de empréstimos
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Emprestimo> listarTodosEmprestimos() throws Exception {
        try {
            return emprestimoDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos: " + e.getMessage());
            throw new Exception("Falha ao listar empréstimos: " + e.getMessage(), e);
        }
    }

    /**
     * Lista empréstimos ativos (não devolvidos).
     * @return lista de empréstimos ativos
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Emprestimo> listarEmprestimosAtivos() throws Exception {
        try {
            return emprestimoDAO.listarAtivos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos ativos: " + e.getMessage());
            throw new Exception("Falha ao listar empréstimos ativos: " + e.getMessage(), e);
        }
    }

    /**
     * Lista empréstimos atrasados.
     * @return lista de empréstimos atrasados
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Emprestimo> listarEmprestimosAtrasados() throws Exception {
        try {
            return emprestimoDAO.listarAtrasados();
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos atrasados: " + e.getMessage());
            throw new Exception("Falha ao listar empréstimos atrasados: " + e.getMessage(), e);
        }
    }

    /**
     * Lista empréstimos de um aluno.
     * @param alunoId o ID do aluno
     * @return lista de empréstimos do aluno
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Emprestimo> listarEmprestimosPorAluno(int alunoId) throws Exception {
        if (alunoId <= 0) {
            throw new Exception("ID do aluno inválido para busca");
        }
        
        try {
            // Verifica se o aluno existe
            Aluno aluno = alunoService.buscarAlunoPorId(alunoId);
            if (aluno == null) {
                throw new Exception("Aluno não encontrado com o ID " + alunoId);
            }
            
            return emprestimoDAO.listarPorAluno(alunoId);
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos por aluno: " + e.getMessage());
            throw new Exception("Falha ao listar empréstimos por aluno: " + e.getMessage(), e);
        }
    }

    /**
     * Lista empréstimos de um livro.
     * @param livroId o ID do livro
     * @return lista de empréstimos do livro
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Emprestimo> listarEmprestimosPorLivro(int livroId) throws Exception {
        if (livroId <= 0) {
            throw new Exception("ID do livro inválido para busca");
        }
        
        try {
            // Verifica se o livro existe
            Livro livro = livroService.buscarLivroPorId(livroId);
            if (livro == null) {
                throw new Exception("Livro não encontrado com o ID " + livroId);
            }
            
            return emprestimoDAO.listarPorLivro(livroId);
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos por livro: " + e.getMessage());
            throw new Exception("Falha ao listar empréstimos por livro: " + e.getMessage(), e);
        }
    }

    /**
     * Calcula a data prevista de devolução com base na data de empréstimo e no número de dias.
     * @param dataEmprestimo a data de empréstimo
     * @param dias o número de dias para o empréstimo
     * @return a data prevista de devolução
     */
    private Timestamp calcularDataDevolucao(Timestamp dataEmprestimo, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataEmprestimo.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return new Timestamp(calendar.getTimeInMillis());
    }
}