package com.xxxt.cupm

import com.xxxt.cupm.Item.DevolutionRelicItem
import com.xxxt.cupm.items.BallSynchronizerItem
import com.xxxt.cupm.items.ShinyCardItem
import com.xxxt.cupm.items.ShinyLotteryItem
import com.xxxt.cupm.items.TransgenderOrbItem
import com.xxxt.cupm.items.VoidFeatherItem
import com.xxxt.cupm.items.caps.*
import net.minecraft.world.item.Item
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier
//@EventBusSubscriber(modid = CUPMMod.MODID, bus = EventBusSubscriber.Bus.MOD)
object CUPMItems {


    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems("cobblemon_utility_plus_modified")



    val GOLDEN_CAP: DeferredItem<Item> =
        ITEMS.register("golden_cap", Supplier { GoldenCapItem() })

    val SILVER_CAP: DeferredItem<Item> =
        ITEMS.register("silver_cap", Supplier { SilverCapItem() })

    val COPPER_CAP: DeferredItem<Item> =
        ITEMS.register("copper_cap",Supplier { CopperCapItem() })

    val IRON_CAP: DeferredItem<Item> =
        ITEMS.register("iron_cap", Supplier { IronCapItem() })

    val OBSIDIAN_CAP: DeferredItem<Item> =
        ITEMS.register("obsidian_cap", Supplier { ObsidianCapItem() })

    val VOID_CAP: DeferredItem<Item> =
        ITEMS.register("void_cap", Supplier { VoidCapItem() })

    val BALLSYNCHRONIZER: DeferredItem<Item> =
        ITEMS.register("ball_synchronizer", Supplier { BallSynchronizerItem() })


    val TRANSGENDER_ORB: DeferredItem<Item> =
        ITEMS.register("transgender_orb.json", Supplier { TransgenderOrbItem() })

    val VOID_FEATHER: DeferredItem<Item> =
        ITEMS.register("void_feather", Supplier { VoidFeatherItem() })

    val SHINY_LOTTERY: DeferredItem<Item> =
        ITEMS.register("shiny_lottery.json", Supplier { ShinyLotteryItem() })

    val SHINY_CARD: DeferredItem<Item> =
        ITEMS.register("shiny_card", Supplier { ShinyCardItem() })

    val DEVOLUTION_RELIC: DeferredItem<Item> =
        ITEMS.register("devolution_relic", Supplier { DevolutionRelicItem() })


    val ALL = listOf(
        GOLDEN_CAP,
        SILVER_CAP,
        COPPER_CAP,
        IRON_CAP,
        OBSIDIAN_CAP,
        VOID_CAP,
        BALLSYNCHRONIZER,
        TRANSGENDER_ORB,
        VOID_FEATHER,
        SHINY_LOTTERY,
        SHINY_CARD,
        DEVOLUTION_RELIC
    )

    fun register(eventBus: IEventBus) {
        ITEMS.register(eventBus)
    }
}