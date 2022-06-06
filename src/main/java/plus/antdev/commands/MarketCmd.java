package plus.antdev.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import plus.antdev.modules.market.ItemMarket;
import plus.antdev.modules.market.Market;

import java.util.*;

public class MarketCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player && args.length == 2)) {
            return false;
        }
        return switch (args[0]) {
            case "create" -> createMarket(args[1]);
            case "show" -> giveItemMarket(args[1], ((Player) commandSender).getPlayer());
            case "add" -> addItemToMarket(Objects.requireNonNull(((Player) commandSender).getPlayer()).getInventory().getItemInMainHand(), Market.load(args[1]), ((Player) commandSender).getPlayer());
            default -> false;
        };
    }

    private boolean createMarket(String name) {
        List<String> list = new ArrayList<>();
        Market market = new Market(name, list);
        Market.register(market);
        return true;
    }

    private boolean addItemToMarket(ItemStack itemStack, Market market, Player player) {
        ItemMarket itemMarket = new ItemMarket(itemStack, 1, player);
        //TODO
        ItemMarket.register(itemMarket, market);
        market.addItemMarket(itemMarket);
        return market.save();
    }

    //TODO
    private boolean giveItemMarket(String marketName, Player player) {
        Market market = Market.load(marketName);
        for (int i = 0; i < market.getItemMarketList().size(); i++) {
            ItemMarket itemMarket = ItemMarket.load(market.getItemMarketList().get(i));
            player.getInventory().addItem(itemMarket.getItemStack());
        }
        return true;
    }
}