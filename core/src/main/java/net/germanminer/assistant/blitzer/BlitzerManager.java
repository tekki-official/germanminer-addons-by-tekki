package net.germanminer.assistant.blitzer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.germanminer.assistant.GermanminerAssistant;
import net.labymod.api.LabyAPI;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Verwaltet alle gespeicherten Blitzer-Einträge.
 * Lädt/speichert diese als JSON-Datei.
 */
public class BlitzerManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<BlitzerEntry>>() {}.getType();

    private final GermanminerAssistant addon;
    private final Path saveFile;
    private final List<BlitzerEntry> blitzerList = new ArrayList<>();

    // Tracking für Warn-Cooldown: vermeidet Spam
    private final List<String> warnedBlitzer = new ArrayList<>();

    public BlitzerManager(GermanminerAssistant addon) {
        this.addon = addon;
        this.saveFile = addon.labyAPI().labyModDirectory()
                .resolve("addons/germanminer-assistant/blitzer.json");
        load();
    }

    // ---- CRUD ----

    public void addBlitzer(BlitzerEntry entry) {
        blitzerList.add(entry);
        save();
    }

    public boolean removeBlitzer(String name) {
        boolean removed = blitzerList.removeIf(e -> e.getName().equalsIgnoreCase(name));
        if (removed) save();
        return removed;
    }

    public List<BlitzerEntry> getAll() {
        return Collections.unmodifiableList(blitzerList);
    }

    public BlitzerEntry findNearest(double px, double pz) {
        BlitzerEntry nearest = null;
        double minDist = Double.MAX_VALUE;
        for (BlitzerEntry e : blitzerList) {
            double d = e.distanceTo(px, pz);
            if (d < minDist) {
                minDist = d;
                nearest = e;
            }
        }
        return nearest;
    }

    /**
     * Gibt alle Blitzer zurück, die innerhalb der Warndistanz sind.
     */
    public List<BlitzerEntry> getNearby(double px, double pz, int warnDistance) {
        List<BlitzerEntry> nearby = new ArrayList<>();
        for (BlitzerEntry e : blitzerList) {
            if (e.distanceTo(px, pz) <= warnDistance) {
                nearby.add(e);
            }
        }
        return nearby;
    }

    public boolean isWarned(String name) {
        return warnedBlitzer.contains(name);
    }

    public void markWarned(String name) {
        if (!warnedBlitzer.contains(name)) {
            warnedBlitzer.add(name);
        }
    }

    public void clearWarned(String name) {
        warnedBlitzer.remove(name);
    }

    // ---- Persistence ----

    private void load() {
        try {
            if (!Files.exists(saveFile)) {
                Files.createDirectories(saveFile.getParent());
                return;
            }
            try (Reader reader = Files.newBufferedReader(saveFile)) {
                List<BlitzerEntry> loaded = GSON.fromJson(reader, LIST_TYPE);
                if (loaded != null) blitzerList.addAll(loaded);
            }
            addon.logger().info("[BlitzerManager] " + blitzerList.size() + " Blitzer geladen.");
        } catch (IOException e) {
            addon.logger().error("[BlitzerManager] Fehler beim Laden: " + e.getMessage());
        }
    }

    private void save() {
        try {
            Files.createDirectories(saveFile.getParent());
            try (Writer writer = Files.newBufferedWriter(saveFile)) {
                GSON.toJson(blitzerList, writer);
            }
        } catch (IOException e) {
            addon.logger().error("[BlitzerManager] Fehler beim Speichern: " + e.getMessage());
        }
    }
}
