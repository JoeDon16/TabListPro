package me.joedon.tlpversiondetectors;

import java.lang.reflect.Field;

import me.joedon.TabListPro;
import org.bukkit.entity.Player;

import me.joedon.TabListPro.TabV;

public class NewVersionDetector111 implements TabV{

	  public TabListPro plugin6;
		
	     public NewVersionDetector111(TabListPro plugin) {
	         this.plugin6 = plugin;
	     }
	
	 	public NewVersionDetector111(NewVersionDetector111 plugin12) {
		}

		public void sendTabHF(Player player, String header, String footer) {

				org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer craftplayer = (org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer) player;
				net.minecraft.server.v1_11_R1.PlayerConnection connection = craftplayer.getHandle().playerConnection;
				net.minecraft.server.v1_11_R1.IChatBaseComponent headerJSON = net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
				net.minecraft.server.v1_11_R1.IChatBaseComponent footerJSON = net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
				net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter packet = new net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter();

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
					//e.printStackTrace();
				}

				connection.sendPacket(packet);
			}
		}
