
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getCategoria() { return categoria; }
    public LocalDateTime getHorario() { return horario; }
    public String getDescricao() { return descricao; }

    public String toString() {
        return nome + " | " + categoria + " | " + endereco + " | " + horario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "\nDescrição: " + descricao;
    }

    public String toDataString() {
        return nome + ";" + endereco + ";" + categoria + ";" + horario.toString() + ";" + descricao;
    }

    public static Evento fromDataString(String data) {
        String[] parts = data.split(";");
        return new Evento(parts[0], parts[1], parts[2], LocalDateTime.parse(parts[3]), parts[4]);
    }
}
