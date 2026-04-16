package net.germanminer.assistant.config;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.binding.annotation.AutomaticFilling;

@ConfigName("settings")
public class GermanminerConfig extends AddonConfig {

    // --- Addon General ---
    @AutomaticFilling
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    // --- Commands ---
    @AutomaticFilling
    private final ConfigProperty<Boolean> commandsEnabled = new ConfigProperty<>(true);

    // --- Keybinds ---
    @AutomaticFilling
    private final ConfigProperty<Boolean> keybindsEnabled = new ConfigProperty<>(true);

    // --- Blitzer ---
    @AutomaticFilling
    private final ConfigProperty<Boolean> blitzerEnabled = new ConfigProperty<>(true);

    @AutomaticFilling
    private final ConfigProperty<Boolean> blitzerSound = new ConfigProperty<>(true);

    @AutomaticFilling
    private final ConfigProperty<Integer> blitzerWarnDistance = new ConfigProperty<>(150);

    // --- HUD ---
    @AutomaticFilling
    private final ConfigProperty<Boolean> hudEnabled = new ConfigProperty<>(true);

    @AutomaticFilling
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
