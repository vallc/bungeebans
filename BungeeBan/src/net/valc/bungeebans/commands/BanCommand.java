package net.valc.bungeebans.commands;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.valc.bungeebans.Main;
import net.valc.bungeebans.utils.ConfigManager;
import net.valc.bungeebans.utils.Profile;

public class BanCommand
  extends Command
{
  public BanCommand(String name)
  {
    super(name);
  }
  ;
  
  public void execute(final CommandSender sender, final String[] args) {
	  if(sender.hasPermission("bungeeban.command.ban")) {
		  if(args.length >= 4) {
			  String playerName = args[0];
			  String reason = "";
			  for(int i = 3; i <= args.length - 1; i++) {
				  reason = reason + args[i] + " ";
				  Main.getConfigManager().save();
				  Profile profile = new Profile(playerName);
				  if(profile != null) {
					  if(!profile.isBanned()) {
						  try {
							  long seconds = Integer.parseInt(args[1]);
							  Main.TimeUnit unit = Main.TimeUnit.getByString(args[2]);
							  if(unit != null) {
								  seconds *= unit.getSeconds();
								  profile.setBanned(reason, sender.getName(), seconds);
								  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.ban.banned", new String[] { "{NAME}~" + playerName }));							  }
						  } catch (NumberFormatException e) {sender.sendMessage("An interal error occured");}
					  } else {
						  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_already_banned", new String[] { "{NAME}~" + playerName }));						  }
				  } else {
					  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found"));
				  }
			  }
		  }
		  else {
			  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.ban.syntax"));
			  }
	  } else {
		  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions"));
	  };
  } 
}
