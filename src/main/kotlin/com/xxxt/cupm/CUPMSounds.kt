package com.xxxt.cupm

import com.cobblemon.mod.common.platform.PlatformRegistry
import com.xxxt.cupm.utils.cupmResource
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object CUPMSounds{

    val SOUNDS
     = DeferredRegister.create(Registries.SOUND_EVENT,"utility_items")

    val INSERT_COIN_SOUND = SOUNDS.register(
        "insert_coin",
        Supplier{
            SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(
                    CUPMMod.MODID,
                    "insert_coin"
                )
            )
        }
    )

    fun register(eventBus : IEventBus){
        SOUNDS.register(eventBus)
    }

}