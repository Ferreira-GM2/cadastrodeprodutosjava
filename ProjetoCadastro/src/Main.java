import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String NOME_ARQUIVO = "produtos.txt";
    private static List<Produto> produtos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args)
    {
        carregarProdutos();
        String logo = """
                                
                8888888888        888                  
                888               888                  
                888               888                  
                8888888           888  .d88b.   .d88b. 
                888               888 d88""88b d88P"88b
                888        888888 888 888  888 888  888
                888               888 Y88..88P Y88b 888
                8888888888        888  "Y88P"   "Y88888
                _____________________               888
                T R A N S P O R T E S          Y8b d88P
                                                "Y88P"               
                """;
        System.out.println(logo);
        int opcao;
        do {
            System.out.println("========================");
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Listar produtos");
            System.out.println("3. Deletar produto");
            System.out.println("4. Sair\n");
            System.out.println("========================");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    deletarProduto();
                    break;
            }
        } while (opcao != 4);
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.next();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        produtos.add(new Produto(nome, preco));
        salvarProdutos();
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto produto : produtos) {
                System.out.println(produto.getNome()
                        + " - R$" + produto.getPreco());
            }
        }
    }

    private static void deletarProduto() {
        System.out.print("Digite o nome do produto a ser deletado: ");
        String nomeProduto = scanner.next();

        produtos.removeIf(produto -> produto.getNome().equalsIgnoreCase(nomeProduto));
        salvarProdutos();
        System.out.println("Produto deletado com sucesso!");
    }

    private static void salvarProdutos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Produto produto : produtos) {
                writer.write(produto.toLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os produtos: " + e.getMessage());
        }
    }

    private static void carregarProdutos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                produtos.add(new Produto(dados[0], Double.parseDouble(dados[1])));
            }
        } catch (IOException e) {
            // Se o arquivo não existir, a lista de produtos permanece vazia
        }
    }
}