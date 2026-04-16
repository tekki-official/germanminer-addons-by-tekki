package net.germanminer.assistant.activity;

import net.germanminer.assistant.GermanminerAssistant;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;

@AutoActivity
@Link("settings.lss")
public class SettingsActivity extends Activity {

    private final GermanminerAssistant addon;

    public SettingsActivity(GermanminerAssistant addon) {
        this.addon = addon;
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);
    }
}
