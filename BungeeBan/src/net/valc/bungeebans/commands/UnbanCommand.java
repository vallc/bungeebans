package net.valc.bungeebans.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.valc.bungeebans.Main;
import net.valc.bungeebans.utils.Profile;

public class UnbanCommand
  extends Command
{
  public UnbanCommand(String name)
  {
    super(name);
  }
  ;
  
  public void execute(final CommandSender sender, final String[] args) {
	  if(sender.hasPermission("bungeeban.command.unban")) {
		  if(args.length == 1) {
			  String playerName = args[0];
			  Profile profile = new Profile(playerName);
			  if(profile != null) {
				  if(profile.isBanned()) {
					  profile.unban();
					  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.unban.unbanned", new String[] { "{NAME}~" + playerName }));							  
				  }
				  else {
					  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_banned", new String[] { "{NAME}~" + playerName }));				  }
			  }
			  else {
				  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found"));			  }
		  }
		  else {
			  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.unban.syntax"));
		  }
	  }
	  else {
		  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions"));	  }
  }
}
