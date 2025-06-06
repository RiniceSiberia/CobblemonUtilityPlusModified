package com.xxxt.cupm.items

import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.Config
import com.xxxt.cupm.utils.allEVZero
import com.xxxt.cupm.utils.clearEVs
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import kotlin.random.Random

class ShinyLotteryItem : CUPMSelectingItemImpl(itemRarity = Rarity.COMMON) {

    override val name: String = "shiny_lottery"

    override val hoverText: MutableComponent
        get() = Component.translatable(hoverTextPath,
            String.format("%.1f",Config.shinyLotteryBasicDenominator),
            String.format("%.1f",Config.shinyLotteryLegendaryMultiply*Config.shinyLotteryBasicDenominator))

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (!pokemon.shiny){
            var denominator = Config.shinyLotteryBasicDenominator
            if (denominator.isNaN()){
                val mutableComponent = Component.translatable("${getItemMsgPath()}basic_denominator_nan_err")
                player.sendSystemMessage(mutableComponent)
                return InteractionResultHolder.fail(stack)
            }
            if (pokemon.isLegendary()){
                val legendaryMultiply = Config.shinyLotteryLegendaryMultiply
                if (legendaryMultiply.isNaN()){
                    val mutableComponent = Component.translatable("${getItemMsgPath()}legendary_multiply_nan_err")
                    player.sendSystemMessage(mutableComponent)
                    return InteractionResultHolder.fail(stack)
                }
                denominator = denominator * legendaryMultiply
                if (denominator.isNaN()){
                    val mutableComponent = Component.translatable("${getItemMsgPath()}result_denominator_nan_err")
                    player.sendSystemMessage(mutableComponent)
                    return InteractionResultHolder.fail(stack)
                }
            }

            if (!player.isCreative) {
                stack.shrink(1)
            }
            val lotteryNum = Random.nextDouble(0.0,denominator)
            if (lotteryNum <= 1.0){
                pokemon.shiny = true
                player.server.sendSystemMessage(
                    Component.translatable("${getItemMsgPath()}success",pokemon.nickname,player.customName)
                )
                pokemon.entity?.playSound(SoundEvents.BEACON_ACTIVATE, 1F, 1F)
                return InteractionResultHolder.success(stack)
            }else{
                player.server.sendSystemMessage(
                    Component.translatable("${getItemMsgPath()}fail",pokemon.nickname,player.customName)
                )
            }


        }

            val mutableComponent = Component.translatable("${getItemMsgPath()}has_shiny")
            player.sendSystemMessage(mutableComponent)
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && !pokemon.shiny
    }
}