package com.xxxt.cupm

import com.mojang.logging.LogUtils
import com.xxxt.cupm.events.CUPMCommands
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.RegisterCommandsEvent


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CUPMMod.MODID)
class CUPMMod(modEventBus: IEventBus, modContainer: ModContainer) {
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    init {
        NeoForge.EVENT_BUS.register(this)

        CUPMItems.register(modEventBus)
        CUPMTags.register(modEventBus)
        CUPMCreativeModTabs.register(modEventBus)


        with(NeoForge.EVENT_BUS){
            addListener(::registerCommands)
//            addListener(CUPMCreativeModTabs::onBuildCreativeModeTabContentsEvent)
        }
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC)

    }

    @SubscribeEvent
    fun registerCommands(e: RegisterCommandsEvent) {
        CUPMCommands.register(e.dispatcher, e.buildContext, e.commandSelection)
    }


    companion object {
        // Define mod id in a common place for everything to reference
        const val MODID: String = "cobblemon_utility_plus_modified"

        // Directly reference a slf4j logger
        private val LOGGER: org.slf4j.Logger = LogUtils.getLogger()

    }
}