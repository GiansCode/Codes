package com.gianscode.codes.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gianscode.codes.Main;
import com.gianscode.codes.objects.Code;

public class Redeem implements CommandExecutor {

	private Main plugin;

	public Redeem(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("redeem")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can only be executed by players.");
				return true;
			}

			Player p = (Player) sender;

			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "Please define a Code to redeem.");
				return true;
			}

			String c = args[0];

			List<Code> codes = plugin.getPluginConfig().getCodes();
			List<String> redeemed = plugin.redeemed.get(c);

			for (Code code : codes) {
				if (code.getName().equalsIgnoreCase(c)) {
					if (redeemed.contains(p.getUniqueId().toString())) {
						p.sendMessage(plugin.getPluginConfig().getAlreadyRedeemed().replace("%code%", code.getName()));
						return true;
					}

					for (String cmds : code.getCommands()) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmds.replace("%player%", p.getName()));
					}

					p.sendMessage(plugin.getPluginConfig().getSuccessfullyRedeemed().replace("%code%", code.getName()));

					redeemed.add(p.getUniqueId().toString());
					plugin.redeemed.put(c, redeemed);
				} else {
					p.sendMessage(plugin.getPluginConfig().getInvalidCode().replace("%code%", c));
					return true;
				}
			}
		}
		return true;
	}
}