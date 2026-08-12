package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.client.fx.WispParticleData;

public class SubTileReikarLily extends GeneratingFlowerBlockEntity {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final String TAG_COOLDOWN = "cooldown";
    private static final String TAG_CD = "cd";
    private static final int RANGE = 5;
    int burnTime = 0, cooldown = 0, cd = 0;

    public SubTileReikarLily(BlockPos pos, BlockState state) {
        super(ModSubtiles.REIKARLILY.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if (getLevel().isRaining() && getLevel().canSeeSky(worldPosition) && cd == 0) {
            int baseY = 64;
            if (getLevel().random.nextInt((int) (4000 * baseY / worldPosition.getY())) == 1) {
                LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, getLevel());
                bolt.moveTo(getEffectivePos().getX(), getEffectivePos().getY(), getEffectivePos().getZ());
                if(!getLevel().isClientSide)
                    getLevel().addFreshEntity(bolt);
                cd += getCooldown();
                if (cooldown == 0) {
                    burnTime += 1500;
                    if (getMana() < getMaxMana())
                        addMana(getMaxMana());
                    cooldown = getCooldown();
                }
            }
        }

        for (LightningBolt bolt : getLevel().getEntitiesOfClass(LightningBolt.class,
                new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE),
                        getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
            if (!bolt.isRemoved()) {
                if (cooldown == 0) {
                    burnTime += 1500;
                    if (getMana() < getMaxMana())
                        addMana(getMaxMana());
                    cooldown = getCooldown();
                    bolt.discard();
                    break;
                }
            }
        }

        if (cooldown > 0) {
            cooldown--;
            for (int i = 0; i < 3; i++) {
                WispParticleData data = WispParticleData.wisp((float) Math.random() / 6, 0.1F, 0.1F, 0.1F, 1);
                getLevel().addParticle(data, getEffectivePos().getX() + 0.5 + Math.random() * 0.2 - 0.1, getEffectivePos().getY() + 0.5 + Math.random() * 0.2 - 0.1, getEffectivePos().getZ() + 0.5 + Math.random() * 0.2 - 0.1, 0, (float) Math.random() / 30, 0);
            }
        }
        if (cd > 0)
            cd--;
        if (burnTime > 0)
            burnTime--;

    }

    @Override
    public int getMaxMana() {
        return 12000;
    }

    @Override
    public int getColor() {
        return 0x0000CD;
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_BURN_TIME, burnTime);
        cmp.putInt(TAG_COOLDOWN, cooldown);
        cmp.putInt(TAG_CD, cd);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        burnTime = cmp.getInt(TAG_BURN_TIME);
        cooldown = cmp.getInt(TAG_COOLDOWN);
        cd = cmp.getInt(TAG_CD);
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

    public int getCooldown() {
        return 3600;
    }
}
