package eddxample.twitch;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.arguments.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;

import java.awt.*;

public class TwitchCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("twitch").executes(context -> f0(context.getSource()))
                    .then(CommandManager.literal("listen").then(CommandManager.argument("twitch channel", MessageArgumentType.message()).executes(context -> f1(context.getSource()))))
                    .then(CommandManager.literal("play").executes(context -> f2(context.getSource())))
                    .then(CommandManager.literal("stop").executes(context -> f3(context.getSource()))));
    }

    /* Functions */

    /* twitch */
    private static int f0(CommandSource src) {return 1;}

    /* twitch listen <twitch channel> */
    private static int f1(CommandSource src) {return 1;}

    /* twitch play */
    private static int f2(CommandSource src) {return 1;}

    /* twitch stop */
    private static int f3(CommandSource src) {return 1;}

}
