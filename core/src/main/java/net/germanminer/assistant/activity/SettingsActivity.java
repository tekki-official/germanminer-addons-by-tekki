package net.germanminer.assistant.activity;

import net.germanminer.assistant.GermanminerAssistant;
import net.germanminer.assistant.blitzer.BlitzerEntry;
import net.germanminer.assistant.blitzer.BlitzerManager;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget.SettingActivity;

/**
 * Öffnet sich über LabyMod > Addons > Germanminer Assistant > Einstellungen.
 * Die Hauptkonfiguration wird über GermanminerConfig gesteuert.
 *
 * Für ein vollständiges eigenes Einstellungs-Screen:
 * Nutze LabyMod Activity API (siehe LabyMod 4 Doku).
 */
@AutoActivity
@Link("settings.lss")
@SettingActivity
public class SettingsActivity extends Activity {

    private final GermanminerAssistant addon;

    public SettingsActivity(GermanminerAssistant addon) {
        this.addon = addon;
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);
        // Widgets werden über die .lss (LabyMod Style Sheet) Datei geladen
    }
}
