package com.gianscode.codes.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.gianscode.codes.Main;
import com.gianscode.codes.objects.Code;
import com.gianscode.codes.objects.Config;

public class Configuration {

	public static Config createConfig(Main plugin) {
		org.bukkit.configuration.Configuration config = plugin.getConfig();

		String alreadyRedeemed = ChatColor.translateAlternateColorCodes('&', config.getString("messages.alreadyRedeemed"));
		String invalidCode = ChatColor.translateAlternateColorCodes('&', config.getString("messages.invalidCode"));
		String successfullyRedeemed = ChatColor.translateAlternateColorCodes('&', config.getString("messages.successfullyRedeemed"));
		List<Code> codes = new LinkedList<>();

		config.getConfigurationSection("codes").getKeys(false).forEach(key -> {
			Optional<List<String>> commands = Optional.ofNullable(plugin.getConfig().getStringList("codes." + key + ".commands"));
			List<String> cmds;

			try {
				if (commands.isPresent()) {
					cmds = commands.get();
				} else {
					Bukkit.getServer().getLogger().severe("[Codes] No commands are defined for the Code " + key + ". Skipping.");
					return;
				}
			} catch (IllegalArgumentException ex) {
				Bukkit.getServer().getLogger().severe("[Codes] Issue parsing the commands for the Code " + key + ". Skipping.");
				return;
			}

			codes.add(new Code(key, cmds));
		});

		return new Config(alreadyRedeemed, invalidCode, successfullyRedeemed, codes);
	}
}