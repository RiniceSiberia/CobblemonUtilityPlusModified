package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.Config
import com.xxxt.cupm.items.getItemMsgPath
import com.xxxt.cupm.utils.allIVZero
import com.xxxt.cupm.utils.setAllIVZero
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class VoidCapItem() : CapImpl(itemRarity = Rarity.UNCOMMON) {

    override val name: String
        get() = "void_cap"

    override val dittoLimit: Boolean = false


    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && !allIVZero(pokemon)
    }


    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (!allIVZero(pokemon)) {
            setAllIVZero(pokemon)
            if (Config.convertPokeBallAfterUsing){
                pokemon.caughtBall = PokeBalls.CHERISH_BALL
            }
            if (!player.isCreative) {
                stack.shrink(1)
            }
            pokemon.entity?.playSound(CobblemonSounds.MEDICINE_CANDY_USE, 1F, 1F)
            return InteractionResultHolder.success(stack)
        } else if (!(player.level()).isClientSide) {
            val pokeName = pokemon.getDisplayName().string
            val mutableComponent = Component.translatable("${getItemMsgPath()}has_iv_min",pokeName)
            player.sendSystemMessage(mutableComponent)
        }
        return InteractionResultHolder.fail(stack)
    }
}