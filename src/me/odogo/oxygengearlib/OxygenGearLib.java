package me.odogo.oxygengearlib;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.odogo.oxygengearlib.cmds.OxygenGearCMD;

public class OxygenGearLib extends JavaPlugin {

	public File playerData = null;
	public FileConfiguration pDataConfig = null;

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		registerFileData();

		getCommand("oxygengear").setExecutor(new OxygenGearCMD());
		getCommand("ogear").setExecutor(new OxygenGearCMD());
		getServer().getPluginManager().registerEvents(new OxygenGearCMD(), this);
	}

	@Override
	public void onDisable() {

	}

	private void registerFileData() {
		playerData = new File(this.getDataFolder(), "player-data.yml");

		if(!this.getDataFolder().exists()) {
			this.getDataFolder().mkdirs();
		}

		if(!this.playerData.exists()) {
			try {
				playerData.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		pDataConfig = YamlConfiguration.loadConfiguration(playerData);
	}

}
