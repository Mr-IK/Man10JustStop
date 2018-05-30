package red.man10.man10juststop;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
    private Man10JustStop plugin;
    static int time;
    public Timer(Man10JustStop plugin){
        this.plugin = plugin;
    }
    public void bidTime(){

        time = 120;

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!Man10JustStop.rectime) {
                    time = 0;
                    cancel();
                    return;
                }

                if (time == 0){
                    if(!Man10JustStop.rectime) {
                        time = 0;
                        cancel();
                        return;
                    }
                    plugin.data.gameStop();
                    Bukkit.broadcastMessage(plugin.prefix+"§4§lタイムアウトのため、ゲームが中止されました");
                    time = 0;
                    cancel();
                    return;
                }

                if (time % 60 == 0&&3600 > time){
                    Bukkit.broadcastMessage(plugin.prefix + "§6募集終了まで残り§e§l" + time/60 + "分");
                }else if ((time % 10 == 0&&60 > time) || (time <= 5&&60 > time) ){
                    Bukkit.broadcastMessage(plugin.prefix + "§6募集終了まで残り§e§l" + time + "秒");
                }

                time--;

            }
        }.runTaskTimer(plugin,0,20);
    }
}
