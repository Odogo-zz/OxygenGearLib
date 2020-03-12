package me.odogo.oxygengearlib.cmds;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.odogo.oxygengearlib.OxygenGearLib;
import me.odogo.oxygengearlib.api.OxygenGearAPI;

public class OxygenGearCMD implements CommandExecutor, Listener {

	private OxygenGearLib plugin = OxygenGearLib.getPlugin(OxygenGearLib.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("oxygengear") || cmd.getName().equalsIgnoreCase("ogear")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;

				if(this.getInventory(player) == null) {
					player.sendMessage(ChatColor.RED + "This command has returned null, therefor please contact admins to resolve issue.");
					return true;
				}

				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
				player.openInventory(this.getInventory(player));
			} else {
				sender.sendMessage("I see, well. This command isn't really for you at all.");
			}
		}

		return true;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		Inventory i = e.getClickedInventory();
		ItemStack cursor = e.getCursor();
		ItemStack clickedOn = e.getCurrentItem();
		String title = "";

		int slot = e.getSlot();

		if(i == null || clickedOn == null) {
			return;
		}

		title = e.getView().getTitle();

		if(title == "" || title == null) { return; }

		if(title.equals(ChatColor.BLACK + "[OxygenGearLib] GUI")
				|| title.equals(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("inventory-display.display")))) {

			if(!(slot >= 11 && slot <= 15)) {
				e.setCancelled(true);
			} 

			if(slot == 11) {
				if(cursor.getType() == Material.AIR) {
					if(clickedOn.getType() == Material.STAINED_GLASS) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
					}
				} else if(cursor.getType() != Material.STAINED_GLASS) {
					e.setCancelled(true);
					player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 0);
				}
			} else if(slot == 12) {
				if(cursor.getType() == Material.AIR) {
					if(clickedOn.getType() == Material.LEATHER_CHESTPLATE) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
					}
				} else if(cursor.getType() != Material.LEATHER_CHESTPLATE) {
					e.setCancelled(true);
					player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 0);
				}
			} else if(slot == 13) {
				if(cursor.getType() == Material.AIR) {
					if(clickedOn.getType() == Material.END_ROD) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
					}
				} else if(cursor.getType() != Material.END_ROD) {
					e.setCancelled(true);
					player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 0);
				}
			} else if(slot == 14) {
				if(cursor.getType() == Material.AIR) {
					if(clickedOn.getType() == Material.MAGENTA_SHULKER_BOX) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
					}
				} else if(cursor.getType() != Material.MAGENTA_SHULKER_BOX) {
					e.setCancelled(true);
					player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 0);
				}
			} else if(slot == 15) {
				if(cursor.getType() == Material.AIR) {
					if(clickedOn.getType() == Material.MAGENTA_SHULKER_BOX) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
					}
				} else if(cursor.getType() != Material.MAGENTA_SHULKER_BOX) {
					e.setCancelled(true);
					player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 0);
				}
			}
		}

	}

	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		Inventory i = e.getInventory();
		String title = "";

		title = e.getView().getTitle();

		if(title == "" || title == null) { return; }

		if(title.equals(ChatColor.BLACK + "[OxygenGearLib] GUI") || title.equals(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("inventory-display.display")))) {
			OxygenGearAPI.saveContents(i, player);
		}
	}

	public Inventory getInventory(Player player) {

		Inventory i = null;

		if(plugin.getConfig().getString("inventory-display.display-type").equalsIgnoreCase("default")) {
			i = plugin.getServer().createInventory(null, 18, ChatColor.BLACK + "[OxygenGearLib] GUI");
		} else if(plugin.getConfig().getString("inventory-display.display-type").equalsIgnoreCase("custom")) {
			i = plugin.getServer().createInventory(null, 18, ChatColor.BLACK + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("inventory-display.display")));
		} else {
			plugin.getLogger().severe("[Inv Builder] [FAILED] Config section 'inventory-display.display-type' doesn't match registered types!");
			return null;
		}

		ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta emptyM = empty.getItemMeta(); emptyM.setDisplayName(""); empty.setItemMeta(emptyM);
		i.setItem(0, empty); i.setItem(1, empty); i.setItem(7, empty); i.setItem(8, empty);
		i.setItem(9, empty); i.setItem(10, empty); i.setItem(16, empty); i.setItem(17, empty);

		ItemStack helmet = new ItemStack(Material.STAINED_GLASS, 1); ItemMeta helmetM = helmet.getItemMeta();
		helmetM.setDisplayName(ChatColor.AQUA + "Helmet"); helmetM.setLore(Arrays.asList(ChatColor.GRAY + "Set your helmet below, otherwise you wont be able to breath.", ChatColor.RED + "And who wants to die cause of their own stupidity?"));
		helmet.setItemMeta(helmetM); i.setItem(2, helmet);

		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE, 1); ItemMeta chestM = chest.getItemMeta();
		chestM.setDisplayName(ChatColor.AQUA + "Tank(s) Holder"); chestM.setLore(Arrays.asList(ChatColor.GRAY + "It'll be easier to put it on something, and not directly on your back."));
		chest.setItemMeta(chestM); i.setItem(3, chest);

		ItemStack pipe = new ItemStack(Material.END_ROD, 1); ItemMeta pipeM = pipe.getItemMeta();
		pipeM.setDisplayName(ChatColor.AQUA + "Piping"); pipeM.setLore(Arrays.asList(ChatColor.GRAY + "How you suppose to get the oxygen from the tank to helmet?", ChatColor.LIGHT_PURPLE + "Magic?", ChatColor.GRAY + "No one asked you."));
		pipe.setItemMeta(pipeM); i.setItem(4, pipe);

		ItemStack tankOne = new ItemStack(Material.MAGENTA_SHULKER_BOX, 1); ItemMeta tankOneM = tankOne.getItemMeta();
		tankOneM.setDisplayName(ChatColor.AQUA + "Tank Slot #1"); tankOneM.setLore(Arrays.asList(ChatColor.GRAY + "Now we're getting down to business."));
		tankOne.setItemMeta(tankOneM); i.setItem(5, tankOne);

		ItemStack tankTwo = new ItemStack(Material.MAGENTA_SHULKER_BOX, 1); ItemMeta tankTwoM = tankTwo.getItemMeta();
		tankTwoM.setDisplayName(ChatColor.AQUA + "Tank Slot #2"); tankTwoM.setLore(Arrays.asList(ChatColor.GRAY + "Double? I like you."));
		tankTwo.setItemMeta(tankTwoM); i.setItem(6, tankTwo);

		OxygenGearAPI.restoreContents(i, player);

		return i;
	}
}







