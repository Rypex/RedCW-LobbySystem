package net.redcw.lobbysystem.akinator.core.exceptions;

import net.redcw.lobbysystem.akinator.core.entities.ServerGroup;

/**
 * An exception that signals that all servers from a {@link ServerGroup} are
 * unavailable.
 */
public class ServerGroupUnavailableException extends ServerUnavailableException {

	/**
	 * Creates a new {@link ServerGroupUnavailableException}.
	 *
	 * @param sg
	 *            the unavailable {@link ServerGroup}.
	 */
	public ServerGroupUnavailableException(ServerGroup sg) {
		super(sg.getServers());
	}

}
