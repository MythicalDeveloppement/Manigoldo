package plus.antdev.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerPayEvent extends Event {

    private final Player creditor;
    private final Player debtor;
    private final float amount;

    public PlayerPayEvent(Player creditor, Player debtor, float amount) {
        this.creditor = creditor;
        this.debtor = debtor;
        this.amount = amount;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getCreditor() {
        return creditor;
    }

    public Player getDebtor() {
        return debtor;
    }

    public float getAmount() {
        return amount;
    }
}