package br.com.pedrodcp.plobby.GUI;

import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Lobbies {

    private Inventory inventory;

    public Lobbies() {
        inventory = Bukkit.createInventory(null, 9 * 4, "§8Lobbies");
        colocarItens();
    }

    public void colocarItens() {
        ItemStack i1 = new Item(Material.INK_SACK, 1, (short) 10)
                .setName("§aLobby Principal 1")
                .setLore(Arrays.asList("§fJogadores: §7" + Bukkit.getOnlinePlayers().size(), "", "§eVocê está aqui."))
                .addEnchant(Enchantment.DURABILITY, 1)
                .hideEnchant()
                .getItemStack();

        inventory.setItem(10, i1);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
