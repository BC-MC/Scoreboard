package com.ep4.sb;
 
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
 
import java.util.HashMap;
import java.util.Map;

public class Core extends JavaPlugin implements Listener {
 
    private Scoreboard board;
    private Map<String, Integer> mobsKilled = new HashMap<String, Integer>();
 
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        setupScoreboard();
    }
 
    public void setupScoreboard() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
 
        Objective kills = board.registerNewObjective("kills", "dummy");
        kills.setDisplaySlot(DisplaySlot.SIDEBAR);
        kills.setDisplayName("Mob Kills");
    }
 
    public Scoreboard getScoreboard() {
        return this.board;
    }
 
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player) {
            Player player = e.getEntity().getKiller();
            int kills = 0;
            if (mobsKilled.containsKey(player.getName())) { kills=mobsKilled.get(player); }
            kills++;
            mobsKilled.put(player.getName(), kills);
            getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(player).setScore(kills);
        }
    }
 
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setScoreboard(getScoreboard());
    }
 
}