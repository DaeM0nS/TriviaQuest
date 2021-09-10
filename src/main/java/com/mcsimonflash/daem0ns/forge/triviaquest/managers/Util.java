package com.mcsimonflash.daem0ns.forge.triviaquest.managers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    public static Random random = new Random();

    public static String getText(String message) {
    	/*return message.replaceAll("(?<!\\\\)&", "\u00A7");*/
        return message.replaceAll("&4","\u00A74")
                .replaceAll("&c","\u00A7c")
                .replaceAll("&6","\u00A76")
                .replaceAll("&e","\u00A7e")
                .replaceAll("&2","\u00A72")
                .replaceAll("&a","\u00A7a")
                .replaceAll("&b","\u00A7b")
                .replaceAll("&3","\u00A73")
                .replaceAll("&1","\u00A71")
                .replaceAll("&9","\u00A79")
                .replaceAll("&d","\u00A7d")
                .replaceAll("&5","\u00A75")
                .replaceAll("&f","\u00A7f")
                .replaceAll("&7","\u00A77")
                .replaceAll("&8","\u00A78")
                .replaceAll("&0","\u00A70")
                .replaceAll("&r","\u00A7r")
                .replaceAll("&l","\u00A7l")
                .replaceAll("&o","\u00A7o")
                .replaceAll("&n","\u00A7n")
                .replaceAll("&m","\u00A7m")
                .replaceAll("&k","\u00A7k");
    }

    
    public static Optional<String> getReward() {
        if (Config.chanceSum != 0) {
            double rand = random.nextDouble() * Config.chanceSum;
            for (Map.Entry<String, Integer> reward : Config.rewardCommands.entrySet()) {
                rand -= reward.getValue();
                if (rand <= 0) {
                    return Optional.of(reward.getKey());
                }
            }
        }
        return Optional.empty();
    }

    public static String getCompletion(String word) {
        List<Integer> positions = IntStream.range(0, word.length()).boxed().collect(Collectors.toList());
        Collections.shuffle(positions);
        int blanks = (int) Math.ceil((1 + Math.random()) * word.length() / 4.0);
        StringBuilder completion = new StringBuilder(word);
        for (int i = 0; i < blanks; i++) {
            completion.setCharAt(positions.get(i), '_');
        }
        return completion.toString();
    }

    public static String getScramble(String word) {
        char[] scramble = word.toCharArray();
        for (int i = scramble.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char t = scramble[j];
            scramble[j] = scramble[i];
            scramble[i] = t;
        }
        return new String(scramble);
    }

}