package br.com.pedrodcp.plobby.gui.profile;

import br.com.pedrodcp.pexternal.api.PluginAPI;
import br.com.pedrodcp.plobby.utils.Item;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Collections;

import static br.com.pedrodcp.plobby.events.InteractEvent.*;

public class Perfil {

    private String formatarCash(String jogador) {
        PluginAPI formatter = new PluginAPI();
        double CashDoJogador = PluginAPI.getAccount(jogador).getCash();
        return formatter.formatNumber(CashDoJogador);
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
        metaitem.setLore(Arrays.asList("§fGrupo: " + LuckPermsProvider.get().getGroupManager().getGroup(LuckPermsProvider.get().getUserManager().getUser(p.getName()).getPrimaryGroup()).getDisplayName(), "§fCash: §7" + formatarCash(nickname), "", "§eredeteste.com.br"));

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
                            .setLore(Arrays.asList("", "§eVocê possui §6MinePoints §esuficientes para", "§erealizar a evolução.", "", "§7Clique para evoluir ao grupo §bMVP", "§7por §64.500 Cash§7.", "", "§eClique para realizar a evolução."))
                            .getItemStack();

                    inventory.setItem(15, evoluções);

                } else {
                    ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                            .setName("§cEvolua seu VIP")
                            .setLore(Arrays.asList("", "§cVocê não possui §6MinePoints §csuficientes para realizar", "§ca evolução para o grupo §bMVP", "", "§7Compre §64.500 Cash §7em nossa loja.", "", "§eClique para acessar a loja."))
                            .getItemStack();

                    inventory.setItem(15, evoluções);
                }

            } else {
                if (p.hasPermission("group.mvp")) {
                    if (PluginAPI.getAccount(p.getName()).getCash() >= 9500) {
                        ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                                .setName("§aEvolua seu VIP")
                                .setLore(Arrays.asList("", "§eVocê possui §6MinePoints §esuficientes para", "§erealizar a evolução.", "", "§7Clique para evoluir ao grupo §5PRO", "§7por §69.500 Cash§7.", "", "§eClique para realizar a evolução."))
                                .getItemStack();

                        inventory.setItem(15, evoluções);

                    } else {
                        ItemStack evoluções = new Item(Material.EXP_BOTTLE, 1, (short) 0)
                                .setName("§cEvolua seu VIP")
                                .setLore(Arrays.asList("", "§cVocê não possui §6MinePoints §csuficientes para realizar", "§ca evolução para o grupo §5PRO", "", "§7Compre §69.500 Cash §7em nossa loja.", "", "§eClique para acessar a loja."))
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
