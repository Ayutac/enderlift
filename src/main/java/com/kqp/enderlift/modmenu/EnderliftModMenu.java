package com.kqp.enderlift.modmenu;

import java.util.function.Function;

import com.kqp.enderlift.Enderlift;
import com.kqp.enderlift.EnderliftConfig;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;

public class EnderliftModMenu implements ModMenuApi {
    private static final String REDSTONE_DISABLED = "option.enderlift.redstonetype1";
    private static final String REDSTONE_ENABLES = "option.enderlift.redstonetype2";
    private static final String REDSTONE_DISABLES = "option.enderlift.redstonetype3";

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
		return screen -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle("title.enderlift.config");

            EnderliftConfig temp = new EnderliftConfig();

            ConfigCategory general = builder.getOrCreateCategory("category.enderlift.general");
            general.addEntry(builder.entryBuilder().startIntField("option.enderlift.range", Enderlift.config.range)
                .setDefaultValue(256)
                .setTooltip("Range of the Enderlift")
                .setSaveConsumer(newValue -> temp.range = newValue)
                .build()
            );
            general.addEntry(builder.entryBuilder().startIntField("option.enderlift.xpCost", Enderlift.config.xpCost)
                .setDefaultValue(0)
                .setTooltip("Cost of using the Enderlift (0 to disable)")
                .setSaveConsumer(newValue -> temp.xpCost = newValue)
                .build()
            );
            general.addEntry(builder.entryBuilder().startIntField("option.enderlift.damage", Enderlift.config.damage)
                .setDefaultValue(0)
                .setTooltip("Damage whenever using the Enderlift (0 to disable)")
                .setSaveConsumer(newValue -> temp.damage = newValue)
                .build()
            );
            general.addEntry(builder.entryBuilder().startIntField("option.enderlift.redstoneType", Enderlift.config.redstoneType)
                .setDefaultValue(0)
                .setTooltip("0=ignore redstone, 1=redstone signal enables lift, 2=redstone signal disables lift")
                .setSaveConsumer(newValue -> temp.redstoneType = newValue)
                .build()
            );
            general.addEntry(builder.entryBuilder().startBooleanToggle("option.enderlift.woolMatch", Enderlift.config.woolMatch)
                    .setDefaultValue(true)
                    .setTooltip("True to have Enderlifts require matching wool to teleport")
                    .setSaveConsumer(newValue -> temp.woolMatch = newValue)
                    .build()
            );
            builder.setSavingRunnable(() -> {
                Enderlift.config = temp;
                EnderliftConfig.save(temp);
            });

            return builder.build();
        };
    }
    
    @Override
    public String getModId() {
        return "enderlift";
    }
}