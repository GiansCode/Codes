package com.gianscode.codes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gianscode.codes.commands.Redeem;
import com.gianscode.codes.config.Configuration;
import com.gianscode.codes.objects.Config;

public class Main extends JavaPlugin implements Listener {

	private Config config;
	public HashMap<String, List<String>> redeemed;

	@Override
	public void onEnable() {
		Bukkit.getServer().getLogger().info("[Codes] Enabling the plugin...");

		this.saveDefaultConfig();
		this.getCommand("redeem").setExecutor(new Redeem(this));

		config = Configuration.createConfig(this);
		redeemed = new HashMap<String, List<String>>();

		File file = new File(this.getDataFolder(), "redeemed.yml");

		if (!(file.exists() || file.isDirectory())) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().warning("[Codes] Failed to create redeemed.yml, disabling.");

				Bukkit.getPluginManager().disablePlugin(this);
			}
		}

		YamlConfiguration redeemed = YamlConfiguration.loadConfiguration(file);

		this.getConfig().getConfigurationSection("codes").getKeys(false).forEach(code -> {
			this.redeemed.put(code.toLowerCase(), redeemed.getStringList(code));
		});

		Bukkit.getServer().getLogger().info("[Codes] Enabled Codes v" + this.getDescription().getVersion() + "!");
	}

	@Override
	public void onDisable() {
		Bukkit.getServer().getLogger().info("[Codes] Disabling the plugin...");

		this.saveDefaultConfig();

		File file = new File(getDataFolder(), "redeemed.yml");

		if (!(file.exists() || file.isDirectory())) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().warning("[Codes] Failed to create redeemed.yml");
			}
		}

		YamlConfiguration redeemed = YamlConfiguration.loadConfiguration(file);

		for (Entry<String, List<String>> entry: this.redeemed.entrySet()) {
			redeemed.set(entry.getKey(), entry.getValue());
		}

		try {
			redeemed.save(file);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().warning("[Codes] Failed to save redeemed.yml");
		}

		config = null;
		redeemed = null;
	}

	public Config getPluginConfig() {
		return config;
	}
}