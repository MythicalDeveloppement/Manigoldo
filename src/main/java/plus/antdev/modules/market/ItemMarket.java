package plus.antdev.modules.market;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import plus.antdev.Manigoldo;
import plus.antdev.json.JsonManager;
import plus.antdev.utils.FileUtils;
import plus.antdev.utils.HashCreator;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;

/*
    @TODO : Arrow / fish etc....
 */

public class ItemMarket {

    private String hash;
    private final ItemStack itemStack;
    private final int price;
    private final UUID owner;

    public ItemMarket(ItemStack item, int price, Player owner) {
        this.itemStack = item;
        this.price = price;
        this.owner = owner.getUniqueId();

        try {
            setHash(HashCreator.createMD5Hash(this.itemStack.toString() + this.price + this.owner.toString()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getHash() {
        return hash;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getPrice() {
        return price;
    }

    public UUID getOwner() {
        return owner;
    }

    private void setHash(String hash) {
        this.hash = hash;
    }

    public static void register(ItemMarket itemMarket, Market market) {
        Manigoldo.getInstance().getLogger().log(Level.WARNING, "New itemMarket has been registered :" + market.getName() + "/" + itemMarket.getPrice());
        FileUtils.save(new File(Manigoldo.getInstance().getMarketItemsDir(), itemMarket.hash), new JsonManager().serializeItemMarket(itemMarket));
    }

    public static ItemMarket load(String itemMarketName) {
        String json = FileUtils.loadContent(new File(Manigoldo.getInstance().getMarketItemsDir(), itemMarketName));
        return new JsonManager().deserializeItemMarket(json);
    }
}