package br.com.pedrodcp.plobby.commands;

import static br.com.pedrodcp.plobby.Main.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if (!(s instanceof Player)) return false;

        if (c.getName().equalsIgnoreCase("spawn")) {
            Player p = (Player) s;

            Location destino = new Location(Bukkit.getServer().getWorld(getInstance().getConfig().getString("Spawn.world")), getInstance().getConfig().getDouble("Spawn.X"), getInstance().getConfig().getDouble("Spawn.Y"), getInstance().getConfig().getDouble("Spawn.Z"), (float) getInstance().getConfig().getInt("Spawn.yaw"), (float) getInstance().getConfig().getInt("Spawn.pitch"));
            p.teleport(destino);
        }

        return false;
    }
}
