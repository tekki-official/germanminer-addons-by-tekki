package net.germanminer.assistant.hud;

import net.germanminer.assistant.GermanminerAssistant;
import net.germanminer.assistant.blitzer.BlitzerEntry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.entity.player.ClientPlayer;

import java.util.List;

/**
 * Zeigt näheste Blitzer-Information als HUD-Element an.
 */
public class BlitzerHud extends TextHudWidget<TextHudWidgetConfig> {

    private static final HudWidgetCategory CATEGORY = new HudWidgetCategory(
        "germanminer-assistant", "Germanminer Assistant"
    );

    private final GermanminerAssistant addon;

    public BlitzerHud(GermanminerAssistant addon) {
        super("blitzer_hud", TextHudWidgetConfig.class);
        this.addon = addon;
    }

    @Override
    public void onTick() {
        if (!addon.configuration().enabled().get()) return;
        if (!addon.configuration().blitzerEnabled().get()) return;

        ClientPlayer player = addon.labyAPI().minecraft().getClientPlayer();
        if (player == null) {
            this.getTextLines().clear();
            return;
        }

        double px = player.getX();
        double pz = player.getZ();
        int warnDist = addon.configuration().blitzerWarnDistance().get();

        List<BlitzerEntry> nearby = addon.getBlitzerManager().getNearby(px, pz, warnDist);

        this.getTextLines().clear();

        if (!nearby.isEmpty()) {
            this.getTextLines().add("§c§l⚡ BLITZER IN DER NÄHE!");
            for (BlitzerEntry e : nearby) {
                int dist = (int) e.distanceTo(px, pz);
                this.getTextLines().add(
                    "§e" + e.getName() + " §c" + dist + "m"
                );
            }
        }
    }

    @Override
    public HudWidgetCategory getCategory() {
        return CATEGORY;
    }
}
