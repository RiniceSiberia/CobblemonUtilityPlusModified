package com.xxxt.cupm.items

import com.cobblemon.mod.common.item.CobblemonItem
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

abstract class CUPMItemImpl(
    val itemRarity: Rarity,
    properties : Properties = Properties()
) : CobblemonItem(properties.apply {
    rarity(itemRarity)
}) {
    abstract val name: String


    val itemNamePath = "${basicPath}$name"

    val hoverText = Component.translatable("$itemNamePath.tooltip")

    val msgPath = "$itemNamePath.msg."

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        listComponent: MutableList<Component?>,
        tooltipFlag: TooltipFlag
    ) {
        listComponent.add(hoverText)
        super.appendHoverText(stack, context, listComponent, tooltipFlag)
    }
    companion object{
        val basicPath = "item.cobblemon_utility_plus_modified."
    }
}