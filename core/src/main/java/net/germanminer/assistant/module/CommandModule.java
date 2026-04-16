package net.germanminer.assistant.module;

import net.germanminer.assistant.GermanminerAssistant;
import net.germanminer.assistant.blitzer.BlitzerEntry;
import net.germanminer.assistant.blitzer.BlitzerManager;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

import java.util.List;

/**
 * Verarbeitet alle Chat-Befehle des Addons.
 *
 * Nutzung:
 *   .motor          -> /vehicles motor
 *   .sirene         -> /vehicles sirene
 *   .firstaid       -> /acceptfirstaid
 *   .bp             -> /backpack
 *   .menu           -> /menu
 *   .blitzer add <name> <x> <y> <z>    -> Blitzer hinzufügen
 *   .blitzer remove <name>             -> Blitzer entfernen
 *   .blitzer list                      -> Alle Blitzer auflisten
 *   .gma help                          -> Alle Befehle anzeigen
 */
public class CommandModule {

    private final GermanminerAssistant addon;

    public CommandModule(GermanminerAssistant addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onChatSend(ChatMessageSendEvent event) {
        if (!addon.configuration().enabled().get()) return;
        if (!addon.configuration().commandsEnabled().get()) return;

        String message = event.getMessage().trim();
        if (!message.startsWith(".")) return;

        ChatExecutor chat = addon.labyAPI().minecraft().chatExecutor();
        String lower = message.toLowerCase();

        // --- Fahrzeug Commands ---
        if (lower.equals(".motor")) {
            event.setCancelled(true);
            chat.chat("/vehicles motor");
            return;
        }

        if (lower.equals(".sirene")) {
            event.setCancelled(true);
            chat.chat("/vehicles sirene");
            return;
        }

        // --- Spieler Commands ---
        if (lower.equals(".firstaid") || lower.equals(".fa")) {
            event.setCancelled(true);
            chat.chat("/acceptfirstaid");
            return;
        }

        if (lower.equals(".bp") || lower.equals(".backpack")) {
            event.setCancelled(true);
            chat.chat("/backpack");
            return;
        }

        if (lower.equals(".menu")) {
            event.setCancelled(true);
            chat.chat("/menu");
            return;
        }

        // --- Blitzer Commands ---
        if (lower.startsWith(".blitzer")) {
            event.setCancelled(true);
            handleBlitzerCommand(message.substring(8).trim(), chat);
            return;
        }

        // --- Help ---
        if (lower.equals(".gma") || lower.equals(".gma help")) {
            event.setCancelled(true);
            sendHelp(chat);
        }
    }

    private void handleBlitzerCommand(String args, ChatExecutor chat) {
        BlitzerManager manager = addon.getBlitzerManager();
        String[] parts = args.split(" ");

        if (parts.length == 0 || parts[0].isEmpty()) {
            sendBlitzerHelp(chat);
            return;
        }

        switch (parts[0].toLowerCase()) {
            case "add": {
                if (parts.length < 2) {
                    chat.displayClientMessage("§cNutzung: §e.blitzer add <name> [x] [y] [z]");
                    return;
                }
                String name = parts[1];
                double x, y, z;

                if (parts.length >= 5) {
                    // Koordinaten manuell angegeben
                    try {
                        x = Double.parseDouble(parts[2]);
                        y = Double.parseDouble(parts[3]);
                        z = Double.parseDouble(parts[4]);
                    } catch (NumberFormatException e) {
                        chat.displayClientMessage("§cUngültige Koordinaten!");
                        return;
                    }
                } else {
                    // Aktuelle Spieler-Position verwenden
                    ClientPlayer player = addon.labyAPI().minecraft().getClientPlayer();
                    if (player == null) {
                        chat.displayClientMessage("§cSpieler nicht gefunden!");
                        return;
                    }
                    x = Math.floor(player.getX());
                    y = Math.floor(player.getY());
                    z = Math.floor(player.getZ());
                }

                manager.addBlitzer(new BlitzerEntry(name, x, y, z, "world"));
                chat.displayClientMessage(
                    "§a§l✔ §aBlitzer §e\"" + name + "\" §ahinzugefügt! " +
                    "§7(X:" + (int)x + " Y:" + (int)y + " Z:" + (int)z + ")"
                );
                break;
            }

            case "remove":
            case "del":
            case "rm": {
                if (parts.length < 2) {
                    chat.displayClientMessage("§cNutzung: §e.blitzer remove <name>");
                    return;
                }
                String name = parts[1];
                boolean removed = manager.removeBlitzer(name);
                if (removed) {
                    chat.displayClientMessage("§a§l✔ §aBlitzer §e\"" + name + "\" §aentfernt!");
                } else {
                    chat.displayClientMessage("§c§l✘ §cKein Blitzer mit dem Namen §e\"" + name + "\" §cgefunden.");
                }
                break;
            }

            case "list": {
                List<BlitzerEntry> all = manager.getAll();
                if (all.isEmpty()) {
                    chat.displayClientMessage("§eKeine Blitzer gespeichert.");
                    return;
                }
                chat.displayClientMessage("§6§l=== Gespeicherte Blitzer (" + all.size() + ") ===");
                for (BlitzerEntry e : all) {
                    chat.displayClientMessage(
                        "§c⚡ §e" + e.getName() + " §7- X:" + (int)e.getX() +
                        " Y:" + (int)e.getY() + " Z:" + (int)e.getZ()
                    );
                }
                break;
            }

            default:
                sendBlitzerHelp(chat);
        }
    }

    private void sendBlitzerHelp(ChatExecutor chat) {
        chat.displayClientMessage("§6§l=== Blitzer-Befehle ===");
        chat.displayClientMessage("§e.blitzer add <name> §7- Blitzer an aktueller Position");
        chat.displayClientMessage("§e.blitzer add <name> <x> <y> <z> §7- Blitzer mit Koordinaten");
        chat.displayClientMessage("§e.blitzer remove <name> §7- Blitzer entfernen");
        chat.displayClientMessage("§e.blitzer list §7- Alle Blitzer auflisten");
    }

    private void sendHelp(ChatExecutor chat) {
        chat.displayClientMessage("§6§l╔══ GermanminerAssistant Hilfe ══╗");
        chat.displayClientMessage("§6§l║ §eChat-Commands (Shortcuts):");
        chat.displayClientMessage("§6§l║ §e.motor §7- /vehicles motor");
        chat.displayClientMessage("§6§l║ §e.sirene §7- /vehicles sirene");
        chat.displayClientMessage("§6§l║ §e.firstaid §7(.fa) - /acceptfirstaid");
        chat.displayClientMessage("§6§l║ §e.bp §7(.backpack) - /backpack");
        chat.displayClientMessage("§6§l║ §e.menu §7- /menu");
        chat.displayClientMessage("§6§l║");
        chat.displayClientMessage("§6§l║ §eHotkeys (Tastenkürzel):");
        chat.displayClientMessage("§6§l║ §7Tasten in §eKeybindModule.java §7eintragen");
        chat.displayClientMessage("§6§l║ §7(KEY_MOTOR, KEY_SIRENE, KEY_FIRSTAID, ...)");
        chat.displayClientMessage("§6§l║");
        chat.displayClientMessage("§6§l║ §eBlitzer-Warner:");
        chat.displayClientMessage("§6§l║ §e.blitzer add <n> §7- Blitzer speichern");
        chat.displayClientMessage("§6§l║ §e.blitzer remove <n> §7- Blitzer löschen");
        chat.displayClientMessage("§6§l║ §e.blitzer list §7- Alle Blitzer");
        chat.displayClientMessage("§6§l╚════════════════════════════╝");
    }
}
