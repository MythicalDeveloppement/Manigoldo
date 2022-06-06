package plus.antdev.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plus.antdev.Manigoldo;

import java.io.IOException;

public class ManigoldoPlayerListener implements Listener {

    private final Manigoldo plugin;

    public ManigoldoPlayerListener(Manigoldo manigoldo) {
        this.plugin = manigoldo;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        Manigoldo.displayBalance(player);
    }
}