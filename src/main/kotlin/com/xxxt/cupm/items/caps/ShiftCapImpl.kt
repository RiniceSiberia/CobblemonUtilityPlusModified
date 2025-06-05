package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.xxxt.cupm.CUPMTags
import com.xxxt.cupm.utils.statTranslate
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level


abstract class ShiftCapImpl(itemRarity: Rarity) : CapImpl(
    itemRarity,
    properties = Properties().apply {
        this.component(DataComponents.CUSTOM_NAME,
                    Component.translatable("item.cobblemon_utility_plus_modified.silver_cap")
                        .append("(").append(Stats.HP.displayName).append(")"))
    }){

    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (user.isCrouching){
            val stack = user.getItemInHand(hand)
            if (!world.isClientSide) {
                val tag = stack.components
                val mode = tag.getOrDefault(
                    CUPMTags.CAP_STATE_TAG.get(),
                    0)%6

                stack.set(
                    DataComponents.CUSTOM_NAME,
                    Component.translatable("item.cobblemon_utility_plus_modified.silver_cap")
                        .append("(").append(statTranslate(mode).displayName).append(")"))

                stack.set(CUPMTags.CAP_STATE_TAG.get(),(mode+1)%6)
                return InteractionResultHolder.success(stack)
            }
        }
        return super.use(world, user, hand)
    }
}