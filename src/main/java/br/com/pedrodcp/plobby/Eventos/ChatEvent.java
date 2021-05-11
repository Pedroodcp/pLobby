package br.com.pedrodcp.plobby.Eventos;

import static br.com.pedrodcp.plobby.Comandos.Chat.chat.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void Chat(AsyncPlayerChatEvent e) {
        if (Chat == true) {
            e.setFormat(e.getPlayer().getDisplayName() + ": §f" + e.getMessage());
        } else {
            if (Chat == false) {
                if (e.getPlayer().hasPermission("chat.admin")) {
                    e.setFormat(e.getPlayer().getDisplayName() + ": §f" + e.getMessage());
                } else {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("§cO bate-papo está desativado.");
                }
            }
        }
    }

}
