package eddxample.twitch;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageController {

    public static Map<String, Long> flags = new ConcurrentHashMap<>();
    public static long timestamp = System.currentTimeMillis(), pressDuration = 400L;
    public static boolean twitchPlays;

    static {
        flags.put("A", 0L);
        flags.put("D", 0L);
        flags.put("W", 0L);
        flags.put("S", 0L);
        flags.put("J", 0L);
    }

    public static void process(String user, String msg) {

        boolean isCommand = twitchPlays && msg.length() == 1 && flags.containsKey(msg);

        if (isCommand) flags.replace(msg, timestamp);

        try { MinecraftClient.getInstance().player.sendMessage(new LiteralText(String.format(isCommand ? "<§e%s§r> §6§l%s§r" : "<§5%s§r> %s§r", user, msg))); }
        catch (NullPointerException e) {}
    }

    public static boolean shouldPress(String s) {
        return timestamp - flags.get(s) < pressDuration;
    }


    public static void update() {
        timestamp = System.currentTimeMillis();
    }

}

