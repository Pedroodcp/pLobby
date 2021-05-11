package br.com.pedrodcp.plobby.Eventos;

import br.com.pedrodcp.pexternal.API.PluginAPI;
import br.com.pedrodcp.pexternal.models.Account;
import br.com.pedrodcp.plobby.Main;
import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static br.com.pedrodcp.plobby.Main.*;
import static br.com.pedrodcp.plobby.Main.getInstance;

public class JoinAndQuit implements Listener {

    @EventHandler
    public void Join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        getPlayerManager().loadPlayer(p.getPlayer().getName());
        Date date = new Date();
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");

        if (PluginAPI.getAccount(p) == null) {
            Account.accounts.add(new Account(e.getPlayer().getName().toLowerCase(), 0));
            getPlayerManager().getPlayer(p.getName()).setID(Account.accounts.size());
            getPlayerManager().getPlayer(p.getName()).setFirstLogin(data.format(date) + " às " + hora.format(date));
            getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
            sc.scoreGame(p);

        } else {
            if (getPlayerManager().getPlayer(p.getName()).getID() == 0) {
                getPlayerManager().getPlayer(p.getName()).setID(Account.accounts.size());
                getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));
                sc.scoreGame(p);
            }
        }

        Location destino = new Location(Bukkit.getServer().getWorld(getInstance().getConfig().getString("Spawn.world")), getInstance().getConfig().getDouble("Spawn.X"), getInstance().getConfig().getDouble("Spawn.Y"), getInstance().getConfig().getDouble("Spawn.Z"), (float) getInstance().getConfig().getInt("Spawn.yaw"), (float) getInstance().getConfig().getInt("Spawn.pitch"));
        p.teleport(destino);

        //Pega todos os jogadores do servidor, verifica quais tem a opção de jogadores desativados e esconde o
        //jogador que entrou
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (Main.getPlayerManager().getPlayer(all.getName()).getJogadoresOption().equals("off")) {
                if (!p.hasPermission("lobby.prioridade")) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                        all.hidePlayer(p);
                    }, 2 * 5L);
                } else {
                    if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                        if (!all.hasPermission("vanish.use"))
                            all.hidePlayer(p);
                    }
                }
            }
        }

        //Toda vez que um jogador entrar no servidor, ele atualiza a ScoreBoard
        for (Player all : Bukkit.getOnlinePlayers()) {
            sc.scoreGame(all);
        }

        //Pega todos os jogadores do servidor, verifica se o jogador que entrou tem a permissão "vanish.use", se o jogador
        //tiver, vai verificar se ele está com o vanish ativado, se sim, o sistema vai pegar os jogadores que não possuem a permissão "vanish.use"
        //e esconder o jogador que entrou dos jogadores que não possuem a permissão
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("vanish.use")) {
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    if (!all.hasPermission("vanish.use")) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                            all.hidePlayer(p);
                        }, 2 * 5L);
                    }
                }
            }
        }

        //Verifica se o jogador que entrou tem a opção de jogadores ativados, pega todos os jogadores do servidor
        //e verifica quais deles tem o modo vanish desativado para que todos os jogadores sejam mostrados ao jogador que entrou
        if (Main.getPlayerManager().getPlayer(p.getName()).getJogadoresOption().equals("on")) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (Main.getPlayerManager().getPlayer(all.getName()).getVanishOption().equals("off")) {
                    p.showPlayer(all);

                    //Verifica se o jogador que entrou tem permissão de "vanish.use", se não tiver
                    //ele vai esconder todos os jogadores que estão de vanish
                } else {
                    if (!p.hasPermission("vanish.use"))
                        p.hidePlayer(all);
                }
            }
        } else {

            //Pega todos os jogadores do servidor, o sistema verifica se eles não tem a opção de "lobby.prioridade", verifica se tem
            //jogadores com o vanish ativado e depois esconde todos os jogadores
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!all.hasPermission("lobby.prioridade")) {
                    p.hidePlayer(all);

                    //Se o jogador tiver a permissão "lobby.prioridade", o sistema verifica se o jogador que entrou tem
                    //o vanish ativado, se sim, todos os jogadores vão esconder o jogador que entrou
                } else {
                    if (Main.getPlayerManager().getPlayer(all.getName()).getVanishOption().equals("on")) {
                        if (!p.hasPermission("vanish.use"))
                            p.hidePlayer(all);
                    }
                }
            }
        }

        //Verifica se o jogador que entrou tem a opção de voar no Lobby ativado
        //Se tiver:
        if (Main.getPlayerManager().getPlayer(p.getName()).getFlyOption().equals("on")) {
            if (p.hasPermission("lobby.voar")) {
                p.setAllowFlight(true);

                //Se o jogador não tiver a permissão "lobby.voar":
            } else {
                Main.getPlayerManager().getPlayer(p.getName()).setFlyOption("off");
                p.setAllowFlight(false);
            }

            //Se o jogador não estiver com a opção de voar no Lobby ativada:
        } else {
            p.setAllowFlight(false);
        }

        p.getInventory().clear();
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealthScale(20);

        ItemStack i1 = new Item(Material.COMPASS, 1, (short) 0)
                .setName("§aModos de Jogo")
                .setLore(Collections.singletonList("§eEscolha seu modo de jogo!"))
                .getItemStack();

        p.getInventory().setItem(0, i1);

        ItemStack i2 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta metaitem = (SkullMeta) i2.getItemMeta();
        metaitem.setOwner(p.getName());
        metaitem.setDisplayName("§aPerfil");
        metaitem.setLore(Collections.singletonList("§eClique para ver seu perfil."));
        i2.setItemMeta(metaitem);

        p.getInventory().setItem(1, i2);

        ItemStack i3 = new Item(Material.CHEST, 1, (short) 0)
                .setName("§cCosméticos")
                .setLore(Arrays.asList("§eClique para abrir o menu", "§ede cosméticos. "))
                .getItemStack();

        p.getInventory().setItem(4, i3);

        if (Main.getPlayerManager().getPlayer(p.getName()).getJogadoresOption().equals("on")) {
            ItemStack i4 = new Item(Material.INK_SACK, 1, (short) 10)
                    .setName("§fJogadores: §aON")
                    .setLore(Arrays.asList("§eClique para §cdesativar §eos", "§ejogadores do lobby."))
                    .getItemStack();

            p.getInventory().setItem(7, i4);

        } else {
            ItemStack i4 = new Item(Material.INK_SACK, 1, (short) 8)
                    .setName("§fJogadores: §cOFF")
                    .setLore(Arrays.asList("§eClique para §aativar §eos", "§ejogadores do lobby."))
                    .getItemStack();

            p.getInventory().setItem(7, i4);
        }

        ItemStack i5 = new Item(Material.NETHER_STAR, 1, (short) 0)
                .setName("§aLobbies")
                .setLore(Collections.singletonList("§eClique para mudar de lobby."))
                .getItemStack();

        p.getInventory().setItem(8, i5);

        if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
            e.setJoinMessage(null);
            p.sendMessage("§aConectando...");
            if (p.hasPermission("staff.admin")) {
                p.setDisplayName("§4[Admin] " + p.getName());
                return;
            }
            if (p.hasPermission("staff.gerente")) {
                p.setDisplayName("§9[Gerente] " + p.getName());
                return;
            }
            if (p.hasPermission("staff.coord")) {
                p.setDisplayName("§5[Coordenador] " + p.getName());
                return;
            }
            if (p.hasPermission("staff.moderador")) {
                p.setDisplayName("§2[Moderador] " + p.getName());
                return;
            }
            if (p.hasPermission("staff.ajudante")) {
                p.setDisplayName("§a[Ajudante] " + p.getName());
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                }
                return;
            }
            if (p.hasPermission("group.construtor")) {
                p.setDisplayName("§e[Construtor] " + p.getName());
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                }
                return;
            }
            if (p.hasPermission("group.youtuber")) {
                p.setDisplayName("§c[YouTuber] " + p.getName());
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                }
                return;
            }
            if (p.hasPermission("group.lendario")) {
                p.setDisplayName("§6[Lendário] " + p.getName());
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                }
                return;
            }
            if (p.hasPermission("group.mvp")) {
                p.setDisplayName("§b[MVP] " + p.getName());
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                }
                return;
            }
            if (p.hasPermission("group.vip")) {
                p.setDisplayName("§a[VIP] " + p.getName());
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                }
                return;
            }
            if (p.hasPermission("group.membro")) {
                p.setDisplayName("§7" + p.getName());
                if (Main.getPlayerManager().getPlayer(p.getName()).getVanishOption().equals("on")) {
                    Main.getPlayerManager().getPlayer(p.getName()).setVanishOption("off");
                }
            }

        } else {
            if (p.hasPermission("staff.admin")) {
                p.setDisplayName("§4[Admin] " + p.getName());
                e.setJoinMessage("§4[Admin] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("staff.gerente")) {
                p.setDisplayName("§9[Gerente] " + p.getName());
                e.setJoinMessage("§9[Gerente] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("staff.coord")) {
                p.setDisplayName("§5[Coordenador] " + p.getName());
                e.setJoinMessage("§5[Coordenador] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("staff.moderador")) {
                p.setDisplayName("§2[Moderador] " + p.getName());
                e.setJoinMessage("§2[Moderador] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("staff.ajudante")) {
                p.setDisplayName("§a[Ajudante] " + p.getName());
                e.setJoinMessage("§a[Ajudante] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("group.construtor")) {
                p.setDisplayName("§e[Construtor] " + p.getName());
                e.setJoinMessage("§e[Construtor] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("group.youtuber")) {
                p.setDisplayName("§c[YouTuber] " + p.getName());
                e.setJoinMessage("§c[YouTuber] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("group.lendario")) {
                p.setDisplayName("§6[Lendário] " + p.getName());
                e.setJoinMessage("§6[Lendário] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("group.mvp")) {
                p.setDisplayName("§b[MVP] " + p.getName());
                e.setJoinMessage("§b[MVP] " + p.getName() + " §6entrou neste lobby!");
                return;
            }
            if (p.hasPermission("group.vip")) {
                p.setDisplayName("§a[VIP] " + p.getName());
                e.setJoinMessage("§a[VIP] " + p.getName() + " §6entrou neste lobby!");
                return;

            }
            if (p.hasPermission("group.membro")) {
                p.setDisplayName("§7" + p.getName());
                e.setJoinMessage(null);
            }
        }
    }

    @EventHandler
    public void Quit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(null);

        Main.getPlayerManager().salvarDados(getPlayerManager().getPlayer(p.getName()));

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (Player all : Bukkit.getOnlinePlayers()) {
                sc.scoreGame(all);
            }
        }, 2 * 20L);
    }

}
