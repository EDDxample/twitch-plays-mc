package eddxample.twitch;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import net.minecraft.command.arguments.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class TwitchCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tpmc")
            .executes(context -> f0(context.getSource()))
            .then(CommandManager.argument("channel", MessageArgumentType.message())
                .executes(context -> f1(context.getSource(), MessageArgumentType.getMessage(context, "channel"))))
            .then(CommandManager.literal("play")
                .executes(context -> f2(context.getSource())))
            .then(CommandManager.literal("stop")
                .executes(context -> f3(context.getSource())))
            .then(CommandManager.literal("disconnect")
                .executes(context -> f4(context.getSource()))));
    }

    /* Functions */

    /* tpmc */
    private static int f0(ServerCommandSource src) {
        src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText(String.format("[§dTPMC§r] §7channel: %s, play: %s§r", ChatReader.channel, MessageController.twitchPlays ? "true" : "false")));
        return 1;
    }

    /* tpmc <twitch channel> */
    private static int f1(ServerCommandSource src, Message channel) {
        ChatReader.start(channel.getString().toLowerCase());
        return 1;
    }

    /* tpmc play */
    private static int f2(ServerCommandSource src) {
        if (ChatReader.channel == null) src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText("[§dTPMC§r] §cConnect to a channel using §4/tpmc <twitch channel> §r"));
        else {
            src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText("[§dTPMC§r] §l§dTwitch Plays MineCraft!§r"));
            MessageController.twitchPlays = true;
        }
        return 1;
    }

    /* tpmc stop */
    private static int f3(ServerCommandSource src) {
        if (ChatReader.channel == null) src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText("[§dTPMC§r] §cConnect to a channel using §4/tpmc <twitch channel> §r"));
        else if (!MessageController.twitchPlays) src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText("[§dTPMC§r] §cLet the chat move your player using §4/tpmc play§r"));
        else {
            MessageController.twitchPlays = false;
            src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText("[§dTPMC§r] §dTwitch Plays MineCraft§7 disabled§r"));
        }
        return 1;
    }

    /* tpmc disconnect */
    private static int f4(ServerCommandSource src) {
        if (ChatReader.channel == null) {
            src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText("[§dTPMC§r] §cConnect to a channel using §4/tpmc <twitch channel>§r"));
        } else {
            MessageController.twitchPlays = false;
            src.getMinecraftServer().getPlayerManager().sendToAll(new LiteralText("[§dTPMC§r] §dTwitch Plays MineCraft§7 disabled§r"));
            ChatReader.stop();
        }
        return 1;
    }
}
