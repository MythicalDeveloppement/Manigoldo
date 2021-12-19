package plus.antdev;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import plus.antdev.commands.Balance;
import plus.antdev.commands.Pay;
import plus.antdev.listeners.ManigoldoPlayerListener;
import plus.antdev.modules.economy.Account;

import java.io.File;
import java.io.IOException;

public class Manigoldo extends JavaPlugin {

    private static Manigoldo INSTANCE;

    // Directories
    private File AccountDir;

    @Override
    public void onLoad() {
        INSTANCE = this;
        this.AccountDir = new File(this.getDataFolder(), "/accounts");
    }

    private final ManigoldoPlayerListener playerListener = new ManigoldoPlayerListener(this);

    @Override
    public void onEnable() {
        // Register listeners
        registerEvents();
        // Register commands
        registerCommands();
    }

    public static Manigoldo getInstance() {
        return INSTANCE;
    }

    public File getAccountDir() {
        return AccountDir;
    }

    private void registerEvents(){
        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(playerListener,this);
    }

    private void registerCommands(){
        getCommand("pay").setExecutor(new Pay());
        getCommand("balance").setExecutor(new Balance());
    }

    public static void displayBalance(Player player) throws IOException {
        Account account = Account.load(player);
        final String msg = ChatColor.WHITE + "[" + ChatColor.GOLD + "⛁" + ChatColor.WHITE + "]: %f";
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(
                        String.format(msg,account.getBalance())
                )
        );
    }
}
