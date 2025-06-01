package src.service;

import src.dao.LivroDAO;
import src.model.Livro;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de serviço para operações relacionadas a livros.
 */
public class LivroService {
    private LivroDAO livroDAO;

    public LivroService() {
        try {
            this.livroDAO = new LivroDAO();
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar LivroService: " + e.getMessage());
            throw new RuntimeException("Falha ao conectar ao banco de dados", e);
        }
    }

    /**
     * Cadastra um novo livro.
     * @param livro o livro a ser cadastrado
     * @return o livro cadastrado com ID gerado
     * @throws Exception se ocorrer um erro durante o cadastro
     */
    public Livro cadastrarLivro(Livro livro) throws Exception {
        validarLivro(livro);
        
        try {
            // Verifica se já existe um livro com o mesmo ISBN (se fornecido)
            if (livro.getIsbn() != null && !livro.getIsbn().trim().isEmpty()) {
                Livro livroExistente = livroDAO.buscarPorIsbn(livro.getIsbn());
                if (livroExistente != null) {
                    throw new Exception("Já existe um livro cadastrado com o ISBN " + livro.getIsbn());
                }
            }
            
            return livroDAO.inserir(livro);
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar livro: " + e.getMessage());
            throw new Exception("Falha ao cadastrar livro: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um livro existente.
     * @param livro o livro com os dados atualizados
     * @return true se a atualização foi bem-sucedida, false caso contrário
     * @throws Exception se ocorrer um erro durante a atualização
     */
    public boolean atualizarLivro(Livro livro) throws Exception {
        validarLivro(livro);
        
        if (livro.getId() <= 0) {
            throw new Exception("ID do livro inválido para atualização");
        }
        
        try {
            // Verifica se o livro existe
            Livro livroExistente = livroDAO.buscarPorId(livro.getId());
            if (livroExistente == null) {
                throw new Exception("Livro não encontrado com o ID " + livro.getId());
            }
            
            // Verifica se o novo ISBN já está em uso por outro livro
            if (livro.getIsbn() != null && !livro.getIsbn().trim().isEmpty() && 
                (livroExistente.getIsbn() == null || !livroExistente.getIsbn().equals(livro.getIsbn()))) {
                Livro livroComMesmoIsbn = livroDAO.buscarPorIsbn(livro.getIsbn());
                if (livroComMesmoIsbn != null && livroComMesmoIsbn.getId() != livro.getId()) {
                    throw new Exception("Já existe outro livro cadastrado com o ISBN " + livro.getIsbn());
                }
            }
            
            // Verifica se a quantidade disponível é válida
            if (livro.getQuantidadeDisponivel() > livro.getQuantidadeTotal()) {
                throw new Exception("A quantidade disponível não pode ser maior que a quantidade total");
            }
            
            return livroDAO.atualizar(livro);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
            throw new Exception("Falha ao atualizar livro: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um livro pelo ID.
     * @param id o ID do livro a ser removido
     * @return true se a remoção foi bem-sucedida, false caso contrário
     * @throws Exception se ocorrer um erro durante a remoção
     */
    public boolean removerLivro(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID do livro inválido para remoção");
        }
        
        try {
            // Verifica se o livro existe
            Livro livro = livroDAO.buscarPorId(id);
            if (livro == null) {
                throw new Exception("Livro não encontrado com o ID " + id);
            }
            
            // TODO: Verificar se o livro possui empréstimos ativos antes de remover
            
            return livroDAO.remover(id);
        } catch (SQLException e) {
            System.err.println("Erro ao remover livro: " + e.getMessage());
            throw new Exception("Falha ao remover livro: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um livro pelo ID.
     * @param id o ID do livro a ser buscado
     * @return o livro encontrado ou null se não existir
     * @throws Exception se ocorrer um erro durante a busca
     */
    public Livro buscarLivroPorId(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID do livro inválido para busca");
        }
        
        try {
            return livroDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
            throw new Exception("Falha ao buscar livro: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um livro pelo ISBN.
     * @param isbn o ISBN do livro a ser buscado
     * @return o livro encontrado ou null se não existir
     * @throws Exception se ocorrer um erro durante a busca
     */
    public Livro buscarLivroPorIsbn(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new Exception("ISBN inválido para busca");
        }
        
        try {
            return livroDAO.buscarPorIsbn(isbn);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro por ISBN: " + e.getMessage());
            throw new Exception("Falha ao buscar livro: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os livros cadastrados.
     * @return lista de livros
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Livro> listarTodosLivros() throws Exception {
        try {
            return livroDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
            throw new Exception("Falha ao listar livros: " + e.getMessage(), e);
        }
    }

    /**
     * Lista livros disponíveis para empréstimo.
     * @return lista de livros disponíveis
     * @throws Exception se ocorrer um erro durante a listagem
     */
    public List<Livro> listarLivrosDisponiveis() throws Exception {
        try {
            return livroDAO.listarDisponiveis();
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros disponíveis: " + e.getMessage());
            throw new Exception("Falha ao listar livros disponíveis: " + e.getMessage(), e);
        }
    }

    /**
     * Busca livros pelo título (busca parcial).
     * @param titulo parte do título a ser buscado
     * @return lista de livros que contêm o título buscado
     * @throws Exception se ocorrer um erro durante a busca
     */
    public List<Livro> buscarLivrosPorTitulo(String titulo) throws Exception {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("Título inválido para busca");
        }
        
        try {
            return livroDAO.buscarPorTitulo(titulo);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros por título: " + e.getMessage());
            throw new Exception("Falha ao buscar livros: " + e.getMessage(), e);
        }
    }

    /**
     * Busca livros pelo autor (busca parcial).
     * @param autor parte do nome do autor a ser buscado
     * @return lista de livros que contêm o autor buscado
     * @throws Exception se ocorrer um erro durante a busca
     */
    public List<Livro> buscarLivrosPorAutor(String autor) throws Exception {
        if (autor == null || autor.trim().isEmpty()) {
            throw new Exception("Autor inválido para busca");
        }
        
        try {
            return livroDAO.buscarPorAutor(autor);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros por autor: " + e.getMessage());
            throw new Exception("Falha ao buscar livros: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza a quantidade total e disponível de um livro.
     * @param id o ID do livro
     * @param quantidadeTotal a nova quantidade total
     * @return o livro atualizado
     * @throws Exception se ocorrer um erro durante a atualização
     */
    public Livro atualizarQuantidadeLivro(int id, int quantidadeTotal) throws Exception {
        if (id <= 0) {
            throw new Exception("ID do livro inválido para atualização");
        }
        
        if (quantidadeTotal < 0) {
            throw new Exception("Quantidade total não pode ser negativa");
        }
        
        try {
            // Busca o livro
            Livro livro = livroDAO.buscarPorId(id);
            if (livro == null) {
                throw new Exception("Livro não encontrado com o ID " + id);
            }
            
            // Calcula a diferença entre a quantidade atual e a nova quantidade
            int diferenca = quantidadeTotal - livro.getQuantidadeTotal();
            
            // Atualiza a quantidade total e a quantidade disponível
            livro.setQuantidadeTotal(quantidadeTotal);
            livro.setQuantidadeDisponivel(Math.max(0, livro.getQuantidadeDisponivel() + diferenca));
            
            // Atualiza o livro no banco de dados
            if (livroDAO.atualizar(livro)) {
                return livro;
            } else {
                throw new Exception("Falha ao atualizar quantidade do livro");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar quantidade do livro: " + e.getMessage());
            throw new Exception("Falha ao atualizar quantidade do livro: " + e.getMessage(), e);
        }
    }

    /**
     * Valida os dados de um livro.
     * @param livro o livro a ser validado
     * @throws Exception se os dados do livro forem inválidos
     */
    private void validarLivro(Livro livro) throws Exception {
        if (livro == null) {
            throw new Exception("Livro não pode ser nulo");
        }
        
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new Exception("Título do livro é obrigatório");
        }
        
        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new Exception("Autor do livro é obrigatório");
        }
        
        if (livro.getQuantidadeTotal() < 0) {
            throw new Exception("Quantidade total não pode ser negativa");
        }
        
        if (livro.getQuantidadeDisponivel() < 0) {
            throw new Exception("Quantidade disponível não pode ser negativa");
        }
        
        if (livro.getQuantidadeDisponivel() > livro.getQuantidadeTotal()) {
            throw new Exception("Quantidade disponível não pode ser maior que a quantidade total");
        }
        
        // Validações adicionais podem ser implementadas conforme necessário
    }
}