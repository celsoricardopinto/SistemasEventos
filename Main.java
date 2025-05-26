
import model.Usuario;
import model.Evento;
import service.GerenciadorEventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final GerenciadorEventos gerenciador = new GerenciadorEventos();
    private static Usuario usuario;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cadastrarUsuario();
        menuPrincipal();
    }

    private static void cadastrarUsuario() {
        System.out.println("Cadastro do Usuário:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        usuario = new Usuario(nome, email, cidade);
    }

    private static void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("3. Confirmar Participação");
            System.out.println("4. Cancelar Participação");
            System.out.println("5. Meus Eventos");
            System.out.println("6. Eventos em Andamento");
            System.out.println("7. Eventos Passados");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1: cadastrarEvento();
                case 2: listarEventos(gerenciador.listarEventos());
                case 3: confirmarParticipacao();
                case 4: cancelarParticipacao();
                case 5: listarEventos(gerenciador.eventosParticipando());
                case 6: listarEventos(gerenciador.eventosEmAndamento());
                case 7: listarEventos(gerenciador.eventosPassados());
            }
        } while (opcao != 0);
    }

    private static void cadastrarEvento() {
        System.out.println("Cadastro de Evento:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Categoria (Festa, Esporte, Show): ");
        String categoria = scanner.nextLine();
        System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
        LocalDateTime data = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        Evento evento = new Evento(nome, endereco, categoria, data, descricao);
        gerenciador.cadastrarEvento(evento);
        System.out.println("Evento cadastrado!");
    }

    private static void listarEventos(List<Evento> eventos) {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
        } else {
            for (int i = 0; i < eventos.size(); i++) {
                System.out.println((i + 1) + ". " + eventos.get(i));
            }
        }
    }

    private static void confirmarParticipacao() {
        listarEventos(gerenciador.listarEventos());
        System.out.print("Escolha o número do evento: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        List<Evento> lista = gerenciador.listarEventos();
        if (index >= 0 && index < lista.size()) {
            gerenciador.confirmarParticipacao(lista.get(index));
            System.out.println("Participação confirmada!");
        }
    }

    private static void cancelarParticipacao() {
        listarEventos(gerenciador.eventosParticipando());
        System.out.print("Escolha o número do evento: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        List<Evento> lista = gerenciador.eventosParticipando();
        if (index >= 0 && index < lista.size()) {
            gerenciador.cancelarParticipacao(lista.get(index));
            System.out.println("Participação cancelada.");
        }
    }
}
