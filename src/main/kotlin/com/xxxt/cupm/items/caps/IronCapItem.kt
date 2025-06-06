package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.CUPMTags
import com.xxxt.cupm.Config
import com.xxxt.cupm.items.getItemMsgPath
import com.xxxt.cupm.utils.getVCount
import com.xxxt.cupm.utils.isIVMax
import com.xxxt.cupm.utils.statTranslate
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class IronCapItem() : ShiftCapImpl(itemRarity = Rarity.UNCOMMON, name = "iron_cap") {

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        val mode = stack.components.getOrDefault(
            CUPMTags.CAP_STATE_TAG.get(),
            0)%6
        if (!isIVMax(pokemon,mode)) {
            if (!(player.level()).isClientSide) {
                val stat= statTranslate(mode)
                pokemon.setIV(stat,pokemon.ivs[stat]!!+1)
                if (Config.convertPokeBallAfterUsing && getVCount(pokemon)>= Config.ironCopperCovertLimit){
                    pokemon.caughtBall = PokeBalls.CHERISH_BALL
                }
                if (!player.isCreative) {
                    stack.shrink(1)
                }
                pokemon.entity?.playSound(CobblemonSounds.MEDICINE_CANDY_USE, 1F, 1F)
                return InteractionResultHolder.success(stack)
            }
        } else if (!(player.level()).isClientSide) {
            val pokeName = pokemon.getDisplayName()
            val mutableComponent = Component.translatable("${getItemMsgPath()}has_iv_max",pokeName,statTranslate(mode).displayName)
            player.sendSystemMessage(mutableComponent)
        }
        return InteractionResultHolder.fail(stack)
    }

}