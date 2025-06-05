package com.xxxt.cupm.items

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.item.PokeBallItem
import com.cobblemon.mod.common.pokemon.EVs
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.Config
import com.xxxt.cupm.utils.allEVZero
import com.xxxt.cupm.utils.clearEVs
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class VoidFeatherItem : CUPMSelectingItemImpl(itemRarity = Rarity.UNCOMMON) {

    override val name: String = "void_feather"

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (!allEVZero(pokemon)){
            clearEVs(pokemon)
            if (!player.isCreative) {
                stack.shrink(1)
            }
            pokemon.entity?.playSound(SoundEvents.ENDERMAN_TELEPORT, 1F, 1F)
            return InteractionResultHolder.success(stack)
        }

            val mutableComponent = Component.translatable("${msgPath}has_evs_zero")
            player.sendSystemMessage(mutableComponent)
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && !allEVZero(pokemon)
    }
}