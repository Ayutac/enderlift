package com.kqp.enderlift.modmenu;

import java.util.function.Function;

import com.kqp.enderlift.Enderlift;
import com.kqp.enderlift.EnderliftConfig;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;

public class EnderliftModMenu implements ModMenuApi {
    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
		return screen -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle("title.enderlift.config");

            EnderliftConfig temp = new EnderliftConfig();

            ConfigCategory general = builder.getOrCreateCategory("category.enderlift.general");
            general.addEntry(builder.entryBuilder().startIntField("optional.enderlift.range", 256)
                .setDefaultValue(256)
                .setTooltip("Range of the Enderlift")
                .setSaveConsumer(newValue -> temp.range = newValue)
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