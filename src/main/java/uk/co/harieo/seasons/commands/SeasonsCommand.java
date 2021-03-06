package uk.co.harieo.seasons.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.harieo.seasons.Seasons;
import uk.co.harieo.seasons.configuration.SeasonsConfig;
import uk.co.harieo.seasons.models.Cycle;
import uk.co.harieo.seasons.models.Season;
import uk.co.harieo.seasons.models.Weather;
import uk.co.harieo.seasons.models.effect.Effect;

public class SeasonsCommand implements CommandExecutor {

	private static final String WATERMARK =
			ChatColor.GOLD + ChatColor.BOLD.toString() + "Seasons " + ChatColor.GRAY + "v"
					+ Seasons.getPlugin().getDescription().getVersion() + " made by " + ChatColor.YELLOW + "Harieo";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may use this command!");
			return false;
		}

		Player player = (Player) sender;
		Cycle cycle = Seasons.getWorldCycle(player.getWorld());
		boolean hasEnabledEffects = SeasonsConfig.get().hasEnabledEffects();

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("import")) {
				importWorld(player, player.getWorld());
			} else if (args[0].equalsIgnoreCase("effects")) {
				sendEffectsList(player, cycle, hasEnabledEffects);
			}
		} else {
			player.sendMessage(WATERMARK);
			sendSeasonInfo(player, cycle, hasEnabledEffects);
		}

		return false;
	}

	private void sendEffectsList(Player player, Cycle cycle, boolean hasEnabledEffects) {
		if (cycle == null) {
			player.sendMessage(ChatColor.RED + "This world is barren and full of darkness... It has no season!");
			return;
		}

		if (hasEnabledEffects) {
			Weather weather = cycle.getWeather();
			player.sendMessage(Seasons.PREFIX +
					ChatColor.GRAY + "For the weather " + ChatColor.YELLOW + weather.getName()
					+ ChatColor.GRAY + " the effects are:");
			for (Effect effect : weather.getEffects()) {
				player.sendMessage(
						ChatColor.GOLD + ChatColor.BOLD.toString() + effect.getName() + ": "
								+ ChatColor.GRAY + effect.getDescription());
			}
		} else {
			player.sendMessage(
					ChatColor.RED + "Your server administrator has decreed that all effects be disabled...");
		}
	}

	private void sendSeasonInfo(Player player, Cycle cycle, boolean hasEnabledEffects) {
		if (cycle == null) {
			player.sendMessage(ChatColor.RED + "This world is barren and full of darkness... It has no season!");
			return;
		}

		Season season = cycle.getSeason();
		player.sendMessage(season.getColor() + "Your world is in " + season.getName());
		player.sendMessage(ChatColor.GREEN + "The weather is currently " + ChatColor.DARK_GREEN
				+ cycle.getWeather().getName());
		player.sendMessage(ChatColor.GOLD + "Today is day " + cycle.getDay()
				+ " of " + SeasonsConfig.get().getDaysPerSeason());

		if (hasEnabledEffects) {
			player.sendMessage(
					ChatColor.GRAY + "To see the effects of this weather, use the command " + ChatColor.YELLOW
							+ "/seasons effects");
		}
	}

	private void importWorld(Player player, World world) {
		if (Seasons.getWorldCycle(world) != null) {
			player.sendMessage(Seasons.PREFIX + ChatColor.RED + "This world has already been imported!");
		} else if (world.getEnvironment() != Environment.NORMAL) {
			player.sendMessage(Seasons.PREFIX + ChatColor.RED
					+ "This world has an invalid environment, Seasons doesn't use NETHER or END worlds");
		} else {
			Seasons.getWorldHandler().addWorld(world);
			player.sendMessage(Seasons.PREFIX + ChatColor.GREEN + "Successfully imported world!");
		}
	}

}
