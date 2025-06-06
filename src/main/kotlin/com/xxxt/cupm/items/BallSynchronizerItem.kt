package com.xxxt.cupm.items

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.item.PokeBallItem
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.Config
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class BallSynchronizerItem() : CUPMSelectingItemImpl(itemRarity = Rarity.RARE) {

    override val name: String = "ball_synchronizer"

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        val heldItem = pokemon.heldItem().item
        if (heldItem is PokeBallItem) {
            val pokeBall = heldItem.pokeBall
            if (Config.convertPokeBallAfterUsing && pokeBall == PokeBalls.CHERISH_BALL){
                val mutableComponent = Component.translatable("${getItemMsgPath()}to_cherish_prohibit")
                player.sendSystemMessage(mutableComponent)
            }else if(Config.convertPokeBallAfterUsing && pokemon.caughtBall == PokeBalls.CHERISH_BALL){
                val mutableComponent = Component.translatable("${getItemMsgPath()}from_cherish_prohibit")
                player.sendSystemMessage(mutableComponent)
            }else{
                pokemon.caughtBall = pokeBall

                if (!player.isCreative) {
                    stack.shrink(1)
                }
                pokemon.entity?.playSound(CobblemonSounds.POKE_BALL_SEND_OUT, 1F, 1F)
                return InteractionResultHolder.success(stack)
            }
        } else if (!(player.level()).isClientSide) {
            val mutableComponent = Component.translatable("${getItemMsgPath()}need_ball")
            player.sendSystemMessage(mutableComponent)
        }
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        val held = pokemon.heldItem().item
        if (held !is PokeBallItem) return false
        return super.canUseOnPokemon(pokemon) && if (Config.convertPokeBallAfterUsing){
            pokemon.caughtBall != PokeBalls.CHERISH_BALL
                    && held.pokeBall != PokeBalls.CHERISH_BALL
        }else{
            true
        }
    }
}