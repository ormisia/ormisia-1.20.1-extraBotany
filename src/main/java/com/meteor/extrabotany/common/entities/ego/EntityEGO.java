package com.meteor.extrabotany.common.entities.ego;

import com.google.common.collect.ImmutableList;
import com.meteor.extrabotany.common.core.ConfigHandler;
import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.bauble.ItemNatureOrb;
import com.meteor.extrabotany.common.items.relic.ItemFirstFractal;
import com.meteor.extrabotany.common.items.relic.ItemInfluxWaver;
import com.meteor.extrabotany.common.items.relic.ItemStarWrath;
import com.meteor.extrabotany.common.items.relic.ItemTrueShadowKatana;
import com.meteor.extrabotany.common.items.relic.ItemTrueTerrablade;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Difficulty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.client.core.handler.BossBarHandler;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.helper.MathHelper;
import vazkii.botania.common.helper.VecHelper;
import vazkii.botania.common.lib.BotaniaTags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EntityEGO extends Mob implements IEntityAdditionalSpawnData {

    public static final float ARENA_RANGE = 12F;
    public static final int ARENA_HEIGHT = 5;

    public static final float MAX_HP = 600F;

    private static final String TAG_INVUL_TIME = "invulTime";
    private static final String TAG_SOURCE_X = "sourceX";
    private static final String TAG_SOURCE_Y = "sourceY";
    private static final String TAG_SOURCE_Z = "sourcesZ";
    private static final String TAG_PLAYER_COUNT = "playerCount";
    private static final String TAG_STAGE = "stage";
    private static final String TAG_WEAPONTYPE = "weapontype";
    private static final TagKey<Block> BLACKLIST = BotaniaTags.Blocks.GAIA_BREAK_BLACKLIST;

    private static final EntityDataAccessor<Integer> INVUL_TIME = SynchedEntityData.defineId(EntityEGO.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STAGE = SynchedEntityData.defineId(EntityEGO.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WEAPON_TYPE = SynchedEntityData.defineId(EntityEGO.class, EntityDataSerializers.INT);

    private static final List<BlockPos> PYLON_LOCATIONS = ImmutableList.of(
            new BlockPos(4, 1, 4),
            new BlockPos(4, 1, -4),
            new BlockPos(-4, 1, 4),
            new BlockPos(-4, 1, -4)
    );

    private static final List<ResourceLocation> CHEATY_BLOCKS = Arrays.asList(
            new ResourceLocation("openblocks", "beartrap"),
            new ResourceLocation("thaumictinkerer", "magnet")
    );

    private int changeWeaponDelay = 0;
    private int attackDelay = 0;
    private float damageTaken = 0;
    private int tpDelay = 0;
    private int playerCount = 0;
    private BlockPos source = BlockPos.ZERO;
    private final List<UUID> playersWhoAttacked = new ArrayList<>();
    private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(ModEntities.EGO.get().getDescription(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS).setCreateWorldFog(true);
    private UUID bossInfoUUID = bossInfo.getId();
    public Player trueKiller = null;
    private int MAX_WAVE = 6;
    private int wave = 0;
    private int tpTimes = 0;
    private Integer[] waves = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7};

    public EntityEGO(EntityType<EntityEGO> type, Level world) {
        super(type, world);
        xpReward = 825;
    }

    public static boolean spawn(Player player, ItemStack stack, Level world, BlockPos pos) {
        //initial checks
        if (!(world.getBlockEntity(pos) instanceof BeaconBlockEntity) ||
                !isTruePlayer(player) ||
                countEGOAround(world, pos) > 0) {
            return false;
        }

        if(!checkInventory(player)){
            if (!world.isClientSide) {
                player.sendSystemMessage(Component.translatable("extrabotanymisc.inventoryUnfeasible").withStyle(ChatFormatting.RED));
            }
            return false;
        }

        //check difficulty
        if (world.getDifficulty() == Difficulty.PEACEFUL) {
            if (!world.isClientSide) {
                player.sendSystemMessage(Component.translatable("botaniamisc.peacefulNoob").withStyle(ChatFormatting.RED));
            }
            return false;
        }

        //check pylons
        List<BlockPos> invalidPylonBlocks = checkPylons(world, pos);
        if (!invalidPylonBlocks.isEmpty()) {
            if (world.isClientSide) {
                warnInvalidBlocks(world, invalidPylonBlocks);
            } else {
                player.sendSystemMessage(Component.translatable("botaniamisc.needsCatalysts").withStyle(ChatFormatting.RED));
            }

            return false;
        }

        //check arena shape
        List<BlockPos> invalidArenaBlocks = checkArena(world, pos);
        if (!invalidArenaBlocks.isEmpty()) {
            if (world.isClientSide) {
                warnInvalidBlocks(world, invalidArenaBlocks);
            } else {
                // TODO: 1.16 sent a Botania ARENA_INDICATOR PacketBotaniaEffect here. The 1.20.1 equivalent is
                // vazkii.botania.network.clientbound.BotaniaEffectPacket (EffectType.ARENA_INDICATOR), but the
                // packet-sending path is unported, so it is omitted for now.
                player.sendSystemMessage(Component.translatable("botaniamisc.badArena").withStyle(ChatFormatting.RED));
            }

            return false;
        }

        if(stack.getItem() == ModItems.natureorb.get()){
            ItemNatureOrb orb = (ItemNatureOrb) stack.getItem();
            if(orb.getXP(stack) < 200000)
                return false;
        }

        //all checks ok, spawn the boss
        if (!world.isClientSide) {

            if(stack.getItem() == ModItems.natureorb.get()){
                ItemNatureOrb orb = (ItemNatureOrb) stack.getItem();
                orb.setXP(stack, orb.getXP(stack) - 200000);
            }else
                stack.shrink(1);

            EntityEGO e = ModEntities.EGO.get().create(world);
            e.setPos(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5);
            e.source = pos;
            e.setWeaponType(0);
            e.setCustomName(player.getDisplayName());

            int playerCount = e.getPlayersAround().size();
            e.playerCount = playerCount;
            e.setInvulTime(0);
            e.getAttribute(Attributes.ARMOR).setBaseValue(20);
            e.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MAX_HP * playerCount);
            e.playSound(SoundEvents.ENDER_DRAGON_GROWL, 10F, 0.1F);
            e.finalizeSpawn((ServerLevel) world, world.getCurrentDifficultyAt(e.blockPosition()), MobSpawnType.EVENT, null, null);
            world.addFreshEntity(e);
        }

        return true;
    }

    private static List<BlockPos> checkPylons(Level world, BlockPos beaconPos) {
        List<BlockPos> invalidPylonBlocks = new ArrayList<>();

        for (BlockPos coords : PYLON_LOCATIONS) {
            BlockPos pos_ = beaconPos.offset(coords);

            BlockState state = world.getBlockState(pos_);
            if (state.getBlock() != BotaniaBlocks.gaiaPylon) {
                invalidPylonBlocks.add(pos_);
            }
        }

        return invalidPylonBlocks;
    }

    private static List<BlockPos> checkArena(Level world, BlockPos beaconPos) {
        List<BlockPos> trippedPositions = new ArrayList<>();
        int range = (int) Math.ceil(ARENA_RANGE);
        BlockPos pos;

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                if (Math.abs(x) == 4 && Math.abs(z) == 4 || MathHelper.pointDistancePlane(x, z, 0, 0) > ARENA_RANGE) {
                    continue; // Ignore pylons and out of circle
                }

                boolean hasFloor = false;

                for (int y = -2; y <= ARENA_HEIGHT; y++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue; //the beacon
                    }

                    pos = beaconPos.offset(x, y, z);

                    BlockState state = world.getBlockState(pos);

                    boolean allowBlockHere = y < 0;
                    boolean isBlockHere = !state.getCollisionShape(world, pos).isEmpty();

                    if (allowBlockHere && isBlockHere) //floor is here! good
                    {
                        hasFloor = true;
                    }

                    if (y == 0 && !hasFloor) //column is entirely missing floor
                    {
                        trippedPositions.add(pos.below());
                    }

                    if (!allowBlockHere && isBlockHere && !state.is(BLACKLIST)) //ceiling is obstructed in this column
                    {
                        trippedPositions.add(pos);
                    }
                }
            }
        }

        return trippedPositions;
    }

    private static void warnInvalidBlocks(Level world, Iterable<BlockPos> invalidPositions) {
        WispParticleData data = WispParticleData.wisp(0.5F, 1F, 0.2F, 0.2F, 8F, false);
        for (BlockPos pos_ : invalidPositions) {
            world.addParticle(data, pos_.getX() + 0.5, pos_.getY() + 0.5, pos_.getZ() + 0.5, 0, 0, 0);
        }
    }

    public ItemStack getWeapon(){
        switch (getWeaponType()){
            case 0:
                return new ItemStack(ModItems.trueshadowkatana.get());
            case 1:
                return new ItemStack(ModItems.trueterrablade.get());
            case 2:
                return new ItemStack(ModItems.influxwaver.get());
            case 3:
                return new ItemStack(ModItems.starwrath.get());
            case 4:
                return new ItemStack(ModItems.firstfractal.get());
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, ARENA_RANGE * 1.5F));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(INVUL_TIME, 0);
        entityData.define(STAGE, 0);
        entityData.define(WEAPON_TYPE, 0);
    }

    public int getInvulTime() {
        return entityData.get(INVUL_TIME);
    }

    public BlockPos getSource() {
        return source;
    }

    public void setInvulTime(int time) {
        entityData.set(INVUL_TIME, time);
    }

    public int getStage() {
        return entityData.get(STAGE);
    }

    public void setStage(int time) {
        entityData.set(STAGE, time);
    }

    public int getWeaponType() {
        return entityData.get(WEAPON_TYPE);
    }

    public void setWeaponType(int time) {
        entityData.set(WEAPON_TYPE, time);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag cmp) {
        super.addAdditionalSaveData(cmp);
        cmp.putInt(TAG_INVUL_TIME, getInvulTime());

        cmp.putInt(TAG_SOURCE_X, source.getX());
        cmp.putInt(TAG_SOURCE_Y, source.getY());
        cmp.putInt(TAG_SOURCE_Z, source.getZ());

        cmp.putInt(TAG_PLAYER_COUNT, playerCount);
        cmp.putInt(TAG_STAGE, getStage());
        cmp.putInt(TAG_WEAPONTYPE, getWeaponType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        super.readAdditionalSaveData(cmp);
        setInvulTime(cmp.getInt(TAG_INVUL_TIME));

        int x = cmp.getInt(TAG_SOURCE_X);
        int y = cmp.getInt(TAG_SOURCE_Y);
        int z = cmp.getInt(TAG_SOURCE_Z);
        source = new BlockPos(x, y, z);

        if (cmp.contains(TAG_PLAYER_COUNT)) {
            playerCount = cmp.getInt(TAG_PLAYER_COUNT);
        } else {
            playerCount = 1;
        }

        setStage(cmp.getInt(TAG_STAGE));
        setWeaponType(cmp.getInt(TAG_WEAPONTYPE));

        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        Entity e = source.getEntity();
        if (e instanceof Player && isTruePlayer(e) && getInvulTime() == 0) {
            Player player = (Player) e;

            if (!playersWhoAttacked.contains(player.getUUID())) {
                playersWhoAttacked.add(player.getUUID());
            }

            int cap = 25;
            float dmg = Math.min(cap, amount);
            damageTaken+=dmg;

            if(damageTaken >= 50){
                if(tryAttack()) {
                    damageTaken = 0;
                    teleportRandomly();
                }
            }
            return super.hurt(source, dmg);
        }

        return false;
    }

    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    public static boolean isTruePlayer(Entity e) {
        if (!(e instanceof Player)) {
            return false;
        }

        Player player = (Player) e;

        String name = player.getName().getString();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }

    @Override
    public void die(@Nonnull DamageSource source) {
        super.die(source);
        LivingEntity entitylivingbase = getLastHurtByMob();

        playSound(SoundEvents.GENERIC_EXPLODE, 20F, (1F + (level().random.nextFloat() - level().random.nextFloat()) * 0.2F) * 0.7F);
        level().addParticle(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 1D, 0D, 0D);

        for (EntityEGOLandmine landmine : level().getEntitiesOfClass(EntityEGOLandmine.class, getArenaBB(getSource()))) {
            landmine.discard();
        }

        for (EntityEGOMinion minion : level().getEntitiesOfClass(EntityEGOMinion.class, getArenaBB(getSource()))) {
            minion.discard();
        }
    }

    @Override
    public boolean removeWhenFarAway(double dist) {
        return false;
    }

    @Override
    protected void dropFromLootTable(@Nonnull DamageSource source, boolean wasRecentlyHit) {
        // Save true killer, they get extra loot
        if (wasRecentlyHit && source.getEntity() instanceof Player) {
            trueKiller = (Player) source.getEntity();
        }

        // Generate loot table for every single attacking player
        for (UUID u : playersWhoAttacked) {
            Player player = level().getPlayerByUUID(u);
            if (player == null) {
                continue;
            }

            LivingEntity saveLastAttacker = getLastHurtByMob();
            Vec3 savePos = position();

            setLastHurtByMob(player); // Fake attacking player as the killer
            // Spoof pos so drops spawn at the player
            setPos(player.getX(), player.getY(), player.getZ());
            super.dropFromLootTable(player.damageSources().playerAttack(player), wasRecentlyHit);
            setPos(savePos.x, savePos.y, savePos.z);
            setLastHurtByMob(saveLastAttacker);
        }

        trueKiller = null;
    }

    public List<Player> getPlayersAround() {
        return level().getEntitiesOfClass(Player.class, getArenaBB(source), player -> isTruePlayer(player) && !player.isSpectator());
    }

    private static int countEGOAround(Level world, BlockPos source) {
        List<EntityEGO> l = world.getEntitiesOfClass(EntityEGO.class, getArenaBB(source));
        return l.size();
    }

    @Nonnull
    private static AABB getArenaBB(@Nonnull BlockPos source) {
        double range = ARENA_RANGE + 3D;
        return new AABB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range);
    }

    private static int countEGOMinionAround(Level world, BlockPos source) {
        List<EntityEGOMinion> l = world.getEntitiesOfClass(EntityEGOMinion.class, getArenaBB(source));
        return l.size();
    }

    private void smashBlocksAround(int centerX, int centerY, int centerZ, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius + 1; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int x = centerX + dx;
                    int y = centerY + dy;
                    int z = centerZ + dz;

                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level().getBlockState(pos);
                    Block block = state.getBlock();

                    if (state.getDestroySpeed(level(), pos) == -1) {
                        continue;
                    }

                    if (CHEATY_BLOCKS.contains(BuiltInRegistries.BLOCK.getKey(block))) {
                        level().destroyBlock(pos, true);
                    } else {
                        //don't break blacklisted blocks
                        if (block.builtInRegistryHolder().is(BLACKLIST)) {
                            continue;
                        }
                        //don't break the floor
                        if (y < source.getY()) {
                            continue;
                        }
                        //don't break blocks in pylon columns
                        if (Math.abs(source.getX() - x) == 4 && Math.abs(source.getZ() - z) == 4) {
                            continue;
                        }

                        level().destroyBlock(pos, true);
                    }
                }
            }
        }
    }

    private void clearPotions(Player player) {
        List<MobEffect> potionsToRemove = player.getActiveEffects().stream()
                .filter(effect -> effect.getDuration() < 160 && effect.isAmbient() && effect.getEffect().getCategory() != MobEffectCategory.HARMFUL)
                .map(MobEffectInstance::getEffect)
                .distinct()
                .collect(Collectors.toList());

        potionsToRemove.forEach(potion -> {
            player.removeEffect(potion);
            ((ServerLevel) level()).getChunkSource().broadcastAndSend(player,
                    new ClientboundRemoveMobEffectPacket(player.getId(), potion));
        });
    }

    private void keepInsideArena(Player player) {
        if (MathHelper.pointDistanceSpace(player.getX(), player.getY(), player.getZ(), source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5) >= ARENA_RANGE) {
            Vec3 sourceVector = new Vec3(source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5);
            Vec3 playerVector = VecHelper.fromEntityCenter(player);
            Vec3 motion = sourceVector.subtract(playerVector).normalize();

            player.setDeltaMovement(motion.x, 0.2, motion.z);
            player.hasImpulse = true;
        }
    }

    private void particles() {
        for (int i = 0; i < 360; i += 8) {
            float r = 0.6F;
            float g = 0F;
            float b = 0.2F;
            float m = 0.15F;
            float mv = 0.35F;

            float rad = i * (float) Math.PI / 180F;
            double x = source.getX() + 0.5 - Math.cos(rad) * ARENA_RANGE;
            double y = source.getY() + 0.5;
            double z = source.getZ() + 0.5 - Math.sin(rad) * ARENA_RANGE;

            WispParticleData data = WispParticleData.wisp(0.5F, r, g, b);
            level().addParticle(data, x, y, z, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * mv, (float) (Math.random() - 0.5F) * m);
        }

        if (getInvulTime() >= 20) {
            Vec3 pos = VecHelper.fromEntityCenter(this).subtract(new Vec3(0, 0.2, 0));
            for (BlockPos arr : PYLON_LOCATIONS) {
                Vec3 pylonPos = new Vec3(source.getX() + arr.getX(), source.getY() + arr.getY(), source.getZ() + arr.getZ());
                double worldTime = tickCount;
                worldTime /= 5;

                float rad = 0.75F + (float) Math.random() * 0.05F;
                double xp = pylonPos.x + 0.5 + Math.cos(worldTime) * rad;
                double zp = pylonPos.z + 0.5 + Math.sin(worldTime) * rad;

                Vec3 partPos = new Vec3(xp, pylonPos.y, zp);
                Vec3 mot = pos.subtract(partPos).scale(0.04);

                float r = 0.7F + (float) Math.random() * 0.3F;
                float g = (float) Math.random() * 0.3F;
                float b = 0.7F + (float) Math.random() * 0.3F;

                WispParticleData data = WispParticleData.wisp(0.25F + (float) Math.random() * 0.1F, r, g, b, 1);
                level().addParticle(data, partPos.x, partPos.y, partPos.z, 0, -(-0.075F - (float) Math.random() * 0.015F), 0);
                WispParticleData data1 = WispParticleData.wisp(0.4F, r, g, b);
                level().addParticle(data1, partPos.x, partPos.y, partPos.z, (float) mot.x, (float) mot.y, (float) mot.z);
            }
        }
    }

    public static boolean checkFeasibility(ItemStack stack){
        if(stack.isEmpty())
            return true;

        ResourceLocation reg = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if(reg == null)
            return true;
        String modid = reg.getNamespace();
        if(modid.contains("extrabotany") || modid.contains("botania") || modid.contains("minecraft")){
            return true;
        }
        return false;
    }

    public static boolean checkInventory(Player player){
        if (player.isCreative() || ConfigHandler.COMMON.disableDisarm.get()) {
            return true;
        }
        for(int i = 0; i < player.getInventory().getMaxStackSize(); i++){
            final ItemStack stack = player.getInventory().getItem(i);
            if(!checkFeasibility(stack))
                return false;
        }
        return true;
    }

    public static void disarm(Player player){
        if (!ConfigHandler.COMMON.disableDisarm.get() && !player.isCreative()) {
            for(int i = 0; i < player.getInventory().getMaxStackSize(); i++){
                final ItemStack stack = player.getInventory().getItem(i);
                if(!checkFeasibility(stack)){
                    player.drop(stack, false);
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public void unlegalPlayercount(){
        if(getPlayersAround().size() > playerCount){
            for(Player player : getPlayersAround())
                if (!level().isClientSide) {
                    player.sendSystemMessage(Component.translatable("extrabotanymisc.unlegalPlayercount").withStyle(ChatFormatting.RED));
                }
            this.discard();
        }
    }

    public boolean tryAttack(){
        if(getPlayersAround().isEmpty())
            return false;

        Entity target = getPlayersAround().get(0);

        this.swing(InteractionHand.MAIN_HAND);
        if(!level().isClientSide) {
            switch (getWeaponType()) {
                case 0: {
                    ((ItemTrueShadowKatana) ModItems.trueshadowkatana.get()).attackEntity(this, target);
                    break;
                }
                case 1: {
                    ((ItemTrueTerrablade) ModItems.trueterrablade.get()).attackEntity(this, target);
                    break;
                }
                case 2: {
                    ((ItemInfluxWaver) ModItems.influxwaver.get()).attackEntity(this, target);
                    break;
                }
                case 3: {
                    ((ItemStarWrath) ModItems.starwrath.get()).attackEntity(this, target);
                    break;
                }
                case 4: {
                    ((ItemFirstFractal) ModItems.firstfractal.get()).attackEntity(this, target);
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        int invul = getInvulTime();

        List<Integer> WAVES = Arrays.asList(waves);

        if (level().isClientSide) {
            particles();
            Player player = Minecraft.getInstance().player;
            if (player != null && getPlayersAround().contains(player)) {
                player.getAbilities().flying &= player.getAbilities().instabuild;
            }
            return;
        }

        bossInfo.setProgress(getHealth() / getMaxHealth());

        if (isPassenger()) {
            stopRiding();
        }

        if (level().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }

        if(!level().isClientSide)
            for(Player player : getPlayersAround())
                disarm(player);

        unlegalPlayercount();

        if(invul > 0){
            setInvulTime(invul - 1);
            if(getStage() == 1){
                if(invul >= 20){
                    setDeltaMovement(getDeltaMovement().x, 0, getDeltaMovement().z);
                    if(invul % 60 == 0)
                        if(wave < MAX_WAVE){
                            EntityEGOLandmine.spawnLandmine(wave, level(), source, this);
                            wave++;
                        }
                    return;
                }

            }

            if(getStage() == 2){
                if(invul >= 20){
                    setDeltaMovement(getDeltaMovement().x, 0, getDeltaMovement().z);
                    setHealth(getHealth() + 0.25F);
                    if(countEGOMinionAround(level(), source) == 0)
                        setInvulTime(0);
                    return;
                }
            }
        }

        if(attackDelay > 0){
            attackDelay--;
        }else{
            if(tryAttack()){
                int delay = (int) (80 - getStage() * 15 + 15 * Math.random());
                attackDelay = delay;
            }
        }

        smashBlocksAround(Mth.floor(getX()), Mth.floor(getY()), Mth.floor(getZ()), 1);

        List<Player> players = getPlayersAround();

        if (players.isEmpty() && !level().players().isEmpty()) {
            this.discard();
        } else {
            for (Player player : players) {
                //also see SleepingHandler
                if (player.isSleeping()) {
                    player.stopSleeping();
                }

                clearPotions(player);
                keepInsideArena(player);
                player.getAbilities().flying &= player.getAbilities().instabuild;
            }
        }

        if (!isAlive() || players.isEmpty()) {
            return;
        }

        if(changeWeaponDelay > 0){
            changeWeaponDelay--;
        }else{
            changeWeaponDelay = 100;
            int weaponType = getStage() == 0 ? level().random.nextInt(2) : getStage() == 1 ? level().random.nextInt(4) : 4;
            setWeaponType(weaponType);
        }

        if(tpDelay > 0){
            tpDelay--;
        }else{
            if(tryAttack()) {
                teleportRandomly();
                tpTimes++;
                tpDelay = 100 - getStage() * 10;
            }
        }

        if(getStage() >= 1 && tpTimes % 7 == 0){
            EntityEGOLandmine.spawnLandmine(level().random.nextInt(8), level(), source, this);
            tpTimes++;
        }

        if(getStage() == 0 && getHealth() < 0.75F * getMaxHealth()) {
            setStage(1);
            setInvulTime(460);
            Collections.shuffle(WAVES);
            this.teleportTo(source.getX()+0.5, source.getY()+3, source.getZ()+0.5);
        }
        if(getStage() == 1 && getHealth() < 0.25F * getMaxHealth()){
            setStage(2);
            setInvulTime(600);
            setWeaponType(4);
            EntityEGOMinion.spawn(this, level(), getSource(), 60F * playerCount);
            this.teleportTo(source.getX()+0.5, source.getY()+3, source.getZ()+0.5);
        }

    }

    @Override
    public void setHealth(float f){
        super.setHealth(f);
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && getInvulTime() == 0;
    }

    private void teleportRandomly() {
        //choose a location to teleport to
        double oldX = getX(), oldY = getY(), oldZ = getZ();
        double newX, newY = source.getY(), newZ;
        int tries = 0;

        do {
            newX = source.getX() + (random.nextDouble() - .5) * ARENA_RANGE;
            newZ = source.getZ() + (random.nextDouble() - .5) * ARENA_RANGE;
            tries++;
            //ensure it's inside the arena ring, and not just its bounding square
        } while (tries < 50 && MathHelper.pointDistanceSpace(newX, newY, newZ, source.getX(), source.getY(), source.getZ()) > 12);

        if (tries == 50) {
            //failsafe: teleport to the beacon
            newX = source.getX() + .5;
            newY = source.getY() + 1.6;
            newZ = source.getZ() + .5;
        }

        //for low-floor arenas, ensure landing on the ground
        BlockPos tentativeFloorPos = BlockPos.containing(newX, newY - 1, newZ);
        if (level().getBlockState(tentativeFloorPos).getCollisionShape(level(), tentativeFloorPos).isEmpty()) {
            newY--;
        }

        //teleport there
        this.teleportTo(newX, newY, newZ);

        //play sound
        level().playSound(null, oldX, oldY, oldZ, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
        this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);

        RandomSource random = getRandom();

        //spawn particles along the path
        int particleCount = 128;
        for (int i = 0; i < particleCount; ++i) {
            double progress = i / (double) (particleCount - 1);
            float vx = (random.nextFloat() - 0.5F) * 0.2F;
            float vy = (random.nextFloat() - 0.5F) * 0.2F;
            float vz = (random.nextFloat() - 0.5F) * 0.2F;
            double px = oldX + (newX - oldX) * progress + (random.nextDouble() - 0.5D) * getBbWidth() * 2.0D;
            double py = oldY + (newY - oldY) * progress + random.nextDouble() * getBbHeight();
            double pz = oldZ + (newZ - oldZ) * progress + (random.nextDouble() - 0.5D) * getBbWidth() * 2.0D;
            level().addParticle(ParticleTypes.PORTAL, px, py, pz, vx, vy, vz);
        }

        Vec3 oldPosVec = new Vec3(oldX, oldY + getBbHeight() / 2, oldZ);
        Vec3 newPosVec = new Vec3(newX, newY + getBbHeight() / 2, newZ);

        if (oldPosVec.distanceToSqr(newPosVec) > 1) {
            //damage players in the path of the teleport
            for (Player player : getPlayersAround()) {
                boolean hit = player.getBoundingBox().inflate(0.25).clip(oldPosVec, newPosVec) != null;
                if (hit) {
                    player.hurt(player.damageSources().mobAttack(this), 6);
                }
            }

            //break blocks in the path of the teleport
            int breakSteps = (int) oldPosVec.distanceTo(newPosVec);
            if (breakSteps >= 2) {
                for (int i = 0; i < breakSteps; i++) {
                    float progress = i / (float) (breakSteps - 1);
                    int breakX = Mth.floor(oldX + (newX - oldX) * progress);
                    int breakY = Mth.floor(oldY + (newY - oldY) * progress);
                    int breakZ = Mth.floor(oldZ + (newZ - oldZ) * progress);

                    smashBlocksAround(breakX, breakY, breakZ, 1);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getBossBarTexture() {
        // TODO: BossBarHandler.defaultBossBar was removed in 1.20.1; returning a placeholder until a custom
        // boss bar texture hook is set up.
        return ResourceLocation.fromNamespaceAndPath("extrabotany", "textures/gui/bossbar_ego.png");
    }

    @OnlyIn(Dist.CLIENT)
    public Rect2i getBossBarTextureRect() {
        return new Rect2i(0, 0, 185, 15);
    }

    @OnlyIn(Dist.CLIENT)
    public Rect2i getBossBarHPTextureRect() {
        Rect2i barRect = getBossBarTextureRect();
        return new Rect2i(0, barRect.getY() + barRect.getHeight(), 181, 7);
    }

    @OnlyIn(Dist.CLIENT)
    public int bossBarRenderCallback(PoseStack ms, int x, int y) {
        // TODO: this custom Botania boss bar callback is not wired up in 1.20.1 (BossBarHandler API changed);
        // kept for reference only.
        return 5;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossInfo.removePlayer(player);
    }

    public UUID getBossInfoUuid() {
        return bossInfoUUID;
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(playerCount);
        buffer.writeLong(source.asLong());
        buffer.writeLong(bossInfoUUID.getMostSignificantBits());
        buffer.writeLong(bossInfoUUID.getLeastSignificantBits());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void readSpawnData(FriendlyByteBuf additionalData) {
        playerCount = additionalData.readInt();
        source = BlockPos.of(additionalData.readLong());
        long msb = additionalData.readLong();
        long lsb = additionalData.readLong();
        bossInfoUUID = new UUID(msb, lsb);
        Minecraft.getInstance().getSoundManager().play(new EntityEGO.EgoMusic(this));
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    private static class EgoMusic extends AbstractTickableSoundInstance {
        private final EntityEGO guardian;

        public EgoMusic(EntityEGO guardian) {
            super(ModSounds.swordland.get(), SoundSource.RECORDS, guardian.getRandom());
            this.guardian = guardian;
            this.x = guardian.getSource().getX();
            this.y = guardian.getSource().getY();
            this.z = guardian.getSource().getZ();
            // this.repeat = true; TODO restore once LWJGL3/vanilla bug fixed?
        }

        @Override
        public void tick() {
            if (!guardian.isAlive()) {
                this.stop();
            }
        }
    }

}
