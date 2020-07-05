package net.redcw.lobbysystem.akinator.core.entities;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * An interface used to represent an identifiable object (if the object has an
 * appended ID set by Akinator's servers).
 *
 * @author Marko Zajc
 */
public interface Identifiable {

	/**
	 * @return ID of that object. Each object has an unique ID
	 */
	@Nonnull
	String getId();

	/**
	 * @return ID as a long
	 *
	 * @see #getId()
	 */
	@Nonnegative
	default long getIdLong() {
		return Long.parseLong(getId());
	}

}
