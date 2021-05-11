package br.com.pedrodcp.plobby.ScoreBoard;

import br.com.pedrodcp.pexternal.API.PluginAPI;
import br.com.pedrodcp.plobby.Main;
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

    private String formatarCashDouble(double quantia) {
        PluginAPI formatter = new PluginAPI();
        return formatter.formatNumber(quantia);
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


    private static Main plugin = Main.getPlugin(Main.class);

    public void scoreGame(Player player) {
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();
        o = b.registerNewObjective("plugin", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName("§6§lREDE MAKE");

        espaço = o.getScore("");
        espaço.setScore(8);

        if (player.hasPermission("staff.admin")) {
            grupo = o.getScore("§fGrupo: §4Admin");
            grupo.setScore(7);
        } else {
            if (player.hasPermission("staff.gerente")) {
                grupo = o.getScore("§fGrupo: §9Gerente");
                grupo.setScore(7);
            } else {
                if (player.hasPermission("staff.coord")) {
                    grupo = o.getScore("§fGrupo: §5Coordenador");
                    grupo.setScore(7);
                } else {
                    if (player.hasPermission("staff.moderador")) {
                        grupo = o.getScore("§fGrupo: §2Moderador");
                        grupo.setScore(7);
                    } else {
                        if (player.hasPermission("staff.ajudante")) {
                            grupo = o.getScore("§fGrupo: §aAjudante");
                            grupo.setScore(7);
                        } else {
                            if (player.hasPermission("group.construtor")) {
                                grupo = o.getScore("§fGrupo: §eConstrutor");
                                grupo.setScore(7);
                            } else {
                                if (player.hasPermission("group.youtuber")) {
                                    grupo = o.getScore("§fGrupo: §cYouTuber");
                                    grupo.setScore(7);
                                } else {
                                    if (player.hasPermission("group.lendario")) {
                                        grupo = o.getScore("§fGrupo: §6Lendário");
                                        grupo.setScore(7);
                                    } else {
                                        if (player.hasPermission("group.mvp")) {
                                            grupo = o.getScore("§fGrupo: §bMVP");
                                            grupo.setScore(7);
                                        } else {
                                            if (player.hasPermission("group.vip")) {
                                                grupo = o.getScore("§fGrupo: §aVIP");
                                                grupo.setScore(7);
                                            } else {
                                                if (player.hasPermission("group.membro")) {
                                                    grupo = o.getScore("§fGrupo: §7Membro");
                                                    grupo.setScore(7);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        espaço = o.getScore(" ");
        espaço.setScore(6);

        if (player.getServer().getPort() == 25567) {
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

        IP = o.getScore("§eredemake.com");
        IP.setScore(0);

        player.setScoreboard(b);
    }

}
