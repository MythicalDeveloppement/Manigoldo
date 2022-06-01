package plus.antdev.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plus.antdev.Manigoldo;
import plus.antdev.modules.economy.Account;

import java.io.IOException;

public class Pay implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String cmd, String[] args) {
        if(commandSender instanceof Player debtor && args.length == 2){
            Player creditor = Bukkit.getPlayer(args[0]);
           float amount;
           if(creditor == null){
               return false;
           }
           if (creditor.getUniqueId() == debtor.getUniqueId()){
               final String msg = ChatColor.WHITE + "[" + ChatColor.GOLD + "⛁" + ChatColor.WHITE + "]: " + ChatColor.DARK_RED +"creditor can't be you !";
               debtor.sendMessage(msg);
               return false;
           }

            try{
                amount = Float.parseFloat(args[1]);
            }catch(NumberFormatException e){
                return false;
            }
            try {
                pay(debtor, creditor, amount);
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    private void pay(Player debtor, Player creditor, float amount) throws IOException {
        Account accountDebtor = Account.load(debtor);
        Account accountCreditor = Account.load(creditor);

        accountCreditor.add(amount);
        accountDebtor.withdraw(amount);

        Manigoldo.displayBalance(debtor);
        Manigoldo.displayBalance(creditor);
    }
}
