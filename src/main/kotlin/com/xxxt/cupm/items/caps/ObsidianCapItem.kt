package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.CUPMTags
import com.xxxt.cupm.utils.isIVZero
import com.xxxt.cupm.utils.setIVZero
import com.xxxt.cupm.utils.statTranslate
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class ObsidianCapItem: ShiftCapImpl(itemRarity = Rarity.COMMON,name = "obsidian_cap")   {

    override val dittoLimit: Boolean = false


    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        val mode = stack.components.getOrDefault(
            CUPMTags.CAP_STATE_TAG.get(),
            0)%6
        if (!isIVZero(pokemon,mode)) {
            setIVZero(pokemon,mode)
            if (!player.isCreative) {
                stack.shrink(1)
            }
            pokemon.entity?.playSound(SoundEvents.ENDERMAN_TELEPORT, 1F, 1F)
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