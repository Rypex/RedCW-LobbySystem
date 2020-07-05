package net.redcw.lobbysystem.akinator.core.exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

import net.redcw.lobbysystem.akinator.core.entities.Server;
import net.redcw.lobbysystem.akinator.core.entities.impl.immutable.StatusImpl;

/**
 * An exception representing that the currently used {@link Server} has gone offline.
 */
public class ServerUnavailableException extends StatusException {

	private final String serverUrl;

	/**
	 * Creates a new {@link ServerUnavailableException} instance for a single server.
	 *
	 * @param server
	 */
	public ServerUnavailableException(Server server) {
		super(new StatusImpl("KO - SERVER DOWN"));
		this.serverUrl = server.getApiUrl();
	}

	/**
	 * Creates a new {@link ServerUnavailableException} instance for multiple servers.
	 *
	 * @param servers
	 */
	public ServerUnavailableException(Collection<Server> servers) {
		super(new StatusImpl("KO - SERVER DOWN"));
		this.serverUrl = servers.stream().map(Server::getApiUrl).collect(Collectors.joining(", "));
	}

	/**
	 * Returns the URL of the API server that went down
	 *
	 * @return API server's URL
	 */
	public String getServerUrl() {
		return this.serverUrl;
	}

}
