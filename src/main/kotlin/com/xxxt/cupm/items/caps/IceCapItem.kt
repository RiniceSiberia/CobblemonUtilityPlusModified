package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.api.pokemon.egg.EggGroup
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.Config
import com.xxxt.cupm.utils.getVCount
import com.xxxt.cupm.utils.weightedShuffle
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack

class IceCapItem : CapImpl(){
    override val name: String
        get() = "ice_cap"

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && pokemon.species.eggGroups.contains(EggGroup.UNDISCOVERED)
    }


    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (canUseOnPokemon(pokemon)){

            if (getVCount(pokemon)<3) {
                val stats = mapOf(
                    Stats.HP to 25,
                    Stats.ATTACK to 35,
                    Stats.DEFENCE to 25,
                    Stats.SPECIAL_ATTACK to 35,
                    Stats.SPECIAL_DEFENCE to 25,
                    Stats.SPEED to 30
                )

                val lottey = weightedShuffle(stats)

                lottey.forEachIndexed { idx, it ->
                    pokemon.ivs[it] = if(idx < 3){
                        31
                    }else{
                        0
                    }
                }
                if (!player.isCreative) {
                    stack.shrink(1)
                }
                pokemon.entity?.playSound(SoundEvents.ENDERMAN_TELEPORT, 1F, 1F)
                player.sendSystemMessage(getSuccessMsg(pokemon.getDisplayName()))
                return InteractionResultHolder.success(stack)
            } else if (!(player.level()).isClientSide) {
                val pokeName = pokemon.getDisplayName().string
                val mutableComponent = Component.translatable("${getItemMsgPath()}has_iv_full",pokeName)
                player.sendSystemMessage(mutableComponent)
            }
        }else{
            player.sendSystemMessage(
                Component.translatable(
                    "${getItemMsgPath()}cannot_use",
                    pokemon.getDisplayName()
                )
            )
        }
        return InteractionResultHolder.fail(stack)
    }
}