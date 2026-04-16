package net.germanminer.assistant.config;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class GermanminerConfig extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> commandsEnabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> keybindsEnabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> blitzerEnabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> blitzerSound = new ConfigProperty<>(true);

    @SliderSetting(min = 50, max = 500)
    private final ConfigProperty<Integer> blitzerWarnDistance = new ConfigProperty<>(150);

    @SwitchSetting
    private final ConfigProperty<Boolean> hudEnabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> notificationsEnabled = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    public ConfigProperty<Boolean> commandsEnabled() { return commandsEnabled; }
    public ConfigProperty<Boolean> keybindsEnabled() { return keybindsEnabled; }
    public ConfigProperty<Boolean> blitzerEnabled() { return blitzerEnabled; }
    public ConfigProperty<Boolean> blitzerSound() { return blitzerSound; }
    public ConfigProperty<Integer> blitzerWarnDistance() { return blitzerWarnDistance; }
    public ConfigProperty<Boolean> hudEnabled() { return hudEnabled; }
    public ConfigProperty<Boolean> notificationsEnabled() { return notificationsEnabled; }
}
