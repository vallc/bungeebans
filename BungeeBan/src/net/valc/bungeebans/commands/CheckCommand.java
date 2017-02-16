package net.valc.bungeebans.commands;

import java.util.List;
import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.valc.bungeebans.Main;
import net.valc.bungeebans.utils.Profile;

public class CheckCommand
  extends Command
{
  public CheckCommand(String name)
  {
    super(name);
  }
  
  public void execute(final CommandSender sender, final String[] args)
  {
    BungeeCord.getInstance().getScheduler().runAsync(Main.sharedInstance(), new Runnable()
    {
      public void run()
      {
        if (sender.hasPermission("bungeeban.command.check"))
        {
          if (args.length == 1)
          {
            Profile profile = new Profile(args[0]);
            if (profile != null)
            {
              String playername = args[0];
              sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.check", new String[] { "{PLAYERNAME}~" + playername }));
              if (profile.isBanned())
              {
                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.banned.positive", new String[] { "{NAME}~" + playername, "{REASON}~" +  profile.getBanReason(), "{BY}~" + profile.getBanBy(), "{REMAININGTIME}~" + profile.getRemainingbanTime() });
                for (String msg : msgs) {
                  sender.sendMessage(Main.PREFIX + msg);
                }
              }
              else
              {
                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.banned.negative", new String[] { "{NAME}~" + playername });
                for (String msg : msgs) {
                  sender.sendMessage(Main.PREFIX + msg);
                }
              }
              if (profile.isMuted())
              {
                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.muted.positive", new String[] { "{NAME}~" + playername, "{REASON}~" + profile.getMuteReason(), "{BY}~" + profile.getMutedBy(), "{REMAININGTIME}~" + profile.getRemainingmuteTime() });
                for (String msg : msgs) {
                  sender.sendMessage(Main.PREFIX + msg);
                }
              }
              else
              {
                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.muted.negative", new String[] { "{NAME}~" + playername });
                for (String msg : msgs) {
                  sender.sendMessage(Main.PREFIX + msg);
                }
              }
            }
            else
            {
              sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found"));
            }
          }
          else
          {
            sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.check.syntax"));
          }
        }
        else {
          sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions"));
        }
      }
    });
  }
}
