package customitems.commands;

import static org.bukkit.Bukkit.getPlayer;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import customitems.main.Main;

public class GiveCustommineralCmd implements CommandExecutor {
	private Main instance;

	public GiveCustommineralCmd(Main instance) {
		this.instance = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (!(args.length > 1 && cmd.getName().equalsIgnoreCase("givecustommineral"))) {
				String msg = instance.getConfig().getString("config.messages.command-mineral-ussage");
				sender.sendMessage(Main.colorize(msg));
				return true;
			}
			if (!(sender.hasPermission("customitems.givecustommineral") || sender.isOp())) {
				String msg = instance.getConfig().getString("config.messages.insuficient-permissions");
				sender.sendMessage(Main.colorize(msg));
				return true;

			}

			String key = "";
			int checker = 0;
			Player target = getPlayer(sender.getName());
			if (args.length >= 3) {
				target = Bukkit.getPlayer(args[2]);
				if (target == null) {
					String msg = instance.getConfig().getString("config.messages.target-not-exists");
					msg = msg.replaceAll("%player%", args[2]);
					sender.sendMessage(Main.colorize(msg));
					return true;
				}
			}

			for (String string : instance.getConfig().getConfigurationSection("config.minerals").getKeys(false)) {
				if (args[0].equalsIgnoreCase(string)) {
					checker++;
					key = string;
				}
			}

			if (checker != 1) {
				String msg = instance.getConfig().getString("config.messages.mineral-not-exist");
				msg = msg.replaceAll("%mineral%", args[0]);
				sender.sendMessage(Main.colorize(msg));
				return true;
			}

			PlayerInventory tInv = target.getInventory();
			String itemCustomModelDataStr = (instance.getConfig()
					.getString("config.minerals." + key + ".custommodeldata"));
			int itemCustomModelData = Integer.parseInt(itemCustomModelDataStr);
			String materialName = instance.getConfig().getString("config.minerals." + key + ".name");
			List<String> lore = instance.getConfig().getStringList("config.minerals." + key + ".lore");
			String mineralId = instance.getConfig().getString("config.minerals." + key + ".itemid");
			ItemStack item = new ItemStack(Material.valueOf(mineralId), Integer.parseInt(args[1]));
			ItemMeta meta = item.getItemMeta();
			meta.setCustomModelData(itemCustomModelData);
			if (lore != null) {
				meta.setLore(Main.colorize(lore));
			}
			if (materialName != null) {
				meta.setDisplayName(Main.colorize(materialName));
			}
			item.setItemMeta(meta);
			for (int i = 0; i <= 35; i++) {
				if (tInv.getItem(i) == null) {
					tInv.setItem(i, item);
					return true;
				}
			}
			String noSpaceMsg = instance.getConfig().getString("config.messages.no-disponible-slot");
			sender.sendMessage(Main.colorize(noSpaceMsg));
			return true;

		} else if (sender instanceof ConsoleCommandSender) {
			if (!(args.length > 2 && cmd.getName().equalsIgnoreCase("givecustommineral"))) {
				String msg = instance.getConfig().getString("config.messages.command-mineral-ussage");
				Bukkit.getConsoleSender().sendMessage(Main.colorize(msg));
				return true;

			}
			String key = "";
			int checker = 0;
			Player target = Bukkit.getPlayer(args[2]);
			if (target == null) {
				String msg = instance.getConfig().getString("config.messages.target-not-exists");
				msg = msg.replaceAll("%player%", args[2]);
				Bukkit.getConsoleSender().sendMessage(Main.colorize(msg));
				return true;
			}

			for (String string : instance.getConfig().getConfigurationSection("config.minerals").getKeys(false)) {
				if (args[0].equalsIgnoreCase(string)) {
					checker++;
					key = string;
				}
			}

			if (checker != 1) {
				String msg = instance.getConfig().getString("config.messages.mineral-not-exist");
				msg = msg.replaceAll("%mineral%", args[0]);
				Bukkit.getConsoleSender().sendMessage(Main.colorize(msg));
				return true;
			}

			PlayerInventory tInv = target.getInventory();
			String itemCustomModelDataStr = (instance.getConfig()
					.getString("config.minerals." + key + ".custommodeldata"));
			int itemCustomModelData = Integer.parseInt(itemCustomModelDataStr);
			String materialName = instance.getConfig().getString("config.minerals." + key + ".name");
			List<String> lore = instance.getConfig().getStringList("config.minerals." + key + ".lore");
			String mineralId = instance.getConfig().getString("config.minerals." + key + ".itemid");
			ItemStack item = new ItemStack(Material.valueOf(mineralId), Integer.parseInt(args[1]));
			ItemMeta meta = item.getItemMeta();
			meta.setCustomModelData(itemCustomModelData);
			if (lore != null) {
				meta.setLore(Main.colorize(lore));
			}
			if (materialName != null) {
				meta.setDisplayName(Main.colorize(materialName));
			}
			item.setItemMeta(meta);
			for (int i = 0; i <= 35; i++) {
				if (tInv.getItem(i) == null) {
					tInv.setItem(i, item);
					return true;
				}
			}
			String noSpaceMsg = instance.getConfig().getString("config.messages.no-disponible-slot");
			Bukkit.getConsoleSender().sendMessage(Main.colorize(noSpaceMsg));
			return true;
		}

		return false;
	}

}
