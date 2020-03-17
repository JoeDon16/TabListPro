package me.joedon.tlpversiondetectors;

import me.joedon.TabListPro;
import me.joedon.TabListPro.TabV;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class NewVersionDetector1144 implements TabV {

    public TabListPro plugin10;

    public NewVersionDetector1144(TabListPro plugin) {
        this.plugin10 = plugin;
    }

    public NewVersionDetector1144(NewVersionDetector1144 plugin14) {
    }

    public void sendTabHF(Player player, String header, String footer) {
        org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer craftplayer = (org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer) player;
        net.minecraft.server.v1_14_R1.PlayerConnection connection = craftplayer.getHandle().playerConnection;
        net.minecraft.server.v1_14_R1.IChatBaseComponent headerJSON = net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        net.minecraft.server.v1_14_R1.IChatBaseComponent footerJSON = net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter packet = new net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = packet.getClass().getDeclaredField("header");
            headerField.setAccessible(true);
            headerField.set(packet, headerJSON);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("footer");
            footerField.setAccessible(true);
            footerField.set(packet, footerJSON);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception e) {
            e.printStackTrace();
        }

        connection.sendPacket(packet);
    }
}
