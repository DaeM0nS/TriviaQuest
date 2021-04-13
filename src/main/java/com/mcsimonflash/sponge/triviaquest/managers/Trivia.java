package com.mcsimonflash.sponge.triviaquest.managers;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.mcsimonflash.sponge.triviaquest.TriviaQuest;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Trivia {

    public static String prefix;

    public static boolean runnerEnabled = false;
    public static int triviaIndex = 0;
    public static com.mcsimonflash.sponge.triviaquest.objects.Trivia trivia = null;
    public static List<com.mcsimonflash.sponge.triviaquest.objects.Trivia> triviaList = Lists.newArrayList();

    public static void startRunner() {
        if (Trivia.trivia != null) {
        	Trivia.trivia=null;
        }
        TriviaQuest.getInstance().started = true;
    }

    public static void newQuestion() {
        if (triviaIndex == 0) {
            triviaIndex = triviaList.size();
            Collections.shuffle(triviaList);
        }
        trivia = triviaList.get(--triviaIndex);
    }

    public static void askQuestion(boolean override) {
        if (shouldTriviaRun(override) && trivia == null) {
            newQuestion();
            FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(prefix+trivia.getQuestion()));
        } else if (runnerEnabled) {
            startRunner();
        }
    }

    public static void closeQuestion(boolean answered) {
        if (!answered) {
        	FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(Util.getText(prefix+"Times up! " + (Config.showAnswers && trivia.showAnswer() ? trivia.getAnswer() : "Better luck next time!"))));
        }
        trivia = null;

        if (runnerEnabled) {
            startRunner();
        }
    }

    public static boolean shouldTriviaRun(boolean override) {
        if (override) {
            return true;
        } else if (!runnerEnabled) {
            return false;
        }
        return FMLCommonHandler.instance().getMinecraftServerInstance().getOnlinePlayerNames().length >= Config.enableTriviaCount;
    }

    public static boolean processAnswer(ICommandSender src, String answer) {
        if (trivia.checkAnswer(answer)) {
        	FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(Util.getText(prefix+"&d" + src.getName() + "&f got it! " + (Config.showAnswers && trivia.showAnswer() ? trivia.getAnswer() : "Better luck next time!"))));
            if (FMLCommonHandler.instance().getMinecraftServerInstance().getOnlinePlayerNames().length >= Config.enableRewardsCount) {
                String rewardCmd = Util.getReward().orElse(null);
                if (rewardCmd != null && !rewardCmd.isEmpty()) {
                    if (src instanceof EntityPlayerMP) {
                    	FMLCommonHandler.instance().getMinecraftServerInstance().commandManager.executeCommand(FMLCommonHandler.instance().getMinecraftServerInstance(), rewardCmd.replace("<player>", src.getName()));
                    } else {
                        src.sendMessage(new TextComponentString(Util.getText(prefix+"Sorry! Only a player can receive a reward!")));
                    }
                }
            }
            closeQuestion(true);
            return true;
        }
        return false;
    }
}