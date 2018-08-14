package uk.co.harieo.seasons.models;

import org.bukkit.ChatColor;

import java.util.*;
import uk.co.harieo.seasons.Seasons;
import uk.co.harieo.seasons.models.effect.Effect;

public enum Weather {

	BEAUTIFUL("美極了",
			ChatColor.GREEN + "閃耀的太陽, 草是綠油油der 且這天氣... " + ChatColor.YELLOW
					+ "真是美好",
			false, false,
			Arrays.asList(Season.SPRING, Season.SUMMER)),
	BREEZY("微風輕拂",
			ChatColor.GRAY + "涼爽的微風輕拂你的身旁,這將會變得 " + ChatColor.GREEN + "更加愉悅(吸大麻?",
			false, false,
			Arrays.asList(Season.SPRING, Season.AUTUMN)),
	CHILLY("變冷了",
			ChatColor.BLUE + "當霜凍霜在你周圍閃閃發光時，你將會顫抖, 這將會變得 " + ChatColor.DARK_BLUE + "寒冷... "
					+ ChatColor.BLUE + "就是今天",
			false, false,
			Collections.singletonList(Season.SPRING)),
	RAINY("下雨了",
			ChatColor.BLUE + "周圍的地變得又濕又滑, 這是... " + ChatColor.DARK_BLUE + "雨",
			false, true,
			Arrays.asList(Season.SPRING, Season.AUTUMN, Season.WINTER)),
	SCORCHING("酷熱",
			ChatColor.YELLOW + "熾熱的太陽灼燒了妳的皮膚，地表開始加熱, 這是... " + ChatColor.GOLD
					+ "酷熱",
			false, false,
			Collections.singletonList(Season.SUMMER)),
	HOT("好熱",
			ChatColor.YELLOW + "今天變得越來越 " + ChatColor.GOLD + "熱 " + ChatColor.YELLOW
					+ "",
			false, false,
			Collections.singletonList(Season.SUMMER)),
	WARM("好溫暖",
			ChatColor.YELLOW + "當你移動時，一種舒緩的溫暖擁抱著你, 好..好 " + ChatColor.GOLD + "溫暖 "
					+ ChatColor.YELLOW + "的一天",
			false, false,
			Collections.singletonList(Season.SUMMER)),
	COLD("好冷",
			ChatColor.BLUE + "水已經非常的 " + ChatColor.DARK_BLUE + "冷了 " + ChatColor.BLUE + "",
			false, false,
			Arrays.asList(Season.AUTUMN, Season.WINTER)),
	STORMY("暴風雨來襲",
			ChatColor.RED + "強烈的 " + ChatColor.DARK_RED + "暴風雨 " + ChatColor.RED
					+ "產生了, 神明在這裡生氣了... 自己要多保重!",
			true, true,
			Collections.singletonList(Season.AUTUMN)),
	FREEZING("雪虐風饕虐",
			ChatColor.BLUE + "水被一塊一塊地被凍結成冰，你感覺很冷, 這真是.. "
					+ ChatColor.DARK_BLUE + "冰冷至極",
			true, false,
			Collections.singletonList(Season.WINTER)),
	SNOWY("靄靄白雪",
			ChatColor.GRAY + "一條白色的大毯覆蓋了整個世界, 這是.. " + ChatColor.WHITE + "雪",
			false, true,
			Collections.singletonList(Season.WINTER)),
	NIGHT("Calm", // Night is a weather with no effect, to give people a break
			ChatColor.GRAY + "The world rests with the sun and all is calm... Until the mobs come to eat you!",
			false, false);

	private static final Random random = new Random();

	private String name; // Name shown to players
	private String message; // Initial broadcast on weather trigger
	private boolean catastrophic; // Is there is a high risk of this weather killing a player?
	private boolean storm;
	private List<Season> seasons; // List of seasons this weather can be triggered on

	Weather(String name, String broadcast, boolean catastrophic, boolean storm, List<Season> seasons) {
		this.name = name;
		this.message = broadcast;
		this.catastrophic = catastrophic;
		this.storm = storm;
		this.seasons = seasons;
	}

	Weather(String name, String broadcast, boolean catastrophic, boolean storm) {
		this.name = name;
		this.message = broadcast;
		this.catastrophic = catastrophic;
		this.storm = storm;
		this.seasons = Collections.emptyList();
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public boolean isCatastrophic() {
		return catastrophic;
	}

	public boolean isStorm() {
		return storm;
	}

	public List<Season> getAffectedSeasons() {
		return seasons;
	}

	public List<Effect> getEffects() {
		List<Effect> effects = new ArrayList<>();

		for(Effect effect : Seasons.getEffects()) {
			if (effect.isWeatherApplicable(this)) {
				effects.add(effect);
			}
		}

		return effects;
	}

	/**
	 * Finds the relevant {@link Weather} based on the name property Note: This will ignore cases for user friendliness
	 *
	 * @param name to check for
	 * @return the relevant {@link Weather} or null if none found
	 */
	public static Weather fromName(String name) {
		for (Weather weather : values()) {
			if (weather.getName().equalsIgnoreCase(name.toLowerCase())) {
				return weather;
			}
		}

		return null;
	}

	/**
	 * Gets a random {@link Weather} from the list of values without any constraints
	 *
	 * @return a randomly selected {@link Weather}
	 */
	public static Weather randomWeather() {
		return values()[random.nextInt(values().length)];
	}

	/**
	 * Gets a random {@link Weather} that is applicable in the stated {@link Season}
	 *
	 * @param season as a constraint to what weathers will be used
	 * @return a random {@link Weather} that can be used in the stated {@link Season}
	 */
	public static Weather randomWeather(Season season) {
		List<Weather> applicableWeathers = new ArrayList<>();
		for (Weather weather : values()) {
			if (weather.seasons.contains(season)) { // Whether the weather can be used with the season
				applicableWeathers.add(weather);
			}
		}

		return applicableWeathers.get(random.nextInt(applicableWeathers.size()));
	}

}
