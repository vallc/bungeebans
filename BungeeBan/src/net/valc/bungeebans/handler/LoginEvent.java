package net.valc.bungeebans.handler;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.valc.bungeebans.utils.Profile;

public class LoginEvent implements Listener {

	@EventHandler
	public void onLogin(net.md_5.bungee.api.event.LoginEvent e) {
		Profile profile = new Profile(e.getConnection().getName());
		if(profile.isBanned()) {
			long end = profile.getBanEnd();
			long current = System.currentTimeMillis();
			if(end > 0L) {
				if(end < current) {
					profile.unban();
				}
				else {
					e.setCancelled(true);
					e.setCancelReason(profile.getBanKickMessage() + "");
				}
			}
			else {
				e.setCancelReason(profile.getBanKickMessage() + "");
				e.setCancelled(true);
			}
		}
	}
}
