package br.com.pedrodcp.plobby.Comandos;

import br.com.pedrodcp.plobby.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static br.com.pedrodcp.plobby.Main.getPlayerManager;

public class voar implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if (!(s instanceof Player)) return false;
        Player p = (Player) s;

        if (c.getName().equalsIgnoreCase("voar")) {
            if (!p.hasPermission("lobby.voar")) {
                p.sendMessage("§cVocê não possui permissão para utilizar este comando.");

            } else {
                if (Main.getPlayerManager().getPlayer(p.getName()).getFlyOption().equals("off")) {
                    p.setAllowFlight(true);
                    p.sendMessage("§eAgora você pode voar!");
                    Main.getPlayerManager().getPlayer(p.getName()).setFlyOption("on");
                    Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));

                } else {
                    p.setAllowFlight(false);
                    p.sendMessage("§eAgora você não pode mais voar.");
                    Main.getPlayerManager().getPlayer(p.getName()).setFlyOption("off");
                    Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                }
            }
        }

        return false;
    }
}
