package plus.antdev.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import plus.antdev.modules.economy.Account;

public class JsonManager {
    private final Gson gson;

    public JsonManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serializeAccount(Account account) {
        return this.gson.toJson(account);
    }

    public Account deserializeAccount(String json) {
        return this.gson.fromJson(json, Account.class);
    }
}
