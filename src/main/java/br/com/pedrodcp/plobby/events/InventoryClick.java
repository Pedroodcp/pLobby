package br.com.pedrodcp.plobby.events;

import br.com.pedrodcp.pexternal.api.PluginAPI;
import br.com.pedrodcp.plobby.gui.Compass;
import br.com.pedrodcp.plobby.gui.Lobbies;
import br.com.pedrodcp.plobby.gui.profile.evolutions.Confirmar;
import br.com.pedrodcp.plobby.gui.profile.Perfil;
import br.com.pedrodcp.plobby.gui.profile.Preferências;
import br.com.pedrodcp.plobby.Main;
import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static br.com.pedrodcp.plobby.commands.build.*;
import static br.com.pedrodcp.plobby.Main.getPlayerManager;

public class InventoryClick implements Listener {

    public static String nickname;

    HashMap<Player, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!builder.contains(e.getWhoClicked())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            ItemStack i = e.getCurrentItem();

            if (e.getInventory().getName().equalsIgnoreCase("§8Modos de Jogo")) {
                if (i.getType() == Material.BOOK) {
                    p.closeInventory();
                    p.sendMessage("");
                    p.sendMessage("§cEste servidor se encontra em manutenção.");
                    p.sendMessage("");
                }
            } else {
                if (e.getInventory().getName().equalsIgnoreCase("§8Lobbies")) {
                    if (i.getType() == Material.INK_SACK) {
                        p.sendMessage("§cVocê já está neste lobby.");
                        p.closeInventory();
                    }
                } else {
                    if (e.getInventory().getName().equalsIgnoreCase("§8Perfil")) {
                        if (i.getType() == Material.REDSTONE_COMPARATOR) {
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5F, 1.0F);
                            p.openInventory(new Preferências().getInventory());
                            nickname = p.getName();

                        } else {
                            if (i.getType() == Material.SKULL_ITEM) {
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5F, 1.0F);

                            } else {
                                if (i.getType() == Material.EXP_BOTTLE) {
                                    if (p.hasPermission("group.membro")) {
                                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 5F, 1.0F);
                                    } else {
                                        if (p.hasPermission("group.vip")) {
                                            if (PluginAPI.getAccount(p.getName()).getCash() >= 4500) {
                                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5F, 1.0F);
                                                p.openInventory(new Confirmar().getInventory());
                                            } else {
                                                p.closeInventory();
                                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 5F, 1.0F);
                                                p.sendMessage("");
                                                p.sendMessage("§eCompre pacotes de §6MinePoints §eem nossa loja, acesse §nmineup.com.br/loja");
                                                p.sendMessage("");
                                            }
                                        } else {
                                            if (p.hasPermission("group.mvp")) {
                                                if (PluginAPI.getAccount(p.getName()).getCash() >= 9500) {
                                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5F, 1.0F);
                                                    p.openInventory(new Confirmar().getInventory());
                                                } else {
                                                    p.closeInventory();
                                                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 5F, 1.0F);
                                                    p.sendMessage("");
                                                    p.sendMessage("§eCompre pacotes de §6MinePoints §eem nossa loja, acesse §nmineup.com.br/loja");
                                                    p.sendMessage("");
                                                }
                                            } else {
                                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 5F, 1.0F);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        if (e.getInventory().getName().equalsIgnoreCase("§8Preferências")) {
                            if (i.getType() == Material.RAW_FISH) {
                                if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                    return;
                                }
                                cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                if (Main.getPlayerManager().getPlayer(p.getName()).getJogadoresOption().equals("off")) {
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
                                    p.openInventory(new Preferências().getInventory());
                                    p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);

                                } else {
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
                                    p.openInventory(new Preferências().getInventory());
                                    p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                }
                            } else {
                                if (i.getType() == Material.MAP) {
                                    if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                        return;
                                    }
                                    cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                    if (Main.getPlayerManager().getPlayer(p.getName()).getTellOption().equals("off")) {
                                        Main.getPlayerManager().getPlayer(p.getName()).setTellOption("on");
                                        Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                        p.openInventory(new Preferências().getInventory());
                                        p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                    } else {
                                        Main.getPlayerManager().getPlayer(p.getName()).setTellOption("off");
                                        Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                        p.openInventory(new Preferências().getInventory());
                                        p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                    }
                                } else {
                                    if (i.getType() == Material.FEATHER) {
                                        if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                            return;
                                        }
                                        cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                        if (Main.getPlayerManager().getPlayer(p.getName()).getFlyOption().equals("off")) {
                                            Main.getPlayerManager().getPlayer(p.getName()).setFlyOption("on");
                                            Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                            p.setAllowFlight(true);
                                            p.openInventory(new Preferências().getInventory());
                                            p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                        } else {
                                            Main.getPlayerManager().getPlayer(p.getName()).setFlyOption("off");
                                            Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                            p.setAllowFlight(false);
                                            p.openInventory(new Preferências().getInventory());
                                            p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                        }
                                    } else {
                                        if (i.getType() == Material.POTION) {
                                            if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                                return;
                                            }
                                            cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                            if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("off")) {
                                                Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("on");
                                                Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                for (Player all : Bukkit.getOnlinePlayers()) {
                                                    all.hidePlayer(p);
                                                }
                                                p.openInventory(new Preferências().getInventory());
                                                p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);

                                            } else {
                                                Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                                                Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                for (Player all : Bukkit.getOnlinePlayers()) {
                                                    all.showPlayer(p);
                                                }
                                                p.openInventory(new Preferências().getInventory());
                                                p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                            }
                                        } else {
                                            if (i.getType() == Material.STAINED_GLASS_PANE) {
                                                if (i.getItemMeta().getDisplayName().contains("Visibilidade")) {
                                                    if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                                        return;
                                                    }
                                                    cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                                    if (Main.getPlayerManager().getPlayer(p.getName()).getJogadoresOption().equals("off")) {
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
                                                        p.openInventory(new Preferências().getInventory());
                                                        p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);

                                                    } else {
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
                                                        p.openInventory(new Preferências().getInventory());
                                                        p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                    }
                                                } else {
                                                    if (i.getItemMeta().getDisplayName().contains("Mensagens Privadas")) {
                                                        if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                                            return;
                                                        }
                                                        cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                                        if (Main.getPlayerManager().getPlayer(p.getName()).getTellOption().equals("off")) {
                                                            Main.getPlayerManager().getPlayer(p.getName()).setTellOption("on");
                                                            Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                            p.openInventory(new Preferências().getInventory());
                                                            p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                        } else {
                                                            Main.getPlayerManager().getPlayer(p.getName()).setTellOption("off");
                                                            Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                            p.openInventory(new Preferências().getInventory());
                                                            p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                        }
                                                    } else {
                                                        if (i.getItemMeta().getDisplayName().contains("Voar no Lobby")) {
                                                            if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                                                return;
                                                            }
                                                            cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                                            if (Main.getPlayerManager().getPlayer(p.getName()).getFlyOption().equals("off")) {
                                                                Main.getPlayerManager().getPlayer(p.getName()).setFlyOption("on");
                                                                Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                                p.setAllowFlight(true);
                                                                p.openInventory(new Preferências().getInventory());
                                                                p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                            } else {
                                                                Main.getPlayerManager().getPlayer(p.getName()).setFlyOption("off");
                                                                Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                                p.setAllowFlight(false);
                                                                p.openInventory(new Preferências().getInventory());
                                                                p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                            }
                                                        } else {
                                                            if (i.getItemMeta().getDisplayName().contains("Invisibilidade")) {
                                                                if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                                                    return;
                                                                }
                                                                cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                                                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("off")) {
                                                                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("on");
                                                                    Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                                                        all.hidePlayer(p);
                                                                    }
                                                                    p.openInventory(new Preferências().getInventory());
                                                                    p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                                } else {
                                                                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                                                                    Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                                                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                                                        all.showPlayer(p);
                                                                    }
                                                                    p.openInventory(new Preferências().getInventory());
                                                                    p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (i.getType() == Material.ARROW) {
                                                    p.openInventory(new Perfil().getInventory());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (e.getInventory().getName().equalsIgnoreCase("§8Evolução")) {
                                if (i.getType() == Material.WOOL) {

                                    ConsoleCommandSender cmd = Bukkit.getServer().getConsoleSender();

                                    switch (i.getDurability()) {
                                        case 5:
                                            if (p.hasPermission("group.vip")) {
                                                Bukkit.dispatchCommand(cmd, "lp user " + p.getName() + " parent add mvp");
                                                Bukkit.dispatchCommand(cmd, "lp user " + p.getName() + " parent remove vip");
                                                PluginAPI.getAccount(p.getName()).setCash(PluginAPI.getAccount(p.getName()).getCash() - 4500);
                                                p.closeInventory();
                                                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 5F, 1.0F);
                                                p.sendMessage("");
                                                p.sendMessage("§eParabéns, agora você é §6MVP§e! Basta relogar do servidor para receber seu grupo.");
                                                p.sendMessage("");
                                                return;

                                            } else {
                                                if (p.hasPermission("group.mvp")) {
                                                    Bukkit.dispatchCommand(cmd, "lp user " + p.getName() + " parent add pro");
                                                    Bukkit.dispatchCommand(cmd, "lp user " + p.getName() + " parent remove mvp");
                                                    PluginAPI.getAccount(p.getName()).setCash(PluginAPI.getAccount(p.getName()).getCash() - 9500);
                                                    p.closeInventory();
                                                    p.playSound(p.getLocation(), Sound.VILLAGER_YES, 5F, 1.0F);
                                                    p.sendMessage("");
                                                    p.sendMessage("§eParabéns, agora você é §5PRO§e! Basta relogar do servidor para receber seu grupo.");
                                                    p.sendMessage("");
                                                    return;
                                                }
                                            }
                                        case 14:
                                            p.openInventory(new Perfil().getInventory());
                                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 5F, 1.0F);
                                    }
                                }
                            } else {
                                if (i.getType() == Material.COMPASS) {
                                    p.openInventory(new Compass().getInventory());
                                } else {
                                    if (i.getType() == Material.SKULL_ITEM) {
                                        if (i.getItemMeta().getDisplayName().equals("§aPerfil")) {
                                            p.openInventory(new Perfil().getInventory());
                                        }
                                    } else {
                                        if (i.getType() == Material.CHEST) {
                                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 5F, 1.0F);
                                            p.sendMessage("§cAtualmente, este sistema está em desenvolvimento.");
                                        } else {
                                            if (i.getType() == Material.INK_SACK) {
                                                if (cooldown.containsKey(p) && !(System.currentTimeMillis() >= cooldown.get(p))) {
                                                    return;
                                                }
                                                cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
                                                if (Main.getPlayerManager().getPlayer(p.getName()).getJogadoresOption().equals("off")) {
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
                                                    p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                    p.sendMessage("§aVisibilidade dos jogadores ativada.");
                                                    return;

                                                } else {
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
                                                    p.playSound(p.getLocation(), Sound.CLICK, 5F, 1.0F);
                                                    p.sendMessage("§cVisibilidade dos jogadores desativada.");
                                                }
                                            } else {
                                                if (i.getType() == Material.NETHER_STAR) {
                                                    p.openInventory(new Lobbies().getInventory());
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
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory().getType() == InventoryType.BEACON) {
            e.setCancelled(true);

        } else {
            if (e.getInventory().getType() == InventoryType.ANVIL) {
                e.setCancelled(true);

            } else {
                if (e.getInventory().getType() == InventoryType.CREATIVE) {
                    if (!builder.contains(p)) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage("§cVocê não está com o modo construtor ativado.");
                    }
                }
            }
        }
    }

}
