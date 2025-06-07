package com.xxxt.cupm.items

import com.cobblemon.mod.common.item.CobblemonItem
import com.mojang.logging.LogUtils
import com.xxxt.cupm.items.CUPMItemImpl.Companion.basicPath
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import org.slf4j.Logger

abstract class CUPMItemImpl(
    properties : Properties = Properties()
) : CobblemonItem(properties) {

    constructor(itemRarity: Rarity) : this(
        Properties().apply {
            rarity(itemRarity)
        }
    )


    abstract val name : String

    fun getItemPath() : String = "${basicPath}${name}"

    fun getItemMsgPath() : String = "${this.getItemPath()}.msg."

    open val hoverTextPath
        get() = "${getItemPath()}.tooltip"

    open val hoverText: MutableComponent
        get() = Component.translatable(hoverTextPath)


    fun getSuccessMsg(vararg args : Any) : Component{
        return Component.translatable("${getItemMsgPath()}success",*args)
    }


    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        listComponent: MutableList<Component?>,
        tooltipFlag: TooltipFlag
    ) {
//        val logger = LogUtils.getLogger()
//        logger.info("name:${name}")
//        logger.info("item_path:${getItemPath()}")
//        logger.info("hover_text_path:${hoverTextPath}")
//        logger.info("tooltips:${hoverText}")
//        logger.info("msgPath:${getItemMsgPath()}")
        listComponent.add(hoverText)
        super.appendHoverText(stack, context, listComponent, tooltipFlag)
    }
    companion object{
        val basicPath = "item.cobblemon_utility_plus_modified."
    }
}