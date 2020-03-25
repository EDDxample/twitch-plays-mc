package eddxample.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.GameJoinS2CPacket;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class JoinWorldMixin {
    @Shadow MinecraftClient client;

    @Inject(at = @At("RETURN"), method = "onGameJoin")
    public void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        Text text = new LiteralText("§a by EDDxample§r");
        text.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("youtube.com/EDDxample")));
        if (this.client.player != null) {
            this.client.player.sendMessage(new LiteralText("[§dTPMC§r] §l§dTwitch Plays MineCraft§r").append(text));
            this.client.player.sendMessage(new LiteralText("[§dTPMC§r] §7Connect to a twitch channel using §6/tpmc <channel> §r"));
        }
    }
}
