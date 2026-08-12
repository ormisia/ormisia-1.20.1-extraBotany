package com.meteor.extrabotany.common.entities.mountable;

import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.entities.EntityKeyOfTruth;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.common.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityMotor extends EntityMountable {

    private static final String TAG_CYCLONETICKS = "cycloneticks";
    private static final String TAG_TECTONICENERGY = "tectonicenergy";
    private static final String TAG_OWNERUUID = "owneruuid";

    private static final EntityDataAccessor<Integer> CYCLONE_TICKS = SynchedEntityData.defineId(EntityMotor.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TECTONIC_ENERGY = SynchedEntityData.defineId(EntityMotor.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(EntityMotor.class,
            EntityDataSerializers.OPTIONAL_UUID);

    public int ridingTicks = 0;

    public EntityMotor(EntityType<? extends EntityMotor> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityMotor(Level worldIn) {
        super(ModEntities.MOTOR.get(), worldIn);
    }

    public EntityMotor(Level worldIn, double x, double y, double z) {
        super(ModEntities.MOTOR.get(), worldIn);
        this.setPos(x, y, z);
        this.setDeltaMovement(0, 0, 0);
        this.xOld = x;
        this.yOld = y;
        this.zOld = z;
    }

    @Override
    public void push(Entity entityIn) {
        super.push(entityIn);
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof Player player) {
            Entity passenger = getPassengers().size() > 1 ? getPassengers().get(1) : null;
            if(entityIn != player && entityIn != passenger) {
                HerrscherHandler.iceAttack(entityIn, player, 7F);
                if(!(entityIn instanceof Player))
                    player.setLastHurtMob(entityIn);
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TECTONIC_ENERGY, 0);
        this.entityData.define(CYCLONE_TICKS, 0);
        this.entityData.define(OWNER_UUID, Optional.empty());
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.225D;
    }

    @Override
    public Item getItemBoat() {
        return ModItems.motor.get();
    }

    @Override
    public ItemStack getItemStack(){
        ItemStack motor = new ItemStack(getItemBoat());
        ItemNBTHelper.setString(motor, "soulbindUUID", getOwnerUUID().toString());
        return getMountable() ? ItemStack.EMPTY : motor;
    }

    @Override
    public void tick() {
        super.tick();
        Player player = null;
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof Player) {
            player = (Player) this.getPassengers().get(0);

            if (this.getCycloneTicks() > 0) {
                setPitch(-5);
                if (this.getCycloneTicks() > 6)
                    setRotation(-5);
            }

            float speed = 1.65F;
            double mx = (double) (-Mth.sin(this.getYRot() / 180.0F * (float) Math.PI)
                    * Mth.cos(this.getXRot() / 180.0F * (float) Math.PI) * speed);
            double mz = (double) (Mth.cos(this.getYRot() / 180.0F * (float) Math.PI)
                    * Mth.cos(this.getXRot() / 180.0F * (float) Math.PI) * speed);
            double my = this.getDeltaMovement().y;

            if(this.forwardInputDown){
                this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), -mx, 0.15F, -mz);
            }

            if (this.rightInputDown) {
                setRotation(-5);
            } else if (this.leftInputDown) {
                setRotation(5);
            } else {
                setRotation(0);
                setPitch(0);
            }

            ridingTicks++;

            if (this.forwardInputDown && this.horizontalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y+0.08f, this.getDeltaMovement().z);
            }

            if (ridingTicks >= 120) {

                if(this.getTectonicEnergy() < 800)
                    this.setTectonicEnergy(Math.min(800, this.getTectonicEnergy() + 2));

                if (this.spaceInputDown){
                    if(this.getTectonicEnergy() >= 200) {
                        this.setTectonicEnergy(Math.max(0, this.getTectonicEnergy() - 6));
                        this.setDeltaMovement(mx, my, mz);
                        if (this.level().getBlockState(BlockPos.containing(getX(), getY(), getZ()).offset((int)mx,(int)my,(int)mz)).isAir() == false)
                            this.setPos(this.getX(), this.getY() + 1F, this.getZ());
                    }else
                        this.setTectonicEnergy(0);
                }

                for (LivingEntity living : getEntitiesAround()) {
                    if (living == player)
                        continue;
                    if(getPassengers().size() > 1 && living == getPassengers().get(1))
                        continue;
                    if (!living.hasLineOfSight(player))
                        continue;
                    if ((living instanceof Enemy || player.getLastHurtMob() == living) && tickCount % 15 == 0
                    ) {
                        EntityKeyOfTruth key = new EntityKeyOfTruth(level(), player);
                        key.setPos(player.getX() - Math.random() * 2F + 1F, player.getY() + 2.2F,
                                player.getZ() - Math.random() * 2F + 1F);
                        if (Math.random() < 0.5F)
                            key.setPos(living.getX() - Math.random() * 2F + 1F, living.getY() + 2.2F,
                                    living.getZ() - Math.random() * 2F + 1F);
                        key.setYRot(player.getYRot());
                        key.setPitch(-player.getXRot());
                        key.setRotation(Mth.wrapDegrees(-player.getYRot() + 180));
                        if (!level().isClientSide) {
                            level().addFreshEntity(key);
                        }
                        break;
                    }
                }

                if(this.getCycloneTicks() == 0 && this.ctrlInputDown && this.getTectonicEnergy() >= 400){
                    this.setCycloneTicks(15);
                    this.setTectonicEnergy(this.getTectonicEnergy() - 400);
                    level().playSound(null, getX(), getY(), getZ(), ModSounds.cyclone.get(), SoundSource.PLAYERS, 1.2F, 1F);
                }

                if (this.getCycloneTicks() > 6) {
                    player.setYRot(player.getYRot() - 1);
                    player.setYHeadRot(player.getYHeadRot() - 1);
                    // TODO: Boat.applyYawToEntity() no longer exists in 1.20.1; the rider yaw is
                    // synchronised by positionRider()/clampRotation() in the base class.
                }

                if (player.getHealth() < player.getMaxHealth() * 0.5F)
                    player.heal(0.5F);

                if (this.getCycloneTicks() == 12 || this.getCycloneTicks() == 6)
                    for (LivingEntity living : getEntitiesAround(BlockPos.containing(player.getX(), player.getY(), player.getZ()), 4F, player.level())) {
                        if (living == player) {
                            continue;
                        }
                        if(getPassengers().size() > 1 && living == getPassengers().get(1))
                            continue;
                        HerrscherHandler.iceAttack(living, player,4.5F);
                        player.setLastHurtMob(living);
                    }

                if (getCycloneTicks() > 0) {
                    setCycloneTicks(getCycloneTicks() - 1);
                    // TODO: Boat.deltaRotation is private in 1.20.1; the cyclone spin cannot be
                    // applied directly anymore.
                }

            }

        }else
            ridingTicks = 0;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.level().isClientSide && !this.isRemoved()) {
            if (source.getDirectEntity() != null && this.hasPassenger(source.getDirectEntity())) {
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

    public List<LivingEntity> getEntitiesAround() {
        return getEntitiesAround(BlockPos.containing(getX(), getY(), getZ()), 12F, this.level());
    }

    public static List<LivingEntity> getEntitiesAround(BlockPos source, float range, Level world) {
        return world.getEntitiesOfClass(LivingEntity.class,
                new AABB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    // Note: Boat.controlBoat() is private in 1.20.1 so this does not override anything; kept as a
    // reference implementation (movement is driven from tick()).
    public void controlBoat() {
        // TODO: Boat.controlBoat() is private in 1.20.1 and no longer overridable. Movement input is
        // handled in tick() via the input flags captured in EntityMountable.setInput().
        if (this.isVehicle()) {
            float f = 0.0F;
            if (this.leftInputDown) {
                this.setYRot(this.getYRot() - 1F);
            }

            if (this.rightInputDown) {
                this.setYRot(this.getYRot() + 1F);
            }

            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005F;
            }

            if (this.forwardInputDown) {
                f += 0.05F * 1.25F;
            }

            if (this.backInputDown) {
                f -= 0.006F;
            }

            this.setDeltaMovement(this.getDeltaMovement().add((double)(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f), 0.0D, (double)(Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f)));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        } else {
            if (!this.level().isClientSide) {
                if(player.startRiding(this))
                    level().playSound(null, getX(), getY(), getZ(), ModSounds.rideon.get(), SoundSource.PLAYERS, 4F, 1F);
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setTectonicEnergy(compound.getInt(TAG_TECTONICENERGY));
        setCycloneTicks(compound.getInt(TAG_CYCLONETICKS));
        setOwnerUUID(compound.getUUID(TAG_OWNERUUID));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(TAG_TECTONICENERGY, getTectonicEnergy());
        compound.putInt(TAG_CYCLONETICKS, getCycloneTicks());
        if (this.getOwnerUUID() != null) {
            compound.putUUID(TAG_OWNERUUID, this.getOwnerUUID());
        }
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setCycloneTicks(int i) {
        this.entityData.set(CYCLONE_TICKS, i);
    }

    public int getCycloneTicks() {
        return this.entityData.get(CYCLONE_TICKS);
    }

    public void setTectonicEnergy(int i) {
        this.entityData.set(TECTONIC_ENERGY, i);
    }

    public int getTectonicEnergy() {
        return this.entityData.get(TECTONIC_ENERGY);
    }

}
