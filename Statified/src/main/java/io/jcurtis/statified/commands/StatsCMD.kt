package io.jcurtis.statified.commands

import io.jcurtis.statified.Stat
import io.jcurtis.statified.Statified
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StatsCMD(private val statified: Statified): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.sendMessage("§7---------------------- [ §aStats §7] ----------------------")

            for (stat in Stat.values()) {
                val statContainer = statified.statsContainers[sender.uniqueId] ?: return false
                sender.sendMessage("§7- §a${stat.name} §f- §7${statContainer.getStat(stat)}")
            }
        }
        return false
    }
}