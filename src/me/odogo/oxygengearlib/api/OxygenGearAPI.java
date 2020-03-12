package me.odogo.oxygengearlib.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.odogo.oxygengearlib.OxygenGearLib;

public class OxygenGearAPI {

	private static OxygenGearLib plugin = OxygenGearLib.getPlugin(OxygenGearLib.class);
	private static FileConfiguration data = plugin.pDataConfig;
	private static File fData = plugin.playerData;

	/**
	 * Used for main plugin, DO NOT USE UNLESS YOU KNOW WHAT YOU ARE DOING
	 * 
	 * @param inv The inventory to take the items from
	 * @param player The player its held to
	 */
	public static void saveContents(Inventory inv, Player player) {
		UUID uuid = player.getUniqueId();

		ArrayList<ItemStack> contents = new ArrayList<ItemStack>();

		contents.add(inv.getItem(11)); contents.add(inv.getItem(12)); contents.add(inv.getItem(13)); contents.add(inv.getItem(14)); contents.add(inv.getItem(15));

		data.set(uuid + ".contains", contents);

		try {
			data.save(fData);

			if(plugin.getConfig().getBoolean("post-logs")) {
				plugin.getLogger().info("Successfully saved " + uuid + " or " + player.getName() + " gear data.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used for main plugin, DO NOT USE UNLESS YOU KNOW WHAT YOU ARE DOING
	 * 
	 * @param inv The inventory to take the items from
	 * @param player The player its held to
	 */
	public static void restoreContents(Inventory inv, Player player) {
		UUID uuid = player.getUniqueId();

		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> contents = (ArrayList<ItemStack>) data.get(uuid + ".contains");

		if(contents == null) {
			if(plugin.getConfig().getBoolean("post-logs")) {
				plugin.getLogger().info("Restored " + uuid + " or " + player.getName() + "'s nullified data.");
			}

			inv.setItem(11, null); inv.setItem(12, null); inv.setItem(13, null); inv.setItem(14, null); inv.setItem(15, null);
			return;
		}

		inv.setItem(11, contents.get(0)); inv.setItem(12, contents.get(1)); inv.setItem(13, contents.get(2)); inv.setItem(14, contents.get(3)); inv.setItem(15, contents.get(4));

		if(plugin.getConfig().getBoolean("post-logs")) {
			plugin.getLogger().info("Successfully restored " + uuid + " or " + player.getName() + "'s gear data.");
		}
	}

	/**
	 * Returns the item of which slot is picked from a specific player.
	 * 
	 * @param slot The slot in the config.
	 * @param player Where the slot is from
	 * @return The item from the slot in the config.
	 * @throws IndexOutOfBoundsException Slot could be out of bounds.
	 */
	public static ItemStack getItemFromSlot(int slot, Player player) {
		UUID uuid = player.getUniqueId();

		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> contents = (ArrayList<ItemStack>) data.get(uuid + ".contains");

		return contents.get(slot);
	}
}
