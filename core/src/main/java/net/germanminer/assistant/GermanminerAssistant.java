package net.germanminer.assistant;

import com.google.inject.Inject;
import net.germanminer.assistant.blitzer.BlitzerManager;
import net.germanminer.assistant.config.GermanminerConfig;
import net.germanminer.assistant.listener.BlitzerListener;
import net.germanminer.assistant.module.CommandModule;
import net.germanminer.assistant.module.KeybindModule;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget.ButtonSetting;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GermanminerAssistant extends LabyAddon<GermanminerConfig> {

    private static GermanminerAssistant instance;
    private BlitzerManager blitzerManager;

    @Override
    protected void enable() {
        instance = this;
        this.blitzerManager = new BlitzerManager(this);

        // Register listeners
        this.registerListener(new BlitzerListener(this));

        // Register modules
        this.labyAPI().eventBus().registerListener(new CommandModule(this));
        this.labyAPI().eventBus().registerListener(new KeybindModule(this));

        this.logger().info("[GermanminerAssistant] Addon gestartet!");
    }

    @Override
    protected Class<GermanminerConfig> configurationClass() {
        return GermanminerConfig.class;
    }

    public BlitzerManager getBlitzerManager() {
        return blitzerManager;
    }

    public static GermanminerAssistant getInstance() {
        return instance;
    }
}
