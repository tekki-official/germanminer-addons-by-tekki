package net.germanminer.assistant.module;

import net.germanminer.assistant.GermanminerAssistant;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.keyboard.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyInputEvent;

/**
 * Verarbeitet alle Hotkey-Eingaben des Addons.
 *
 * Alle Hotkeys sind im Code eingetragen, aber standardmäßig UNBELEGT (Key.UNKNOWN).
 * Die eigentliche Taste kann der Nutzer in den LabyMod-Einstellungen unter
 *   Addons → Germanminer Assistant → Keybinds
 * selbst festlegen.
 *
 * Verfügbare Hotkeys (Standardtaste → Aktion):
 *   KEY_MOTOR       → /vehicles motor
 *   KEY_SIRENE      → /vehicles sirene
 *   KEY_FIRSTAID    → /acceptfirstaid
 *   KEY_BACKPACK    → /backpack
 *   KEY_MENU        → /menu
 */
public class KeybindModule {

    // -------------------------------------------------------------------------
    // Hotkey-Definitionen – trage hier den gewünschten Standard-Key ein,
    // z. B. Key.N, Key.F6, Key.H  – oder lass ihn auf Key.UNKNOWN (= unbelegt).
    // -------------------------------------------------------------------------

    /** Hotkey: Motor an/aus  →  /vehicles motor */
    private static final Key KEY_MOTOR    = Key.UNKNOWN;

    /** Hotkey: Sirene an/aus  →  /vehicles sirene */
    private static final Key KEY_SIRENE   = Key.UNKNOWN;

    /** Hotkey: First-Aid annehmen  →  /acceptfirstaid */
    private static final Key KEY_FIRSTAID = Key.UNKNOWN;

    /** Hotkey: Rucksack öffnen  →  /backpack */
    private static final Key KEY_BACKPACK = Key.UNKNOWN;

    /** Hotkey: Hauptmenü öffnen  →  /menu */
    private static final Key KEY_MENU     = Key.UNKNOWN;

    // -------------------------------------------------------------------------

    private final GermanminerAssistant addon;

    public KeybindModule(GermanminerAssistant addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onKeyInput(KeyInputEvent event) {
        // Addon oder Keybinds deaktiviert → nichts tun
        if (!addon.configuration().enabled().get()) return;
        if (!addon.configuration().keybindsEnabled().get()) return;

        // Nur bei "Taste gedrückt" reagieren, nicht beim Loslassen
        if (!event.isDown()) return;

        // Kein Spieler im Spiel? Abbrechen.
        if (addon.labyAPI().minecraft().getClientPlayer() == null) return;

        Key pressed = event.key();
        ChatExecutor chat = addon.labyAPI().minecraft().chatExecutor();

        if (KEY_MOTOR    != Key.UNKNOWN && pressed == KEY_MOTOR) {
            chat.chat("/vehicles motor");
            return;
        }

        if (KEY_SIRENE   != Key.UNKNOWN && pressed == KEY_SIRENE) {
            chat.chat("/vehicles sirene");
            return;
        }

        if (KEY_FIRSTAID != Key.UNKNOWN && pressed == KEY_FIRSTAID) {
            chat.chat("/acceptfirstaid");
            return;
        }

        if (KEY_BACKPACK != Key.UNKNOWN && pressed == KEY_BACKPACK) {
            chat.chat("/backpack");
            return;
        }

        if (KEY_MENU     != Key.UNKNOWN && pressed == KEY_MENU) {
            chat.chat("/menu");
        }
    }
}
