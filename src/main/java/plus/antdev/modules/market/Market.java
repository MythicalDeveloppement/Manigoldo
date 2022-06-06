package plus.antdev.modules.market;

import plus.antdev.Manigoldo;
import plus.antdev.json.JsonManager;
import plus.antdev.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

public class Market {
    private final String name;
    private final List<String> itemMarketList;

    public Market(String name, List<String> itemMarketList) {
        this.name = name;
        this.itemMarketList = itemMarketList;
    }

    public String getName() {
        return name;
    }

    public List<String> getItemMarketList() {
        return itemMarketList;
    }

    public static void register(Market market) {
        File marketFile = new File(Manigoldo.getInstance().getMarketDir(), market.name);
        if (marketFile.exists()) {
            return;
        }
        Manigoldo.getInstance().getLogger().log(Level.WARNING, "New market has been registered :" + market.name);
        FileUtils.save(new File(marketFile, market.name), new JsonManager().serializeMarket(market));
    }

    public boolean save() {
        File marketFile = new File(Manigoldo.getInstance().getMarketDir(), this.name);
        if (!marketFile.exists()) {
            return false;
        }
        FileUtils.save(new File(marketFile, this.name), new JsonManager().serializeMarket(this));
        return true;
    }

    public static Market load(String marketName) {
        File marketFile = new File(Manigoldo.getInstance().getMarketDir(), marketName);
        String json = FileUtils.loadContent(new File(marketFile, marketName));
        return new JsonManager().deserializeMarket(json);
    }

    public void addItemMarket(ItemMarket itemMarket) {
        System.out.println("1" + this.getItemMarketList().size());
        this.itemMarketList.add(itemMarket.getHash());
        System.out.println("2" + this.getItemMarketList().size());
    }
}