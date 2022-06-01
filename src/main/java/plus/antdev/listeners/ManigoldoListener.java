package plus.antdev.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plus.antdev.Manigoldo;
import plus.antdev.events.PlayerPayEvent;

public class ManigoldoListener implements Listener {

    private final Manigoldo plugin;

    public ManigoldoListener(Manigoldo manigoldo) {
        this.plugin = manigoldo;
    }

    @EventHandler
    public void onPlayerPay(PlayerPayEvent event) {
        plugin.getLogger().info("New transaction successfully completed");
    }
}