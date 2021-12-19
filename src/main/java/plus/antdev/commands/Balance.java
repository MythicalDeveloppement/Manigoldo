package plus.antdev.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plus.antdev.Manigoldo;

import java.io.IOException;

public class Balance implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            try {
                Manigoldo.displayBalance((Player) commandSender);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
}
