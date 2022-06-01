package plus.antdev;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import plus.antdev.commands.Balance;
import plus.antdev.commands.Pay;
import plus.antdev.listeners.ManigoldoListener;
import plus.antdev.listeners.ManigoldoPlayerListener;
import plus.antdev.modules.economy.Account;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Manigoldo extends JavaPlugin {

    private static Manigoldo INSTANCE;

    // Directories
    private File AccountDir;
    //Listeners
    private final ManigoldoListener manigoldoListener = new ManigoldoListener(this);
    private final ManigoldoPlayerListener playerListener = new ManigoldoPlayerListener(this);

    //////////////////
    // PRIVATE ZONE //
    //////////////////

    private void registerCommands(){
        Objects.requireNonNull(getCommand("pay")).setExecutor(new Pay());
        Objects.requireNonNull(getCommand("balance")).setExecutor(new Balance());
    }

    /////////////////
    // PUBLIC ZONE //
    /////////////////

    @Override
    public void onLoad() {
        INSTANCE = this;
        this.AccountDir = new File(this.getDataFolder(), "/accounts");
    }
    @Override
    public void onEnable() {
        // Register listeners
        registerEvents();
        // Register commands
        registerCommands();
    }

    // Getters
    public static Manigoldo getInstance() {
        return INSTANCE;
    }
    public File getAccountDir() {
        return AccountDir;
    }

    private void registerEvents(){
        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(manigoldoListener,this);
        pluginManager.registerEvents(playerListener,this);
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