package com.xxxt.cupm.items

import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.utils.allEVZero
import com.xxxt.cupm.utils.clearEVs
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class ShinyCardItem : CUPMSelectingItemImpl(itemRarity = Rarity.EPIC) {

    override val name: String = "shiny_card"

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (!pokemon.shiny){
            pokemon.shiny = true
            if (!player.isCreative) {
                stack.shrink(1)
            }
            pokemon.entity?.playSound(SoundEvents.BEACON_ACTIVATE, 1F, 1F)
            return InteractionResultHolder.success(stack)
        }

            val mutableComponent = Component.translatable("${getItemMsgPath()}has_shiny")
            player.sendSystemMessage(mutableComponent)
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && !pokemon.shiny
    }
}