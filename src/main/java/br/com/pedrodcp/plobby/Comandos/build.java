package br.com.pedrodcp.plobby.Comandos;

import br.com.pedrodcp.plobby.Main;
import br.com.pedrodcp.plobby.utils.Item;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class build implements CommandExecutor {

    public static ArrayList<Player> builder = new ArrayList<Player>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if (!(s instanceof Player)) return false;

        if (c.getName().equalsIgnoreCase("build")) {
            Player p = (Player) s;
            if (!p.hasPermission("build.use")) {
                p.sendMessage("§cVocê não possui permissão para utilizar este comando.");

            } else {
                if (!builder.contains(p)) {
                    builder.add(p);
                    p.getInventory().clear();
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage("§aModo construtor ativado.");

                } else {
                    builder.remove(p);
                    p.getInventory().clear();
                    p.setGameMode(GameMode.SURVIVAL);
                    if (Main.getPlayerManager().getPlayer(p.getName()).getFlyOption().equals("on")) {
                        p.setAllowFlight(true);
                    }
                    p.sendMessage("§cModo construtor desativado.");

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
                }
            }
        }

        return false;
    }
}
