package customitems.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import customitems.commands.GiveCustomItemCmd;

public class Main extends JavaPlugin{
	public static Main instance;
	public String rutaConfig;
@Override
public void onEnable() {
	instance = this;
	try {
		Bukkit.getConsoleSender().sendMessage(colorize("&bCustomItemsPro: &eIniciando activacion"));
		registerCommands();
		registerEvents();
		config();
		String msgs = instance.getConfig().getString("config.messages.item-not-exist");
		Bukkit.getConsoleSender().sendMessage(msgs);
		
	} catch (Exception ex) {
		Bukkit.getConsoleSender().sendMessage(colorize("&4CustomItemsPro error : &e" + ex.getMessage()));
	}
}


public void config(){
    File config = new File(this.getDataFolder(),"config.yml");
    rutaConfig = config.getPath();
    if(!config.exists()){
        this.getConfig().options().copyDefaults(true);
        saveConfig();
    }}

private void registerCommands() {
	getCommand("givecustomitem").setExecutor(new GiveCustomItemCmd(this));
}
private void registerEvents() {
	
}
public static String colorize(String str) {
	return ChatColor.translateAlternateColorCodes('&', str);
}
public static List<String> colorize (List<String> str){
	List<String> colorList = new ArrayList<String>();
	for(String string : str) {
		colorList.add(colorize(string));
	}
	return colorList;
}

}
