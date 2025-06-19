package com.xxxt.cupm.items

import com.cobblemon.mod.common.api.item.PokemonSelectingItem
import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.CUPMTags.BINDING_PLAYER_NAME_TAG
import com.xxxt.cupm.CUPMTags.EXPIRE_TIME_STAMP_TAG
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import java.util.*

abstract class CUPMSelectingItemImpl(
    properties : Properties = Properties()
) : CUPMItemImpl(properties), PokemonSelectingItem{

    constructor(itemRarity: Rarity) : this(
        Properties().apply {
            rarity(itemRarity)
        }
    )


    override val bagItem: BagItem? = null
    override fun canUseOnPokemon(pokemon: Pokemon): Boolean = pokemon.isPlayerOwned()


    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        listComponent: MutableList<Component?>,
        tooltipFlag: TooltipFlag
    ) {
        super.appendHoverText(stack, context, listComponent, tooltipFlag)
        stack.components.get(EXPIRE_TIME_STAMP_TAG.get())?.also {
            val diffInMillis = it.timeInMillis - Calendar.getInstance().timeInMillis
            if (diffInMillis > 0){
                // 将毫秒差值转换为秒、分钟、小时、天数
                val seconds = diffInMillis / 1000 % 60
                val secondsText = if (seconds > 0){
                    Component.translatable("unit.cobblemon_utility_plus_modified.time.month",seconds)
                }else{
                    Component.empty()
                }
                val minutes = diffInMillis / (1000 * 60) % 60
                val minutesText = if (minutes > 0) {
                    Component.translatable("unit.cobblemon_utility_plus_modified.time.minute",minutes)
                } else{
                    Component.empty()
                }
                val hours = diffInMillis / (1000 * 60 * 60) % 24
                val hoursText = if (hours > 0) {
                    Component.translatable("unit.cobblemon_utility_plus_modified.time.hour",hours)
                } else{
                    Component.empty()
                }
                val days = diffInMillis / (1000 * 60 * 60 * 24) % 7
                val daysText = if (days > 0) {
                    Component.translatable("unit.cobblemon_utility_plus_modified.time.day",days)
                } else{
                    Component.empty()
                }
                val weeks = diffInMillis / (1000 * 60 * 60 * 24 * 7)
                val weeksText = if (weeks > 0) {
                    Component.translatable("unit.cobblemon_utility_plus_modified.time.week",weeks)
                } else{
                    Component.empty()
                }
                listComponent.add(
                    Component.translatable(
                        "${basicPath}tooltip.expire",
                        it.toString(),
                        weeksText.append(daysText.append(hoursText.append(minutesText.append(secondsText))))
                            .withStyle(ChatFormatting.ITALIC)
                            .also { it ->
                                if (diffInMillis/(1000 * 60 * 60 * 24) < 1){
                                it.withStyle(ChatFormatting.RED)
                            }
                        }
                    ))
                }else{
                listComponent.add(
                    Component.translatable(
                        "${basicPath}tooltip.expired",
                        it.toString()
                    ).withStyle(ChatFormatting.RED)
                        .withStyle(ChatFormatting.ITALIC)
                )
            }
        }

        stack.components.get(BINDING_PLAYER_NAME_TAG.get())?.also {
            listComponent.add(Component.translatable("${basicPath}tooltip.binding",it))
        }
    }

    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = user.getItemInHand(hand)
        if (user is ServerPlayer) {
            return use(user, stack)
        }
        return InteractionResultHolder.success(user.getItemInHand(hand))
    }

    override fun use(player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack> {
        //过期检测

        val expireTimestamp = stack.components.get(EXPIRE_TIME_STAMP_TAG.get())

        if(!player.isCreative && expireTimestamp != null && Calendar.getInstance() >= expireTimestamp){
            stack.shrink(stack.count)
            player.sendSystemMessage(
                Component.translatable("${basicPath}msg.expire",expireTimestamp.toString())
            )
            return InteractionResultHolder.fail(stack)
        }
        //绑定检测
        val bindingPlayerName = stack.components.get(BINDING_PLAYER_NAME_TAG.get())

        if (bindingPlayerName != null && player.name.toString() != bindingPlayerName) {
            val mutableComponent = Component.translatable(
                "${basicPath}msg.binding_deviation",
                bindingPlayerName,
                player.name
            )
            player.sendSystemMessage(mutableComponent)
            return InteractionResultHolder.fail(stack)
        }

        return super<PokemonSelectingItem>.use(player, stack)
    }
}