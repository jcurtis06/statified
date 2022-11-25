package io.jcurtis.statified.listeners

import io.jcurtis.statified.Stat
import io.jcurtis.statified.Statified
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class BlocksListener(private val statified: Statified): Listener {
    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        val pContainer = statified.statsContainers[e.player.uniqueId] ?: return
        pContainer.incrementStatBy(Stat.BLOCKS_BROKE, 1.0)
    }

    @EventHandler
    fun onPlace(e: BlockPlaceEvent) {
        val pContainer = statified.statsContainers[e.player.uniqueId] ?: return
        pContainer.incrementStatBy(Stat.BLOCKS_PLACED, 1.0)
    }
}