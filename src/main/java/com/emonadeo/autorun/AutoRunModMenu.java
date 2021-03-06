package com.emonadeo.autorun;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;

import java.util.function.Function;

public class AutoRunModMenu implements ModMenuApi {
    @Override
    public String getModId() {
        return AutoRun.MODID;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return this::create;
    }

    public Screen create(Screen screen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle("title." + AutoRun.MODID + ".config");

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory("config." + AutoRun.MODID + ".general");
        general.addEntry(entryBuilder.startIntField("config." + AutoRun.MODID + ".delayBuffer", AutoRun.getDelayBuffer())
            .setDefaultValue(20)
            .setTooltip("Activation buffer before Auto-Run can be cancelled again by WASD movement")
            .setSaveConsumer(AutoRun::setDelayBuffer)
            .build());

        return builder.setSavingRunnable(() -> {
            AutoRun.saveConfig(AutoRun.CFG_FILE);
            AutoRun.loadConfig(AutoRun.CFG_FILE);
        }).build();
    }
}
