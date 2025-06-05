
package com.xxxt.cupm.items

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.pokemon.Gender
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity

class TransgenderOrbItem() : CUPMSelectingItemImpl(itemRarity = Rarity.UNCOMMON) {

    override val name: String = "transgender_orb"

    override fun applyToPokemon(
        player: ServerPlayer,
        stack: ItemStack,
        pokemon: Pokemon
    ): InteractionResultHolder<ItemStack>? {
        val pokeName = pokemon.getDisplayName()
        val pokeGender = pokemon.gender
        if (pokemon.species.possibleGenders.size > 1){
            if (pokeGender == Gender.FEMALE) {
                pokemon.gender = Gender.MALE
                if (pokemon.gender == Gender.MALE) {
                    if (!player.isCreative) {
                        stack.shrink(1)
                    }
                    pokemon.entity?.playSound(CobblemonSounds.POKE_BALL_SEND_OUT, 1F, 1F)
                    return InteractionResultHolder.success(stack)
                }
            } else if (pokeGender == Gender.MALE) {
                pokemon.gender = Gender.FEMALE
                if (pokemon.gender == Gender.FEMALE) {
                    if (!player.isCreative) {
                        stack.shrink(1)
                    }
                    pokemon.entity?.playSound(CobblemonSounds.POKE_BALL_SEND_OUT, 1F, 1F)
                    return InteractionResultHolder.success(stack)
                }
            }
        }
        val genderError = Component.translatable(
            "${msgPath}can_not_change_gender",
            pokeName)
        player.sendSystemMessage(genderError)
        return InteractionResultHolder.fail(stack)
    }

    override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
        return super.canUseOnPokemon(pokemon)
                && pokemon.gender != Gender.GENDERLESS
                && pokemon.species.possibleGenders.size > 1
    }
}