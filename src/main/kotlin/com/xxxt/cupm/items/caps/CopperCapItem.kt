package com.xxxt.cupm.items.caps

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.CUPMTags
import com.xxxt.cupm.Config
import com.xxxt.cupm.utils.getVCount
import com.xxxt.cupm.utils.statTranslate
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import kotlin.random.Random

class CopperCapItem() : ShiftCapImpl(itemRarity = Rarity.RARE, name = "copper_cap") {


    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        val mode = stack.components.getOrDefault(
            CUPMTags.CAP_STATE_TAG.get(),
            0)%6
        if (!(player.level()).isClientSide) {
            val randomValue = Random.nextInt(0,31)
            pokemon.setIV(stat = statTranslate(mode), value = randomValue)
            if (Config.convertPokeBallAfterUsing && getVCount(pokemon)>= Config.ironCopperCovertLimit){
                pokemon.caughtBall = PokeBalls.CHERISH_BALL
            }
            if (!player.isCreative) {
                stack.shrink(1)
            }

            pokemon.entity?.playSound(CobblemonSounds.MEDICINE_CANDY_USE, 1F, 1F)
        }
        return InteractionResultHolder.success(stack)
    }


}