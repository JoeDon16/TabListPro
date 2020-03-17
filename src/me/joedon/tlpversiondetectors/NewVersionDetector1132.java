package me.joedon.tlpversiondetectors;

import me.joedon.TabListPro;
import me.joedon.TabListPro.TabV;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class NewVersionDetector1132 implements TabV {

	public TabListPro plugin9;

	public NewVersionDetector1132(TabListPro plugin) {
		this.plugin9 = plugin;
	}

	public NewVersionDetector1132(NewVersionDetector1132 plugin13) {
	}

	public void sendTabHF(Player player, String header, String footer) {
        org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer craftplayer = (org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer) player;
        net.minecraft.server.v1_13_R2.PlayerConnection connection = craftplayer.getHandle().playerConnection;
        net.minecraft.server.v1_13_R2.IChatBaseComponent headerJSON = net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        net.minecraft.server.v1_13_R2.IChatBaseComponent footerJSON = net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        net.minecraft.server.v1_13_R2.PacketPlayOutPlayerListHeaderFooter packet = new net.minecraft.server.v1_13_R2.PacketPlayOutPlayerListHeaderFooter();

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
