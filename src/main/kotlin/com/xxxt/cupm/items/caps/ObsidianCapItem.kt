package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.CUPMTags
import com.xxxt.cupm.utils.isIVZero
import com.xxxt.cupm.utils.setIVZero
import com.xxxt.cupm.utils.statTranslate
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class ObsidianCapItem: ShiftCapImpl(Rarity.COMMON)   {

    override val name: String = "obsidian_cap"

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
            pokemon.entity?.playSound(CobblemonSounds.MEDICINE_CANDY_USE, 1F, 1F)
            return InteractionResultHolder.success(stack)
        }else{
            val pokeName = pokemon.getDisplayName()
            val mutableComponent = Component.translatable("${msgPath}has_iv_max",pokeName,statTranslate(mode).displayName)
            player.sendSystemMessage(mutableComponent)
        }
        return InteractionResultHolder.fail(stack)
    }
}