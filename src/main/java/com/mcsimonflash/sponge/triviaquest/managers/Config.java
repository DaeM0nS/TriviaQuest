package com.mcsimonflash.sponge.triviaquest.managers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.mcsimonflash.sponge.triviaquest.TriviaQuest;
import com.mcsimonflash.sponge.triviaquest.objects.Completion;
import com.mcsimonflash.sponge.triviaquest.objects.Question;
import com.mcsimonflash.sponge.triviaquest.objects.Scramble;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Config {

    private static Path dir = Sponge.getConfigManager().getPluginConfig(TriviaQuest.getContainer()).getDirectory(), packs = dir.resolve("packs");

    public static boolean showAnswers;
    public static int chanceSum;
    public static int enableRewardsCount;
    public static int enableTriviaCount;
    public static int triviaInterval;
    public static int triviaLength;
    public static String completionQuestion;
    public static String completionAnswer;
    public static String scrambleQuestion;
    public static String scrambleAnswer;
    public static List<String> enabledPacks = Lists.newArrayList();
    public static Map<String, Integer> rewardCommands = Maps.newHashMap();

    public static void readConfig() {
        try {
            Files.createDirectories(dir);
            Path core = dir.resolve("triviaquest.core");
            TriviaQuest.getContainer().getAsset("triviaquest.core").get().copyToFile(core);
            if (Files.notExists(packs)) {
                Files.createDirectories(packs);
                TriviaQuest.getContainer().getAsset("trivia.pack").get().copyToFile(packs.resolve("trivia.pack"));
            }
            CommentedConfigurationNode root = HoconConfigurationLoader.builder().setPath(core).build().load();
            enabledPacks = root.getNode("enabled-packs").getChildrenList().stream().map(ConfigurationNode::getString).collect(Collectors.toList());
            enabledPacks.forEach(p -> loadPack(packs.resolve(p + ".pack")));
            root.getNode("rewards").getChildrenMap().values().forEach(r -> rewardCommands.put(r.getNode("command").getString(""), r.getNode("chance").getInt(0)));
            chanceSum = rewardCommands.values().stream().mapToInt(Integer::intValue).sum();
            showAnswers = root.getNode("config", "show-answers").getBoolean(false);
            enableRewardsCount = root.getNode("config", "enable-rewards-count").getInt(0);
            enableTriviaCount = root.getNode("config", "enable-trivia-count").getInt(0);
            triviaInterval = root.getNode("config", "trivia-interval").getInt(300);
            triviaLength = root.getNode("config", "trivia-length").getInt(30);
            if (triviaInterval < 1 || triviaLength < 1) {
                TriviaQuest.getLogger().error("Interval and length must be at least 1! | Interval:[" + triviaInterval + "] Length:[" + triviaLength + "]");
                triviaInterval = 300;
                triviaLength = 30;
            }
            completionQuestion = root.getNode("config", "completion-question").getString("&fFill in the blanks: <word>");
            completionAnswer = root.getNode("config", "completion-answer").getString("&fThe word was &d<word>&f!");
            scrambleQuestion = root.getNode("config", "scramble-question").getString("&fUnscramble the word: <word>");
            scrambleAnswer = root.getNode("config", "scramble-answer").getString("&fThe word was &d<word>&f!");
            Trivia.prefix = Util.toText(root.getNode("config", "trivia-prefix").getString("&8&l[&5TriviaQuest&8&l]&f "));
            if (root.getNode("config", "enable-on-startup").getBoolean(false)) {
                Trivia.startRunner();
            }
        } catch (IOException e) {
            TriviaQuest.getLogger().error("Config could not be loaded!");
            e.printStackTrace();
        }
    }

    public static void loadPack(Path path) {
        if (Files.exists(path)) {
            try {
                CommentedConfigurationNode node = HoconConfigurationLoader.builder().setPath(path).build().load();
                node.getNode("trivia", "completions").getChildrenMap().values().forEach(n -> Trivia.triviaList.add(new Completion(n.getNode("word").getString(""), getList(path, n.getNode("choices")))));
                node.getNode("trivia", "questions").getChildrenMap().values().forEach(n -> Trivia.triviaList.add(new Question(n.getNode("question").getString(""), getList(path, n.getNode("answers")))));
                node.getNode("trivia", "scrambles").getChildrenMap().values().forEach(n -> Trivia.triviaList.add(new Scramble(n.getNode("word").getString(""), getList(path, n.getNode("choices")))));
            } catch (IOException e) {
                TriviaQuest.getLogger().error("Error loading pack " + path.getFileName() + ".");
                e.printStackTrace();
            }
        } else {
            TriviaQuest.getLogger().error("Attempted to load non-existent pack " + path.getFileName() + ".");
        }
    }

    public static List<String> getList(Path path, ConfigurationNode node) {
        try {
            return node.getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            TriviaQuest.getLogger().error("Unable to load list from pack " + path.getFileName() + " at " + Arrays.toString(node.getPath()));
            return Lists.newArrayList();
        }
    }

}