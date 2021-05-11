package br.com.pedrodcp.plobby.GUI;

import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Compass {

    private Inventory inventory;

    public Compass() {
        inventory = Bukkit.createInventory(null, 9 * 4, "§8Modos de Jogo");
        colocarItens();
    }

    public void colocarItens() {
        ItemStack i1 = new Item(Material.BOOK, 1, (short) 0)
                .setName("§cManutenção")
                .setLore(Arrays.asList("", "§7Estamos em manutenção, acesse §eredeteste.com/manutencao", "§7para mais detalhes."))
                .addEnchant(Enchantment.DURABILITY, 1)
                .hideEnchant()
                .getItemStack();

        inventory.setItem(13, i1);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
