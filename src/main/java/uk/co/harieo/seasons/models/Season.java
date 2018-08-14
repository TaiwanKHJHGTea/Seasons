package uk.co.harieo.seasons.models;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.Validate;

public enum Season {

	SPRING("Spring", "太陽緩緩升起, 鮮美的花朵和春天就在我們的腳步下...",ChatColor.YELLOW),
	SUMMER("Summer", "今天遠比其他季節還溫暖, 也許...太溫暖了 夏天來了!!", ChatColor.GOLD),
	AUTUMN("Autumn", "涼爽的微風緩緩地將樹上通紅的葉子吹落力上, \"是秋天!!\"", ChatColor.DARK_GREEN),
	WINTER("Winter", "雪白雪花飄落，世界變得寒冷, 是冬天來臨的標誌...", ChatColor.BLUE);

	private String name; // Unique identifier for the season that will look nice in chat (front and back end)
	private String message;
	private ChatColor color; // Color to be used when the season is referenced

	Season(String name, String message, ChatColor seasonColor) {
		this.name = name;
		this.message = message;
		this.color = seasonColor;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public ChatColor getColor() {
		return color;
	}

	@Override
	public String toString() {
		return color + name;
	}

	/**
	 * Finds the relevant {@link Season} based on the name property Note: This will ignore cases for user friendliness
	 *
	 * @param name to check for
	 * @return the relevant {@link Season} or null if none found
	 */
	public static Season fromName(String name) {
		for (Season season : values()) {
			if (season.getName().equalsIgnoreCase(name.toLowerCase())) {
				return season;
			}
		}

		return null;
	}

	/**
	 * Returns the next {@link Season} in the list in ascending order or the start of the list if there are no more
	 * seasons after the index
	 *
	 * @param currentSeason that the cycle is currently on
	 * @return the next season
	 */
	public static Season next(Season currentSeason) {
		Validate.notNull(currentSeason);

		List<Season> seasons = Arrays.asList(values());
		int index = seasons.indexOf(currentSeason);
		if (index + 1 >= seasons.size()) { // If there are no more seasons after the current one
			return seasons.get(0); // Return the start of the list
		} else {
			return seasons.get(index + 1); // Return the next season
		}
	}
}
