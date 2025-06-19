package com.xxxt.cupm.utils

import com.cobblemon.mod.common.api.pokemon.egg.EggGroup
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.CUPMConfig

fun dittoUseCapCheck(pokemon: Pokemon): Boolean{
    if (!CUPMConfig.prohibitDittoModified) return true
    return pokemon.species.eggGroups.all{it != EggGroup.DITTO}
}