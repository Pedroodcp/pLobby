package br.com.pedrodcp.plobby.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {

    private ItemStack ItemStack;
    private ItemMeta ItemMeta;

    public Item(Material m, int quantidade, short data) {
        ItemStack = new ItemStack(m, quantidade, data);
        ItemMeta = ItemStack.getItemMeta();
    }

    public Item setName(String nome) {
        ItemMeta.setDisplayName(nome);
        ItemStack.setItemMeta(ItemMeta);
        return this;
    }

    public Item setLore(List<String> lore) {
        ItemMeta.setLore(lore);
        ItemStack.setItemMeta(ItemMeta);
        return this;
    }

    public Item addEnchant(Enchantment encanto, int forca) {
        ItemMeta.addEnchant(encanto, forca, false);
        ItemStack.setItemMeta(ItemMeta);
        return this;
    }

    public Item hideEnchant() {
        ItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ItemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        ItemStack.setItemMeta(ItemMeta);
        return this;
    }

    public org.bukkit.inventory.ItemStack getItemStack() {
        return ItemStack;
    }

}
