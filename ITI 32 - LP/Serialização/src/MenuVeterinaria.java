import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Veterinarios implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String especialidade;

    // Construtor
    public Veterinarios(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    // Método para exibir informações do veterinário
    @Override
    public String toString() {
        return "Nome: " + nome + "\nEspecialidade: " + especialidade;
    }
}

class Animal implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String status;

    // Construtor
    public Animal(String nome, String especie, String raca, int idade, String status) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.status = status;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("em tratamento") || status.equals("disponível")) {
            this.status = status;
        } else {
            this.status = "em tratamento";
        }
    }

    // Método para exibir informações do animal
    @Override
    public String toString() {
        return "Nome: " + nome + "\nEspécie: " + especie + "\nRaça: " + raca + "\nIdade: " + idade + "\nStatus: " + status;
    }
}

public class MenuVeterinaria {
    private static ArrayList<Animal> animais = new ArrayList<>();
    private static ArrayList<Veterinarios> veterinarios = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_ANIMAIS = "animais.dat";
    private static final String FILE_VETERINARIOS = "veterinarios.dat";

    public static void main(String[] args) {
        carregarDados();

        boolean continuar = true;
        while (continuar) {
            System.out.println("Cadastro de Animais");
            System.out.println("1. Cadastrar animal");
            System.out.println("2. Cadastrar Veterinário");
            System.out.println("3. Listar animais");
            System.out.println("4. Listar veterinários");
            System.out.println("5. Sair e salvar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarAnimal();
                    break;
                case 2:
                    cadastrarVeterinario();
                    break;
                case 3:
                    listarAnimais();
                    break;
                case 4:
                    listarVeterinarios();
                    break;
                case 5:
                    salvarDados();
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Dados salvos! Volte sempre.");
    }

    private static void cadastrarAnimal() {
        System.out.print("Nome do animal: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie do animal (Ex: cão, gato): ");
        String especie = scanner.nextLine();
        System.out.print("Raça do animal: ");
        String raca = scanner.nextLine();
        System.out.print("Idade do animal: ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        System.out.print("Status do animal (em tratamento/disponível): ");
        String status = scanner.nextLine();

        Animal animal = new Animal(nome, especie, raca, idade, status);
        animais.add(animal);
        System.out.println("Animal cadastrado com sucesso!\n");
    }

    private static void cadastrarVeterinario() {
        System.out.print("Nome do Veterinário: ");
        String nome = scanner.nextLine();
        System.out.print("Especialidade do Veterinário: ");
        String especialidade = scanner.nextLine();

        Veterinarios veterinario = new Veterinarios(nome, especialidade);
        veterinarios.add(veterinario);
        System.out.println("Veterinário cadastrado com sucesso!\n");
    }

    private static void listarAnimais() {
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.\n");
        } else {
            System.out.println("Animais cadastrados:");
            for (Animal animal : animais) {
                System.out.println(animal);
                System.out.println("-----------------------");
            }
        }
    }

    private static void listarVeterinarios() {
        if (veterinarios.isEmpty()) {
            System.out.println("Nenhum veterinário cadastrado.\n");
        } else {
            System.out.println("Veterinários cadastrados:");
            for (Veterinarios veterinario : veterinarios) {
                System.out.println(veterinario);
                System.out.println("-----------------------");
            }
        }
    }

    // Métodos para salvar e carregar dados
    private static void salvarDados() {
        try (ObjectOutputStream outAnimais = new ObjectOutputStream(new FileOutputStream(FILE_ANIMAIS));
             ObjectOutputStream outVeterinarios = new ObjectOutputStream(new FileOutputStream(FILE_VETERINARIOS))) {

            outAnimais.writeObject(animais);
            outVeterinarios.writeObject(veterinarios);
            System.out.println("Dados salvos com sucesso!");

        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private static void carregarDados() {
        try (ObjectInputStream inAnimais = new ObjectInputStream(new FileInputStream(FILE_ANIMAIS));
             ObjectInputStream inVeterinarios = new ObjectInputStream(new FileInputStream(FILE_VETERINARIOS))) {

            animais = (ArrayList<Animal>) inAnimais.readObject();
            veterinarios = (ArrayList<Veterinarios>) inVeterinarios.readObject();
            System.out.println("Dados carregados com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum dado anterior encontrado. Iniciando novo cadastro.");
        }
    }
}
