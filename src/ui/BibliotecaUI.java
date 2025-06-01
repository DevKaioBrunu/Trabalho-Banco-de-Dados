package src.ui;

import src.model.Aluno;
import src.model.Livro;
import src.model.Emprestimo;
import src.service.AlunoService;
import src.service.LivroService;
import src.service.EmprestimoService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela interface de usuário do sistema de biblioteca.
 */
public class BibliotecaUI {
    private Scanner scanner;
    private AlunoService alunoService;
    private LivroService livroService;
    private EmprestimoService emprestimoService;
    private SimpleDateFormat dateFormat;

    public BibliotecaUI() {
        this.scanner = new Scanner(System.in);
        this.alunoService = new AlunoService();
        this.livroService = new LivroService();
        this.emprestimoService = new EmprestimoService();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * Inicia a interface do usuário.
     */
    public void iniciar() {
        boolean executando = true;

        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        menuAlunos();
                        break;
                    case 2:
                        menuLivros();
                        break;
                    case 3:
                        menuEmprestimos();
                        break;
                    case 4:
                        menuRelatorios();
                        break;
                    case 0:
                        executando = false;
                        System.out.println("Encerrando o sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Exibe o menu principal.
     */
    private void exibirMenuPrincipal() {
        System.out.println("\n===== SISTEMA DE GERENCIAMENTO DA BIBLIOTECA =====");
        System.out.println("1. Gerenciar Alunos");
        System.out.println("2. Gerenciar Livros");
        System.out.println("3. Gerenciar Empréstimos");
        System.out.println("4. Relatórios");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Menu de gerenciamento de alunos.
     */
    private void menuAlunos() throws Exception {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("\n===== GERENCIAMENTO DE ALUNOS =====");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Buscar Aluno");
            System.out.println("3. Listar Todos os Alunos");
            System.out.println("4. Atualizar Aluno");
            System.out.println("5. Remover Aluno");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarAluno();
                    break;
                case 2:
                    buscarAluno();
                    break;
                case 3:
                    listarAlunos();
                    break;
                case 4:
                    atualizarAluno();
                    break;
                case 5:
                    removerAluno();
                    break;
                case 0:
                    voltarMenu = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Menu de gerenciamento de livros.
     */
    private void menuLivros() throws Exception {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("\n===== GERENCIAMENTO DE LIVROS =====");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Buscar Livro");
            System.out.println("3. Listar Todos os Livros");
            System.out.println("4. Listar Livros Disponíveis");
            System.out.println("5. Atualizar Livro");
            System.out.println("6. Atualizar Quantidade de Livros");
            System.out.println("7. Remover Livro");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    buscarLivro();
                    break;
                case 3:
                    listarLivros();
                    break;
                case 4:
                    listarLivrosDisponiveis();
                    break;
                case 5:
                    atualizarLivro();
                    break;
                case 6:
                    atualizarQuantidadeLivro();
                    break;
                case 7:
                    removerLivro();
                    break;
                case 0:
                    voltarMenu = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Menu de gerenciamento de empréstimos.
     */
    private void menuEmprestimos() throws Exception {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("\n===== GERENCIAMENTO DE EMPRÉSTIMOS =====");
            System.out.println("1. Realizar Empréstimo");
            System.out.println("2. Registrar Devolução");
            System.out.println("3. Buscar Empréstimo");
            System.out.println("4. Listar Empréstimos Ativos");
            System.out.println("5. Listar Empréstimos Atrasados");
            System.out.println("6. Listar Empréstimos por Aluno");
            System.out.println("7. Listar Empréstimos por Livro");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    realizarEmprestimo();
                    break;
                case 2:
                    registrarDevolucao();
                    break;
                case 3:
                    buscarEmprestimo();
                    break;
                case 4:
                    listarEmprestimosAtivos();
                    break;
                case 5:
                    listarEmprestimosAtrasados();
                    break;
                case 6:
                    listarEmprestimosPorAluno();
                    break;
                case 7:
                    listarEmprestimosPorLivro();
                    break;
                case 0:
                    voltarMenu = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Menu de relatórios.
     */
    private void menuRelatorios() throws Exception {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("\n===== RELATÓRIOS =====");
            System.out.println("1. Relatório de Alunos");
            System.out.println("2. Relatório de Livros");
            System.out.println("3. Relatório de Empréstimos");
            System.out.println("4. Relatório de Livros Mais Emprestados");
            System.out.println("5. Relatório de Alunos com Mais Empréstimos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    relatorioAlunos();
                    break;
                case 2:
                    relatorioLivros();
                    break;
                case 3:
                    relatorioEmprestimos();
                    break;
                case 4:
                    System.out.println("Funcionalidade não implementada.");
                    break;
                case 5:
                    System.out.println("Funcionalidade não implementada.");
                    break;
                case 0:
                    voltarMenu = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // ===== OPERAÇÕES DE ALUNOS =====

    /**
     * Cadastra um novo aluno.
     */
    private void cadastrarAluno() throws Exception {
        System.out.println("\n===== CADASTRO DE ALUNO =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Aluno aluno = new Aluno(nome, matricula, email, telefone);
        Aluno alunoCadastrado = alunoService.cadastrarAluno(aluno);

        System.out.println("\nAluno cadastrado com sucesso!");
        exibirAluno(alunoCadastrado);
    }

    /**
     * Busca um aluno por ID ou matrícula.
     */
    private void buscarAluno() throws Exception {
        System.out.println("\n===== BUSCA DE ALUNO =====");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Matrícula");
        System.out.println("3. Buscar por Nome");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();

        switch (opcao) {
            case 1:
                System.out.print("ID do Aluno: ");
                int id = lerInteiro();
                Aluno alunoPorId = alunoService.buscarAlunoPorId(id);

                if (alunoPorId != null) {
                    exibirAluno(alunoPorId);
                } else {
                    System.out.println("Aluno não encontrado.");
                }
                break;

            case 2:
                System.out.print("Matrícula do Aluno: ");
                String matricula = scanner.nextLine();
                Aluno alunoPorMatricula = alunoService.buscarAlunoPorMatricula(matricula);

                if (alunoPorMatricula != null) {
                    exibirAluno(alunoPorMatricula);
                } else {
                    System.out.println("Aluno não encontrado.");
                }
                break;

            case 3:
                System.out.print("Nome do Aluno (ou parte): ");
                String nome = scanner.nextLine();
                List<Aluno> alunosPorNome = alunoService.buscarAlunosPorNome(nome);

                if (!alunosPorNome.isEmpty()) {
                    System.out.println("\nAlunos encontrados:");
                    for (Aluno a : alunosPorNome) {
                        exibirAluno(a);
                        System.out.println("------------------------------");
                    }
                } else {
                    System.out.println("Nenhum aluno encontrado com esse nome.");
                }
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

    /**
     * Lista todos os alunos cadastrados.
     */
    private void listarAlunos() throws Exception {
        System.out.println("\n===== LISTA DE ALUNOS =====");

        List<Aluno> alunos = alunoService.listarTodosAlunos();

        if (!alunos.isEmpty()) {
            for (Aluno aluno : alunos) {
                exibirAluno(aluno);
                System.out.println("------------------------------");
            }
            System.out.println("Total de alunos: " + alunos.size());
        } else {
            System.out.println("Nenhum aluno cadastrado.");
        }
    }

    /**
     * Atualiza os dados de um aluno.
     */
    private void atualizarAluno() throws Exception {
        System.out.println("\n===== ATUALIZAÇÃO DE ALUNO =====");

        System.out.print("ID do Aluno a ser atualizado: ");
        int id = lerInteiro();

        Aluno aluno = alunoService.buscarAlunoPorId(id);

        if (aluno != null) {
            System.out.println("\nDados atuais do aluno:");
            exibirAluno(aluno);

            System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");

            System.out.print("Nome [" + aluno.getNome() + "]: ");
            String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) {
                aluno.setNome(nome);
            }

            System.out.print("Matrícula [" + aluno.getMatricula() + "]: ");
            String matricula = scanner.nextLine();
            if (!matricula.trim().isEmpty()) {
                aluno.setMatricula(matricula);
            }

            System.out.print("Email [" + aluno.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) {
                aluno.setEmail(email);
            }

            System.out.print("Telefone [" + aluno.getTelefone() + "]: ");
            String telefone = scanner.nextLine();
            if (!telefone.trim().isEmpty()) {
                aluno.setTelefone(telefone);
            }

            boolean atualizado = alunoService.atualizarAluno(aluno);

            if (atualizado) {
                System.out.println("\nAluno atualizado com sucesso!");
                exibirAluno(aluno);
            } else {
                System.out.println("Falha ao atualizar aluno.");
            }
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    /**
     * Remove um aluno.
     */
    private void removerAluno() throws Exception {
        System.out.println("\n===== REMOÇÃO DE ALUNO =====");

        System.out.print("ID do Aluno a ser removido: ");
        int id = lerInteiro();

        Aluno aluno = alunoService.buscarAlunoPorId(id);

        if (aluno != null) {
            System.out.println("\nDados do aluno a ser removido:");
            exibirAluno(aluno);

            System.out.print("\nConfirma a remoção? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                boolean removido = alunoService.removerAluno(id);

                if (removido) {
                    System.out.println("Aluno removido com sucesso!");
                } else {
                    System.out.println("Falha ao remover aluno.");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    /**
     * Exibe os dados de um aluno.
     */
    private void exibirAluno(Aluno aluno) {
        System.out.println("\nID: " + aluno.getId());
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("Matrícula: " + aluno.getMatricula());
        System.out.println("Email: " + aluno.getEmail());
        System.out.println("Telefone: " + aluno.getTelefone());
        System.out.println("Data de Cadastro: " + (aluno.getDataCadastro() != null ? dateFormat.format(aluno.getDataCadastro()) : "N/A"));
    }

    // ===== OPERAÇÕES DE LIVROS =====

    /**
     * Cadastra um novo livro.
     */
    private void cadastrarLivro() throws Exception {
        System.out.println("\n===== CADASTRO DE LIVRO =====");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Editora: ");
        String editora = scanner.nextLine();

        System.out.print("Ano de Publicação: ");
        int anoPublicacao = lerInteiro();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Quantidade: ");
        int quantidade = lerInteiro();

        Livro livro = new Livro(titulo, autor, editora, anoPublicacao, isbn, quantidade);
        Livro livroCadastrado = livroService.cadastrarLivro(livro);

        System.out.println("\nLivro cadastrado com sucesso!");
        exibirLivro(livroCadastrado);
    }

    /**
     * Busca um livro por ID, ISBN, título ou autor.
     */
    private void buscarLivro() throws Exception {
        System.out.println("\n===== BUSCA DE LIVRO =====");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por ISBN");
        System.out.println("3. Buscar por Título");
        System.out.println("4. Buscar por Autor");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();
        scanner.nextLine(); // Limpa o buffer

        switch (opcao) {
            case 1:
                System.out.print("ID do Livro: ");
                int id = lerInteiro();
                Livro livroPorId = livroService.buscarLivroPorId(id);

                if (livroPorId != null) {
                    exibirLivro(livroPorId);
                } else {
                    System.out.println("Livro não encontrado.");
                }
                break;

            case 2:
                System.out.print("ISBN do Livro: ");
                String isbn = scanner.nextLine();
                Livro livroPorIsbn = livroService.buscarLivroPorIsbn(isbn);

                if (livroPorIsbn != null) {
                    exibirLivro(livroPorIsbn);
                } else {
                    System.out.println("Livro não encontrado.");
                }
                break;

            case 3:
                System.out.print("Título do Livro (ou parte): ");
                String titulo = scanner.nextLine();
                List<Livro> livrosPorTitulo = livroService.buscarLivrosPorTitulo(titulo);

                if (!livrosPorTitulo.isEmpty()) {
                    System.out.println("\nLivros encontrados:");
                    for (Livro l : livrosPorTitulo) {
                        exibirLivro(l);
                        System.out.println("------------------------------");
                    }
                } else {
                    System.out.println("Nenhum livro encontrado com esse título.");
                }
                break;

            case 4:
                System.out.print("Autor do Livro (ou parte): ");
                String autor = scanner.nextLine();
                List<Livro> livrosPorAutor = livroService.buscarLivrosPorAutor(autor);

                if (!livrosPorAutor.isEmpty()) {
                    System.out.println("\nLivros encontrados:");
                    for (Livro l : livrosPorAutor) {
                        exibirLivro(l);
                        System.out.println("------------------------------");
                    }
                } else {
                    System.out.println("Nenhum livro encontrado com esse autor.");
                }
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

    /**
     * Lista todos os livros cadastrados.
     */
    private void listarLivros() throws Exception {
        System.out.println("\n===== LISTA DE LIVROS =====");

        List<Livro> livros = livroService.listarTodosLivros();

        if (!livros.isEmpty()) {
            for (Livro livro : livros) {
                exibirLivro(livro);
                System.out.println("------------------------------");
            }
            System.out.println("Total de livros: " + livros.size());
        } else {
            System.out.println("Nenhum livro cadastrado.");
        }
    }

    /**
     * Lista livros disponíveis para empréstimo.
     */
    private void listarLivrosDisponiveis() throws Exception {
        System.out.println("\n===== LIVROS DISPONÍVEIS =====");

        List<Livro> livros = livroService.listarLivrosDisponiveis();

        if (!livros.isEmpty()) {
            for (Livro livro : livros) {
                exibirLivro(livro);
                System.out.println("------------------------------");
            }
            System.out.println("Total de livros disponíveis: " + livros.size());
        } else {
            System.out.println("Nenhum livro disponível para empréstimo.");
        }
    }

    /**
     * Atualiza os dados de um livro.
     */
    private void atualizarLivro() throws Exception {
        System.out.println("\n===== ATUALIZAÇÃO DE LIVRO =====");

        System.out.print("ID do Livro a ser atualizado: ");
        int id = lerInteiro();

        Livro livro = livroService.buscarLivroPorId(id);

        if (livro != null) {
            System.out.println("\nDados atuais do livro:");
            exibirLivro(livro);

            System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");

            System.out.print("Título [" + livro.getTitulo() + "]: ");
            String titulo = scanner.nextLine();
            if (!titulo.trim().isEmpty()) {
                livro.setTitulo(titulo);
            }

            System.out.print("Autor [" + livro.getAutor() + "]: ");
            String autor = scanner.nextLine();
            if (!autor.trim().isEmpty()) {
                livro.setAutor(autor);
            }

            System.out.print("Editora [" + livro.getEditora() + "]: ");
            String editora = scanner.nextLine();
            if (!editora.trim().isEmpty()) {
                livro.setEditora(editora);
            }

            System.out.print("Ano de Publicação [" + livro.getAnoPublicacao() + "]: ");
            String anoStr = scanner.nextLine();
            if (!anoStr.trim().isEmpty()) {
                try {
                    int ano = Integer.parseInt(anoStr);
                    livro.setAnoPublicacao(ano);
                } catch (NumberFormatException e) {
                    System.out.println("Ano inválido. Mantendo o valor atual.");
                }
            }

            System.out.print("ISBN [" + livro.getIsbn() + "]: ");
            String isbn = scanner.nextLine();
            if (!isbn.trim().isEmpty()) {
                livro.setIsbn(isbn);
            }

            boolean atualizado = livroService.atualizarLivro(livro);

            if (atualizado) {
                System.out.println("\nLivro atualizado com sucesso!");
                exibirLivro(livro);
            } else {
                System.out.println("Falha ao atualizar livro.");
            }
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    /**
     * Atualiza a quantidade de exemplares de um livro.
     */
    private void atualizarQuantidadeLivro() throws Exception {
        System.out.println("\n===== ATUALIZAÇÃO DE QUANTIDADE DE LIVRO =====");

        System.out.print("ID do Livro: ");
        int id = lerInteiro();

        Livro livro = livroService.buscarLivroPorId(id);

        if (livro != null) {
            System.out.println("\nDados atuais do livro:");
            exibirLivro(livro);

            System.out.print("\nNova quantidade total: ");
            int novaQuantidade = lerInteiro();

            Livro livroAtualizado = livroService.atualizarQuantidadeLivro(id, novaQuantidade);

            System.out.println("\nQuantidade atualizada com sucesso!");
            exibirLivro(livroAtualizado);
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    /**
     * Remove um livro.
     */
    private void removerLivro() throws Exception {
        System.out.println("\n===== REMOÇÃO DE LIVRO =====");

        System.out.print("ID do Livro a ser removido: ");
        int id = lerInteiro();

        Livro livro = livroService.buscarLivroPorId(id);

        if (livro != null) {
            System.out.println("\nDados do livro a ser removido:");
            exibirLivro(livro);

            System.out.print("\nConfirma a remoção? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                boolean removido = livroService.removerLivro(id);

                if (removido) {
                    System.out.println("Livro removido com sucesso!");
                } else {
                    System.out.println("Falha ao remover livro.");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    /**
     * Exibe os dados de um livro.
     */
    private void exibirLivro(Livro livro) {
        System.out.println("\nID: " + livro.getId());
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor());
        System.out.println("Editora: " + livro.getEditora());
        System.out.println("Ano de Publicação: " + livro.getAnoPublicacao());
        System.out.println("ISBN: " + livro.getIsbn());
        System.out.println("Quantidade Total: " + livro.getQuantidadeTotal());
        System.out.println("Quantidade Disponível: " + livro.getQuantidadeDisponivel());
        System.out.println("Disponível para Empréstimo: " + (livro.isDisponivel() ? "Sim" : "Não"));
        System.out.println("Data de Cadastro: " + (livro.getDataCadastro() != null ? dateFormat.format(livro.getDataCadastro()) : "N/A"));
    }

    // ===== OPERAÇÕES DE EMPRÉSTIMOS =====

    /**
     * Realiza um novo empréstimo.
     */
    private void realizarEmprestimo() throws Exception {
        System.out.println("\n===== REALIZAR EMPRÉSTIMO =====");

        System.out.print("ID ou Matrícula do Aluno: ");
        String alunoIdentificador = scanner.nextLine();

        Aluno aluno = null;
        try {
            int alunoId = Integer.parseInt(alunoIdentificador);
            aluno = alunoService.buscarAlunoPorId(alunoId);
        } catch (NumberFormatException e) {
            aluno = alunoService.buscarAlunoPorMatricula(alunoIdentificador);
        }

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("\nAluno selecionado:");
        exibirAluno(aluno);

        System.out.print("\nID ou ISBN do Livro: ");
        String livroIdentificador = scanner.nextLine();

        Livro livro = null;
        try {
            int livroId = Integer.parseInt(livroIdentificador);
            livro = livroService.buscarLivroPorId(livroId);
        } catch (NumberFormatException e) {
            livro = livroService.buscarLivroPorIsbn(livroIdentificador);
        }

        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        if (!livro.isDisponivel()) {
            System.out.println("Livro não disponível para empréstimo.");
            return;
        }

        System.out.println("\nLivro selecionado:");
        exibirLivro(livro);

        System.out.print("\nDias para empréstimo (deixe em branco para usar o padrão): ");
        String diasStr = scanner.nextLine();
        Integer dias = null;

        if (!diasStr.trim().isEmpty()) {
            try {
                dias = Integer.parseInt(diasStr);
                if (dias <= 0) {
                    System.out.println("Número de dias inválido. Usando o padrão.");
                    dias = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Número de dias inválido. Usando o padrão.");
            }
        }

        System.out.print("\nConfirmar empréstimo? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            Emprestimo emprestimo = emprestimoService.realizarEmprestimo(aluno.getId(), livro.getId(), dias);

            System.out.println("\nEmpréstimo realizado com sucesso!");
            exibirEmprestimo(emprestimo);
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    /**
     * Registra a devolução de um livro.
     */
    private void registrarDevolucao() throws Exception {
        System.out.println("\n===== REGISTRAR DEVOLUÇÃO =====");

        System.out.print("ID do Empréstimo: ");
        int id = lerInteiro();

        Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(id);

        if (emprestimo == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }

        if (emprestimo.getStatus() == Emprestimo.StatusEmprestimo.DEVOLVIDO) {
            System.out.println("Este livro já foi devolvido.");
            return;
        }

        System.out.println("\nDados do empréstimo:");
        exibirEmprestimo(emprestimo);

        System.out.print("\nConfirmar devolução? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            Emprestimo emprestimoAtualizado = emprestimoService.registrarDevolucao(id);

            System.out.println("\nDevolução registrada com sucesso!");
            exibirEmprestimo(emprestimoAtualizado);
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    /**
     * Busca um empréstimo pelo ID.
     */
    private void buscarEmprestimo() throws Exception {
        System.out.println("\n===== BUSCA DE EMPRÉSTIMO =====");

        System.out.print("ID do Empréstimo: ");
        int id = lerInteiro();

        Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(id);

        if (emprestimo != null) {
            exibirEmprestimo(emprestimo);
        } else {
            System.out.println("Empréstimo não encontrado.");
        }
    }

    /**
     * Lista empréstimos ativos.
     */
    private void listarEmprestimosAtivos() throws Exception {
        System.out.println("\n===== EMPRÉSTIMOS ATIVOS =====");

        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimosAtivos();

        if (!emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : emprestimos) {
                exibirEmprestimo(emprestimo);
                System.out.println("------------------------------");
            }
            System.out.println("Total de empréstimos ativos: " + emprestimos.size());
        } else {
            System.out.println("Nenhum empréstimo ativo.");
        }
    }

    /**
     * Lista empréstimos atrasados.
     */
    private void listarEmprestimosAtrasados() throws Exception {
        System.out.println("\n===== EMPRÉSTIMOS ATRASADOS =====");

        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimosAtrasados();

        if (!emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : emprestimos) {
                exibirEmprestimo(emprestimo);
                System.out.println("Dias de atraso: " + emprestimo.getDiasAtraso());
                System.out.println("------------------------------");
            }
            System.out.println("Total de empréstimos atrasados: " + emprestimos.size());
        } else {
            System.out.println("Nenhum empréstimo atrasado.");
        }
    }

    /**
     * Lista empréstimos de um aluno.
     */
    private void listarEmprestimosPorAluno() throws Exception {
        System.out.println("\n===== EMPRÉSTIMOS POR ALUNO =====");

        System.out.print("ID ou Matrícula do Aluno: ");
        String alunoIdentificador = scanner.nextLine();

        Aluno aluno = null;
        try {
            int alunoId = Integer.parseInt(alunoIdentificador);
            aluno = alunoService.buscarAlunoPorId(alunoId);
        } catch (NumberFormatException e) {
            aluno = alunoService.buscarAlunoPorMatricula(alunoIdentificador);
        }

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("\nAluno selecionado:");
        exibirAluno(aluno);

        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimosPorAluno(aluno.getId());

        if (!emprestimos.isEmpty()) {
            System.out.println("\nEmpréstimos do aluno:");
            for (Emprestimo emprestimo : emprestimos) {
                exibirEmprestimo(emprestimo);
                System.out.println("------------------------------");
            }
            System.out.println("Total de empréstimos: " + emprestimos.size());
        } else {
            System.out.println("\nNenhum empréstimo encontrado para este aluno.");
        }
    }

    /**
     * Lista empréstimos de um livro.
     */
    private void listarEmprestimosPorLivro() throws Exception {
        System.out.println("\n===== EMPRÉSTIMOS POR LIVRO =====");

        System.out.print("ID ou ISBN do Livro: ");
        String livroIdentificador = scanner.nextLine();

        Livro livro = null;
        try {
            int livroId = Integer.parseInt(livroIdentificador);
            livro = livroService.buscarLivroPorId(livroId);
        } catch (NumberFormatException e) {
            livro = livroService.buscarLivroPorIsbn(livroIdentificador);
        }

        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        System.out.println("\nLivro selecionado:");
        exibirLivro(livro);

        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimosPorLivro(livro.getId());

        if (!emprestimos.isEmpty()) {
            System.out.println("\nEmpréstimos do livro:");
            for (Emprestimo emprestimo : emprestimos) {
                exibirEmprestimo(emprestimo);
                System.out.println("------------------------------");
            }
            System.out.println("Total de empréstimos: " + emprestimos.size());
        } else {
            System.out.println("\nNenhum empréstimo encontrado para este livro.");
        }
    }

    /**
     * Exibe os dados de um empréstimo.
     */
    private void exibirEmprestimo(Emprestimo emprestimo) {
        System.out.println("\nID: " + emprestimo.getId());

        if (emprestimo.getAluno() != null) {
            System.out.println("Aluno: " + emprestimo.getAluno().getNome() + " (ID: " + emprestimo.getAlunoId() + ")");
        } else {
            System.out.println("ID do Aluno: " + emprestimo.getAlunoId());
        }

        if (emprestimo.getLivro() != null) {
            System.out.println("Livro: " + emprestimo.getLivro().getTitulo() + " (ID: " + emprestimo.getLivroId() + ")");
        } else {
            System.out.println("ID do Livro: " + emprestimo.getLivroId());
        }

        System.out.println("Data do Empréstimo: " + dateFormat.format(emprestimo.getDataEmprestimo()));
        System.out.println("Data Prevista de Devolução: " + dateFormat.format(emprestimo.getDataDevolucaoPrevista()));

        if (emprestimo.getDataDevolucaoEfetiva() != null) {
            System.out.println("Data de Devolução Efetiva: " + dateFormat.format(emprestimo.getDataDevolucaoEfetiva()));
        }

        System.out.println("Status: " + emprestimo.getStatus());

        if (emprestimo.isAtrasado()) {
            System.out.println("Situação: ATRASADO");
        }
    }

    // ===== RELATÓRIOS =====

    /**
     * Gera relatório de alunos.
     */
    private void relatorioAlunos() throws Exception {
        System.out.println("\n===== RELATÓRIO DE ALUNOS =====");

        List<Aluno> alunos = alunoService.listarTodosAlunos();

        if (!alunos.isEmpty()) {
            System.out.println("Total de alunos cadastrados: " + alunos.size());

            System.out.println("\nListagem de alunos:");
            System.out.printf("%-5s | %-30s | %-15s | %-30s | %-15s\n", "ID", "Nome", "Matrícula", "Email", "Telefone");
            System.out.println("----------------------------------------------------------------------------------------------");

            for (Aluno aluno : alunos) {
                System.out.printf("%-5d | %-30s | %-15s | %-30s | %-15s\n", 
                        aluno.getId(), 
                        limitarTexto(aluno.getNome(), 30), 
                        aluno.getMatricula(), 
                        limitarTexto(aluno.getEmail(), 30), 
                        aluno.getTelefone());
            }
        } else {
            System.out.println("Nenhum aluno cadastrado.");
        }
    }

    /**
     * Gera relatório de livros.
     */
    private void relatorioLivros() throws Exception {
        System.out.println("\n===== RELATÓRIO DE LIVROS =====");

        List<Livro> livros = livroService.listarTodosLivros();

        if (!livros.isEmpty()) {
            int totalLivros = 0;
            int totalDisponiveis = 0;

            for (Livro livro : livros) {
                totalLivros += livro.getQuantidadeTotal();
                totalDisponiveis += livro.getQuantidadeDisponivel();
            }

            System.out.println("Total de títulos cadastrados: " + livros.size());
            System.out.println("Total de exemplares: " + totalLivros);
            System.out.println("Total de exemplares disponíveis: " + totalDisponiveis);
            System.out.println("Total de exemplares emprestados: " + (totalLivros - totalDisponiveis));

            System.out.println("\nListagem de livros:");
            System.out.printf("%-5s | %-40s | %-30s | %-10s | %-10s\n", "ID", "Título", "Autor", "Total", "Disponível");
            System.out.println("-------------------------------------------------------------------------------------------");

            for (Livro livro : livros) {
                System.out.printf("%-5d | %-40s | %-30s | %-10d | %-10d\n", 
                        livro.getId(), 
                        limitarTexto(livro.getTitulo(), 40), 
                        limitarTexto(livro.getAutor(), 30), 
                        livro.getQuantidadeTotal(), 
                        livro.getQuantidadeDisponivel());
            }
        } else {
            System.out.println("Nenhum livro cadastrado.");
        }
    }

    /**
     * Gera relatório de empréstimos.
     */
    private void relatorioEmprestimos() throws Exception {
        System.out.println("\n===== RELATÓRIO DE EMPRÉSTIMOS =====");

        List<Emprestimo> emprestimos = emprestimoService.listarTodosEmprestimos();
        List<Emprestimo> emprestimosAtivos = emprestimoService.listarEmprestimosAtivos();
        List<Emprestimo> emprestimosAtrasados = emprestimoService.listarEmprestimosAtrasados();

        if (!emprestimos.isEmpty()) {
            System.out.println("Total de empréstimos: " + emprestimos.size());
            System.out.println("Empréstimos ativos: " + emprestimosAtivos.size());
            System.out.println("Empréstimos atrasados: " + emprestimosAtrasados.size());

            System.out.println("\nEmpréstimos ativos:");
            System.out.printf("%-5s | %-20s | %-40s | %-20s | %-20s\n", "ID", "Aluno", "Livro", "Data Empréstimo", "Data Devolução");
            System.out.println("-------------------------------------------------------------------------------------------------------");

            for (Emprestimo emprestimo : emprestimosAtivos) {
                String nomeAluno = (emprestimo.getAluno() != null) ? emprestimo.getAluno().getNome() : "ID: " + emprestimo.getAlunoId();
                String tituloLivro = (emprestimo.getLivro() != null) ? emprestimo.getLivro().getTitulo() : "ID: " + emprestimo.getLivroId();

                System.out.printf("%-5d | %-20s | %-40s | %-20s | %-20s\n", 
                        emprestimo.getId(), 
                        limitarTexto(nomeAluno, 20), 
                        limitarTexto(tituloLivro, 40), 
                        dateFormat.format(emprestimo.getDataEmprestimo()), 
                        dateFormat.format(emprestimo.getDataDevolucaoPrevista()));
            }

            if (!emprestimosAtrasados.isEmpty()) {
                System.out.println("\nEmpréstimos atrasados:");
                System.out.printf("%-5s | %-20s | %-40s | %-20s | %-20s | %-10s\n", "ID", "Aluno", "Livro", "Data Empréstimo", "Data Devolução", "Dias Atraso");
                System.out.println("------------------------------------------------------------------------------------------------------------------");

                for (Emprestimo emprestimo : emprestimosAtrasados) {
                    String nomeAluno = (emprestimo.getAluno() != null) ? emprestimo.getAluno().getNome() : "ID: " + emprestimo.getAlunoId();
                    String tituloLivro = (emprestimo.getLivro() != null) ? emprestimo.getLivro().getTitulo() : "ID: " + emprestimo.getLivroId();

                    System.out.printf("%-5d | %-20s | %-40s | %-20s | %-20s | %-10d\n", 
                            emprestimo.getId(), 
                            limitarTexto(nomeAluno, 20), 
                            limitarTexto(tituloLivro, 40), 
                            dateFormat.format(emprestimo.getDataEmprestimo()), 
                            dateFormat.format(emprestimo.getDataDevolucaoPrevista()),
                            emprestimo.getDiasAtraso());
                }
            }
        } else {
            System.out.println("Nenhum empréstimo registrado.");
        }
    }

    // ===== UTILITÁRIOS =====

    /**
     * Lê uma opção do menu como inteiro.
     */
    private int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1; // Opção inválida
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Erro ao ler entrada: " + e.getMessage());
            System.out.println("Executando em modo não interativo. Retornando opção padrão (0).");
            return 0; // Retorna opção para sair quando não há entrada disponível
        }
    }

    /**
     * Lê um valor inteiro da entrada.
     */
    private int lerInteiro() {
        try {
            int valor = Integer.parseInt(scanner.nextLine());
            return valor;
        } catch (NumberFormatException e) {
            return -1; // Valor inválido
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Erro ao ler entrada: " + e.getMessage());
            System.out.println("Executando em modo não interativo. Retornando valor padrão (-1).");
            return -1; // Retorna valor padrão quando não há entrada disponível
        }
    }

    /**
     * Limita o tamanho de um texto para exibição.
     */
    private String limitarTexto(String texto, int tamanho) {
        if (texto == null) {
            return "";
        }

        if (texto.length() <= tamanho) {
            return texto;
        }

        return texto.substring(0, tamanho - 3) + "...";
    }
}
