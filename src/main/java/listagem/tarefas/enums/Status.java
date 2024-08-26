package listagem.tarefas.enums;

public enum Status {
    PENDENTE("Pendente"),EM_ANDAMENTO("emAndamento"),CONCLUIDA("Concluida");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
