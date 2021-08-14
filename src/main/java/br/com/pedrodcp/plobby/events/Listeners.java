package br.com.pedrodcp.plobby.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static br.com.pedrodcp.plobby.Main.getInstance;
import static br.com.pedrodcp.plobby.commands.build.builder;

public class Listeners implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != null) {
            if (!builder.contains(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!builder.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (!builder.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            e.setCancelled(true);

        } else {
            e.setCancelled(true);
            Player p = (Player) e.getEntity();
            Location destino = new Location(Bukkit.getServer().getWorld(getInstance().getConfig().getString("Spawn.world")), getInstance().getConfig().getDouble("Spawn.X"), getInstance().getConfig().getDouble("Spawn.Y"), getInstance().getConfig().getDouble("Spawn.Z"), (float) getInstance().getConfig().getInt("Spawn.yaw"), (float) getInstance().getConfig().getInt("Spawn.pitch"));
            p.teleport(destino);
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (e.getNewGameMode() == GameMode.CREATIVE) {
            if (!builder.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (builder.contains(p)) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

}
