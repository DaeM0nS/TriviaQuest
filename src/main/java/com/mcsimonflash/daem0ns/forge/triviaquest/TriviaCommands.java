package com.mcsimonflash.daem0ns.forge.triviaquest;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.mcsimonflash.daem0ns.forge.triviaquest.managers.Config;
import com.mcsimonflash.daem0ns.forge.triviaquest.managers.Trivia;
import com.mcsimonflash.daem0ns.forge.triviaquest.managers.Util;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class TriviaCommands extends CommandBase {

	public String getName() {
		return "TriviaQuest";
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList("TriviaQuest", "triviaquest", "trivia", "tq");
	}

	@Override
	public String getUsage(ICommandSender icommandsender) {
		return "/triviaquest <answer|post|toggle>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!sender.canUseCommand(0, "triviaquest.base")) {
			sender.sendMessage(
					new TextComponentString(TextFormatting.RED + "You don't have the permission to use this command."));
			return;
		}
		if (args.length == 0) {
			sender.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "+=-=-=-=-=["
					+ TextFormatting.LIGHT_PURPLE + "TriviaQuest" + TextFormatting.DARK_PURPLE + "]=-=-=-=-=+"));

			TextComponentTranslation version = new TextComponentTranslation(TextFormatting.DARK_PURPLE + "TriviaQuest " + TriviaQuest.VERSION + " by SimonFlash and updated to Pure Forge by DaeM0nS\n");

			TextComponentTranslation base = new TextComponentTranslation(TextFormatting.DARK_PURPLE + "/TriviaQuest ");
			base.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/TriviaQuest "))
					.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new TextComponentString(TextFormatting.DARK_PURPLE + "TriviaQuest: "
									+ TextFormatting.LIGHT_PURPLE + "Opens command reference menu\n"
									+ TextFormatting.DARK_PURPLE + "Aliases: " + TextFormatting.LIGHT_PURPLE
									+ "/TriviaQuest+ /Trivia+ /tq\n" + TextFormatting.DARK_PURPLE + "Permission: "
									+ TextFormatting.LIGHT_PURPLE + "triviaquest.base\n" + TextFormatting.DARK_PURPLE
									+ "Note: " + TextFormatting.LIGHT_PURPLE
									+ "Players must have this permission to use any TriviaQuest command")));

			TextComponentTranslation sub = new TextComponentTranslation(TextFormatting.LIGHT_PURPLE + "<Subcommand>");
			sub.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					new TextComponentString(TextFormatting.DARK_PURPLE + "Subcommands: " + TextFormatting.LIGHT_PURPLE
							+ "AnswerTrivia, AskTrivia, CancelTrivia, DisablePack, EnablePack, PostTrivia, ReloadTrivia, ToggleRunner, StopTrivia")));

			sender.sendMessage(new TextComponentTranslation("%s" + "%s" + "%s", new Object[] { version, base, sub }));

			if (sender.canUseCommand(0, "triviaquest.answertrivia.base")) {
				TextComponentString answer = new TextComponentString(
						TextFormatting.DARK_PURPLE + "/TriviaQuest AnswerTrivia ");
				answer.getStyle()
						.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/TriviaQuest AnswerTrivia "));
				answer.getStyle()
						.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new TextComponentString(TextFormatting.DARK_PURPLE + "AnswerTrivia: "
										+ TextFormatting.LIGHT_PURPLE + "Answers a trivia question\n"
										+ TextFormatting.DARK_PURPLE + "Aliases: " + TextFormatting.LIGHT_PURPLE
										+ "AnswerTrivia, Answer, ans\n" + TextFormatting.DARK_PURPLE + "Permission: "
										+ TextFormatting.LIGHT_PURPLE + "triviaquest.answertrivia.base\n"
										+ TextFormatting.DARK_PURPLE + "Note: " + TextFormatting.LIGHT_PURPLE
										+ "Players must have triviaquest.answertrivia.chat to use chat parsing\n"
										+ TextFormatting.DARK_PURPLE + "Note2: " + TextFormatting.LIGHT_PURPLE
										+ "If you use a spigot hybrid server, players must also have minecraft.command.triviaquest.answertrivia.chat to use chat parsing")));

				sender.sendMessage(answer);
			}

			if (sender.canUseCommand(0, "triviaquest.posttrivia.base")) {
				TextComponentTranslation post = new TextComponentTranslation(
						TextFormatting.DARK_PURPLE + "/TriviaQuest PostTrivia");
				post.getStyle()
						.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/TriviaQuest PostTrivia"));
				post.getStyle()
						.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new TextComponentString(TextFormatting.DARK_PURPLE + "PostTrivia: "
										+ TextFormatting.LIGHT_PURPLE + "Posts a trivia question from the pool\n"
										+ TextFormatting.DARK_PURPLE + "Aliases: " + TextFormatting.LIGHT_PURPLE
										+ "PostTrivia, Post, pt\n" + TextFormatting.DARK_PURPLE + "Permission: "
										+ TextFormatting.LIGHT_PURPLE + "triviaquest.posttrivia.base")));

				sender.sendMessage(post);
			}

			if (sender.canUseCommand(0, "triviaquest.togglerunner.base")) {

				TextComponentTranslation toggle = new TextComponentTranslation(
						TextFormatting.DARK_PURPLE + "/TriviaQuest ToggleRunner");
				toggle.getStyle()
						.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/TriviaQuest ToggleRunner"));
				toggle.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new TextComponentString(TextFormatting.DARK_PURPLE + "ToggleRunner: "
								+ TextFormatting.LIGHT_PURPLE + "Enables/Disables the integrated trivia runner\n"
								+ TextFormatting.DARK_PURPLE + "Aliases: " + TextFormatting.LIGHT_PURPLE
								+ "StopTrivia, Stop, off\n" + TextFormatting.DARK_PURPLE + "Permission: "
								+ TextFormatting.LIGHT_PURPLE + "triviaquest.togglerunner.base")));

				sender.sendMessage(toggle);
			}
			if (TriviaQuest.getWiki() != null && TriviaQuest.getDiscord() != null) {

				TextComponentTranslation wiki = new TextComponentTranslation(TextFormatting.DARK_PURPLE + "| "
						+ TextFormatting.LIGHT_PURPLE + TextFormatting.UNDERLINE + "TriviaQuest Wiki");
				wiki.getStyle()
						.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, TriviaQuest.getWiki().toString()));
				wiki.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new TextComponentString("Click to open the TriviaQuest Wiki")));

				TextComponentTranslation discord = new TextComponentTranslation(TextFormatting.RESET + ""
						+ TextFormatting.DARK_PURPLE + " | " + TextFormatting.LIGHT_PURPLE + TextFormatting.UNDERLINE
						+ "Support Discord" + TextFormatting.RESET + TextFormatting.DARK_PURPLE + " |");
				discord.getStyle()
						.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, TriviaQuest.getDiscord().toString()));
				discord.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new TextComponentString("Click to open the Support Discord")));

				sender.sendMessage(new TextComponentTranslation("%s" + "%s", new Object[] { wiki, discord }));
			}
			return;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.canUseCommand(0, "triviaquest.reload")) {
					sender.sendMessage(new TextComponentString(
							TextFormatting.RED + "You don't have the permission to use this command."));
					return;
				}
				Config.readConfig();
				if (Trivia.runnerEnabled) {
					Trivia.startRunner();
				}
				sender.sendMessage(new TextComponentString(Trivia.prefix + "You just reload the plugin!"));
				return;
			} else if (args[0].equalsIgnoreCase("posttrivia") || args[0].equalsIgnoreCase("post")
					|| args[0].equalsIgnoreCase("pt") || args[0].equalsIgnoreCase("p")) {
				if (!sender.canUseCommand(0, "triviaquest.posttrivia.base")) {
					sender.sendMessage(new TextComponentString(
							TextFormatting.RED + "You don't have the permission to use this command."));
					return;
				}
				if (Trivia.trivia != null) {
					throw new CommandException(Config.startTriviaAlreadyActive);
				} else if (Trivia.trivia != null) {
					Trivia.trivia = null;
				}
				Trivia.askQuestion(true);
				TriviaQuest.latestTrivia = System.currentTimeMillis();
				return;
			} else if (args[0].equalsIgnoreCase("togglerunner") || args[0].equalsIgnoreCase("toggle")
					|| args[0].equalsIgnoreCase("runner") || args[0].equalsIgnoreCase("tr")
					|| args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("r")) {
				if (!sender.canUseCommand(0, "triviaquest.togglerunner.base")) {
					sender.sendMessage(new TextComponentString(
							TextFormatting.RED + "You don't have the permission to use this command."));
					return;
				}
				if (Trivia.runnerEnabled = !Trivia.runnerEnabled) {
					Trivia.startRunner();
				}
				sender.sendMessage(new TextComponentString(Util.getText(Trivia.prefix + "The trivia runner has been &d"
						+ (Trivia.runnerEnabled ? "enabled" : "disabled") + "&f!")));
				return;
			} else {
				sender.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + getUsage(sender)));
				return;
			}
		} else if (args.length == 2) {
			 if (args[0].equalsIgnoreCase("answertrivia") || args[0].equalsIgnoreCase("answer")
						|| args[0].equalsIgnoreCase("ans") || args[0].equalsIgnoreCase("a")) {
					if (!sender.canUseCommand(0, "triviaquest.answertrivia.base")) {
						sender.sendMessage(new TextComponentString(
								TextFormatting.RED + "You don't have the permission to use this command."));
						return;
					}
					if (Trivia.trivia == null) {
						throw new CommandException(Config.stopTriviaNotActive);
					} else if (!Trivia.processAnswer(sender, args[1].toString())) {
						sender.sendMessage(new TextComponentString(Trivia.prefix + "Oh no! That wasn't the answer :("));
					}
					return;
			} else if (args[0].equalsIgnoreCase("disablepack")) {

			} else if (args[0].equalsIgnoreCase("enablepack")) {

			}
		} else {
			sender.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + getUsage(sender)));
			return;
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			if (sender.canUseCommand(0, "triviaquest.reload"))
				return CommandBase.getListOfStringsMatchingLastWord(args,
						new String[] { "answer"});
			else
				return CommandBase.getListOfStringsMatchingLastWord(args,
						new String[] { "answer", "post", "toggle", "reload" });
		} else {
			return Collections.emptyList();
		}
	}
}