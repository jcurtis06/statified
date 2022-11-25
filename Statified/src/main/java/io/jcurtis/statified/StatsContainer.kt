package io.jcurtis.statified

class StatsContainer {
    val contents: MutableList<StatStack> = mutableListOf()

    fun getStatStack(stat: Stat): StatStack? {
        val found = contents.find { it.stat == stat }

        if (found != null) {
            return found
        }

        return null
    }

    fun incrementStatBy(stat: Stat, amount: Double) {
        val found = getStatStack(stat)

        if (found != null) {
            found.amount += amount
        } else {
            contents.add(StatStack(stat))
            incrementStatBy(stat, amount)
        }
    }

    fun setStat(stat: Stat, amount: Double) {
        val found = getStatStack(stat)

        if (found != null) {
            found.amount = amount
        } else {
            contents.add(StatStack(stat))
        }
    }

    fun getStat(stat: Stat): Double {
        val found = getStatStack(stat)

        if (found != null) {
            return found.amount
        }

        return 0.0
    }
}