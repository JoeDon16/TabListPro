package me.joedon.scoreboard;

import me.clip.ezrankspro.EZAPI;
import me.clip.ezrankspro.EZRanksPro;
import me.clip.placeholderapi.PlaceholderAPI;
import me.joedon.TabListPro;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class EPScoreboard {

    private TabListPro plugin;

    public EPScoreboard(TabListPro plugin){
        this.plugin = plugin;
    }

    public int updateSpeedGlobal;
    public int updateFrame = 0;
    public int biggestAnimationList = 0;

    public Map<String, String> group = new HashMap<>();
    public Map<String, String> groupTemp = new HashMap<>();

    public Set<String> groupKeys;
    public HashMap<String, List<String>> animations = new HashMap<>();

    private static HashMap<String, String> playerPrefixPlaceheld = new HashMap<>();
    private static HashMap<String, String> playerSuffixPlaceheld = new HashMap<>();

    public void updatePlayerPrefixSuffixPlaceholderString(Player p) {
        String uuid = p.getUniqueId().toString();

        try {
            if (me.joedon.TabListPro.placeholderapi) {
                playerPrefixPlaceheld.put(uuid, PlaceholderAPI.setPlaceholders(p, plugin.getConfig().getString("prefix")));
                playerSuffixPlaceheld.put(uuid, PlaceholderAPI.setPlaceholders(p, plugin.getConfig().getString("suffix")));
            } else {
                playerPrefixPlaceheld.put(uuid, plugin.getConfig().getString("prefix"));
                playerSuffixPlaceheld.put(uuid, plugin.getConfig().getString("suffix"));
            }
        } catch (Exception e) {
        }
    }

    public void setTeam(Scoreboard sb, Player p) {

        String team = getTeam(p);
        Team user = sb.getTeam(team);
        String uuid = p.getUniqueId().toString();

        if (playerPrefixPlaceheld.get(uuid) == null || playerSuffixPlaceheld.get(uuid) == null) {
            updatePlayerPrefixSuffixPlaceholderString(p);
        }

        if (user == null) {
            sb.registerNewTeam(team);
            user = sb.getTeam(team);
        }

        // remove entry before adding again, in case of the team not being new
        user.removeEntry(p.getName());
        if (!user.hasEntry(p.getName())) {
//            for(Team t : p.getScoreboard().getTeams()){
//                t.removeEntry(p.getName());
//            }
            user.addEntry(p.getName());
        }

        if (plugin.getConfig().getBoolean("use-displayname")) {
            //USING DISPLAY NAME
            p.setPlayerListName(plugin.colorString(playerPrefixPlaceheld.get(uuid) + p.getDisplayName() + playerSuffixPlaceheld.get(uuid)));
        }
    }


    //custom prestige and rank sorting
    public final boolean EP_VERSION = false;
    private final boolean HIGHEST_PRESTIGES_FIRST = true;
    //IF THIS IS 10+, add '0's in front when sorting (normal sorts already have)
    private final int MAX_PRESTIGES = 8;
    public static EZAPI ezapi = null;

    private final List<String> RANKS = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    public ArrayList<String> permOrder = new ArrayList<>();

    public String getTeam(Player p) {
        if(!EP_VERSION) {
            for (int i = 0; i < permOrder.size(); i++) {
                if (p.hasPermission(permOrder.get(i))) {
                    //fixes sorting when next x10 up (9 -> 10, 99 -> 100, etc.)
                    if(i >= 999) {
                        //5555TLPS
                        return i + "TLPS";
                    }else if(i >= 99){
                        //0555TLPS
                        return "0" + i + "TLPS";
                    }else if(i >= 9){
                        //0055TLPS
                        return "00" + i + "TLPS";
                    }else{
                        //0005TLPS
                        return "000" + i + "TLPS";
                    }
                }
            }
            //only can be numbers in sorting, so setting 'A' + (...) to be last
            return "zzzzzzzzzzzzzzzz";


            //-=- PRIVATE EMERALDPRISONMC.COM VERSION -=-
        }else{
            if(ezapi == null){
                return "";
            }

            //Joedon (Owner)
            if(p.getUniqueId().toString().equals("c8cf896c-bfc9-406d-b2a6-8999b86b0a9d")){
                return "00000000000TLPS";
            }

            if(p.isOp()){
                return "0000000000TLPS";
            }

            if(p.hasPermission("epc.helper")){
                if (HIGHEST_PRESTIGES_FIRST) {
                    //00000 + inverted prestige number (<10) + fixed inverted rank index
                    String rank = ezapi.getCurrentRank(p).substring(0, 1);


                    int invertedRankIndex = RANKS.size() - RANKS.indexOf(rank);
                    if(invertedRankIndex >= 999) {
                        //00000 5555TLPS
                        return "00000" + (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + invertedRankIndex + "TLPS");
                    }else if(invertedRankIndex >= 99){
                        //00000 0555TLPS
                        return "00000" + (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + "0" + invertedRankIndex + "TLPS");
                    }else if(invertedRankIndex >= 9){
                        //00000 0055TLPS
                        return "00000" + (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + "00" + invertedRankIndex + "TLPS");
                    }else{
                        //00000 0005TLPS
                        return "00000" + (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + "000" + invertedRankIndex + "TLPS");
                    }
                }
            }

            //epc.purchase USE AFTER RESET

            if (HIGHEST_PRESTIGES_FIRST) {

                /*
                System.out.println(RANKS.size() - RANKS.indexOf(perm.getPrimaryGroup(p).substring(0, 1).toUpperCase()));
                System.out.println(Integer.toBinaryString(RANKS.size() - RANKS.indexOf(perm.getPrimaryGroup(p).substring(0, 1).toUpperCase())));
                System.out.println(MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")));
                System.out.println(p.getName());
                */

                //inverted prestige number (<10> + fixed inverted rank index
              //  System.out.println(p.getPlayerListName());

                String rank = ezapi.getCurrentRank(p).substring(0, 1);

                int invertedRankIndex = RANKS.size() - RANKS.indexOf(rank);

              //  System.out.println((MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + "***" + invertedRankIndex + "TLPS"));

                if(invertedRankIndex >= 999) {
                    //5555TLPS
                    return (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + invertedRankIndex + "TLPS");
                }else if(invertedRankIndex >= 99){
                    //0555TLPS
                    return (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + "0" + invertedRankIndex + "TLPS");
                }else if(invertedRankIndex >= 9){
                    //0055TLPS
                    return (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + "00" + invertedRankIndex + "TLPS");
                }else{
                    //0005TLPS
                    return (MAX_PRESTIGES - Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + "000" + invertedRankIndex + "TLPS");
                }
            } else {
                String rank = ezapi.getCurrentRank(p).substring(0, 1);

                //prestige number (<10) + rank letter
                return Integer.valueOf(PlaceholderAPI.setPlaceholders(p, "%ezprestige_prestige%")) + rank;
            }
        }
    }
}