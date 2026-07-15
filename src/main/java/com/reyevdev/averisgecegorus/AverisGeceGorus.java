package com.reyevdev.averisgecegorus;

import org.bukkit.plugin.java.JavaPlugin;

public final class AverisGeceGorus extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GeceGorusListener(this), this);
    }
}