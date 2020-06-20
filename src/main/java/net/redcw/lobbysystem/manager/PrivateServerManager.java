package net.redcw.lobbysystem.manager;

/*
Class created by SpigotSource on 02.02.2020 at 13:30
*/

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.server.ServerConfig;
import de.dytanic.cloudnet.lib.utility.document.Document;

import java.util.UUID;

public class PrivateServerManager {

    public void startServer(final String group, final UUID owner)
    {
        CloudAPI.getInstance().startGameServer(CloudAPI.getInstance().getServerGroupData(group), new ServerConfig(true, "null", new Document().append("uuid", owner.toString()).append("privateServer", true), System.currentTimeMillis()));
    }



}
