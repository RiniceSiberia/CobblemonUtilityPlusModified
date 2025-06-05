package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.items.CUPMSelectingItemImpl
import com.xxxt.cupm.utils.dittoUseCapCheck
import net.minecraft.world.item.Rarity

abstract class CapImpl(
    itemRarity: Rarity,
    properties : Properties = Properties()
) : CUPMSelectingItemImpl(itemRarity,properties) {

    open val dittoLimit = true

    final override val bagItem: BagItem? = null

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && if (dittoLimit){
                    dittoUseCapCheck(pokemon)
                } else true
    }


}