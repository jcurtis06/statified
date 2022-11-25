package io.jcurtis.statified.listeners

import io.jcurtis.statified.Stat
import io.jcurtis.statified.Statified
import io.jcurtis.statified.StatsContainer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class PvPListener(private val statified: Statified): Listener {
    @EventHandler
    fun onDeath(e: EntityDeathEvent) {
        var victim = e.entity

        if (victim is Player) {
            println("Player died")
            val vContainer = statified.statsContainers[victim.uniqueId] ?: return
            vContainer.incrementStatBy(Stat.DEATHS, 1.0)
            vContainer.setStat(Stat.KILL_STREAK, 0.0)
            vContainer.setStat(Stat.KDR, getKDR(vContainer))

            println("Updated stats for victim")
            println("KDR: ${vContainer.getStat(Stat.KDR)}")
            println("KILL_STREAK: ${vContainer.getStat(Stat.KILL_STREAK)}")
            println("DEATHS: ${vContainer.getStat(Stat.DEATHS)}")
        }

        var killer = e.entity.killer ?: return

        if (killer is Player) {
            println("Something killed by player")
            val pContainer = statified.statsContainers[killer.uniqueId] ?: return
            if (victim is Player) {
                println("Player killed player")
                if (victim == killer) return
                println("Victim != killer")

                pContainer.incrementStatBy(Stat.PLAYER_KILLS, 1.0)
                pContainer.incrementStatBy(Stat.KILL_STREAK, 1.0)

                // calculate kill-death ratio for both players
                val victimContainer = statified.statsContainers[victim.uniqueId] ?: return
                victimContainer.setStat(Stat.KILL_STREAK, 0.0)

                pContainer.setStat(Stat.KDR, getKDR(pContainer))
                victimContainer.setStat(Stat.KDR, getKDR(victimContainer))
            } else {
                pContainer.incrementStatBy(Stat.MOB_KILLS, 1.0)
            }
        }
    }

    fun getKDR(sContainer: StatsContainer): Double {
        return sContainer.getStat(Stat.PLAYER_KILLS) / sContainer.getStat(Stat.DEATHS)
    }
}