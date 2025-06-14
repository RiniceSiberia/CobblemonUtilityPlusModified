package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.CUPMSounds
import com.xxxt.cupm.CUPMTags
import com.xxxt.cupm.Config
import com.xxxt.cupm.utils.isIVMax
import com.xxxt.cupm.utils.setVMax
import com.xxxt.cupm.utils.statTranslate
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class SilverCapItem(): ShiftCapImpl(itemRarity = Rarity.EPIC, name = "silver_cap")  {

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        val mode = stack.components.getOrDefault(
            CUPMTags.CAP_STATE_TAG.get(),
            0)%6
        if (!isIVMax(pokemon,mode)) {
            setVMax(pokemon,mode)
            if (Config.convertPokeBallAfterUsing){
                pokemon.caughtBall = PokeBalls.CHERISH_BALL
            }
            if (!player.isCreative) {
                stack.shrink(1)
            }
            pokemon.entity?.playSound(CUPMSounds.INSERT_COIN_SOUND.get(), 1F, 1F)
            player.sendSystemMessage(getSuccessMsg(pokemon.getDisplayName(),statTranslate(mode).displayName))
            return InteractionResultHolder.success(stack)
        }else{
            val pokeName = pokemon.getDisplayName()
            val mutableComponent = Component.translatable("${getItemMsgPath()}has_iv_max",pokeName,statTranslate(mode).displayName)
            player.sendSystemMessage(mutableComponent)
        }
        return InteractionResultHolder.fail(stack)
    }

}