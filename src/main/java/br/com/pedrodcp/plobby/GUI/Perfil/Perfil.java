package br.com.pedrodcp.plobby.GUI.Perfil;

import static br.com.pedrodcp.plobby.Eventos.InteractEvent.*;

import br.com.pedrodcp.pexternal.API.PluginAPI;
import br.com.pedrodcp.plobby.Main;
import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Collections;

public class Perfil {

    private String formatarCash(String jogador) {
        PluginAPI formatter = new PluginAPI();
        double CashDoJogador = PluginAPI.getAccount(jogador).getCash();
        return formatter.formatNumber(CashDoJogador);
    }

    private String formatarCashDouble(double quantia) {
        PluginAPI formatter = new PluginAPI();
        return formatter.formatNumber(quantia);
    }

    private Inventory inventory;

    public Perfil() {
        inventory = Bukkit.createInventory(null, 9 * 3, "§8Perfil");
        colocarItens();
    }

    public void colocarItens() {
        ItemStack i1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta metaitem = (SkullMeta) i1.getItemMeta();
        metaitem.setOwner(nickname);
        Player p = Bukkit.getPlayer(nickname);
        metaitem.setDisplayName("§aInformações Pessoais");
        
        if (p.hasPermission("staff.admin")) {
            metaitem.setLore(Arrays.asList("§fGrupo: §4Admin", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
        } else {
            if (p.hasPermission("staff.gerente")) {
                metaitem.setLore(Arrays.asList("§fGrupo: §9Gerente", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
            } else {
                if (p.hasPermission("staff.coord")) {
                    metaitem.setLore(Arrays.asList("§fGrupo: §5Coordenador", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                } else {
                    if (p.hasPermission("staff.moderador")) {
                        metaitem.setLore(Arrays.asList("§fGrupo: §2Moderador", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                    }
                    if (p.hasPermission("staff.ajudante")) {
                        metaitem.setLore(Arrays.asList("§fGrupo: §aAjudante", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                    } else {
                        if (p.hasPermission("group.construtor")) {
                            metaitem.setLore(Arrays.asList("§fGrupo: §eConstrutor", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                        }
                        if (p.hasPermission("group.youtuber")) {
                            metaitem.setLore(Arrays.asList("§fGrupo: §cYouTuber", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                        } else {
                            if (p.hasPermission("group.lendario")) {
                                metaitem.setLore(Arrays.asList("§fGrupo: §6Lendário", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                            } else {
                                if (p.hasPermission("group.mvp")) {
                                    metaitem.setLore(Arrays.asList("§fGrupo: §bMVP", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                                } else {
                                    if (p.hasPermission("group.vip")) {
                                        metaitem.setLore(Arrays.asList("§fGrupo: §aVIP", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                                    } else {
                                        if (p.hasPermission("group.membro")) {
                                            metaitem.setLore(Arrays.asList("§fGrupo: §7Membro", "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        i1.setItemMeta(metaitem);
        inventory.setItem(13, i1);

        ItemStack i2 = new Item(Material.REDSTONE_COMPARATOR, 1, (short) 0)
                .setName("§aPreferências")
                .setLore(Collections.singletonList("§eControle suas preferências pessoais."))
                .getItemStack();

        inventory.setItem(11, i2);

        if (p.hasPermission("group.membro")) {
            ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                    .setName("§cEvolua seu VIP")
                    .setLore(Collections.singletonList("§7Você não possui evoluções disponíveis."))
                    .getItemStack();

            inventory.setItem(15, evoluções);

        } else {
            if (p.hasPermission("group.vip")) {
                if (PluginAPI.getAccount(p.getName()).getCash() >= 4500) {
                    ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                            .setName("§aEvolua seu VIP")
                            .setLore(Arrays.asList("", "§eVocê possui §6Cash §esuficiente para", "§erealizar a evolução.", "", "§7Clique para evoluir ao grupo §bMVP", "§7por §64.500 Cash§7.", "", "§eClique para realizar a evolução."))
                            .getItemStack();

                    inventory.setItem(15, evoluções);

                } else {
                    ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                            .setName("§cEvolua seu VIP")
                            .setLore(Arrays.asList("", "§cVocê não possui §6Cash §csuficiente para realizar", "§ca evolução para o grupo §bMVP", "", "§7Compre §64.500 Cash §7em nossa loja.", "", "§eClique para acessar a loja."))
                            .getItemStack();

                    inventory.setItem(15, evoluções);
                }

            } else {
                if (p.hasPermission("group.mvp")) {
                    if (PluginAPI.getAccount(p.getName()).getCash() >= 9500) {
                        ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                                .setName("§aEvolua seu VIP")
                                .setLore(Arrays.asList("", "§eVocê possui §6Cash §esuficiente para", "§erealizar a evolução.", "", "§7Clique para evoluir ao grupo §6Lendário", "§7por §69.500 Cash§7.", "", "§eClique para realizar a evolução."))
                                .getItemStack();

                        inventory.setItem(15, evoluções);

                    } else {
                        ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                                .setName("§cEvolua seu VIP")
                                .setLore(Arrays.asList("", "§cVocê não possui §6Cash §csuficiente para realizar", "§ca evolução para o grupo §6Lendário", "", "§7Compre §69.500 Cash §7em nossa loja.", "", "§eClique para acessar a loja."))
                                .getItemStack();

                        inventory.setItem(15, evoluções);
                    }

                } else {
                    ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                            .setName("§cEvolua seu VIP")
                            .setLore(Collections.singletonList("§7Você não possui evoluções disponíveis."))
                            .getItemStack();

                    inventory.setItem(15, evoluções);
                }
            }
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
