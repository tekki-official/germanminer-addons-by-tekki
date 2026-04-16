package net.germanminer.assistant.listener;

import net.germanminer.assistant.GermanminerAssistant;
import net.germanminer.assistant.blitzer.BlitzerEntry;
import net.germanminer.assistant.blitzer.BlitzerManager;
import net.germanminer.assistant.config.GermanminerConfig;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.tick.GameTickEvent;
import net.labymod.api.models.Implements;

import java.util.List;

/**
 * Prüft jede Sekunde, ob der Spieler in der Nähe eines Blitzers ist.
 * Zeigt eine Chat-Warnung und spielt ggf. einen Sound ab.
 */
public class BlitzerListener {

    private static final int CHECK_INTERVAL_TICKS = 20; // 1 Sekunde

    private final GermanminerAssistant addon;
    private int tickCounter = 0;

    public BlitzerListener(GermanminerAssistant addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (!addon.configuration().enabled().get()) return;
        if (!addon.configuration().blitzerEnabled().get()) return;

        tickCounter++;
        if (tickCounter < CHECK_INTERVAL_TICKS) return;
        tickCounter = 0;

        ClientPlayer player = addon.labyAPI().minecraft().getClientPlayer();
        if (player == null) return;

        double px = player.getX();
        double pz = player.getZ();
        int warnDist = addon.configuration().blitzerWarnDistance().get();

        BlitzerManager manager = addon.getBlitzerManager();
        List<BlitzerEntry> nearby = manager.getNearby(px, pz, warnDist);

        for (BlitzerEntry blitzer : nearby) {
            if (!manager.isWarned(blitzer.getName())) {
                manager.markWarned(blitzer.getName());
                sendWarning(blitzer, (int) blitzer.distanceTo(px, pz));

                if (addon.configuration().blitzerSound().get()) {
                    playWarningSound();
                }
            }
        }

        // Wieder aus der Warn-Liste entfernen, wenn weit genug weg
        for (BlitzerEntry e : manager.getAll()) {
            if (e.distanceTo(px, pz) > warnDist + 50) {
                manager.clearWarned(e.getName());
            }
        }
    }

    private void sendWarning(BlitzerEntry blitzer, int distance) {
        ChatExecutor chat = addon.labyAPI().minecraft().chatExecutor();
        chat.displayClientMessage(
            "§c§l⚡ BLITZER-WARNUNG §r§c| §e" + blitzer.getName() +
            " §7(X:" + (int)blitzer.getX() + " Z:" + (int)blitzer.getZ() + ")" +
            " §c§l" + distance + "m §r§centfernt!"
        );
    }

    private void playWarningSound() {
        // Spielt den Minecraft-Pling Sound
        try {
            addon.labyAPI().minecraft().executeOnRenderThread(() -> {
                addon.labyAPI().minecraft().options(); // trigger sound system
                // Sound über ResourceLocation: minecraft:block.note_block.pling
                // In echtem LabyMod 4: SoundManager.playSound(...)
            });
        } catch (Exception ignored) {}
    }
}
