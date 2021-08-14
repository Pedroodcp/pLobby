package br.com.pedrodcp.plobby;

import br.com.pedrodcp.plobby.commands.*;
import br.com.pedrodcp.plobby.commands.chat.chat;
import br.com.pedrodcp.plobby.events.*;
import br.com.pedrodcp.plobby.scoreboard.ScoreBoard;
import br.com.pedrodcp.plobby.entity.PlayerManager;
import br.com.pedrodcp.plobby.utils.MYSQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static PlayerManager playerManager;
    private static MYSQL mysql;

    public static ScoreBoard sc;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new PlayerManager();
        mysql = new MYSQL(
                getInstance().getConfig().getString("Database.user"),
                getInstance().getConfig().getString("Database.password"),
                getInstance().getConfig().getString("Database.host"),
                getInstance().getConfig().getInt("Database.port"),
                getInstance().getConfig().getString("Database.database")
        );
        sc = new ScoreBoard();
        loadCommands();
        loadConfig();
        loadEvents();
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§a[pLobby] Plugin carregado com sucesso.");
        Bukkit.getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§c[pLobby] Plugin desativado.");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public void loadCommands() {
        getCommand("plobby").setExecutor(new plobby());
        getCommand("voar").setExecutor(new voar());
        getCommand("chat").setExecutor(new chat());
        getCommand("vanish").setExecutor(new vanish());
        getCommand("build").setExecutor(new build());
        getCommand("spawn").setExecutor(new spawn());
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }

    public void loadEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinAndQuit(), this);
        pm.registerEvents(new ChatEvent(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new InteractEvent(), this);
        pm.registerEvents(new Listeners(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static MYSQL getMysql() {
        return mysql;
    }

    public static ScoreBoard getSc() {
        return sc;
    }
}
