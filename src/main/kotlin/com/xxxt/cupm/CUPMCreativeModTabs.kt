package com.xxxt.cupm

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier
@EventBusSubscriber(modid = CUPMMod.MODID, bus = EventBusSubscriber.Bus.MOD)
object CUPMCreativeModTabs {
    val CREATIVE_MODE_TAB =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "cobblemon_utility")

    val CAP_ITEMS_TAB = CREATIVE_MODE_TAB.register<CreativeModeTab?>(
        "cap_items_tab",
        Supplier {
            CreativeModeTab.builder().icon {
                CUPMItems.GOLDEN_CAP.get().defaultInstance
            }
                .title(
                    Component.translatable(
                        "creative_tab.cobblemon_utility_plus_modified.utility_items"))
                .displayItems((CreativeModeTab.DisplayItemsGenerator { parameters, output ->
                    CUPMItems.ALL.forEach {
                        output.accept { it.get() }
                    }
                })).build()
        })

    fun register(eventBus: IEventBus) {
        CREATIVE_MODE_TAB.register(eventBus)
    }

    @SubscribeEvent
    @JvmStatic
    fun onBuildCreativeModeTabContentsEvent(event : BuildCreativeModeTabContentsEvent){
//        if (event.tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES){
//            CUPMItems.ALL.forEach {item ->
//                event.accept { item.get() }
//            }
//        }
    }
}