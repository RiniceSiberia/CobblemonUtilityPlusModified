package com.xxxt.cupm.events

import com.xxxt.cupm.CUPMItems
import com.xxxt.cupm.CUPMMod
import com.xxxt.cupm.CUPMTags
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent

@EventBusSubscriber(modid = CUPMMod.Companion.MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ClientModInit {
    @SubscribeEvent
    @JvmStatic
    fun onClientSetup(event: RegisterClientReloadListenersEvent) {
        val shiftCaps = listOf(
            CUPMItems.SILVER_CAP.get(),
            CUPMItems.COPPER_CAP.get(),
            CUPMItems.IRON_CAP.get(),
            CUPMItems.OBSIDIAN_CAP.get()
        )

        for (cap in shiftCaps) {
            ItemProperties.register(cap, CUPMTags.CAP_STATE_TAG.id,
                object : ClampedItemPropertyFunction {
                override fun unclampedCall(
                    stack: ItemStack,
                    level: ClientLevel?,
                    entity: LivingEntity?,
                    seed: Int
                ): Float {
                    return stack.getComponents().getOrDefault(CUPMTags.CAP_STATE_TAG.get(), 0) * 0.1f
                }
            })
        }
    }
}