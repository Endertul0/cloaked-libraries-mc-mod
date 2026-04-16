package io.github.endertul.cloaked.entity.custom;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EncodedEntity extends PassiveEntity {
	private static final TrackedData<String> STORED_ENTITY = DataTracker.registerData(EncodedEntity.class, TrackedDataHandlerRegistry.STRING);
	private static final TrackedData<String> STORED_NBT_STRING = DataTracker.registerData(EncodedEntity.class, TrackedDataHandlerRegistry.STRING);
	
	public EncodedEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static DefaultAttributeContainer.Builder createEncodedEntityAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
	}
	
	@Override
	public void tickMovement() {
	}
	
	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (player.getStackInHand(hand).isOf(ModItems.DECLOAKER)) {
			Entity ent = this.convertSelf();
			
			Cloaked.LOGGER.info("@EncodedEntity:51 - Decloaking entity with type {}Entity!", ent.getType().getName().getString());
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}
	
	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(STORED_ENTITY, EntityType.SQUID.getTranslationKey());
		builder.add(STORED_NBT_STRING, new NbtCompound().toString());
	}
	
	public NbtCompound getStoredNbt() {
		try {
			return StringNbtReader.parse(this.dataTracker.get(STORED_NBT_STRING));
		} catch (CommandSyntaxException e) {
			Cloaked.LOGGER.error(e.getMessage());
		}
		
		return new NbtCompound();
	}
	
	public void setStoredNbt(NbtCompound nbt) {
		this.dataTracker.set(STORED_NBT_STRING, nbt.toString());
	}
	
	public EntityType<? extends Entity> getStoredEntity() {
		String thisType = this.dataTracker.get(STORED_ENTITY);
		for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
			if (entityType.create(getWorld()) instanceof MobEntity) {
				if (Objects.equals(entityType.getTranslationKey(), thisType)) {
					return entityType;
				}
			}
		}
		
		return EntityType.SQUID;
	}
	
	public void setStoredEntity(EntityType<?> eType) {
		this.dataTracker.set(STORED_ENTITY, eType.getTranslationKey());
	}
	
	@Nullable
	public <T extends Entity> T convertSelf() {
		if (this.isRemoved()) {
			Cloaked.LOGGER.error("@EncodedEntity:85 - CloakedEntity was removed during processing!");
			return null;
		} else {
			T entity = (T) this.getStoredEntity().create(this.getWorld());
			if (entity == null) {
				Cloaked.LOGGER.error("@EncodedEntity:90 - Could not spawn entity of type {}Entity!", this.getStoredEntity().getName().getString());
				return null;
			} else {
				NbtCompound storedNbt = getStoredNbt();
				entity.readNbt(storedNbt);
				
				entity.copyPositionAndRotation(this);
				if (this.hasCustomName()) {
					entity.setCustomName(this.getCustomName());
					entity.setCustomNameVisible(this.isCustomNameVisible());
				}
				
				entity.setInvulnerable(this.isInvulnerable());
				
				this.getWorld().spawnEntity(entity);
				if (this.hasVehicle()) {
					Entity riddenEntity = this.getVehicle();
					this.stopRiding();
					entity.startRiding(riddenEntity, true);
				}
				
				this.discard();
				Cloaked.LOGGER.info(
					"@EncodedEntity:93 - Success! (converted to {}Entity at {})",
					entity.getType().getName().getString(),
					entity.getBlockPos().toShortString()
				);
				return entity;
			}
		}
	}
	
	@Override
	public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}
	
	
	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		return ! damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY);
	}
	
	@Override
	public boolean isPersistent() {
		return true;
	}
}