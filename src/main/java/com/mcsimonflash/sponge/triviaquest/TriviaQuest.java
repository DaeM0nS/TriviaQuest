package com.mcsimonflash.sponge.triviaquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

import com.mcsimonflash.sponge.triviaquest.managers.Config;
import com.mcsimonflash.sponge.triviaquest.managers.Trivia;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "triviaquest", name = "TriviaQuest", version = "2.1.1-forge", acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12.2]", serverSideOnly = true)
public class TriviaQuest {

	@Instance("triviaquest")
	private static TriviaQuest instance;

	private File modDirectory;

	private static Logger logger;
	private static URL wiki;
	private static URL discord;

	private int timer = 0;

	public boolean started = false;

	public static long latestTrivia = System.currentTimeMillis();

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event) throws FileNotFoundException, UnsupportedEncodingException {
		instance = this;
		logger = event.getModLog();
		setModDirectory(new File(event.getModConfigurationDirectory(), "triviaquest"));
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	public void onInitilization(FMLInitializationEvent event) {
		logger.info("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
		logger.info("|  TriviaQuest - Version 2.1.1-Forge  |");
		logger.info("|      Developed By: Simon_Flash      |");
		logger.info("|          Ported By: DaeM0nS         |");
		logger.info("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
		Config.readConfig();
		try {
			wiki = new URL("https://github.com/SimonFlash/TriviaQuest/wiki");
			discord = new URL("https://discordapp.com/invite/4wayq37");
		} catch (MalformedURLException ignored) {
			logger.error("Unable to locate TriviaQuest Wiki / Support Discord!");
		}
	}
	
	@Mod.EventHandler
	public void onStart(FMLServerStartingEvent event) {
		if (Trivia.runnerEnabled) {
			Trivia.startRunner();
		}
		event.registerServerCommand(new TriviaCommands());
	}

	@SubscribeEvent
	public void onTickEvent(TickEvent.ServerTickEvent event) {
		if (!started)
			return;
		if (event.phase.equals(TickEvent.Phase.START)) {
			if (timer == 20) {
				if (Trivia.trivia == null) {
					if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - latestTrivia) >= Config.triviaInterval) {
						Trivia.askQuestion(false);
						latestTrivia = System.currentTimeMillis();
					}
				} else if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - latestTrivia) >= Trivia.trivia.getDuration()) {
					Trivia.closeQuestion(false);
				}
				timer = -1;
			}
			timer++;
		}
	}

	@SubscribeEvent
	public void onMessageSend(ServerChatEvent event) {
		if (Trivia.trivia != null && event.getPlayer().canUseCommand(0, "triviaquest.answertrivia.chat")) {
			if (Trivia.processAnswer(event.getPlayer(), event.getMessage())) {
				event.setCanceled(true);
			}
		}
	}

	public static TriviaQuest getInstance() {
		return instance;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static URL getWiki() {
		return wiki;
	}

	public static URL getDiscord() {
		return discord;
	}

	public File getModDirectory() {
		return modDirectory;
	}

	public void setModDirectory(File modDirectory) {
		this.modDirectory = modDirectory;
	}
}