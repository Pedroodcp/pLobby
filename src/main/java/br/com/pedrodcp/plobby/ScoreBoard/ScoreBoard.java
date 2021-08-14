package br.com.pedrodcp.plobby.scoreboard;

import br.com.pedrodcp.pexternal.api.PluginAPI;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoard {

    private String formatarCash(String jogador) {
        PluginAPI formatter = new PluginAPI();
        double CashDoJogador = PluginAPI.getAccount(jogador).getCash();
        return formatter.formatNumber(CashDoJogador);
    }

    private static ScoreboardManager m;
    private static Scoreboard b;
    private static Objective o;
    private static Score grupo;
    private static Score cash;
    private static Score espaço;
    private static Score lobby;
    private static Score jogadores;
    private static Score IP;

    public void scoreGame(Player player) {
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();
        o = b.registerNewObjective("plugin", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName("§6§lREDE TESTE");

        espaço = o.getScore("");
        espaço.setScore(8);

        grupo = o.getScore("§fGrupo: " + LuckPermsProvider.get().getGroupManager().getGroup(LuckPermsProvider.get().getUserManager().getUser(player.getName()).getPrimaryGroup()).getDisplayName().replace("&", "§"));
        grupo.setScore(7);

        espaço = o.getScore(" ");
        espaço.setScore(6);

        if (player.getServer().getPort() == 25605) {
            lobby = o.getScore("§fLobby: §a#1");
            lobby.setScore(5);
        } else {
            lobby = o.getScore("§fLobby: §a-");
            lobby.setScore(5);
        }

        jogadores = o.getScore("§fOnline: §a" + Bukkit.getOnlinePlayers().size());
        jogadores.setScore(4);

        espaço = o.getScore("  ");
        espaço.setScore(3);

        cash = o.getScore("§fCash: §6" + formatarCash(player.getName()));
        cash.setScore(2);

        espaço = o.getScore("   ");
        espaço.setScore(1);

        IP = o.getScore("§eredeteste.com.br");
        IP.setScore(0);

        player.setScoreboard(b);
    }

}
