package plus.antdev.modules.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import plus.antdev.Manigoldo;
import plus.antdev.utils.FileUtils;
import plus.antdev.utils.JsonManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class Account {
    private UUID owner;
    private float balance;
    public static final File accountDir = Manigoldo.getInstance().getAccountDir();

    public Account(UUID owner, float balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public static void register(Player player) throws IOException {
        UUID uuid = player.getUniqueId();
        int defaultBalance = 0;
        File accountFile = new File(accountDir, player.getUniqueId().toString());
        Account account = new Account(uuid,defaultBalance);
        String json = new JsonManager().serializeAccount(account);

        if(accountFile.exists()) {
            return;
        }

        Manigoldo.getInstance().getLogger().log(Level.WARNING,"New account has been registered :" + player.getUniqueId() );
        FileUtils.save(new File( accountDir, uuid.toString()), json);
    }

    public void save(){
        String json = new JsonManager().serializeAccount(this);
        FileUtils.save(new File( accountDir, this.owner.toString()), json);
    }

    public static Account load(Player player) throws IOException {
        File accountFile = new File(accountDir, player.getUniqueId().toString());
        if(!accountFile.exists()) {
            register(player);
        }
        String json = FileUtils.loadContent(accountFile);
        return new JsonManager().deserializeAccount(json);
    }

    public void add(float amount){
        balance = balance + amount;
        final String msg = ChatColor.WHITE + "[" + ChatColor.GOLD + "⛁" + ChatColor.WHITE + "]: You have received a transfer";
        Objects.requireNonNull(Bukkit.getPlayer(owner)).sendMessage(msg);
        save();
    }

    public void withdraw(float amount) {
        balance = balance - amount;
        final String msg = ChatColor.WHITE + "[" + ChatColor.GOLD + "⛁" + ChatColor.WHITE + "]: You have made a transfer";
        Objects.requireNonNull(Bukkit.getPlayer(owner)).sendMessage(msg);
        save();
    }

    public UUID getOwner() {
        return owner;
    }

    public float getBalance() {
        return balance;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
