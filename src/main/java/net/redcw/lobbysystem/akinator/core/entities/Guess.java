package net.redcw.lobbysystem.akinator.core.entities;

import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A class used to represent Akinator's guess from the data acquired from your
 * answers.
 *
 * @author Marko Zajc
 */
public interface Guess extends Identifiable {

	/**
	 * @return guessed characer's name
	 */
	@Nonnull
	public String getName();

	/**
	 * @return probability that this is the answer. 1 is the most sure, 0 is the least
	 *         sure
	 */
	public double getProbability();

	/**
	 * @return description of this character
	 */
	@Nullable
	public String getDescription();

	/**
	 * @return URL to picture or null if no picture is attached
	 */
	@Nullable
	public URL getImage();

}
