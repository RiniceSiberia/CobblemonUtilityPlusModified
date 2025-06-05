package com.xxxt.cupm

import com.xxxt.cupm.CUPMTags
import com.mojang.logging.LogUtils
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.world.item.CreativeModeTabs
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.event.server.ServerStartingEvent


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CUPMMod.Companion.MODID)
class CUPMMod(modEventBus: IEventBus, modContainer: ModContainer) {
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    init {
        NeoForge.EVENT_BUS.register(this)

        CUPMItems.register(modEventBus)
        CUPMTags.register(modEventBus)
        CUPMCreativeModTabs.register(modEventBus)



        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC)

        modEventBus.addListener { event : BuildCreativeModeTabContentsEvent ->
            if (event.tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES){
                CUPMItems.ALL.forEach {item ->
                    event.accept { item.get() }
                }
            }
        }

    }


    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {
    }


    companion object {
        // Define mod id in a common place for everything to reference
        const val MODID: String = "cobblemon_utility_plus_modified"

        // Directly reference a slf4j logger
        private val LOGGER: org.slf4j.Logger = LogUtils.getLogger()

    }
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ClientModEvents {
        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent?) {}
    }
}