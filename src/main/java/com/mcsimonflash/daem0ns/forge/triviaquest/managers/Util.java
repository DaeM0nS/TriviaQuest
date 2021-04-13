package com.mcsimonflash.daem0ns.forge.triviaquest.managers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    public static Random random = new Random();
    
    public static File getFileFromResource(String fileName) throws URISyntaxException{
        ClassLoader classLoader = Util.class.getClassLoader();
        URL resource = classLoader.getResource("assets/triviaquest/"+fileName);
        if (resource == null) {
            throw new IllegalArgumentException("assets/triviaquest/"+fileName);
        } else {
            return new File(resource.toURI());
        }
    }

    public static String getText(String message) {
    	/*return message.replaceAll("(?<!\\\\)&", "\u00A7");*/
        message = message.replaceAll("&4","\u00A74");
        message = message.replaceAll("&c","\u00A7c");
        message = message.replaceAll("&6","\u00A76");
        message = message.replaceAll("&e","\u00A7e");
        message = message.replaceAll("&2","\u00A72");
        message = message.replaceAll("&a","\u00A7a");
        message = message.replaceAll("&b","\u00A7b");
        message = message.replaceAll("&3","\u00A73");
        message = message.replaceAll("&1","\u00A71");
        message = message.replaceAll("&9","\u00A79");
        message = message.replaceAll("&d","\u00A7d");
        message = message.replaceAll("&5","\u00A75");
        message = message.replaceAll("&f","\u00A7f");
        message = message.replaceAll("&7","\u00A77");
        message = message.replaceAll("&8","\u00A78");
        message = message.replaceAll("&0","\u00A70");
        message = message.replaceAll("&r","\u00A7r");
        message = message.replaceAll("&l","\u00A71");
        message = message.replaceAll("&o","\u00A7o");
        message = message.replaceAll("&n","\u00A7n");
        message = message.replaceAll("&m","\u00A7m");
        message = message.replaceAll("&k","\u00A7k");
        return message;
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