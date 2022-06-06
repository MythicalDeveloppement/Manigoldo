package plus.antdev.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import plus.antdev.json.adapters.ItemMarketAdapter;
import plus.antdev.modules.economy.Account;
import plus.antdev.modules.market.ItemMarket;
import plus.antdev.modules.market.Market;

public class JsonManager {
    private final Gson gson;

    public JsonManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .registerTypeAdapter(ItemMarket.class, new ItemMarketAdapter())
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    //    Account     //
    public String serializeAccount(Account account) {
        return this.gson.toJson(account);
    }

    public Account deserializeAccount(String json) {
        return this.gson.fromJson(json, Account.class);
    }

    //    Market     //
    public String serializeMarket(Market market) {
        return this.gson.toJson(market);
    }

    public Market deserializeMarket(String json) {
        return this.gson.fromJson(json, Market.class);
    }

    //   Market-Item //
    public String serializeItemMarket(ItemMarket itemMarket) {
        return this.gson.toJson(itemMarket);
    }

    public ItemMarket deserializeItemMarket(String json) {
        return this.gson.fromJson(json, ItemMarket.class);
    }
}