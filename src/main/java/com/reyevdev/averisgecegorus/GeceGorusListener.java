package com.reyevdev.averisgecegorus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public class GeceGorusListener implements Listener {

    private final Plugin plugin;
    private final PotionEffect nightVisionEffect;
    private final Set<String> blockedCommands = Set.of(
            "pl", "plugins", "plugin",
            "bukkit:pl", "bukkit:plugins", "bukkit:plugin",
            "about", "bukkit:about",
            "ver", "bukkit:ver",
            "version", "bukkit:version",
            "icanhasbukkit"
    );

    public GeceGorusListener(Plugin plugin) {
        this.plugin = plugin;
        this.nightVisionEffect = new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false, false);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().addPotionEffect(nightVisionEffect);
    }

    @EventHandler
    public void onResurrect(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player player) {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (player.isOnline()) {
                    player.addPotionEffect(nightVisionEffect);
                }
            });
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            if (player.isOnline()) {
                player.addPotionEffect(nightVisionEffect);
            }
        });
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("averis.pl")) {
            return;
        }

        String message = event.getMessage().toLowerCase();
        String[] args = message.split(" ");
        if (args.length > 0 && args[0].startsWith("/")) {
            String cmd = args[0].substring(1);
            if (blockedCommands.contains(cmd)) {
                event.setCancelled(true);
                player.sendMessage("§6ᴀᴠᴇʀiѕ §7» §cGeçersiz komut.");
            }
        }
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("averis.pl")) {
            event.getCommands().removeIf(blockedCommands::contains);
        }
    }
}