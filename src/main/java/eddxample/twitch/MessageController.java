package eddxample.twitch;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageController {

    public static Map<String, Long> flags = new ConcurrentHashMap<>();
    static long timestamp = System.currentTimeMillis();

    static {
        flags.put("L", 0L);
        flags.put("R", 0L);
        flags.put("F", 0L);
        flags.put("B", 0L);
        flags.put("J", 0L);
    }

    public static void process(String user, String msg) {

        boolean isCommand = msg.length() == 1 && flags.containsKey(msg);

        if (isCommand) flags.replace(msg, timestamp);

        try { MinecraftClient.getInstance().player.sendMessage(new TranslatableText(String.format(isCommand ? "<§e%s§r> §l%s§r" : "<§5%s§r> §7%s§r", user, msg))); }
        catch (NullPointerException e) {}
    }

    public static boolean shouldPress(String s) {
        return System.currentTimeMillis() - flags.get(s) < 300L;
    }


    public static void update() {
        timestamp = System.currentTimeMillis();
    }

}

