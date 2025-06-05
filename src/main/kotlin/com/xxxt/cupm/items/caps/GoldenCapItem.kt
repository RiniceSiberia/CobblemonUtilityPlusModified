package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.Config
import com.xxxt.cupm.utils.allIVMax
import com.xxxt.cupm.utils.setAllVMax
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level

class GoldenCapItem() : CapImpl(itemRarity = Rarity.EPIC) {

    override val name: String = "golden_cap"


    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (!allIVMax(pokemon)){
            setAllVMax(pokemon)
            if (Config.convertPokeBallAfterUsing){
                pokemon.caughtBall = PokeBalls.CHERISH_BALL
            }
            if (!player.isCreative) {
                stack.shrink(1)
            }
            pokemon.entity?.playSound(CobblemonSounds.MEDICINE_CANDY_USE, 1F, 1F)
            return InteractionResultHolder.success(stack)
        }else{
            val pokeName = pokemon.getDisplayName()
            val mutableComponent = Component.translatable("${msgPath}has_iv_max",pokeName)
            player.sendSystemMessage(mutableComponent)
        }
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && !allIVMax(pokemon)
    }
}