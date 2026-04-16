package net.germanminer.assistant.listener;

import net.germanminer.assistant.GermanminerAssistant;
import net.germanminer.assistant.blitzer.BlitzerEntry;
import net.germanminer.assistant.blitzer.BlitzerManager;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

import java.util.List;

public class BlitzerListener {

    private static final int CHECK_INTERVAL_TICKS = 20;

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
            }
        }

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
}
