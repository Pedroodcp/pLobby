package br.com.pedrodcp.plobby.entity;

public class PlayerOBJ {

    private String nome;
    private String jogadores;
    private String tell;
    private String fly;
    private String vanish;
    private int ID;
    private String firstlogin;
    private String lastlogin;

    public PlayerOBJ(String nome, String preferencias, String tell, String fly, String vanish, int ID, String firstlogin, String lastlogin) {
        this.nome = nome;
        this.jogadores = preferencias;
        this.tell = tell;
        this.fly = fly;
        this.vanish = vanish;
        this.ID = ID;
        this.firstlogin = firstlogin;
        this.lastlogin = lastlogin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getJogadoresOption() {
        return jogadores;
    }

    public void setJogadoresOption(String preferencias) {
        this.jogadores = preferencias;
    }

    public String getTellOption() {
        return tell;
    }

    public void setTellOption(String tell) {
        this.tell = tell;
    }

    public String getFlyOption() {
        return fly;
    }

    public void setFlyOption(String fly) {
        this.fly = fly;
    }

    public String getVanishOption() {
        return vanish;
    }

    public void setVanishOption(String vanish) {
        this.vanish = vanish;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstLogin() {
        return firstlogin;
    }

    public void setFirstLogin(String firstlogin) {
        this.firstlogin = firstlogin;
    }

    public String getLastLogin() {
        return lastlogin;
    }

    public void setLastLogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

}
