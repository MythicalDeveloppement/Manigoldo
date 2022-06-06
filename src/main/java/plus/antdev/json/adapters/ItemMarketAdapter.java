package plus.antdev.json.adapters;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import plus.antdev.modules.market.ItemMarket;

import java.io.IOException;
import java.util.*;

public class ItemMarketAdapter extends TypeAdapter<ItemMarket> {

    @Override
    public void write(JsonWriter jsonWriter, ItemMarket itemMarket) throws IOException {
        ItemStack item = itemMarket.getItemStack();
        ItemMeta meta = item.getItemMeta();
        jsonWriter.beginObject();
        jsonWriter.name("price").value(itemMarket.getPrice());
        jsonWriter.name("owner").value(itemMarket.getOwner().toString());
        jsonWriter.name("item").value(itemMarket.getItemStack().getType().toString());
        jsonWriter.name("amount").value(item.getAmount());
        if (meta != null) {
            jsonWriter.name("itemMeta").beginObject();
            if (meta.hasDisplayName()) {
                jsonWriter.name("displayName").value(meta.getDisplayName());
            }
            if (meta.hasEnchants()) {
                jsonWriter.name("enchants").beginObject();
                Iterator<Map.Entry<Enchantment, Integer>> it = meta.getEnchants().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Enchantment, Integer> pair = it.next();
                    jsonWriter.name(pair.getKey().getKey().toString()).value(pair.getValue());
                }
                jsonWriter.endObject();
            }
            if (meta.hasLore()) {
                jsonWriter.name("lore").beginArray();
                for (int i = 0; i < Objects.requireNonNull(meta.getLore()).size(); i++) {
                    jsonWriter.value(meta.getLore().get(i));
                }
                jsonWriter.endArray();
            }
            if (meta instanceof Damageable damageable) {
                jsonWriter.name("damage").value(damageable.getDamage());
            }
            jsonWriter.endObject();
        }
        jsonWriter.endObject();
    }

    @Override
    public ItemMarket read(JsonReader jsonReader) throws IOException {

        jsonReader.beginObject();
        ItemStack itemStack = null;
        int price = 0;
        UUID owner = null;
        String name = null;
        List<String> lore = new ArrayList<>();
        Map<Enchantment, Integer> enchants = new HashMap<>();
        int damage = 0;
        int amount = 1; // 1 by default

        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "price":
                    price = jsonReader.nextInt();
                    break;
                case "owner":
                    owner = UUID.fromString(jsonReader.nextString());
                    break;
                case "item":
                    itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(jsonReader.nextString())));
                    break;
                case "amount":
                    assert itemStack != null;
                    amount = jsonReader.nextInt();
                    break;
                case "itemMeta":
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        switch (jsonReader.nextName()) {
                            case "displayName":
                                name = jsonReader.nextString();
                                break;
                            case "lore":
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    lore.add(jsonReader.nextString());
                                }
                                jsonReader.endArray();
                                break;
                            case "damage":
                                damage = jsonReader.nextInt();
                                break;
                            case "enchants":
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    enchants.put(Enchantment.getByKey(NamespacedKey.fromString(jsonReader.nextName())), jsonReader.nextInt());
                                }
                                jsonReader.endObject();
                                break;
                        }
                    }
                    jsonReader.endObject();
                    break;
            }
        }
        jsonReader.endObject();
        assert owner != null;
        ItemMeta meta = itemStack.getItemMeta();
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }
        if (damage != 0) {
            ((Damageable) meta).setDamage(damage);
        }
        if (name != null) {
            meta.setDisplayName(name);
        }
        for (Map.Entry<Enchantment, Integer> pair : enchants.entrySet()) {
            meta.addEnchant(pair.getKey(), pair.getValue(), true);
        }
        itemStack.setAmount(amount);
        itemStack.setItemMeta(meta);
        return new ItemMarket(itemStack, price, Objects.requireNonNull(Bukkit.getPlayer(owner)));
    }
}