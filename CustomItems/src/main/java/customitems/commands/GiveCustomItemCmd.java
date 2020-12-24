package customitems.commands;

import static org.bukkit.Bukkit.getPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import customitems.main.Main;

public class GiveCustomItemCmd implements CommandExecutor {
	private Main instance;

	public GiveCustomItemCmd(Main instance) {
		this.instance = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

		if (sender instanceof Player) {

			if (!(args.length > 1 && cmd.getName().equalsIgnoreCase("givecustomitem"))) {
				String msg = instance.getConfig().getString("config.messages.command-item-ussage");
				sender.sendMessage(Main.colorize(msg));
				return true;
			}

			if (!(sender.hasPermission("customitems.giveitem") || sender.isOp())) {
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

			for (String string : instance.getConfig().getConfigurationSection("config.items").getKeys(false)) {
				if (args[0].equalsIgnoreCase(string)) {
					checker++;
					key = string;
				}
			}

			if (checker != 1) {
				String msg = instance.getConfig().getString("config.messages.item-not-exist");
				msg = msg.replaceAll("%item%", args[0]);
				sender.sendMessage(Main.colorize(msg));
				return true;
			}

			PlayerInventory tInv = target.getInventory();
			String itemCustomModelDataStr = (instance.getConfig()
					.getString("config.items." + key + ".custommodeldata"));
			int itemCustomModelData = Integer.parseInt(itemCustomModelDataStr);
			String itemType = instance.getConfig().getString("config.items." + key + ".itemtype");
			String itemMaterialName = instance.getConfig().getString("config.items." + key + ".itemid").toUpperCase();
			List<String> lore = instance.getConfig().getStringList("config.items." + key + ".lore");
			String itemName = instance.getConfig().getString("config.items." + key + ".name");
			ItemStack item = new ItemStack(Material.valueOf(itemMaterialName), Integer.parseInt(args[1]));
			ItemMeta meta = item.getItemMeta();

			meta.setCustomModelData(itemCustomModelData);

			// adding displayname
			if (itemType != null) {
				meta.setDisplayName(Main.colorize(itemName));
			}
			// adding lore
			if (lore != null) {
				meta.setLore(Main.colorize(lore));
			}
			item.setItemMeta(meta);

			// adding enchantments
			List<String> enchantList = new ArrayList<String>();
			for (String str : instance.getConfig().getConfigurationSection("config.items." + key + ".enchantments")
					.getKeys(false)) {
				enchantList.add(str);
			}
			if (enchantList != null) {
				for (int j = 0; j < enchantList.size(); j++) {
					int levelEnchant = Integer.parseInt(instance.getConfig().getString("config.items." + key + ".enchantments." + enchantList.get(j)));
					String enchant =  enchantList.get(j).toLowerCase(); //toUpperCase();
					NamespacedKey nm = NamespacedKey.minecraft(enchant);
					item.addUnsafeEnchantment(Enchantment.getByKey(nm),levelEnchant);

				}
			}
			
		


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

			if (!(args.length > 2 && cmd.getName().equalsIgnoreCase("givecustomitem"))) {
				String msg = instance.getConfig().getString("config.messages.command-item-ussage");
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
			

			for (String string : instance.getConfig().getConfigurationSection("config.items").getKeys(false)) {
				if (args[0].equalsIgnoreCase(string)) {
					checker++;
					key = string;
				}
			}

			if (checker != 1) {
				String msg = instance.getConfig().getString("config.messages.item-not-exist");
				msg = msg.replaceAll("%item%", args[0]);
				Bukkit.getConsoleSender().sendMessage(Main.colorize(msg));
				return true;
			}

			PlayerInventory tInv = target.getInventory();
			String itemCustomModelDataStr = (instance.getConfig()
					.getString("config.items." + key + ".custommodeldata"));
			int itemCustomModelData = Integer.parseInt(itemCustomModelDataStr);
			String itemType = instance.getConfig().getString("config.items." + key + ".itemtype");
			String itemMaterialName = instance.getConfig().getString("config.items." + key + ".itemid").toUpperCase();
			List<String> lore = instance.getConfig().getStringList("config.items." + key + ".lore");
			String itemName = instance.getConfig().getString("config.items." + key + ".name");
			ItemStack item = new ItemStack(Material.valueOf(itemMaterialName), Integer.parseInt(args[1]));
			ItemMeta meta = item.getItemMeta();

			meta.setCustomModelData(itemCustomModelData);

			// adding displayname
			if (itemType != null) {
				meta.setDisplayName(Main.colorize(itemName));
			}
			// adding lore
			if (lore != null) {
				meta.setLore(Main.colorize(lore));
			}
			item.setItemMeta(meta);

			// adding enchantments
			List<String> enchantList = new ArrayList<String>();
			for (String str : instance.getConfig().getConfigurationSection("config.items." + key + ".enchantments")
					.getKeys(false)) {
				enchantList.add(str);
			}
			if (enchantList != null) {
				for (int j = 0; j < enchantList.size(); j++) {
					int levelEnchant = Integer.parseInt(instance.getConfig().getString("config.items." + key + ".enchantments." + enchantList.get(j)));
					String enchant =  enchantList.get(j).toLowerCase(); //toUpperCase();
					NamespacedKey nm = NamespacedKey.minecraft(enchant);
					item.addUnsafeEnchantment(Enchantment.getByKey(nm),levelEnchant);

				}
			}
			
		


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
		return true;
	}

}
