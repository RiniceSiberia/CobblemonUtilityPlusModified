package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.items.CUPMSelectingItemImpl
import com.xxxt.cupm.utils.dittoUseCapCheck
import net.minecraft.world.item.Rarity

abstract class CapImpl(
    properties : Properties = Properties()
) : CUPMSelectingItemImpl(properties) {

    constructor(itemRarity: Rarity) : this(
        Properties().apply {
            rarity(itemRarity)
        }
    )


    open val dittoLimit = true

    final override val bagItem: BagItem? = null

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && if (dittoLimit){
                    dittoUseCapCheck(pokemon)
                } else true
    }

}