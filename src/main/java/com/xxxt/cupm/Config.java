package com.xxxt.cupm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = CUPMMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue CONVERT_POKE_BALL_AFTER_USING = BUILDER
            .comment("Convert a Poke Ball to a Cherish Ball after using a modifier," +
                    "This switch will also prevent the Ball Synchronizer from modifying the Pokemon's Pokeball to a Cherish Pokeball")
            .define("convertPokeBallAfterUsing", true);

    private static final ModConfigSpec.IntValue IRON_COPPER_COVERT_LIMIT = BUILDER
            .comment("After using the Iron Crown or the Bronze Crown, " +
                    "if the number of individual values reaching the max is too high, " +
                    "convert the Poke Ball to a Cherish Ball." +
                    "min is 0,and max is 6")
            .defineInRange("ironCopperCovertLimit", 4,0,6);

    private static final ModConfigSpec.BooleanValue PROHIBIT_DITTO_MODIFIED = BUILDER
            .comment("Ditto is prohibited from using gold, silver, bronze, and iron caps")
            .define("prohibitDittoModified", true);

    private static final ModConfigSpec.DoubleValue SHINY_LOTTERY_BASIC_DENOMINATOR = BUILDER
            .comment("shiny lottery basic denominator, The bigger it is, the less likely it is")
            .defineInRange("shinyLotteryBasicDenominator", 4096.0,1.0,Integer.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue SHINY_LOTTERY_LEGENDARY_MULTIPLY = BUILDER
            .comment("shiny lottery legendary multiplier, The bigger it is, the less likely it is,multiply with basic denominator")
            .defineInRange("shinyLotteryLegendaryMultiply", 4.0,1e-6,Integer.MAX_VALUE);



    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean convertPokeBallAfterUsing;

    public static int ironCopperCovertLimit;

    public static boolean prohibitDittoModified;

    public static double shinyLotteryBasicDenominator;

    public static double shinyLotteryLegendaryMultiply;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        convertPokeBallAfterUsing = CONVERT_POKE_BALL_AFTER_USING.get();
        ironCopperCovertLimit = IRON_COPPER_COVERT_LIMIT.get();
        prohibitDittoModified = PROHIBIT_DITTO_MODIFIED.get();
        shinyLotteryBasicDenominator = SHINY_LOTTERY_BASIC_DENOMINATOR.get();
        shinyLotteryLegendaryMultiply = SHINY_LOTTERY_LEGENDARY_MULTIPLY.get();
    }
}
