package red.man10.man10juststop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MJSData implements Listener {
    String prefix = "§2[§d§lM§c§lJust§7§lStop§2]§r";

    public MJSData(){
    }

    public void gameStop(){
        Man10JustStop.gametime = false;
        Man10JustStop.rectime = false;
        Man10JustStop.justnumber = -1;
        Man10JustStop.number1 = -1;
        Man10JustStop.number2 = -1;
        if(Man10JustStop.uuid1 != null){
            Bukkit.getPlayer(Man10JustStop.uuid1).getInventory().addItem(Man10JustStop.item1);
        }
        if(Man10JustStop.uuid2 != null){
            Bukkit.getPlayer(Man10JustStop.uuid2).getInventory().addItem(Man10JustStop.item2);
        }
        Man10JustStop.uuid1 = null;
        Man10JustStop.uuid2 = null;
        Man10JustStop.item1 = null;
        Man10JustStop.item2 = null;
        for(UUID uuid:Man10JustStop.playerState.keySet()){
            Bukkit.getPlayer(uuid).closeInventory();
        }
        Man10JustStop.playerState.clear();
    }

    public void gameStart(){
        Random rnd = new Random();
        Man10JustStop.justnumber = rnd.nextInt(25)+6;
        Bukkit.getPlayer(Man10JustStop.uuid1).sendMessage(prefix+"§e§l今回のジャストナンバー: "+Man10JustStop.justnumber);
        Bukkit.getPlayer(Man10JustStop.uuid2).sendMessage(prefix+"§e§l今回のジャストナンバー: "+Man10JustStop.justnumber);
        Man10JustStop.number1 = 0;
        Man10JustStop.number2 = 0;
        Inventory inv1 = Bukkit.createInventory(null,9,"あなたの数字: 0");
        ItemStack items = new ItemStack(Material.DIAMOND_HOE,1, (short) 916);
        ItemMeta itemmeta = items.getItemMeta();
        itemmeta.setDisplayName("§6§lナンバー追加§7(クリック)§r");
        itemmeta.setUnbreakable(true);
        itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        List<String> k = new ArrayList<String>();
        k.add("§c自分の数字を追加します");
        k.add("§cジャストナンバーを超えないように注意！");
        itemmeta.setLore(k);
        items.setItemMeta(itemmeta);
        ItemStack itemss = new ItemStack(Material.INK_SACK,1,(short)1);
        ItemMeta itemmetas = itemss.getItemMeta();
        itemmetas.setDisplayName("§c§lジャストナンバー§7(クリック)§r");
        List<String> kk = new ArrayList<String>();
        kk.add("§e今回のジャストナンバー: "+Man10JustStop.justnumber);
        itemmetas.setLore(kk);
        itemss.setItemMeta(itemmetas);
        ItemStack itemsss = new ItemStack(Material.BARRIER);
        ItemMeta itemmetass = itemsss.getItemMeta();
        itemmetass.setDisplayName("§4§l終了§7(クリック)§r");
        List<String> kkk = new ArrayList<String>();
        kkk.add("§eターンを終了し、数字が追加できなくなります。");
        itemmetass.setLore(kkk);
        itemsss.setItemMeta(itemmetass);
        inv1.setItem(1,items);
        inv1.setItem(4,itemss);
        inv1.setItem(7,itemsss);
        Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid1).getUniqueId(),"game1");
        Bukkit.getPlayer(Man10JustStop.uuid1).openInventory(inv1);
        Inventory inv2 = Bukkit.createInventory(null,9,"あなたの数字: 0");
        inv2.setItem(1,items);
        inv2.setItem(4,itemss);
        inv2.setItem(7,itemsss);
        Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid2).getUniqueId(),"game2");
        Bukkit.getPlayer(Man10JustStop.uuid2).openInventory(inv2);
    }

    public void gameEnd(){
        Player p1 = Bukkit.getPlayer(Man10JustStop.uuid1);
        Player p2 = Bukkit.getPlayer(Man10JustStop.uuid2);
        Bukkit.broadcastMessage(prefix+"§a§l結果発表!!: §c§l"+ p1.getDisplayName()+" §f§lvs §b§l"+p2.getDisplayName()+" §eジャストナンバー: "+Man10JustStop.justnumber);
        Bukkit.broadcastMessage(prefix+"§4§l "+Man10JustStop.number1+" §f§lvs §1§l"+Man10JustStop.number2+"");
        p1.playSound(p1.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1.0F,1.0F);
        p2.playSound(p2.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1.0F,1.0F);
        if(Man10JustStop.number1 < Man10JustStop.justnumber && Man10JustStop.number2 < Man10JustStop.justnumber){
            if(Man10JustStop.number1 == Man10JustStop.number2){
                Bukkit.broadcastMessage(prefix+"§6§l引き分け！！");
                gameStop();
            }else if(Man10JustStop.number1 > Man10JustStop.number2){
                Bukkit.broadcastMessage(prefix+"§c§l"+p1.getDisplayName()+" §f§lの勝利！！");
                p1.getInventory().addItem(Man10JustStop.item1);
                p1.getInventory().addItem(Man10JustStop.item2);
                Man10JustStop.uuid1 = null;
                Man10JustStop.uuid2 = null;
                gameStop();
            }else{
                Bukkit.broadcastMessage(prefix+"§b§l"+p2.getDisplayName()+" §f§lの勝利！！");
                p2.getInventory().addItem(Man10JustStop.item1);
                p2.getInventory().addItem(Man10JustStop.item2);
                Man10JustStop.uuid1 = null;
                Man10JustStop.uuid2 = null;
                gameStop();
            }
        }else if(Man10JustStop.number1 > Man10JustStop.justnumber && Man10JustStop.number2 < Man10JustStop.justnumber){
            Bukkit.broadcastMessage(prefix+"§b§l"+p2.getDisplayName()+" §f§lの勝利！！");
            p2.getInventory().addItem(Man10JustStop.item1);
            p2.getInventory().addItem(Man10JustStop.item2);
            Man10JustStop.uuid1 = null;
            Man10JustStop.uuid2 = null;
            gameStop();
        }else if(Man10JustStop.number1 < Man10JustStop.justnumber && Man10JustStop.number2 > Man10JustStop.justnumber){
            Bukkit.broadcastMessage(prefix+"§c§l"+p1.getDisplayName()+" §f§lの勝利！！");
            p1.getInventory().addItem(Man10JustStop.item1);
            p1.getInventory().addItem(Man10JustStop.item2);
            Man10JustStop.uuid1 = null;
            Man10JustStop.uuid2 = null;
            gameStop();
        }else if(Man10JustStop.number1 > Man10JustStop.justnumber && Man10JustStop.number2 > Man10JustStop.justnumber){
            Bukkit.broadcastMessage(prefix+"§6§l引き分け！！");
            gameStop();
        }else if(Man10JustStop.number1 == Man10JustStop.justnumber && Man10JustStop.number2 != Man10JustStop.justnumber){
            Bukkit.broadcastMessage(prefix+"§c§l"+p1.getDisplayName()+" §f§lの勝利！！");
            p1.getInventory().addItem(Man10JustStop.item1);
            p1.getInventory().addItem(Man10JustStop.item2);
            Man10JustStop.uuid1 = null;
            Man10JustStop.uuid2 = null;
            gameStop();
        }else if(Man10JustStop.number1 != Man10JustStop.justnumber && Man10JustStop.number2 == Man10JustStop.justnumber){
            Bukkit.broadcastMessage(prefix+"§b§l"+p2.getDisplayName()+" §f§lの勝利！！");
            p2.getInventory().addItem(Man10JustStop.item1);
            p2.getInventory().addItem(Man10JustStop.item2);
            Man10JustStop.uuid1 = null;
            Man10JustStop.uuid2 = null;
            gameStop();
        }else if(Man10JustStop.number1 == Man10JustStop.justnumber){
            Bukkit.broadcastMessage(prefix+"§6§l引き分け！！");
            gameStop();
        }else {
            Bukkit.broadcastMessage(prefix+"§6§l想定していないゲームエンドです。IKに伝えてください。");
            gameStop();
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();
        if(Man10JustStop.playerState.containsKey(p.getUniqueId())){
            if(Man10JustStop.playerState.get(p.getUniqueId()).equalsIgnoreCase("view")){
                e.setCancelled(true);
                return;
            }else if(Man10JustStop.playerState.get(p.getUniqueId()).equalsIgnoreCase("game1")){
                e.setCancelled(true);
                if(e.getClickedInventory() == p.getInventory()){
                    return;
                }
                if(e.getSlot()==1){
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE,1.0F,1.0F);
                    Random rnd = new Random();
                    int newvalue = rnd.nextInt(5)+1;
                    Man10JustStop.number1 = Man10JustStop.number1 + newvalue;
                    p.closeInventory();
                    Inventory inv1 = Bukkit.createInventory(null,9,"あなたの数字: "+Man10JustStop.number1);
                    ItemStack items = new ItemStack(Material.DIAMOND_HOE,1, (short) 916);
                    ItemMeta itemmeta = items.getItemMeta();
                    itemmeta.setDisplayName("§6§lナンバー追加§7(クリック)§r");
                    itemmeta.setUnbreakable(true);
                    itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    List<String> k = new ArrayList<String>();
                    k.add("§c自分の数字を追加します");
                    k.add("§cジャストナンバーを超えないように注意！");
                    itemmeta.setLore(k);
                    items.setItemMeta(itemmeta);
                    ItemStack itemss = new ItemStack(Material.INK_SACK,1,(short)1);
                    ItemMeta itemmetas = itemss.getItemMeta();
                    itemmetas.setDisplayName("§c§lジャストナンバー§7(クリック)§r");
                    List<String> kk = new ArrayList<String>();
                    kk.add("§e今回のジャストナンバー: "+Man10JustStop.justnumber);
                    itemmetas.setLore(kk);
                    itemss.setItemMeta(itemmetas);
                    ItemStack itemsss = new ItemStack(Material.BARRIER);
                    ItemMeta itemmetass = itemsss.getItemMeta();
                    itemmetass.setDisplayName("§4§l終了§7(クリック)§r");
                    List<String> kkk = new ArrayList<String>();
                    kkk.add("§eターンを終了し、数字が追加できなくなります。");
                    itemmetass.setLore(kkk);
                    itemsss.setItemMeta(itemmetass);
                    inv1.setItem(1,items);
                    inv1.setItem(4,itemss);
                    inv1.setItem(7,itemsss);
                    Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid1).getUniqueId(),"game1");
                    Bukkit.getPlayer(Man10JustStop.uuid1).openInventory(inv1);
                    p.sendMessage(prefix+"§e"+newvalue+" §aが追加され 合計§e§l"+Man10JustStop.number1+" §aになりました");
                    if(Man10JustStop.number1 > Man10JustStop.justnumber){
                        p.closeInventory();
                        Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid1).getUniqueId(),"wait");
                        p.sendMessage(prefix+"§c残念！ジャストナンバーを超えてしまった！");
                        p.sendMessage(prefix+"§aターンを終了しました。");
                        if(Man10JustStop.playerState.get(Man10JustStop.uuid1).equalsIgnoreCase("wait")&&Man10JustStop.playerState.get(Man10JustStop.uuid2).equalsIgnoreCase("wait")){
                            gameEnd();
                        }
                    }else if(Man10JustStop.number1 == Man10JustStop.justnumber){
                        p.closeInventory();
                        Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid1).getUniqueId(),"wait");
                        p.sendMessage(prefix+"§6§lジャストナンバーｷﾀ━━━━(ﾟ∀ﾟ)━━━━!!");
                        p.sendMessage(prefix+"§6§lあなたの数はジャストナンバーと同じになりました！");
                        p.sendMessage(prefix+"§aターンを終了しました。");
                        if(Man10JustStop.playerState.get(Man10JustStop.uuid1).equalsIgnoreCase("wait")&&Man10JustStop.playerState.get(Man10JustStop.uuid2).equalsIgnoreCase("wait")){
                            gameEnd();
                        }
                    }
               }else if(e.getSlot()==7){
                    p.playSound(p.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,1.0F,1.0F);
                    p.closeInventory();
                    Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid1).getUniqueId(),"wait");
                    p.sendMessage(prefix+"§aターンを終了しました。");
                    if(Man10JustStop.playerState.get(Man10JustStop.uuid1).equalsIgnoreCase("wait")&&Man10JustStop.playerState.get(Man10JustStop.uuid2).equalsIgnoreCase("wait")){
                        gameEnd();
                    }
                }
            }else if(Man10JustStop.playerState.get(p.getUniqueId()).equalsIgnoreCase("game2")){
                e.setCancelled(true);
                if(e.getClickedInventory() == p.getInventory()){
                    return;
                }
                if(e.getSlot()==1){
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE,1.0F,1.0F);
                    Random rnd = new Random();
                    int newvalue = rnd.nextInt(5)+1;
                    Man10JustStop.number2 = Man10JustStop.number2 + newvalue;
                    p.closeInventory();
                    Inventory inv1 = Bukkit.createInventory(null,9,"あなたの数字: "+Man10JustStop.number2);
                    ItemStack items = new ItemStack(Material.DIAMOND_HOE,1, (short) 916);
                    ItemMeta itemmeta = items.getItemMeta();
                    itemmeta.setDisplayName("§6§lナンバー追加§7(クリック)§r");
                    itemmeta.setUnbreakable(true);
                    itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    List<String> k = new ArrayList<String>();
                    k.add("§c自分の数字を追加します");
                    k.add("§cジャストナンバーを超えないように注意！");
                    itemmeta.setLore(k);
                    items.setItemMeta(itemmeta);
                    ItemStack itemss = new ItemStack(Material.INK_SACK,1,(short)1);
                    ItemMeta itemmetas = itemss.getItemMeta();
                    itemmetas.setDisplayName("§c§lジャストナンバー§7(クリック)§r");
                    List<String> kk = new ArrayList<String>();
                    kk.add("§e今回のジャストナンバー: "+Man10JustStop.justnumber);
                    itemmetas.setLore(kk);
                    itemss.setItemMeta(itemmetas);
                    ItemStack itemsss = new ItemStack(Material.BARRIER);
                    ItemMeta itemmetass = itemsss.getItemMeta();
                    itemmetass.setDisplayName("§4§l終了§7(クリック)§r");
                    List<String> kkk = new ArrayList<String>();
                    kkk.add("§eターンを終了し、数字が追加できなくなります。");
                    itemmetass.setLore(kkk);
                    itemsss.setItemMeta(itemmetass);
                    inv1.setItem(1,items);
                    inv1.setItem(4,itemss);
                    inv1.setItem(7,itemsss);
                    Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid2).getUniqueId(),"game2");
                    Bukkit.getPlayer(Man10JustStop.uuid2).openInventory(inv1);
                    p.sendMessage(prefix+"§e"+newvalue+" §aが追加され 合計§e§l"+Man10JustStop.number2+" §aになりました");
                    if(Man10JustStop.number2 > Man10JustStop.justnumber){
                        p.closeInventory();
                        Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid2).getUniqueId(),"wait");
                        p.sendMessage(prefix+"§c残念！ジャストナンバーを超えてしまった！");
                        p.sendMessage(prefix+"§aターンを終了しました。");
                        if(Man10JustStop.playerState.get(Man10JustStop.uuid1).equalsIgnoreCase("wait")&&Man10JustStop.playerState.get(Man10JustStop.uuid2).equalsIgnoreCase("wait")){
                            gameEnd();
                        }
                    }else if(Man10JustStop.number2 == Man10JustStop.justnumber){
                        p.closeInventory();
                        Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid2).getUniqueId(),"wait");
                        p.sendMessage(prefix+"§6§lジャストナンバーｷﾀ━━━━(ﾟ∀ﾟ)━━━━!!");
                        p.sendMessage(prefix+"§6§lあなたの数はジャストナンバーと同じになりました！");
                        p.sendMessage(prefix+"§aターンを終了しました。");
                        if(Man10JustStop.playerState.get(Man10JustStop.uuid1).equalsIgnoreCase("wait")&&Man10JustStop.playerState.get(Man10JustStop.uuid2).equalsIgnoreCase("wait")){
                            gameEnd();
                        }
                    }
                }else if(e.getSlot()==7){
                    p.playSound(p.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,1.0F,1.0F);
                    p.closeInventory();
                    Man10JustStop.playerState.put(Bukkit.getPlayer(Man10JustStop.uuid2).getUniqueId(),"wait");
                    p.sendMessage(prefix+"§aターンを終了しました。");
                    if(Man10JustStop.playerState.get(Man10JustStop.uuid1).equalsIgnoreCase("wait")&&Man10JustStop.playerState.get(Man10JustStop.uuid2).equalsIgnoreCase("wait")){
                        gameEnd();
                    }
                }
            }
        }
    }


    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player p = (Player)e.getPlayer();
        if(Man10JustStop.playerState.containsKey(p.getUniqueId())){
            if(Man10JustStop.playerState.get(p.getUniqueId()).equalsIgnoreCase("view")){
                Man10JustStop.playerState.remove(p.getUniqueId());
                return;
            }else if(Man10JustStop.playerState.get(p.getUniqueId()).equalsIgnoreCase("game1")){
                Man10JustStop.playerState.remove(p.getUniqueId());
                return;
            }else if(Man10JustStop.playerState.get(p.getUniqueId()).equalsIgnoreCase("game2")){
                Man10JustStop.playerState.remove(p.getUniqueId());
                return;
            }
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(Man10JustStop.uuid1 == p.getUniqueId()){
            Bukkit.broadcastMessage(prefix+"§c§l"+p.getDisplayName()+" §f§lが退出したためゲームをキャンセルします");
            gameStop();
        }else if(Man10JustStop.uuid2 == p.getUniqueId()){
            Bukkit.broadcastMessage(prefix+"§b§l"+p.getDisplayName()+" §f§lが退出したためゲームをキャンセルします");
            gameStop();
        }
        if(Man10JustStop.playerState.containsKey(p.getUniqueId())){
            Man10JustStop.playerState.remove(p.getUniqueId());
        }
    }
}
