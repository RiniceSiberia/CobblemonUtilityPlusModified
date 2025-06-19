package com.xxxt.cupm

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.neoforge.common.ModConfigSpec

object CUPMConfig {
    private val BUILDER = ModConfigSpec.Builder()

    private val CONVERT_POKE_BALL_AFTER_USING: ModConfigSpec.BooleanValue = BUILDER
        .comment(
            "Convert a Poke Ball to a Cherish Ball after using a modifier," +
                    "This switch will also prevent the Ball Synchronizer from modifying the Pokemon's Pokeball to a Cherish Pokeball"
        )
        .define("convertPokeBallAfterUsing", true)

    private val IRON_COPPER_COVERT_LIMIT: ModConfigSpec.IntValue = BUILDER
        .comment(
            "After using the Iron Crown or the Bronze Crown, " +
                    "if the number of individual values reaching the max is too high, " +
                    "convert the Poke Ball to a Cherish Ball." +
                    "min is 0,and max is 6"
        )
        .defineInRange("ironCopperCovertLimit", 4, 0, 6)

    private val PROHIBIT_DITTO_MODIFIED: ModConfigSpec.BooleanValue = BUILDER
        .comment("Ditto is prohibited from using gold, silver, bronze, and iron caps")
        .define("prohibitDittoModified", true)

    private val SHINY_LOTTERY_BASIC_DENOMINATOR: ModConfigSpec.DoubleValue = BUILDER
        .comment("shiny lottery basic denominator, The bigger it is, the less likely it is")
        .defineInRange("shinyLotteryBasicDenominator", 4096.0, 1.0, Int.Companion.MAX_VALUE.toDouble())

    private val SHINY_LOTTERY_LEGENDARY_MULTIPLY: ModConfigSpec.DoubleValue = BUILDER
        .comment("shiny lottery legendary multiplier, The bigger it is, the less likely it is,multiply with basic denominator")
        .defineInRange("shinyLotteryLegendaryMultiply", 4.0, 1e-6, Int.Companion.MAX_VALUE.toDouble())


    val SPEC: ModConfigSpec = BUILDER.build()

    var convertPokeBallAfterUsing: Boolean = false

    var ironCopperCovertLimit: Int = 0

    var prohibitDittoModified: Boolean = false

    var shinyLotteryBasicDenominator: Double = 0.0

    var shinyLotteryLegendaryMultiply: Double = 0.0


    fun register(modEventBus: IEventBus, modContainer: ModContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, SPEC)
        with(modEventBus){
            addListener { event : ModConfigEvent ->
                convertPokeBallAfterUsing = CONVERT_POKE_BALL_AFTER_USING.get()
                ironCopperCovertLimit = IRON_COPPER_COVERT_LIMIT.get()
                prohibitDittoModified = PROHIBIT_DITTO_MODIFIED.get()
                shinyLotteryBasicDenominator = SHINY_LOTTERY_BASIC_DENOMINATOR.get()
                shinyLotteryLegendaryMultiply = SHINY_LOTTERY_LEGENDARY_MULTIPLY.get()
            }
        }
    }
}