package com.xxxt.cupm.utils

import com.cobblemon.mod.common.api.pokemon.stats.Stat
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.pokemon.EVs
import com.cobblemon.mod.common.pokemon.Pokemon


@Throws(IndexOutOfBoundsException::class)
fun statTranslate(index: Int): Stat {
    when(index){
        0 -> return Stats.HP
        1 -> return Stats.ATTACK
        2 -> return Stats.DEFENCE
        3 -> return Stats.SPECIAL_ATTACK
        4 -> return Stats.SPECIAL_DEFENCE
        5 -> return Stats.SPEED
        else -> throw IndexOutOfBoundsException("""
            index out of range in IVSTranslation.getIvsByAttIndex,
            index: $index
        """.trimIndent())
    }
}

fun allIVMax(pokemon: Pokemon): Boolean{
    return Stats.PERMANENT.all {it -> isIVMax(pokemon,it) }
}

fun isIVMax(pokemon: Pokemon,stat: Stat): Boolean{
    return (pokemon.ivs[stat]?.equals(31)?:false)
}

@Throws(IndexOutOfBoundsException::class)
fun isIVMax(pokemon: Pokemon,statIndex : Int): Boolean{
    return isIVMax(pokemon,statTranslate(statIndex))
}

fun getVCount(pokemon: Pokemon): Int{
    return Stats.PERMANENT.count {it -> isIVMax(pokemon,it) }
}

fun setVMax(pokemon: Pokemon,stat: Stat) {
    pokemon.setIV(stat,31)
}

@Throws(IndexOutOfBoundsException::class)
fun setVMax(pokemon: Pokemon,statIndex : Int) {
    setVMax(pokemon,statTranslate(statIndex))
}

fun setAllVMax(pokemon: Pokemon) {
    Stats.PERMANENT.forEach {
        setVMax(pokemon,it)
    }
}

fun isIVZero(pokemon: Pokemon,stat: Stat): Boolean{
    return pokemon.ivs[stat]?.equals(0)?:false
}

@Throws(IndexOutOfBoundsException::class)
fun isIVZero(pokemon: Pokemon,statIndex : Int): Boolean{
    return isIVZero(pokemon,statTranslate(statIndex))
}

fun allIVZero(pokemon: Pokemon): Boolean{
    return Stats.PERMANENT.all {it -> isIVZero(pokemon,it)}
}

fun setIVZero(pokemon: Pokemon,stat: Stat) {
    pokemon.setIV(stat,0)
}

@Throws(IndexOutOfBoundsException::class)
fun setIVZero(pokemon: Pokemon,statIndex : Int) {
    setIVZero(pokemon,statTranslate(statIndex))
}

fun setAllIVZero(pokemon: Pokemon) {
    Stats.PERMANENT.forEach {
        setIVZero(pokemon,it)
    }
}

fun isEVZero(pokemon: Pokemon,stat: Stat): Boolean{
    return pokemon.evs[stat]?.equals(0)?:false
}

@Throws(IndexOutOfBoundsException::class)
fun isEVZero(pokemon: Pokemon,statIndex : Int): Boolean{
    return isEVZero(pokemon,statTranslate(statIndex))
}

fun allEVZero(pokemon: Pokemon): Boolean{
    return Stats.PERMANENT.all {it -> isEVZero(pokemon,it)}
}

fun isFullEV(pokemon: Pokemon,stat: Stat): Boolean{
    return (pokemon.evs[stat]?.equals(EVs.MAX_STAT_VALUE)?:false)
}

@Throws(IndexOutOfBoundsException::class)
fun isFullEV(pokemon: Pokemon,statIndex : Int): Boolean{
    return isFullEV(pokemon,statTranslate(statIndex))
}

fun allFullEV(pokemon: Pokemon): Boolean{
    return pokemon.evs.sumOf { it.value } >= EVs.MAX_STAT_VALUE
}

fun clearEVs(pokemon: Pokemon){
    Stats.PERMANENT.forEach { pokemon.evs[it] = 0 }
}
