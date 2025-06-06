package com.xxxt.cupm.Item

import com.cobblemon.mod.common.pokemon.Pokemon
import com.xxxt.cupm.items.CUPMSelectingItemImpl
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class DevolutionRelicItem() : CUPMSelectingItemImpl(Rarity.RARE) {

    override val name = "devolution_relic"

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        val pokeDevolution = pokemon.preEvolution
            if (pokeDevolution != null) {
                if (!player.isCreative) {
                    stack.shrink(1)
                }
                pokemon.entity?.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1F, 1F)
                val preEvolve = pokeDevolution.species
                player.sendSystemMessage(
                    Component.translatable(
                        "${msgPath}success",
                        pokemon.nickname,
                        preEvolve.translatedName
                        ))
                pokemon.species = preEvolve
                return InteractionResultHolder.success(stack)
            } else {
                player.sendSystemMessage(Component.translatable("${msgPath}fail",pokemon.nickname))
            }
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && pokemon.preEvolution != null
    }
}