package net.redcw.lobbysystem.internal.commands;

/*
Class created by SpigotSource on 05.01.2020 at 00:06
*/

import de.dytanic.cloudnet.lib.NetworkUtils;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.items.ItemNavigator;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.BukkitPositionAdapter;
import net.redcw.redapi.positions.Position;
import net.redcw.redapi.positions.PositionMeta;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!((Player) commandSender).hasPermission("net.redcw.command.lobby.setup")) {
            ((Player) commandSender).sendMessage(RedAPI.PREFIX + "§cDazu hast du keine Rechte!");
            return true;
        }

        switch (args.length)
        {
            case 4:
                if (args[0].equalsIgnoreCase("setSpawn") && NetworkUtils.checkIsNumber(args[2]) && NetworkUtils.checkIsNumber(args[3])) {
                    Position position = BukkitPositionAdapter.toPosition(((Player) commandSender).getLocation(), new PositionMeta("itemId", args[2]),
                            new PositionMeta("slot", (Integer.parseInt(args[3]) - 1) + ""));
                    LobbyService.getInstance().getSpawnLocations().createPosition(args[1], position);
                    commandSender.sendMessage(LobbyService.PREFIX + "§7Der Spawn §c\"" + args[1] + "\" §7wurde §agesetzt§8!");

                    if(ItemNavigator.getInstance() != null)
                    {
                        ItemNavigator.getInstance().reloadInventory();
                    }

                    return false;
                } else {
                    commandSender.sendMessage(LobbyService.PREFIX + "Invalid Arguments");
                }
                break;
            case 3:
                if(args[0].equalsIgnoreCase("spawnNpc")) {
                    /*Bukkit.getOnlinePlayers().forEach(players -> {
                        LobbyService.getInstance().getNpcList().spawnNPC(args[1], players, ((Player) commandSender).getLocation(), Integer.parseInt(args[2]));
                    });*/
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("removeSpawn")) {
                    if (LobbyService.getInstance().getSpawnLocations().getPositions().containsKey(args[1]))
                        LobbyService.getInstance().getSpawnLocations().deletePosition(args[1]);

                    commandSender.sendMessage(LobbyService.PREFIX + "§7Die Position wurde §cgelöscht§8!");

                    if(ItemNavigator.getInstance() != null)
                    {
                        ItemNavigator.getInstance().reloadInventory();
                    }

                    return true;
                } else if(args[0].equalsIgnoreCase("teleport")) {
                    World world = Bukkit.getWorld(args[1]);

                    if(world == null) {
                        final WorldCreator worldCreator = WorldCreator.name(args[1]);
                        Bukkit.createWorld(worldCreator);
                        world = Bukkit.getWorld(args[1]);
                    }

                    ((Player) commandSender).teleport(world.getSpawnLocation());
                }
                break;
                default: sendHelp((Player) commandSender);
        }
        return false;
    }

    private void sendHelp(final Player player)
    {
        player.sendMessage(LobbyService.PREFIX + "/setup setSpawn <name> <itemId> <slot> [Default: \"Spawn\"]");
        player.sendMessage(LobbyService.PREFIX + "/setup removeSpawn <name>");

    }

}
