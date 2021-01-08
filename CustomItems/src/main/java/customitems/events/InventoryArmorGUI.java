package customitems.events;

import java.util.ArrayList;
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
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import customitems.main.Main;

public class InventoryArmorGUI implements Listener {

	private Main instance;

	public InventoryArmorGUI(Main instance) {
		this.instance = instance;

	}

	public void openArmorGui(Player p, int pageNumber) {
		String invName = instance.getConfig().getString("config.armorgui.name");
		Inventory inventoryArmor = Bukkit.createInventory(null, 54, Main.colorize(invName + " " + pageNumber));
		String nextPageItemType = instance.getConfig().getString("config.armorgui.nextpageitem");
		String previousPageItemType = instance.getConfig().getString("config.armorgui.previouspageitem");
		List<String> previousPageLore = instance.getConfig().getStringList("config.armorgui.previouspagelore");
		List<String> nextPageLore = instance.getConfig().getStringList("config.armorgui.nextpagelore");
		String previousPageName = instance.getConfig().getString("config.armorgui.previouspagename");
		String nextPageName = instance.getConfig().getString("config.armorgui.nextpagename");

		ItemStack nextPageItem = new ItemStack(Material.valueOf((nextPageItemType).toUpperCase()));
		ItemStack previousPageItem = new ItemStack(Material.valueOf((previousPageItemType).toUpperCase()));

		ItemMeta nextPageItemMeta = nextPageItem.getItemMeta();
		ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();

		nextPageItemMeta.setLore(Main.colorize(nextPageLore));
		nextPageItemMeta.setDisplayName(Main.colorize(nextPageName));
		previousPageItemMeta.setLore(Main.colorize(previousPageLore));
		previousPageItemMeta.setDisplayName(Main.colorize(previousPageName));

		nextPageItem.setItemMeta(nextPageItemMeta);
		previousPageItem.setItemMeta(previousPageItemMeta);

		inventoryArmor.setItem(46, previousPageItem);
		inventoryArmor.setItem(52, nextPageItem);

		for (String string : instance.getConfig().getConfigurationSection("config.items").getKeys(false)) {
			String itemName = string;
			String itemType = instance.getConfig().getString("config.items." + itemName + ".itemid");
			ItemStack item = new ItemStack(Material.valueOf((itemType).toUpperCase()));
			ItemMeta meta = item.getItemMeta();
			int customModelData = Integer
					.parseInt(instance.getConfig().getString("config.items." + itemName + ".custommodeldata"));

			for (String requirementStr : instance.getConfig()
					.getConfigurationSection("config.items." + itemName + ".requirements").getKeys(false)) {
				String mineralCost = requirementStr;

				String quantityCost = instance.getConfig()
						.getString("config.items." + itemName + ".requirements." + mineralCost);

				String loreStr = instance.getConfig().getString("config.armorgui.cost-of-item");
				List<String> lore = new ArrayList<String>();
				lore.add(loreStr.replaceAll("%mineral%", mineralCost).replaceAll("%quantity%", quantityCost));
				meta.setLore(lore);
			}
			meta.setDisplayName(Main.colorize("&9" + itemName));
			meta.setCustomModelData(customModelData);
			item.setItemMeta(meta);
			int slot = Integer.parseInt(instance.getConfig().getString("config.items." + itemName + ".gui.slot"));
			int page = Integer.parseInt(instance.getConfig().getString("config.items." + itemName + ".gui.page"));
			String itemCategory = instance.getConfig().getString("config.items." + itemName + ".itemtype");
			if (page == pageNumber && itemCategory.equalsIgnoreCase("armor")) {
				inventoryArmor.setItem(slot, item);
			}

		}
		p.openInventory(inventoryArmor);
		return;
	}

	@EventHandler
	public void clickArmorInventory(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();
		for (int i = 0; i <= 50; i++) {

			String pageStr = String.valueOf(i);
			String inventoryName = Main.colorize(instance.getConfig().getString("config.armorgui.name")) + " "
					+ pageStr;
			String inventoryNameN = ChatColor.stripColor(inventoryName);
			if (ChatColor.stripColor(e.getView().getTitle()).equals(inventoryNameN)) {
				if (e.getCurrentItem() == null || e.getSlotType() == null
						|| e.getCurrentItem() == new ItemStack(Material.AIR)) {
					e.setCancelled(true);
					return;
				}else if (e.getCurrentItem().hasItemMeta()) {
					e.setCancelled(true);
					for (String string : instance.getConfig().getConfigurationSection("config.items").getKeys(false)) {
						String itemName = string;
						int page = Integer
								.parseInt(instance.getConfig().getString("config.items." + itemName + ".gui.page"));
						int slot = Integer
								.parseInt(instance.getConfig().getString("config.items." + itemName + ".gui.slot"));
						if (i == page && e.getSlot() == slot) {

							for (String requirementStr : instance.getConfig()
									.getConfigurationSection("config.items." + itemName + ".requirements")
									.getKeys(false)) {
								String mineralCost = requirementStr;
								int quantityCost = Integer.parseInt(instance.getConfig()
										.getString("config.items." + itemName + ".requirements." + mineralCost));
								PlayerInventory pInv = p.getInventory();
								String mineralNameId = instance.getConfig()
										.getString("config.minerals." + mineralCost + ".itemid");
								String mineralName = instance.getConfig()
										.getString("config.minerals." + mineralCost + ".name");
								List<String> lore = instance.getConfig()
										.getStringList("config.minerals." + mineralCost + ".lore");

								ItemStack itemMaterialRequired = new ItemStack(Material.valueOf(mineralNameId));

								int customModelData = Integer.parseInt(instance.getConfig()
										.getString("config.minerals." + mineralCost + ".custommodeldata"));

								ItemMeta itemMaterialRequiredMeta = itemMaterialRequired.getItemMeta();
								itemMaterialRequiredMeta.setCustomModelData(customModelData);
								itemMaterialRequiredMeta.setDisplayName(Main.colorize(mineralName));
								itemMaterialRequiredMeta.setLore(Main.colorize(lore));
								itemMaterialRequired.setItemMeta(itemMaterialRequiredMeta);
								if (pInv.containsAtLeast(itemMaterialRequired, quantityCost)) {
									for (int k = 0; k < quantityCost; k++) {
										for (int s = 0; s < pInv.getSize(); s++) {

											if (pInv.getItem(s) != null) {
												if (pInv.getItem(s).isSimilar(itemMaterialRequired)) {
													if (pInv.getItem(s).getAmount() > 1) {
														pInv.getItem(s).setAmount(pInv.getItem(s).getAmount() - 1);
														break;
													} else {
														pInv.setItem(s, new ItemStack(Material.AIR));
														break;
													}
												}
											}

										}
									}
									String command = "givecustomitem " + itemName + " " + 1 + " " + p.getName();
									Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
									return;

								} else {
									String msg = instance.getConfig().getString("config.messages.insufficient-ore");
									p.sendMessage(Main.colorize(msg));
									return;
								}
							}
						}else if(e.getSlot() == 46) {
							if(i == 1) {
								e.setCancelled(true);
							}else {
								i --;
								InventoryArmorGUI armorGui = new InventoryArmorGUI(instance);
								armorGui.openArmorGui(p, i);
							}
							
						}else if(e.getSlot() == 52) {
							if(i == 49) {
								e.setCancelled(true);
							}else {
								i++;
								InventoryArmorGUI armorGui = new InventoryArmorGUI(instance);
								armorGui.openArmorGui(p, i);
							}
							
							
						}

					}

				}
			}

		}

	}

}
