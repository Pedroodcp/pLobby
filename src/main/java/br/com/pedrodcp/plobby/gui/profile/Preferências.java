package br.com.pedrodcp.plobby.gui.profile;

import br.com.pedrodcp.plobby.Main;
import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

import static br.com.pedrodcp.plobby.events.InteractEvent.*;

public class Preferências {

    private Inventory inventory;

    public Preferências() {
        inventory = Bukkit.createInventory(null, 9 * 6, "§8Preferências");
        colocarItens();
    }

    public void colocarItens() {
        if (Main.getPlayerManager().getPlayer(nickname).getJogadoresOption().equals("on")) {
            ItemStack i1 = new Item(Material.RAW_FISH, 1, (short) 3)
                    .setName("§aVisibilidade")
                    .setLore(Collections.singletonList("§7Ver outros jogadores nos lobbies."))
                    .getItemStack();

            ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 5)
                    .setName("§aVisibilidade")
                    .setLore(Collections.singletonList("§fEstado: §aLigado"))
                    .getItemStack();

            inventory.setItem(1, i1);
            inventory.setItem(10, i2);

        } else {
            ItemStack i1 = new Item(Material.RAW_FISH, 1, (short) 3)
                    .setName("§cVisibilidade")
                    .setLore(Collections.singletonList("§7Ver outros jogadores nos lobbies."))
                    .getItemStack();

            ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 14)
                    .setName("§cVisibilidade")
                    .setLore(Collections.singletonList("§fEstado: §cDesligado"))
                    .getItemStack();

            inventory.setItem(1, i1);
            inventory.setItem(10, i2);
        }
        if (Main.getPlayerManager().getPlayer(nickname).getTellOption().equals("on")) {
            ItemStack i1 = new Item(Material.MAP, 1, (short) 0)
                    .setName("§aMensagens Privadas")
                    .setLore(Collections.singletonList("§7Receber mensagens privadas."))
                    .getItemStack();

            ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 5)
                    .setName("§aMensagens Privadas")
                    .setLore(Collections.singletonList("§fEstado: §aLigado"))
                    .getItemStack();

            inventory.setItem(2, i1);
            inventory.setItem(11, i2);

        } else {
            ItemStack i1 = new Item(Material.MAP, 1, (short) 0)
                    .setName("§cMensagens Privadas")
                    .setLore(Collections.singletonList("§7Receber mensagens privadas."))
                    .getItemStack();

            ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 14)
                    .setName("§cMensagens Privadas")
                    .setLore(Collections.singletonList("§fEstado: §cDesligado"))
                    .getItemStack();

            inventory.setItem(2, i1);
            inventory.setItem(11, i2);
        }

        Player p = Bukkit.getPlayer(nickname);
        if (p.hasPermission("lobby.voar")) {
            if (Main.getPlayerManager().getPlayer(nickname).getFlyOption().equals("on")) {
                ItemStack i1 = new Item(Material.FEATHER, 1, (short) 0)
                        .setName("§aVoar no Lobby")
                        .setLore(Collections.singletonList("§7Habilitar automaticamente o voo nos lobbies."))
                        .getItemStack();

                ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 5)
                        .setName("§aVoar no Lobby")
                        .setLore(Collections.singletonList("§fEstado: §aLigado"))
                        .getItemStack();

                inventory.setItem(3, i1);
                inventory.setItem(12, i2);

            } else {
                ItemStack i1 = new Item(Material.FEATHER, 1, (short) 0)
                        .setName("§cVoar no Lobby")
                        .setLore(Collections.singletonList("§7Habilitar automaticamente o voo nos lobbies."))
                        .getItemStack();

                ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 14)
                        .setName("§cVoar no Lobby")
                        .setLore(Collections.singletonList("§fEstado: §cDesligado"))
                        .getItemStack();

                inventory.setItem(3, i1);
                inventory.setItem(12, i2);
            }
            if (p.hasPermission("vanish.use")) {
                if (Main.getPlayerManager().getPlayer(nickname).getVanishOption().equals("on")) {
                    ItemStack i1 = new Item(Material.POTION, 1, (short) 8270)
                            .setName("§aInvisibilidade")
                            .setLore(Collections.singletonList("§7Ficar invisível para todos os jogadores."))
                            .hideEnchant()
                            .getItemStack();

                    ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 5)
                            .setName("§aInvisibilidade")
                            .setLore(Collections.singletonList("§fEstado: §aLigado"))
                            .getItemStack();

                    inventory.setItem(4, i1);
                    inventory.setItem(13, i2);

                } else {
                    ItemStack i1 = new Item(Material.POTION, 1, (short) 8270)
                            .setName("§cInvisibilidade")
                            .setLore(Collections.singletonList("§7Ficar invisível para todos os jogadores."))
                            .hideEnchant()
                            .getItemStack();

                    ItemStack i2 = new Item(Material.STAINED_GLASS_PANE, 1, (short) 14)
                            .setName("§cInvisibilidade")
                            .setLore(Collections.singletonList("§fEstado: §cDesligado"))
                            .getItemStack();

                    inventory.setItem(4, i1);
                    inventory.setItem(13, i2);

                }
            }
        }

        ItemStack voltar = new Item(Material.ARROW, 1, (short) 0)
                .setName("§aVoltar")
                .getItemStack();

        inventory.setItem(49, voltar);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
