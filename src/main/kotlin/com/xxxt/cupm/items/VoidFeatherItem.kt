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
                println(getItemMsgPath())
            pokemon.entity?.playSound(SoundEvents.ENDERMAN_TELEPORT, 1F, 1F)
            player.sendSystemMessage(Component.translatable("${getItemMsgPath()}success",pokemon.getDisplayName()))
            return InteractionResultHolder.success(stack)
        }

            val mutableComponent = Component.translatable("${getItemMsgPath()}has_evs_zero")
            player.sendSystemMessage(mutableComponent)
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && !allEVZero(pokemon)
    }
}