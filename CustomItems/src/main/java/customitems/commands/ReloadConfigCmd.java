package customitems.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import customitems.main.Main;

public class ReloadConfigCmd implements CommandExecutor {
	private Main instance;

	public ReloadConfigCmd(Main instance) {
		this.instance = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String configReaload = instance.getConfig().getString("config.messages.reload-config");

		if (sender instanceof Player) {
			if (args.length == 1 && sender.hasPermission("customitems.reloadconfig") || sender.isOp()) {
				instance.reloadConfig();
				sender.sendMessage(Main.colorize(configReaload));
				return true;
			}
		} else if (sender instanceof ConsoleCommandSender) {
			if (args.length == 0) {
				instance.reloadConfig();
				Bukkit.getConsoleSender().sendMessage(Main.colorize(configReaload));
				return true;

			}
		}

		return true;
	}

}
