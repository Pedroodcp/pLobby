package br.com.pedrodcp.plobby.Eventos;

import static br.com.pedrodcp.plobby.Comandos.build.*;
import static br.com.pedrodcp.plobby.Main.getInstance;
import static br.com.pedrodcp.plobby.Main.getPlayerManager;

import br.com.pedrodcp.plobby.GUI.Compass;
import br.com.pedrodcp.plobby.GUI.Lobbies;
import br.com.pedrodcp.plobby.GUI.Perfil.Perfil;
import br.com.pedrodcp.plobby.Main;
import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class InteractEvent implements Listener {

    HashMap<Player, Long> cooldown = new HashMap<>();
    public static String nickname;

    @EventHandler
    public void Interact(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() != null) {
            if (!builder.contains(p)) {
                if (e.getItem().getType().equals(Material.COMPASS)) {
                    p.openInventory(new Compass().getInventory());

                } else {
                    if (e.getItem().getType().equals(Material.SKULL_ITEM)) {
                        nickname = p.getName();
                        p.openInventory(new Perfil().getInventory());

                    } else {
                        if (e.getItem().getType().equals(Material.CHEST)) {
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 5F, 1.0F);
                            p.sendMessage("§cAtualmente, este sistema está em desenvolvimento.");

                        } else {
                            if (e.getItem().getType().equals(Material.INK_SACK)) {
                                if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                    return;
                                }
                                cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                switch (e.getItem().getDurability()) {
                                    case 8:
                                        ItemStack ativado = new Item(Material.INK_SACK, 1, (short) 10)
                                                .setName("§fJogadores: §aON")
                                                .setLore(Arrays.asList("§eClique para §cdesativar §eos", "§ejogadores do lobby."))
                                                .getItemStack();

                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            if (Main.getPlayerManager().getPlayer(all.getName()).getVanishOption().equals("off"))
                                                p.showPlayer(all);
                                        }
                                        Main.getPlayerManager().getPlayer(p.getName()).setJogadoresOption("on");
                                        Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                        p.getInventory().setItem(7, ativado);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5F, 1.0F);
                                        p.sendMessage("§aVisibilidade dos jogadores ativada.");
                                        return;

                                    case 10:
                                        ItemStack desativado = new Item(Material.INK_SACK, 1, (short) 8)
                                                .setName("§fJogadores: §cOFF")
                                                .setLore(Arrays.asList("§eClique para §aativar §eos", "§ejogadores do lobby."))
                                                .getItemStack();

                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            if (!all.hasPermission("lobby.prioridade"))
                                                p.hidePlayer(all);
                                        }
                                        Main.getPlayerManager().getPlayer(p.getName()).setJogadoresOption("off");
                                        Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                        p.getInventory().setItem(7, desativado);
                                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5F, 1.0F);
                                        p.sendMessage("§cVisibilidade dos jogadores desativada.");
                                }
                            } else {
                                if (e.getItem().getType().equals(Material.NETHER_STAR)) {
                                    p.openInventory(new Lobbies().getInventory());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != null) {
            if (!builder.contains(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockBuild(BlockPlaceEvent e) {
        if (!builder.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void DropItem(PlayerDropItemEvent e) {
        if (!builder.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void Damage(EntityDamageEvent e) {
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
    public void Gamemode(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (e.getNewGameMode() == GameMode.CREATIVE) {
            if (!builder.contains(p)) {
                e.setCancelled(true);
                p.sendMessage("§cVocê não está com o modo construtor ativado.");
            }
        }
    }

    @EventHandler
    public void Toggle(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

}
