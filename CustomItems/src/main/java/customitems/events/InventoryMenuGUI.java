package customitems.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import customitems.main.Main;

public class InventoryMenuGUI implements Listener {
	private Main instance;

	public InventoryMenuGUI(Main instance) {
		this.instance = instance;
	}

	public void openMenuGui(Player p) {

		String inventoryName = instance.getConfig().getString("config.menugui.inventoryname");
		Inventory inv = Bukkit.createInventory(null, 9, Main.colorize(inventoryName));
		ItemStack armor = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack tool = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
		ItemStack elytra = new ItemStack(Material.ELYTRA);

		ItemMeta armorMeta = armor.getItemMeta();
		ItemMeta toolMeta = tool.getItemMeta();
		ItemMeta weaponMeta = weapon.getItemMeta();
		ItemMeta elytraMeta = elytra.getItemMeta();

		List<String> loreArmor = instance.getConfig().getStringList("config.menugui.armor.lore");
		List<String> loreWeapon = instance.getConfig().getStringList("config.menugui.weapon.lore");
		List<String> loreTool = instance.getConfig().getStringList("config.menugui.tool.lore");
		List<String> loreElytra = instance.getConfig().getStringList("config.menugui.elytra.lore");

		String displayNameArmor = instance.getConfig().getString("config.menugui.armor.lore");
		String displayNameWeapon = instance.getConfig().getString("config.menugui.weapon.lore");
		String displayNameTool = instance.getConfig().getString("config.menugui.tool.lore");
		String displayNameElytra = instance.getConfig().getString("config.menugui.elytra.lore");

		armorMeta.setLore(Main.colorize(loreArmor));
		toolMeta.setLore(Main.colorize(loreTool));
		weaponMeta.setLore(Main.colorize(loreWeapon));
		elytraMeta.setLore(Main.colorize(loreElytra));

		armorMeta.setDisplayName(Main.colorize(displayNameArmor));
		toolMeta.setDisplayName(Main.colorize(displayNameTool));
		weaponMeta.setDisplayName(Main.colorize(displayNameWeapon));
		elytraMeta.setDisplayName(Main.colorize(displayNameElytra));

		armor.setItemMeta(armorMeta);
		tool.setItemMeta(toolMeta);
		weapon.setItemMeta(weaponMeta);
		elytra.setItemMeta(elytraMeta);

		inv.setItem(1, armor);
		inv.setItem(3, weapon);
		inv.setItem(5, tool);
		inv.setItem(7, elytra);

		p.openInventory(inv);
		return;
	}

	@EventHandler
	public void clickMenuInventory(InventoryClickEvent e) {
		String inventoryName = Main.colorize(instance.getConfig().getString("config.menugui.inventoryname"));
		String inventoryNameN = ChatColor.stripColor(inventoryName);
		Player p = (Player) e.getWhoClicked();
		if (ChatColor.stripColor(e.getView().getTitle()).equals(inventoryNameN)) {
			if (e.getCurrentItem() == null || e.getSlotType() == null
					|| e.getCurrentItem() == new ItemStack(Material.AIR)) {
				e.setCancelled(true);
				return;

			} else if (e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);
				if (e.getSlot() == 1) {
					InventoryArmorGUI inventoryArmor = new InventoryArmorGUI(instance);
					inventoryArmor.openArmorGui(p, 1);
					return;

				} else if (e.getSlot() == 3) {
					InventoryWeaponGUI inventoryWeapon = new InventoryWeaponGUI(instance);
					inventoryWeapon.openWeaponGui(p, 1);
					return;

				} else if (e.getSlot() == 5) {
					InventoryToolGUI inventoryTool = new InventoryToolGUI(instance);
					inventoryTool.openToolGui(p, 1);
					return;

				} else if (e.getSlot() == 7) {
					InventoryElytraGUI inventoryElytra = new InventoryElytraGUI(instance);
					inventoryElytra.openElytraGui(p, 1);
					return;

				} else {
					e.setCancelled(true);
					return;

				}

			} else {
				e.setCancelled(true);
				return;
			}

		}

	}

}
