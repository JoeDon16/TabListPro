package me.joedon.tlpversiondetectors;

import java.lang.reflect.Field;

import me.joedon.TabListPro;
import org.bukkit.entity.Player;

import me.joedon.TabListPro.TabV;

public class NewVersionDetector18 implements TabV{
	
	  public TabListPro plugin2;
		
	     public NewVersionDetector18(TabListPro plugin) {
	         this.plugin2 = plugin;
	     }

	public NewVersionDetector18(NewVersionDetector18 plugin22) {
		}

	public void sendTabHF(Player player, String header, String footer) {
		
		org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer craftplayer = (org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player;
	net.minecraft.server.v1_8_R3.PlayerConnection connection = craftplayer
			.getHandle().playerConnection;
	net.minecraft.server.v1_8_R3.IChatBaseComponent headerJSON = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
			.a("{\"text\": \"" + header + "\"}");
	net.minecraft.server.v1_8_R3.IChatBaseComponent footerJSON = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
			.a("{\"text\": \"" + footer + "\"}");
	net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter packet = new net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter();

	try {
		Field headerField = packet.getClass().getDeclaredField("a");
		headerField.setAccessible(true);
		headerField.set(packet, headerJSON);
		headerField.setAccessible(!headerField.isAccessible());

		Field footerField = packet.getClass().getDeclaredField("b");
		footerField.setAccessible(true);
		footerField.set(packet, footerJSON);
		footerField.setAccessible(!footerField.isAccessible());
	} catch (Exception e) {
	}

	connection.sendPacket(packet);
	
}
}


















