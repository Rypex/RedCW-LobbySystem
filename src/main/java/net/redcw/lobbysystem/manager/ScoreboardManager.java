package net.redcw.lobbysystem.manager;

/*
Class created by SpigotSource on 05.01.2020 at 02:15
*/

import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.player.IPlayerScoreboard;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.game.Scoreboard;
import net.redcw.redapi.language.LanguageDefinition;
import org.bukkit.entity.Player;

public class ScoreboardManager implements IPlayerScoreboard {

    private String[] animation = new String[] {
            " ", " ",
            "§8»", "§8»",
            "§8» ", "§8» ",
            "§8» §4R", "§8» §4R",
            "§8» §4Re", "§8» §4Re",
            "§8» §4Red", "§8» §4Red",
            "§8» §4Red§cC", "§8» §4Red§cC",
            "§8» §4Red§cCW", "§8» §4Red§cCW",
            "§8» §4Red§cCW ", "§8» §4Red§cCW ",
            "§8» §4Red§cCW §8▰", "§8» §4Red§cCW §8▰",
            "§8» §4Red§cCW §8▰§7▰", "§8» §4Red§cCW §8▰§7▰",
            "§8» §4Red§cCW §8▰§7▰ ", "§8» §4Red§cCW §8▰§7▰ ",
            "§8» §4Red§cCW §8▰§7▰ §7L", "§8» §4Red§cCW §8▰§7▰ §7L",
            "§8» §4Red§cCW §8▰§7▰ §7Lo", "§8» §4Red§cCW §8▰§7▰ §7Lo",
            "§8» §4Red§cCW §8▰§7▰ §7Lob", "§8» §4Red§cCW §8▰§7▰ §7Lob",
            "§8» §4Red§cCW §8▰§7▰ §7Lobb", "§8» §4Red§cCW §8▰§7▰ §7Lobb",
            "§8» §4Red§cCW §8▰§7▰ §7Lobby", "§8» §4Red§cCW §8▰§7▰ §7Lobby"
    };

    @Override
    public void set(Player player) {
        final Scoreboard scoreboard = new Scoreboard(player, "§8» §4Red§cCW §8▰§7▰ §7Lobby");

        final PermissionGroup permissionGroup = LobbyService.getInstance().getPlayerGroups().get(player.getUniqueId());

        final boolean german = LobbyService.getInstance().getLanguage(player.getUniqueId()).equals(LanguageDefinition.GERMAN);

        scoreboard.addAnimation(animation, LobbyService.getInstance().getPlugin());

        scoreboard.setLine(14, "§8§m-------", "§8§m-------§r");
        scoreboard.setLine(13, "§1", "§2");
        scoreboard.setLine(12, "  §8» §7" + (german ? "Rang" : "Rank"), "§3");
        scoreboard.setLine(11, "    §8➥ §7", permissionGroup.getColor().replace("&", "§") + permissionGroup.getName());
        scoreboard.setLine(10, "§3", "§4");
        scoreboard.setLine(9, "  §8» §7Coins", "§4");
        scoreboard.setLine(8, "    §8➥ §7", "§c" + RedAPI.getInstance().getTextService().formatInteger(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).$coins()));
        scoreboard.setLine(7, "§5", "§6");
        scoreboard.setLine(6, "  §8» §7RedPass", "§5");
        scoreboard.setLine(5, "    §8➥ §7", "§c§o" + (german ? "Keinen" : "none"));
        scoreboard.setLine(4, "§7", "§8");
        scoreboard.setLine(3, "  §8» §7Twitter", "§6");
        scoreboard.setLine(2, "    §8➥ §7§4@§c", "§cRedCWNetwork");
        scoreboard.setLine(1, "§9", "§0");
        scoreboard.setLine(0, "§8§m-------", "§8§m-------");

        scoreboard.setBoard(player);

        LobbyService.getInstance().getPlayerScoreboard().put(player.getUniqueId(), scoreboard);
    }

    @Override
    public void update(Player player) {

    }
}
