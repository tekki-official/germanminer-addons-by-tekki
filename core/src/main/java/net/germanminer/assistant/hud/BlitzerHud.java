package net.germanminer.assistant.hud;

import net.germanminer.assistant.GermanminerAssistant;
import net.germanminer.assistant.blitzer.BlitzerEntry;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;

import java.util.List;

public class BlitzerHud extends TextHudWidget<TextHudWidgetConfig> {

    private final GermanminerAssistant addon;
    private TextLine statusLine;

    public BlitzerHud(GermanminerAssistant addon) {
        super("blitzer_hud");
        this.addon = addon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.statusLine = this.createLine("Kein Blitzer in der Nähe");
    }

    @Override
    public void onTick() {
        if (!addon.configuration().enabled().get() || !addon.configuration().blitzerEnabled().get()) {
            this.statusLine.updateAndGet(s -> "");
            return;
        }

        ClientPlayer player = addon.labyAPI().minecraft().getClientPlayer();
        if (player == null) {
            this.statusLine.updateAndGet(s -> "");
            return;
        }

        double px = player.getX();
        double pz = player.getZ();
        int warnDist = addon.configuration().blitzerWarnDistance().get();

        List<BlitzerEntry> nearby = addon.getBlitzerManager().getNearby(px, pz, warnDist);

        if (nearby.isEmpty()) {
            this.statusLine.updateAndGet(s -> "");
        } else {
            BlitzerEntry closest = nearby.get(0);
            int dist = (int) closest.distanceTo(px, pz);
            this.statusLine.updateAndGet(s -> "§c⚡ " + closest.getName() + " §7(" + dist + "m)");
        }
    }
}
