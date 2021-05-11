package br.com.pedrodcp.plobby.Comandos.Chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class chat implements CommandExecutor {

    public static boolean Chat = true;

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if (!(s instanceof Player)) return false;
        Player p = (Player) s;

        if (c.getName().equalsIgnoreCase("chat")) {
            if (!p.hasPermission("chat.admin")) {
                p.sendMessage("§cVocê não possui permissão para utilizar este comando.");

            } else {
                if (args.length == 0) {
                    p.sendMessage("§cUtilize /chat <on/off/limpar>");
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("on")) {
                        if (Chat == false) {
                            Chat=true;
                            p.sendMessage("§aO bate-papo foi habilitado.");

                        } else {
                            if (Chat == true) {
                                p.sendMessage("§cO bate-papo já está ligado.");
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("off")) {
                        if (Chat == true) {
                            Chat=false;
                            p.sendMessage("§eO bate-papo foi desabilitado.");

                        } else {
                            if (Chat == false) {
                                p.sendMessage("§cO bate-papo já está desligado.");
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("limpar")) {
                        for (int i = 0; i < 90; i++) {
                            Bukkit.broadcastMessage("");
                        }
                    }
                }
            }
        }

        return false;
    }
}