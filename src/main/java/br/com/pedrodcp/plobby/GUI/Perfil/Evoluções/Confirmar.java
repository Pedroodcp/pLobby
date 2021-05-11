package br.com.pedrodcp.plobby.GUI.Perfil.Evoluções;

import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class Confirmar {

    private Inventory inventory;

    public Confirmar() {
        inventory = Bukkit.createInventory(null, 9 * 3, "§8Evolução");
        colocarItens();
    }

    public void colocarItens() {
        ItemStack i1 = new Item(Material.WOOL, 1, (short) 5)
                .setName("§aConfirmar")
                .setLore(Collections.singletonList("§7Clique para §aconfirmar §7a evolução."))
                .getItemStack();

        inventory.setItem(11, i1);

        ItemStack i2 = new Item(Material.WOOL, 1, (short) 14)
                .setName("§cCancelar")
                .setLore(Collections.singletonList("§7Clique para §ccancelar §7a evolução."))
                .getItemStack();

        inventory.setItem(15, i2);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
