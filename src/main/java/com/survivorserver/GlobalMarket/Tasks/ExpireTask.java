package com.survivorserver.GlobalMarket.Tasks;

import java.util.Collection;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.survivorserver.GlobalMarket.ConfigHandler;
import com.survivorserver.GlobalMarket.Listing;
import com.survivorserver.GlobalMarket.Market;
import com.survivorserver.GlobalMarket.MarketCore;
import com.survivorserver.GlobalMarket.MarketStorage;

public class ExpireTask extends BukkitRunnable {

	Market market;
	ConfigHandler conf;
	MarketCore core;
	MarketStorage storage;
	
	public ExpireTask(Market market, ConfigHandler conf, MarketCore core, MarketStorage storage) {
		this.market = market;
		this.conf = conf;
		this.core = core;
		this.storage = storage;
	}
	
	@Override
	public void run() {
		Collection<Listing> expired = Collections2.filter(market.getStorage().getAllListings(), new Predicate<Listing>() {
			public boolean apply(Listing listing) {
				long diff = System.currentTimeMillis() - listing.getTime();
				return diff / 60 * 60 * 1000 >= market.getExpireTime(listing.getSeller(), listing.getWorld());
			}
		});
		for (Listing ex : expired) {
			market.getCore().equals(ex);
		}
	}
}
