package br.com.pedrodcp.plobby.Comandos;

import br.com.pedrodcp.plobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static br.com.pedrodcp.plobby.Main.getPlayerManager;

public class vanish implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if (!(s instanceof Player)) return false;
        Player p = (Player) s;

        if (c.getName().equalsIgnoreCase("vanish")) {
            if (!p.hasPermission("vanish.use")) {
                p.sendMessage("§cVocê não possui permissão para utilizar este comando.");

            } else {
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("off")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (!all.hasPermission("vanish.use")) {
                            all.hidePlayer(p);
                        }
                    }
                    p.sendMessage("§eInvisibilidade ativada.");
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("on");
                    Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));

                } else {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.showPlayer(p);
                        Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                        Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                    }
                    p.sendMessage("§eInvisibilidade desativada.");
                }
            }
        }

        return false;
    }
}
