package red.man10.man10juststop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Man10JustStop extends JavaPlugin {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if(!power&&!p.hasPermission("ignore")){
            p.sendMessage(prefix+"§4§lMJSはOFFになっています");
            return true;
        }
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("on")){
                if(power) {
                    p.sendMessage(prefix+"§4§lMJSはONになっています");
                    return true;
                }
                power = true;
                p.sendMessage(prefix+"§a§lMJSを起動しました");
                return true;
            }else if(args[0].equalsIgnoreCase("off")){
                if(!power) {
                    p.sendMessage(prefix+"§4§lMJSはoffになっています");
                    return true;
                }
                power =false;
                p.sendMessage(prefix+"§a§lMJSをOFFしました");
                data.gameStop();
                return true;
            }else if(args[0].equalsIgnoreCase("new")){
                if(p.getInventory().getItemInMainHand().getAmount()==0){
                    p.sendMessage(prefix+"§4§lアイテムを持ってください");
                    return true;
                }
                if(gametime) {
                    p.sendMessage(prefix+"§4§l現在ゲーム中です");
                    return true;
                }else if(rectime){
                    p.sendMessage(prefix+"§4§l現在ゲーム募集中です");
                    return true;
                }
                item1 = p.getInventory().getItemInMainHand();
                p.getInventory().setItemInMainHand(null);
                uuid1 = p.getUniqueId();
                rectime = true;
                Bukkit.broadcastMessage(prefix+"§e§l"+p.getDisplayName()+" §a§lさんがMJSゲームを募集開始しました！§f§l : /mjs");
                timer.bidTime();
                p.sendMessage(prefix+"§a§lゲームの募集を開始しました");
                return true;
            }else if(args[0].equalsIgnoreCase("view")){
                Inventory inv = Bukkit.createInventory(null,9,"賭けアイテム");
                ItemStack item11 = new ItemStack(Material.DIAMOND_HOE, 1, (short) 737);
                ItemMeta item1m = item11.getItemMeta();
                item1m.setUnbreakable(true);
                item1m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                if(uuid1!=null) {
                    item1m.setDisplayName("§c§l" + Bukkit.getPlayer(uuid1).getDisplayName());
                }else{
                    item1m.setDisplayName("§c§l" + "現在いません");
                }
                item11.setItemMeta(item1m);
                ItemStack item22 = new ItemStack(Material.DIAMOND_HOE,1,(short)736);
                ItemMeta item2m = item22.getItemMeta();
                item2m.setUnbreakable(true);
                item2m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                if(uuid2!=null) {
                    item2m.setDisplayName("§b§l" + Bukkit.getPlayer(uuid2).getDisplayName());
                }else{
                    item2m.setDisplayName("§b§l" + "現在いません");
                }
                item22.setItemMeta(item2m);
                inv.setItem(2,item11);
                inv.setItem(3,item1);
                inv.setItem(5,item2);
                inv.setItem(6,item22);
                playerState.put(p.getUniqueId(),"view");
                p.openInventory(inv);
                return true;
            }else if(args[0].equalsIgnoreCase("join")){
                if(p.getInventory().getItemInMainHand().getAmount()==0){
                    p.sendMessage(prefix+"§4§lアイテムを持ってください");
                    return true;
                }
                if(gametime) {
                    p.sendMessage(prefix+"§4§l現在ゲーム中です");
                    return true;
                }else if(!rectime){
                    p.sendMessage(prefix+"§4§l現在ゲーム募集中ではありません");
                    return true;
                }
                if(uuid1 == p.getUniqueId()){
                    p.sendMessage(prefix+"§4§l自分自身の募集には参加できません！");
                    return true;
                }
                item2 = p.getInventory().getItemInMainHand();
                p.getInventory().setItemInMainHand(null);
                uuid2 = p.getUniqueId();
                rectime = false;
                gametime = true;
                Bukkit.getPlayer(uuid1).sendMessage(prefix+"§e§l"+p.getDisplayName()+"§6§lさんが参加しようとしています！§f§l :/mjs check");
                p.sendMessage(prefix+"§a§l参加申請をしました。");
                return true;
            }else if(args[0].equalsIgnoreCase("game")) {
                if (uuid1 == p.getUniqueId()) {
                    Inventory inv1 = Bukkit.createInventory(null, 9, "あなたの数字: " + number1);
                    ItemStack items = new ItemStack(Material.DIAMOND_HOE, 1, (short) 916);
                    ItemMeta itemmeta = items.getItemMeta();
                    itemmeta.setDisplayName("§6§lナンバー追加§7(クリック)§r");
                    itemmeta.setUnbreakable(true);
                    itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    List<String> k = new ArrayList<String>();
                    k.add("§c自分の数字を追加します");
                    k.add("§cジャストナンバーを超えないように注意！");
                    itemmeta.setLore(k);
                    items.setItemMeta(itemmeta);
                    ItemStack itemss = new ItemStack(Material.INK_SACK, 1, (short) 1);
                    ItemMeta itemmetas = itemss.getItemMeta();
                    itemmetas.setDisplayName("§c§lジャストナンバー§7(クリック)§r");
                    List<String> kk = new ArrayList<String>();
                    kk.add("§e今回のジャストナンバー: " + justnumber);
                    itemmetas.setLore(kk);
                    itemss.setItemMeta(itemmetas);
                    ItemStack itemsss = new ItemStack(Material.BARRIER);
                    ItemMeta itemmetass = itemsss.getItemMeta();
                    itemmetass.setDisplayName("§4§l終了§7(クリック)§r");
                    List<String> kkk = new ArrayList<String>();
                    kkk.add("§eターンを終了し、数字が追加できなくなります。");
                    itemmetass.setLore(kkk);
                    itemsss.setItemMeta(itemmetass);
                    inv1.setItem(1, items);
                    inv1.setItem(4, itemss);
                    inv1.setItem(7, itemsss);
                    playerState.put(Bukkit.getPlayer(uuid1).getUniqueId(), "game1");
                    Bukkit.getPlayer(uuid1).openInventory(inv1);
                    return true;
                } else if (uuid2 == p.getUniqueId()) {
                    Inventory inv1 = Bukkit.createInventory(null, 9, "あなたの数字: " + number2);
                    ItemStack items = new ItemStack(Material.DIAMOND_HOE, 1, (short) 916);
                    ItemMeta itemmeta = items.getItemMeta();
                    itemmeta.setDisplayName("§6§lナンバー追加§7(クリック)§r");
                    itemmeta.setUnbreakable(true);
                    itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    List<String> k = new ArrayList<String>();
                    k.add("§c自分の数字を追加します");
                    k.add("§cジャストナンバーを超えないように注意！");
                    itemmeta.setLore(k);
                    items.setItemMeta(itemmeta);
                    ItemStack itemss = new ItemStack(Material.INK_SACK, 1, (short) 1);
                    ItemMeta itemmetas = itemss.getItemMeta();
                    itemmetas.setDisplayName("§c§lジャストナンバー§7(クリック)§r");
                    List<String> kk = new ArrayList<String>();
                    kk.add("§e今回のジャストナンバー: " + justnumber);
                    itemmetas.setLore(kk);
                    itemss.setItemMeta(itemmetas);
                    ItemStack itemsss = new ItemStack(Material.BARRIER);
                    ItemMeta itemmetass = itemsss.getItemMeta();
                    itemmetass.setDisplayName("§4§l終了§7(クリック)§r");
                    List<String> kkk = new ArrayList<String>();
                    kkk.add("§eターンを終了し、数字が追加できなくなります。");
                    itemmetass.setLore(kkk);
                    itemsss.setItemMeta(itemmetass);
                    inv1.setItem(1, items);
                    inv1.setItem(4, itemss);
                    inv1.setItem(7, itemsss);
                    playerState.put(Bukkit.getPlayer(uuid2).getUniqueId(), "game2");
                    Bukkit.getPlayer(uuid2).openInventory(inv1);
                    return true;
                }
                Bukkit.broadcastMessage(prefix+"§c§lあなたはゲーム中ではありません");
                return true;
            }else if(args[0].equalsIgnoreCase("end")){
                Bukkit.broadcastMessage(prefix+"§4§l運営によってゲームが中止されました");
                data.gameStop();
                return true;
            }else if(args[0].equalsIgnoreCase("check")){
                if(uuid1 != p.getUniqueId()){
                    p.sendMessage(prefix+"§c§lあなたは主催者ではありません");
                    return true;
                }
                p.sendMessage(prefix+"§6§l/mjs view §f§lで確認後、");
                p.sendMessage(prefix+"§6§l/mjs accept §f§lもしくは");
                p.sendMessage(prefix+"§6§l/mjs deny §f§lを入力");
                return true;
            }else if(args[0].equalsIgnoreCase("accept")){
                if(uuid1 != p.getUniqueId()){
                    p.sendMessage("§c§lあなたは主催者ではありません");
                    return true;
                }
                if(uuid2 == null){
                    p.sendMessage(prefix+"§c§l参加通知が来ていません");
                    return true;
                }
                Bukkit.broadcastMessage(prefix+"§a"+Bukkit.getPlayer(uuid2).getDisplayName()+"§f§lさんの参加により§e§lMJSゲームが開始しました！");
                Bukkit.getPlayer(uuid2).sendMessage(prefix+"§a§lゲームに参加しました");
                data.gameStart();
                return true;
            }else if(args[0].equalsIgnoreCase("deny")){
                if(uuid1 != p.getUniqueId()){
                    p.sendMessage("§c§lあなたは主催者ではありません");
                    return true;
                }
                if(uuid2 == null){
                    p.sendMessage("§c§l参加通知が来ていません");
                    return true;
                }
                Bukkit.getPlayer(uuid2).sendMessage(prefix+"§c§l参加を拒否されました");
                p.sendMessage(prefix+"§c§l参加を拒否しました");
                Bukkit.getPlayer(uuid2).getInventory().addItem(item2);
                uuid2 = null;
                rectime = true;
                gametime = false;
                item2 = null;
                return true;
            }else if(args[0].equalsIgnoreCase("rule")){
                p.sendMessage("§6§l========MJustStopのルール========");
                p.sendMessage("§e・ゲームが始まると、「ジャストナンバー」が決まります。");
                p.sendMessage("§e・ジャストナンバーにいちばん近い数字の人が勝ちです。");
                p.sendMessage("§e・数字はGameGUIの左の＋ボタンで 1～6が追加されます。");
                p.sendMessage("§e・数字がジャストナンバーを超えると失格です。");
                p.sendMessage("§e・数字がジャストナンバーと同じ数字になると、負けはなくなります。(勝ちor引き分け)");
                return true;
            }
        }
        p.sendMessage("==========§d§l●§f§l●§a§l●" + prefix + "§a§l●§f§l●§d§l●§r==========");
        if(gametime) {
            p.sendMessage("§a§lゲーム中: §c§l" + Bukkit.getPlayer(uuid1).getDisplayName() + " §f§lVS" +
                    "§b§l" + Bukkit.getPlayer(uuid2).getDisplayName());
            if(item1.getItemMeta()==null||item1.getItemMeta().getDisplayName()==null) {
                p.sendMessage("§e§lジャストナンバー: §9§l" + justnumber);
            }
        }else if(rectime){
            p.sendMessage("§a§l募集中: §c§l" + Bukkit.getPlayer(uuid1).getDisplayName());
        }else{
            p.sendMessage("§4§lゲーム中・募集中 ではありません");
        }
        p.sendMessage("");
        p.sendMessage("§6§l/mjs new§f§l: MJSゲーム募集開始");
        p.sendMessage("§6§l/mjs view §f§l: 募集中、賭けに出されているアイテムを見る");
        p.sendMessage("§6§l/mjs join §f§l: MJSゲーム参加");
        p.sendMessage("§6§l/mjs check §f§l: 参加の通知を確認する");
        p.sendMessage("§6§l/mjs game §f§l: ゲーム中、ゲームGUIをもう一度開く");
        p.sendMessage("");
        p.sendMessage("§6§l/mjs rule §f§l: ルールを確認する");
        if(p.hasPermission("ophelp")){
            p.sendMessage("§c§l/mjs end §f§l: MJSゲームを強制終了する");
            p.sendMessage("§c§l/mjs on §f§l: MJSゲームを停止する");
            p.sendMessage("§c§l/mjs off §f§l: MJSゲームを起動する");
        }
        p.sendMessage("==========§d§l●§f§l●§a§l●" + prefix + "§a§l●§f§l●§d§l●§r==========");
        return true;
    }
    String prefix = "§2[§d§lM§c§lJust§7§lStop§2]§r";

    //////////////////////////
    //ゲームに関わる変数
    static boolean power = true;
    static boolean rectime = false;
    static boolean gametime = false;
    static int justnumber = -1;
    static int number1 = -1;
    static int number2 = -1;
    static UUID uuid1 = null;
    static UUID uuid2 = null;
    static ItemStack item1 = null;
    static ItemStack item2 = null;
    static HashMap<UUID,String> playerState;
    //////////////////////////

    MJSData data;
    Timer timer;
    @Override
    public void onEnable() {
        // Plugin startup logic
        data = new MJSData();
        timer = new Timer(this);
        getCommand("mjs").setExecutor(this);
        getServer().getPluginManager().registerEvents (data,this);
        playerState = new HashMap<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
