package customitems.commands;

import static org.bukkit.Bukkit.getPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import customitems.events.InventoryArmorGUI;
import customitems.events.InventoryElytraGUI;
import customitems.events.InventoryMenuGUI;
import customitems.events.InventoryToolGUI;
import customitems.events.InventoryWeaponGUI;
import customitems.main.Main;

public class OpenGuiCmd implements CommandExecutor {

	private Main instance;

	public OpenGuiCmd(Main instance) {
		this.instance = instance;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if (args.length == 1 && sender instanceof Player) {
			if (args[0].equalsIgnoreCase("menu"))
				menuGui(sender);
			else if (args[0].equalsIgnoreCase("armor"))
				armorGui(sender);
			else if (args[0].equalsIgnoreCase("tool"))
				toolGui(sender);
			else if (args[0].equalsIgnoreCase("weapon"))
				weaponGui(sender);
			else if (args[0].equalsIgnoreCase("elytra"))
				elytraGui(sender);
		}
		return true;

	}

	private boolean menuGui(CommandSender sender) {
		Player p = getPlayer(sender.getName());
		InventoryMenuGUI inventoryMenu = new InventoryMenuGUI(instance);
		inventoryMenu.openMenuGui(p);
		return true;
	}

	private boolean armorGui(CommandSender sender) {
		Player p = getPlayer(sender.getName());
		InventoryArmorGUI inventoryArmor = new InventoryArmorGUI(instance);
		inventoryArmor.openArmorGui(p, 1);
		return true;
	}

	private boolean toolGui(CommandSender sender) {
		Player p = getPlayer(sender.getName());
		InventoryToolGUI inventoryTool = new InventoryToolGUI(instance);
		inventoryTool.openToolGui(p, 1);
		return true;
	}

	private boolean weaponGui(CommandSender sender) {
		Player p = getPlayer(sender.getName());
		InventoryWeaponGUI inventoryWeapon = new InventoryWeaponGUI(instance);
		inventoryWeapon.openWeaponGui(p, 1);
		return true;
	}

	private boolean elytraGui(CommandSender sender) {
		Player p = getPlayer(sender.getName());
		InventoryElytraGUI inventoryElytra = new InventoryElytraGUI(instance);
		inventoryElytra.openElytraGui(p, 1);
		return true;
	}

}
