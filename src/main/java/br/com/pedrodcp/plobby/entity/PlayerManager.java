package br.com.pedrodcp.plobby.entity;

import static br.com.pedrodcp.plobby.Main.getMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayerManager {

    public static ArrayList<PlayerOBJ> lista;

    public PlayerManager() {
        lista = new ArrayList<>();
    }

    private void createPlayer(PlayerOBJ pb) {
        lista.add(pb);
    }

    public PlayerOBJ getPlayer(String nome) {
        for (PlayerOBJ user : lista) {
            if (user.getNome().equalsIgnoreCase(nome)) {
                return user;
            }
        }
        return null;
    }

    public void salvarDados(PlayerOBJ p) {
        try {

            getMysql().openConnection();
            Connection connection = getMysql().getConnection();

            PreparedStatement ps = connection.prepareStatement("UPDATE config SET jogadores= '" + p.getJogadoresOption() + "', tell= '" + p.getTellOption() + "', fly= '" + p.getFlyOption() + "', vanish= '" + p.getVanishOption() + "', id= '" + p.getID() + "', firstlogin= '" + p.getFirstLogin() + "', lastlogin= '" + p.getLastLogin() + "' WHERE nome = '" + p.getNome() + "'");
            ps.execute();
            ps.close();

            getMysql().closeConnection();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadPlayer(String nome) {
        try {

            getMysql().openConnection();
            Connection c = getMysql().getConnection();

            PreparedStatement ps = c.prepareStatement("SELECT * FROM config WHERE nome = '"+ nome +"'");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                createPlayer(new PlayerOBJ(rs.getString("nome"), rs.getString("jogadores"), rs.getString("tell"), rs.getString("fly"), rs.getString("vanish"), rs.getInt("id"), rs.getString("firstlogin"), rs.getString("lastlogin")));

            } else {
                ps = c.prepareStatement("INSERT config VALUES ('" + nome + "', '" + "on" + "', '" + "on" + "', '" + "off" + "', '" + "off" + "', '" + 0 + "', '" + "indefinido" + "', '" + "indefinido" + "')");
                ps.execute();
                ps.close();

                createPlayer(new PlayerOBJ(nome, "on", "on", "off", "off", 0, "indefinido", "indefinido"));
            }

            getMysql().closeConnection();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
