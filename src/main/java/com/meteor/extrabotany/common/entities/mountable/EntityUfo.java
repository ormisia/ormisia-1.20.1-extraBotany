package com.meteor.extrabotany.common.entities.mountable;

import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityUfo extends EntityMountable {

    private static final String TAG_CATCHED_ID = "catched_id";

    private static final EntityDataAccessor<Integer> CATCHED_ID = SynchedEntityData.defineId(EntityUfo.class,
            EntityDataSerializers.INT);

    public EntityUfo(EntityType<? extends EntityUfo> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityUfo(Level worldIn) {
        super(ModEntities.UFO.get(), worldIn);
    }

    public EntityUfo(Level worldIn, double x, double y, double z) {
        super(ModEntities.UFO.get(), worldIn);
        this.setPos(x, y, z);
        this.setDeltaMovement(0, 0, 0);
        this.xOld = x;
        this.yOld = y;
        this.zOld = z;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CATCHED_ID, -1);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 1-0.395D;
    }

    @Override
    public Item getItemBoat() {
        return ModItems.cosmiccarkey.get();
    }

    @Override
    public void tick() {
        super.tick();
        Player player = null;
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof Player) {
            player = (Player) this.getPassengers().get(0);

            float speed = 0.75F;
            double mx = (double) (-Mth.sin(player.getYRot() / 180.0F * (float) Math.PI)
                    * Mth.cos(player.getXRot() / 180.0F * (float) Math.PI) * speed);
            double mz = (double) (Mth.cos(player.getYRot() / 180.0F * (float) Math.PI)
                    * Mth.cos(player.getXRot() / 180.0F * (float) Math.PI) * speed);
            double my = 0;

            Vec3 f0 = new Vec3(0, 0, 0);
            Vec3 vecf = new Vec3(mx, my, mz);
            Vec3 vecl = new Vec3(mx, my, mz).yRot((float) (Math.PI * 0.5D)).scale(0.75F);
            Vec3 vecr = new Vec3(mx, my, mz).yRot(-(float) (Math.PI * 0.5D)).scale(0.75F);
            Vec3 vecb = new Vec3(mx, my, mz).yRot((float) (Math.PI)).scale(0.6F);

            if(this.forwardInputDown) {
                f0 = f0.add(vecf);
            }
            if(this.leftInputDown) {
                f0 = f0.add(vecl);
            }
            if(this.rightInputDown) {
                f0 = f0.add(vecr);
            }
            if(this.backInputDown) {
                f0 = f0.add(vecb);
            }
            if(this.spaceInputDown)
                f0 = f0.add(0, 0.35D, 0);
            else if(this.ctrlInputDown)
                f0 = f0.add(0, -0.35D, 0);

            if(f0.length() != 0)
                this.setYRot(getRotationFromVector(player.getYRot(), f0.normalize()));

            this.setDeltaMovement(f0);

        }

        if(getCatchedID() != -1){
            Entity entity = level().getEntity(this.getCatchedID());
            if(entity == null || entity.isRemoved() || entity.distanceTo(this) >= 16F)
                setCatchedID(-1);
            else{
                entity.setDeltaMovement(new Vec3(getX() - entity.getX(), getY() - 2F - entity.getY(), getZ() - entity.getZ()).normalize().scale(0.75F));
                if(this.spaceInputDown) {
                    entity.setPos(entity.getX(), entity.getY() + 0.33D, entity.getZ());
                }
                else if(this.ctrlInputDown) {
                    entity.setPos(entity.getX(), entity.getY() - 0.37D, entity.getZ());
                }
                entity.fallDistance = 0;
            }
        }
    }

    public static float getRotationFromVector(float rot, Vec3 vec){
        double f2 = vec.z;
        double f3 = vec.x;
        double f12 = Math.asin(f3);
        double f13 = Math.acos(f2);
        double yawx = -(f13 / ((float)Math.PI / 180F));
        double yawz = -(f12 / ((float)Math.PI / 180F));
        return Mth.wrapDegrees(rot) >= 0 ? (float) -yawx : (float) yawx;
    }

    public List<LivingEntity> getEntitiesBelow() {
        return getEntitiesBelow(BlockPos.containing(getX(), getY(), getZ()), this.level());
    }

    public static List<LivingEntity> getEntitiesBelow(BlockPos source, Level world) {
        return world.getEntitiesOfClass(LivingEntity.class,
                new AABB(source.getX() + 2F, source.getY() - 0.5F, source.getZ() + 2F,
                        source.getX() -1.5F, source.getY() - 16F, source.getZ() -1.5F));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.level().isClientSide && !this.isRemoved()) {
            if (source.getEntity() != null && this.hasPassenger(source.getEntity())) {
                return false;
            } else {
                this.setHurtDir(-this.getHurtDir());
                this.setHurtTime(10);
                this.hasImpulse = true;
                boolean flag = source.getEntity() instanceof Player && ((Player)source.getEntity()).getAbilities().instabuild;
                if(source.getEntity() instanceof Player)
                    this.setDamage(this.getDamage() + amount * 10.0F);
                if (flag || this.getDamage() > 40.0F) {
                    if (!flag && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                        this.spawnAtLocation(this.getItemStack());
                    }
                    this.discard();
                }

                return true;
            }
        } else {
            return true;
        }
    }

    // Note: Boat.controlBoat() is private in 1.20.1 so this does not override anything; kept as a
    // reference (flight is driven from tick()).
    public void controlBoat() {
        // TODO: Boat.controlBoat() is private in 1.20.1 and no longer overridable. The UFO's flight
        // is driven entirely from tick().
    }

    // TODO: Boat.applyYawToEntity() was removed in 1.20.1; rider yaw synchronisation is now handled
    // by the base class (positionRider/clampRotation). The 1.16 implementation:
    //   this.setYBodyRot(entityToUpdate.getYRot());
    //   float f = Mth.wrapDegrees(this.getYRot() - entityToUpdate.getYRot());
    //   float f1 = Mth.clamp(f, -180.0F, 180.0F);
    //   this.yRotO += f1 - f;
    //   this.setYRot(this.getYRot() + f1 - f);
    //   this.setYHeadRot(this.getYRot());

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        } else {
            if (!this.level().isClientSide) {
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setCatchedID(compound.getInt(TAG_CATCHED_ID));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(TAG_CATCHED_ID, getCatchedID());
    }

    public void setCatchedID(int i) {
        this.entityData.set(CATCHED_ID, i);
    }

    public int getCatchedID() {
        return this.entityData.get(CATCHED_ID);
    }

}
