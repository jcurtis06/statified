package io.jcurtis.statified

import io.jcurtis.statified.commands.StatsCMD
import io.jcurtis.statified.listeners.BlocksListener
import io.jcurtis.statified.listeners.PvPListener
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class Statified : JavaPlugin(), Listener {
    val statsContainers: MutableMap<UUID, StatsContainer> = mutableMapOf()

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        server.pluginManager.registerEvents(BlocksListener(this), this)
        server.pluginManager.registerEvents(PvPListener(this), this)

        getCommand("stats")?.setExecutor(StatsCMD(this))
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        statsContainers[e.player.uniqueId] = StatsContainer()
    }
}