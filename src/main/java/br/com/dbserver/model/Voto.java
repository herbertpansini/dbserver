package br.com.dbserver.model;

public enum Voto {
    S("SIM"),
    N("NAO");

    private final String value;

    Voto(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
