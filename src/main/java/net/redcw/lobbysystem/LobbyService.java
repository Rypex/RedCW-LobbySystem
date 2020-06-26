package net.redcw.lobbysystem;

/*
Class created by SpigotSource on 04.01.2020 at 22:46
*/

import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.ClassPath;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import net.redcw.lobbysystem.caseopening.Animator;
import net.redcw.lobbysystem.internal.database.MongoManager;
import net.redcw.lobbysystem.internal.enums.ItemType;
import net.redcw.lobbysystem.manager.FriendManager;
import net.redcw.lobbysystem.manager.ItemManager;
import net.redcw.lobbysystem.manager.ScoreboardManager;
import net.redcw.lobbysystem.particle.Particle;
import net.redcw.lobbysystem.particle.ParticleAPI;
import net.redcw.lobbysystem.particle.ParticleType;
import net.redcw.lobbysystem.player.LobbyPlayerProvider;
import net.redcw.lobbysystem.scheduled.ScheduledActionbar;
import net.redcw.lobbysystem.scheduled.ScheduledNPC;
import net.redcw.lobbysystem.scheduled.ScheduledParticles;
import net.redcw.lobbysystem.scheduled.ScheduledSpawnBlocks;
import net.redcw.lobbysystem.utils.GameStats;
import net.redcw.lobbysystem.utils.ItemHistory;
import net.redcw.lobbysystem.utils.StaticHologram;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.NPCManager;
import net.redcw.redapi.bukkit.RedNPC;
import net.redcw.redapi.bukkit.game.Actionbar;
import net.redcw.redapi.bukkit.game.Scoreboard;
import net.redcw.redapi.language.LanguageDefinition;
import net.redcw.redapi.player.RedPlayerProperty;
import net.redcw.redapi.player.RedPlayerSkinData;
import net.redcw.redapi.positions.PositionProvider;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class LobbyService {

    @Getter
    private static LobbyService instance;

    public static String PREFIX = "§8» §4Red§cCW §8● §7";

    private LobbySystem plugin;

    private LobbyPlayerProvider lobbyPlayerProvider;

    private MongoManager mongoManager;

    private Map<ItemType, ItemStack> germanItems = Maps.newConcurrentMap();
    private Map<ItemType, ItemStack> englishItems = Maps.newConcurrentMap();

    private Map<UUID, Scoreboard> playerScoreboard = Maps.newConcurrentMap();
    private Map<UUID, PermissionGroup> playerGroups = Maps.newConcurrentMap();
    private Map<UUID, Map<String, GameStats>> playerGameStats = Maps.newConcurrentMap();
    private Map<UUID, Integer> playerCookies = Maps.newConcurrentMap();
    private Map<UUID, Document> clanDocuments = Maps.newConcurrentMap();

    private Map<Location, RedNPC> npcMap = Maps.newConcurrentMap();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private PositionProvider spawnLocations;

    private ScoreboardManager scoreboardManager;
    private FriendManager friendManager;
    private NPCManager npcManager;

    private ItemHistory itemHistory;

    private ParticleAPI particleAPI;

    private Actionbar actionbar;

    private Location cookieLocation;

    private Witch dailyRewardEntity;

    private Animator chestAnimator;

    public LobbyService(final LobbySystem lobbySystem)
    {
        instance = this;

        plugin = lobbySystem;

        this.scoreboardManager = new ScoreboardManager();
        this.friendManager = new FriendManager();
        this.lobbyPlayerProvider = new LobbyPlayerProvider(RedAPI.getInstance().getMdbConnectionProvider().getMongoClient().getDatabase(RedAPI.getInstance().getRedAPIConfiguration().getMdbInfo().getDatabase()).getCollection("lobbyplayers"));
        this.mongoManager = new MongoManager();
        this.itemHistory = new ItemHistory();
        this.particleAPI = new ParticleAPI();
        this.actionbar = new Actionbar();
        this.npcManager = new NPCManager();

        //this.mongoManager.connect();
        cookieLocation = new Location(Bukkit.getWorld("Lobby"), 203.5, 36, 814.5);

        initItems();
        initPositions();
        initWorlds(lobbySystem);
        initListener(lobbySystem, "net.redcw.lobbysystem.internal.listener");
        initListener(lobbySystem, "net.redcw.lobbysystem.items");
        initParticles();

        final String worldName = (spawnLocations.getPosition("Spawn").getWorld() == null ? "world" : spawnLocations.getPosition("Spawn").getWorld());

        final WorldCreator worldCreator = WorldCreator.name(worldName);
        Bukkit.createWorld(worldCreator);
        Bukkit.getWorlds().add(Bukkit.getWorld(worldName));

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new ScheduledParticles(), 50, 5);
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new ScheduledActionbar(), 100, 5);
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new ScheduledSpawnBlocks(), 0, 15);
        //plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new ScheduledStats(), 0, 15);

        initNPCs();

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new ScheduledNPC(), 0, 7);
    }

    private void initNPCs() {
        dailyRewardEntity = (Witch) Bukkit.getWorld("Lobby").spawnEntity(new Location(Bukkit.getWorld("Lobby"), 180.5, 39, 830.5, -27, 0), EntityType.WITCH);
        dailyRewardEntity.setCustomNameVisible(false);

        freezeEntity(dailyRewardEntity);

        new StaticHologram(dailyRewardEntity.getLocation().add(0, 2.0, 0), "§8» §6§lDaily§e§lReward §8«", 0).spawn();

        npcMap.put(new Location(Bukkit.getWorld("Lobby"), 183.5, 36, 802.5, 0, 0), new RedNPC(
                new Location(Bukkit.getWorld("Lobby"), 183.5, 36, 802.5, 0, 0),
                new RedPlayerSkinData(
                        "mdOfqzRXiTk3aLftWJZQQL8UrBhF2sNo3kOKE/9R48bDLnI//xotBMxGo4D0xu6hpnx5UGNTHXu2tWaMKzzxy69ulWj1728UPqnRNI/e7oiALryuM2zAtt6Gx6rWf6DnKTmFJ4QyoFWSJUIurn5bM3uGXcXvuN6pC5RdxorXv9jF0ZrZmmpO1i9k7v/p4rW/mfV+5LOZuLwWRxnSsVJGc0g4bASHgZVrZVOhTx/k/2uZYI51tjCDwgXCc6pKExBO5WlqiCUYOHLki4RZAf4zw2an2poBNQSk3kW9s9SdTKFPBqO+u2Y9IWrfXo3fjijpfxpsiqvqakytmSQ6loRwitqxsRtfcXBnMmlz0ZxEtsRqMYUJh1EhtWTIYKgUlRCpjBCH91pQ14dB1JwaQVlQy4p5xjkWMPC9EXsCrclWOMqe6eq7FRwe0bqOHqYvwYqjdIBgzxcECILDO665BXZ5t5O4oKtHmJrzsdASJI+wJIhZbgz8OYirfmdK5mGY7jiuvxFlD2v4h9KnfV1Z3uQzS359U6GRW05FfgC0Io6wEXhxSUkNRPFQHOxif0VpH8dpF2pcQsTbZiD3tSWXA0aso2+4at9tXp3x8Z9+CpF4C5NGMv+v1Nby/RuN79JzRCCnP01FdkVyvmn6ppR5shNFUjAu9pp4MsdxwtseZQYI3Bo=",
                        "ewogICJ0aW1lc3RhbXAiIDogMTU5MTQ2ODE4NDQ2NiwKICAicHJvZmlsZUlkIiA6ICJkOGE3YTM0ZTBiMDg0Y2ZkYjhmYzc2ZTRlY2FmMDEwNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTcGlnb3RTb3VyY2UiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkwZjNjOTRlZTFhNGM5NTA4Mzg2Yzc2ZjUxOTEyNWFmZjMwMWI0ZmY1MjMzYmIwNjRmZjkxOTZjNDNhMDQwZCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9"),
                MinecraftServer.getServer(),
                ((CraftWorld) Bukkit.getWorld("Lobby")).getHandle(),
                new PlayerInteractManager(((CraftWorld) Bukkit.getWorld("Lobby")).getHandle()),
                "§c§lRed§4§lJump"));

        npcMap.put(new Location(Bukkit.getWorld("Lobby"), 177.5, 36, 804.5, -20, 0), new RedNPC(
                new Location(Bukkit.getWorld("Lobby"), 177.5, 36, 804.5, -20, 0),
                new RedPlayerSkinData(
                        "f22vbQjE2+VJYgmd5MEW/K+z/HbmJHOe4W0aBgXF5m3PHgGWvcXkgYsBaJJGp9XxNXKrbb6aR2mEkZ6yXG6Vjm1Rjhh748PHk0d7YSX59wjy+sumuS6LdbEPQIMkLtIy/yyOAyyv5jwfdIse7iKSbIiPAOCIqzLUKK9oLcHVmNYBhOHihIH2y91+AJYEf8wp6sKzT8VDCpG3/DoDz8RMdPvYUnFd08JG/PZ22x40rUF9w0/OKCvx3RIi4WrvwSW3fE5Ie1ujZJX2vP8uYoL1hIq2qwxW6QkqJJJAewWbNfjczusiuGcyXXXdltL5bi1LOBeGKWLpH3BsQQET2i7fG8rD1890+WqrSLZqBdLOmQVN3AmktFc4LESPVwf/7hAw87hu+Yd7zschPTURJBL/LKBWwt5CO6UxHnNcvygMmXaaTKyM/NOV0rWqKmJ433cIpIn/Ho1sxcgfvHnp6xWEzKsuqmI7wog6ay6723rItkeEx1UGX1OW/ZXGZbZCQ63L/HyWnvjay7tglHrY0VLsVtwoMeVQ+Ogsa1MIWUpOkS4lZNL+E/VYKMzcG27SEy8z9tZ3RgfkEG4hGxgyiH8ZwYJtlU18ajZoWsVFvLDpQ3rholmURkS2XGUiSadHPIiKyixie/mvepqPuLcspKbeTVALNrD9cjgw6J9ggAb24DE=",
                        "ewogICJ0aW1lc3RhbXAiIDogMTU4OTgwMzMyOTM0NiwKICAicHJvZmlsZUlkIiA6ICJmNmQxNDM1ZGY4MGM0ZDZjYjRhZDM1MzgwZjYwMDFjYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJSYW5kb21LdWNoZW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGIzOThhNTNlOTczYzg3MjY3MGUzZTZjOGYzNTQyZmY1Y2MwMWUyNWRkZDI2OWY2ZjJkMWI4NjIxOGU0NDlmZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9"),
                MinecraftServer.getServer(),
                ((CraftWorld) Bukkit.getWorld("Lobby")).getHandle(),
                new PlayerInteractManager(((CraftWorld) Bukkit.getWorld("Lobby")).getHandle()),
                "§e§lKnock§6§lPvP"));

        npcMap.put(new Location(Bukkit.getWorld("Lobby"), 189.5, 36, 804.5, 15, 0), new RedNPC(
                new Location(Bukkit.getWorld("Lobby"), 189.5, 36, 804.5, 15, 0),
                new RedPlayerSkinData(
                        "VpCCdcYCqgBDfhzg8u9oEpqntc/jy8tJqZ2Nz+yLRrmje73MoKOLjj8V6oOGxtnBYCWeIZapWCysLt4ulegOk3vhrIYSWGsuuMNA4NXoj1/8JgW8IuEEr49sqw1Mhn0XHqY1Q2Ajzk6YwtxWmwMKnaNzif+13Pap/POQ5IyVaSG98+1oNuUJxLWWYte32P+N4B+noitx+A1qOgqIMLbWA+R76+zDONxE9MKz5KmFLUIJLSZw/9O3lP8aHjmKmfvgNttAa4qLZJVaKFgBrkA15ZQFPKlUyJBvuqGYxkmFBgkubEJ0i2XmHDpMe4338aOIZ1CyHb/7guDAWgoeaMhFPSAZPrqKah+cFm9ZSMwlajaWYxSInNPljiNvuoA9MXkFbh0YBHlTuMf4hHecbKUFtBZGjm/YJ1BldnpBmwXPUHG8OLUZ4cDIJgur7oSSv/ZgeZOyHeRg6V2jOXiAJDdIYByRjkKD6KNvv+mOPGTK8V0MD/rk/XtciB2MJRPUtjiy1HH/5EJ9Hjl6WiRWHOXdTxz14xT44p9DHDnxN1kzoqMrjGlgK1UsDsgq8/S62q5Ng3p2qzevmAgyFYiCR5hocHaPpUySPDtr0CpRLjVPd9AiGzRo/2gbi46umZ3uoEsfLp5CLSc/urhR23YnxXZGZeZQodOTBd+Ey4VZ92pTWSY=",
                        "ewogICJ0aW1lc3RhbXAiIDogMTU5MTQ2OTk2NjQ1NSwKICAicHJvZmlsZUlkIiA6ICJlMGQwOWFmMTE2ZDk0YzllYmY4MTcyNjg0N2M3ZmU3ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJLcmF0emtlXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80YzFiOGQ5ZDBmYWFlZmQ5ZWMxZmM2MDQ0ODFmNjZkNDE1NDI0Nzk1N2RlODcxODI5ZDM2NzhkNDRlMjhmZWViIgogICAgfQogIH0KfQ=="),
                MinecraftServer.getServer(),
                ((CraftWorld) Bukkit.getWorld("Lobby")).getHandle(),
                new PlayerInteractManager(((CraftWorld) Bukkit.getWorld("Lobby")).getHandle()),
                "§3§lMLG§b§lRush"));

        npcManager.setNpcs(new CopyOnWriteArrayList<>(npcMap.values()));
    }

    private void initListener(Plugin plugin, String path)
    {
        try {
            for (UnmodifiableIterator unmodifiableIterator = ClassPath.from(plugin.getClass().getClassLoader()).getTopLevelClasses(path).iterator(); unmodifiableIterator.hasNext(); ) {
                ClassPath.ClassInfo classInfo = (ClassPath.ClassInfo) unmodifiableIterator.next();
                Object obj = Class.forName(classInfo.getName(), true, plugin.getClass().getClassLoader()).newInstance();
                if (obj instanceof Listener) {
                    plugin.getServer().getPluginManager().registerEvents((Listener)obj, plugin);
                    plugin.getLogger().info("Registered " + obj.getClass().getName());
                }
            }
        } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void initItems()
    {
        germanItems.put(ItemType.COMPASS, new ItemManager(Material.COMPASS).setDisplayName("§8» §2Navigator §8▰§7▰ §7Rechtsklick").build());
        germanItems.put(ItemType.PRIVATE_SERVER, new ItemManager(Material.CHEST).setDisplayName("§8» §6Private-Server §8▰§7▰ §7Rechtsklick").build());
        germanItems.put(ItemType.SETTINGS, new ItemManager(Material.STORAGE_MINECART).setDisplayName("§8» §4Einstellungen §8▰§7▰ §7Rechtsklick").build());
        germanItems.put(ItemType.NICK_ON, new ItemManager(Material.NAME_TAG).setDisplayName("§8» §5AutoNick §8▰§7▰ §7Rechtsklick §8» §2✔").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
        germanItems.put(ItemType.NICK_OFF, new ItemManager(Material.NAME_TAG).setDisplayName("§8» §5AutoNick §8▰§7▰ §7Rechtsklick §8» §4✖").build());
        germanItems.put(ItemType.KNOCKBACK_AXE, new ItemManager(Material.GOLD_AXE).setUnbreakable(true).addEnchantment(Enchantment.KNOCKBACK, 3).addItemFlag(ItemFlag.HIDE_ENCHANTS).setDisplayName("§8» §aKnockback-Axt §8▰§7▰ §7Rechtsklick").build());
        germanItems.put(ItemType.LOBBYSWITCHER, new ItemManager(Material.BEACON).setDisplayName("§8» §bLobbywechsler §8▰§7▰ §7Rechtsklick").build());
        germanItems.put(ItemType.PROFILE, new ItemManager(Material.getMaterial(356)).setDisplayName("§8» §dProfil §8▰§7▰ §7Rechtsklick").build());

        englishItems.put(ItemType.COMPASS, new ItemManager(Material.COMPASS).setDisplayName("§8» §2Navigator §8▰§7▰ §7Rightclick").build());
        englishItems.put(ItemType.SETTINGS, new ItemManager(Material.STORAGE_MINECART).setDisplayName("§8» §4Settings §8▰§7▰ §7Rightclick").build());
        englishItems.put(ItemType.NICK_ON, new ItemManager(Material.NAME_TAG).setDisplayName("§8» §5AutoNick §8▰§7▰ §7Rightclick §8» §2✔").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
        englishItems.put(ItemType.NICK_OFF, new ItemManager(Material.NAME_TAG).setDisplayName("§8» §5AutoNick §8▰§7▰ §7Rightclick §8» §4✖").build());
        englishItems.put(ItemType.LOBBYSWITCHER, new ItemManager(Material.BEACON).setDisplayName("§8» §bLobbyswitcher §8▰§7▰ §7Rightclick").build());
        englishItems.put(ItemType.PROFILE, new ItemManager(Material.getMaterial(356)).setDisplayName("§8» §dFriends §8▰§7▰ §7Rightclick").build());
    }

    private void initPositions()
    {
        spawnLocations = RedAPI.getInstance().createPositionProvider("lobby_spawn_position");
    }

    private void initWorlds(Plugin plugin)
    {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getWorlds().forEach(worlds -> {
                worlds.setThundering(false);
                worlds.setStorm(false);
                worlds.setTime(6000);
                worlds.setWeatherDuration(0);
                worlds.setDifficulty(Difficulty.EASY);
                worlds.setTicksPerAnimalSpawns(10000000);
                worlds.setAnimalSpawnLimit(0);
                worlds.setWaterAnimalSpawnLimit(0);
                worlds.getEntities().clear();
                worlds.setGameRuleValue("doDaylightCycle","false");
                worlds.setGameRuleValue("doMobSpawning", "false");
                worlds.setGameRuleValue("randomTickSpeed", "0");

                for(Entity entity : worlds.getEntities()) {
                    if(!(entity instanceof ArmorStand) && !(entity instanceof ItemFrame) && !(entity instanceof Witch))
                        entity.remove();
                }
            });
        }, 4);
    }

    private void initParticles()
    {
        particleAPI.addParticle("silent-hub", new Particle(particleAPI.getData(ParticleType.LAVA)));
        particleAPI.addParticle("sprinting", new Particle(particleAPI.getData(ParticleType.EXPLOSION_NORMAL)));
        particleAPI.addParticle("mob-update", new Particle(particleAPI.getData(ParticleType.NOTE)));
        particleAPI.addParticle("mob-ring", new Particle(particleAPI.getData(ParticleType.SPELL_WITCH)));
        particleAPI.addParticle("join-event", new Particle(particleAPI.getData(ParticleType.ENCHANTMENT_TABLE)));
        particleAPI.addParticle("quit-event", new Particle(particleAPI.getData(ParticleType.SMOKE_LARGE)));
        particleAPI.addParticle("teleport", new Particle(particleAPI.getData(ParticleType.REDSTONE)));
        particleAPI.addParticle("explode", new Particle(particleAPI.getData(ParticleType.EXPLOSION_LARGE)));
        particleAPI.addParticle("portal", new Particle(particleAPI.getData(ParticleType.PORTAL)));
        particleAPI.addParticle("happy-villager", new Particle(particleAPI.getData(ParticleType.SPELL)));
        particleAPI.addParticle("rotator", new Particle(particleAPI.getData(ParticleType.CLOUD)));
        particleAPI.addParticle("rotator-200", new Particle(particleAPI.getData(ParticleType.SMOKE_NORMAL)));
        particleAPI.addParticle("skin", new Particle(particleAPI.getData(ParticleType.PORTAL)));
        particleAPI.addParticle("rankvillager", new Particle(particleAPI.getData(ParticleType.ENCHANTMENT_TABLE)));
        particleAPI.addParticle("dailychestspawn", new Particle(particleAPI.getData(ParticleType.PORTAL)));
    }

    public void setupPlayer(final Player player)
    {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.ADVENTURE);

        scoreboardManager.set(player);

        if(getLanguage(player.getUniqueId()).equals(LanguageDefinition.GERMAN))
        {
            player.getInventory().setItem(0, germanItems.get(ItemType.COMPASS));
            player.getInventory().setItem(1, germanItems.get(ItemType.PRIVATE_SERVER));
            player.getInventory().setItem(2, germanItems.get(ItemType.SETTINGS));

            if(player.hasPermission("net.redcw.nick.autonick"))
            {
                if(RedAPI.getInstance().getRedPlayerProvider().getRedPlayers().get(player.getUniqueId()).isNickEnabled())
                {
                    player.getInventory().setItem(4, germanItems.get(ItemType.NICK_ON));
                } else {
                    player.getInventory().setItem(4, germanItems.get(ItemType.NICK_OFF));
                }

            }

            player.getInventory().setItem(6, germanItems.get(ItemType.KNOCKBACK_AXE));
            player.getInventory().setItem(7, germanItems.get(ItemType.LOBBYSWITCHER));
            player.getInventory().setItem(8, germanItems.get(ItemType.PROFILE));
        } else {
            player.getInventory().setItem(0, englishItems.get(ItemType.COMPASS));
            player.getInventory().setItem(1, englishItems.get(ItemType.SETTINGS));

            if(player.hasPermission("net.redcw.nick.autonick"))
            {
                if(RedAPI.getInstance().getRedPlayerProvider().getRedPlayers().get(player.getUniqueId()).isNickEnabled())
                {
                    player.getInventory().setItem(4, englishItems.get(ItemType.NICK_ON));
                } else {
                    player.getInventory().setItem(4, englishItems.get(ItemType.NICK_OFF));
                }

            }

            player.getInventory().setItem(7, englishItems.get(ItemType.LOBBYSWITCHER));
            player.getInventory().setItem(8, englishItems.get(ItemType.PROFILE));

            if(player.hasPermission("*")) {
                player.getInventory().setItem(22, new ItemManager(Material.COMMAND).setDisplayName("§8» §4Netzwerk-Manager").build());
            }

        }

    }

    public LanguageDefinition getLanguage(final UUID uuid)
    {
        return RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(uuid).getProperty(RedPlayerProperty.COUNTRY);
    }

    public ItemStack getRawSkull(UUID uuid) {

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        final RedPlayerSkinData redPlayerSkinData = RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(uuid).getNoCachedProperty$(RedPlayerProperty.SKIN);
        if (redPlayerSkinData == null) {
            return head;
        }

        String value = redPlayerSkinData.getValue();
        String signature = redPlayerSkinData.getSignature();

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value, signature));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public PermissionGroup getPlayerGroup(final UUID uuid)
    {
        if(playerGroups.containsKey(uuid))
            return playerGroups.get(uuid);

        return CloudAPI
                .getInstance()
                .getOfflinePlayer(uuid)
                .getPermissionEntity()
                .getHighestPermissionGroup(CloudAPI.getInstance().getPermissionPool());
    }

    public String getColor(final UUID uuid)
    {
        if(playerGroups.containsKey(uuid))
            return playerGroups.get(uuid).getSuffix().replace("&", "§");

        return CloudAPI
                .getInstance()
                .getOfflinePlayer(uuid)
                .getPermissionEntity()
                .getHighestPermissionGroup(CloudAPI.getInstance().getPermissionPool())
                .getSuffix()
                .replace("&", "§");
    }

    public void sendTitle(Player player, String message, String subMessage, int fadeIn, int stay, int fadeOut) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(fadeIn, stay, fadeOut));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(message)));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(subMessage)));
    }

    public void freezeEntity(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) entity).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        nmsEn.f(compound);
    }

    public void setChestAnimator(Animator chestAnimator) {
        this.chestAnimator = chestAnimator;
    }
}
