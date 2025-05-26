
package service;

import model.Evento;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class GerenciadorEventos {
    private List<Evento> eventos = new ArrayList<>();
    private Set<Evento> participacoes = new HashSet<>();

    private final String FILE_PATH = "data/events.data";

    public GerenciadorEventos() {
        carregarEventos();
    }

    public void cadastrarEvento(Evento evento) {
        eventos.add(evento);
        salvarEventos();
    }

    public List<Evento> listarEventos() {
        eventos.sort(Comparator.comparing(Evento::getHorario));
        return eventos;
    }

    public void confirmarParticipacao(Evento evento) {
        participacoes.add(evento);
    }

    public void cancelarParticipacao(Evento evento) {
        participacoes.remove(evento);
    }

    public List<Evento> eventosParticipando() {
        return new ArrayList<>(participacoes);
    }

    public List<Evento> eventosEmAndamento() {
        LocalDateTime agora = LocalDateTime.now();
        List<Evento> ativos = new ArrayList<>();
        for (Evento evento : eventos) {
            if (evento.getHorario().isBefore(agora.plusHours(1)) && evento.getHorario().isAfter(agora.minusHours(1))) {
                ativos.add(evento);
            }
        }
        return ativos;
    }

    public List<Evento> eventosPassados() {
        LocalDateTime agora = LocalDateTime.now();
        List<Evento> passados = new ArrayList<>();
        for (Evento evento : eventos) {
            if (evento.getHorario().isBefore(agora)) {
                passados.add(evento);
            }
        }
        return passados;
    }

    private void salvarEventos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Evento e : eventos) {
                pw.println(e.toDataString());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos.");
        }
    }

    private void carregarEventos() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) file.getParentFile().mkdirs();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String linha;
            while ((linha = br.readLine()) != null) {
                eventos.add(Evento.fromDataString(linha));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos.");
        }
    }
}
