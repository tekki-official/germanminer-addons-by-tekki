package net.germanminer.assistant.module;

import net.germanminer.assistant.GermanminerAssistant;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyboardKeyEvent;

public class KeybindModule {

    private final GermanminerAssistant addon;

    public KeybindModule(GermanminerAssistant addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onKeyInput(KeyboardKeyEvent event) {
        if (!addon.configuration().enabled().get()) return;
        if (!addon.configuration().keybindsEnabled().get()) return;
        if (!event.isDown()) return;
        if (addon.labyAPI().minecraft().getClientPlayer() == null) return;

        // Hotkeys hier eintragen wenn gewünscht
        // Beispiel: if (event.key() == Key.F6) { ... }
    }
}
