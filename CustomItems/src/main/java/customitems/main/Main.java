package customitems.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import customitems.commands.GiveCustomItemCmd;
import customitems.commands.GiveCustommineralCmd;
import customitems.commands.OpenGuiCmd;
import customitems.commands.ReloadConfigCmd;
import customitems.events.InventoryArmorGUI;
import customitems.events.InventoryElytraGUI;
import customitems.events.InventoryMenuGUI;
import customitems.events.InventoryToolGUI;
import customitems.events.InventoryWeaponGUI;

public class Main extends JavaPlugin {
	public static Main instance;
	public String rutaConfig;

	@Override
	public void onEnable() {
		instance = this;
		try {
			Bukkit.getConsoleSender().sendMessage(colorize("&bCustomItemsPro: &eIniciando activacion"));
			registerCommands();
			registerEvents();
			loadConfig();
			String msgs = instance.getConfig().getString("config.messages.item-not-exist");
			Bukkit.getConsoleSender().sendMessage(msgs);

		} catch (Exception ex) {
			Bukkit.getConsoleSender().sendMessage(colorize("&4CustomItemsPro error : &e" + ex.getMessage()));
		}
	}

	public void loadConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if (!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}

	private void registerCommands() {
		getCommand("givecustomitem").setExecutor(new GiveCustomItemCmd(this));
		getCommand("givecustommineral").setExecutor(new GiveCustommineralCmd(this));
		getCommand("buycustomitem").setExecutor(new OpenGuiCmd(this));
		getCommand("customitemreload").setExecutor(new ReloadConfigCmd(this));
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryMenuGUI(this), this);
		pm.registerEvents(new InventoryArmorGUI(this), this);
		pm.registerEvents(new InventoryWeaponGUI(this), this);
		pm.registerEvents(new InventoryToolGUI(this), this);
		pm.registerEvents(new InventoryElytraGUI(this), this);

	}

	public static String colorize(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static List<String> colorize(List<String> str) {
		List<String> colorList = new ArrayList<String>();
		for (String string : str) {
			colorList.add(colorize(string));
		}
		return colorList;
	}

}
