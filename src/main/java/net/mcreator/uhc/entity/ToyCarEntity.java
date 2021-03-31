
package net.mcreator.uhc.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ActionResultType;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.uhc.itemgroup.ToysItemGroup;
import net.mcreator.uhc.UhcModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@UhcModElements.ModElement.Tag
public class ToyCarEntity extends UhcModElements.ModElement {
	public static EntityType entity = null;
	public ToyCarEntity(UhcModElements instance) {
		super(instance, 10);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.5f, 0.25f))
						.build("toy_car").setRegistryName("toy_car");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -16764007, -6710887, new Item.Properties().group(ToysItemGroup.tab))
				.setRegistryName("toy_car_spawn_egg"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelcustom_model(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("uhc:textures/car2.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 20);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 0);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends CreatureEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
			setNoAI(false);
			enablePersistence();
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return false;
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source.getImmediateSource() instanceof ArrowEntity)
				return false;
			if (source.getImmediateSource() instanceof PotionEntity)
				return false;
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.CACTUS)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			if (source == DamageSource.LIGHTNING_BOLT)
				return false;
			if (source.isExplosion())
				return false;
			if (source.getDamageType().equals("trident"))
				return false;
			if (source == DamageSource.ANVIL)
				return false;
			if (source == DamageSource.DRAGON_BREATH)
				return false;
			if (source == DamageSource.WITHER)
				return false;
			if (source.getDamageType().equals("witherSkull"))
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
			ItemStack itemstack = sourceentity.getHeldItem(hand);
			ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());
			super.func_230254_b_(sourceentity, hand);
			sourceentity.startRiding(this);
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			return retval;
		}

		@Override
		public void travel(Vector3d dir) {
			Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
			if (this.isBeingRidden()) {
				this.rotationYaw = entity.rotationYaw;
				this.prevRotationYaw = this.rotationYaw;
				this.rotationPitch = entity.rotationPitch * 0.5F;
				this.setRotation(this.rotationYaw, this.rotationPitch);
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.15F;
				this.renderYawOffset = entity.rotationYaw;
				this.rotationYawHead = entity.rotationYaw;
				this.stepHeight = 1.0F;
				if (entity instanceof LivingEntity) {
					this.setAIMoveSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					float forward = ((LivingEntity) entity).moveForward;
					float strafe = ((LivingEntity) entity).moveStrafing;
					super.travel(new Vector3d(strafe, 0, forward));
				}
				this.prevLimbSwingAmount = this.limbSwingAmount;
				double d1 = this.getPosX() - this.prevPosX;
				double d0 = this.getPosZ() - this.prevPosZ;
				float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
				if (f1 > 1.0F)
					f1 = 1.0F;
				this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
				this.limbSwing += this.limbSwingAmount;
				return;
			}
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.travel(dir);
		}
	}

	// Made with Blockbench 3.8.3
	// Exported for Minecraft version 1.15 - 1.16
	// Paste this class into your mod and generate all required imports
	public static class Modelcustom_model extends EntityModel<Entity> {
		private final ModelRenderer bb_main;
		public Modelcustom_model() {
			textureWidth = 128;
			textureHeight = 128;
			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.setTextureOffset(0, 0).addBox(15.0F, -15.0F, -24.0F, 1.0F, 10.0F, 48.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(-16.0F, -15.0F, -24.0F, 1.0F, 10.0F, 48.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 60).addBox(-10.0F, -14.0F, -25.0F, 20.0F, 9.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 123).addBox(-15.0F, -20.0F, -9.0F, 30.0F, 5.0F, 0.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(-15.0F, -15.0F, -24.0F, 30.0F, 10.0F, 16.0F, 0.0F, false);
			bb_main.setTextureOffset(118, 123).addBox(-15.0F, -14.0F, -25.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(118, 123).addBox(11.0F, -14.0F, -25.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 110).addBox(-15.0F, -9.0F, -25.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 110).addBox(11.0F, -9.0F, -25.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(81, 62).addBox(12.0F, -14.0F, 24.0F, 3.0F, 5.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(81, 62).addBox(-15.0F, -14.0F, 24.0F, 3.0F, 5.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 110).addBox(-15.0F, -8.0F, 24.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 110).addBox(12.0F, -8.0F, 24.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(22, 61).addBox(-3.0F, -7.0F, 24.0F, 6.0F, 3.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(102, 52).addBox(-15.0F, -4.0F, -21.0F, 5.0F, 4.0F, 8.0F, 0.0F, false);
			bb_main.setTextureOffset(102, 52).addBox(-15.0F, -4.0F, 13.0F, 5.0F, 4.0F, 8.0F, 0.0F, false);
			bb_main.setTextureOffset(102, 52).addBox(10.0F, -4.0F, 13.0F, 5.0F, 4.0F, 8.0F, 0.0F, false);
			bb_main.setTextureOffset(102, 52).addBox(10.0F, -4.0F, -21.0F, 5.0F, 4.0F, 8.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(16.0F, -11.0F, -23.0F, 1.0F, 7.0F, 12.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(16.0F, -11.0F, 11.0F, 1.0F, 7.0F, 12.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(-17.0F, -11.0F, 11.0F, 1.0F, 7.0F, 12.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(-17.0F, -11.0F, -23.0F, 1.0F, 7.0F, 12.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 60).addBox(-0.5F, -16.0F, -24.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 0).addBox(-15.0F, -15.0F, 8.0F, 30.0F, 10.0F, 16.0F, 0.0F, false);
			bb_main.setTextureOffset(0, 73).addBox(-16.0F, -5.0F, -24.0F, 32.0F, 1.0F, 48.0F, 0.0F, false);
			bb_main.setTextureOffset(52, 61).addBox(-10.0F, -5.0F, 22.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
			bb_main.setTextureOffset(52, 61).addBox(8.0F, -5.0F, 22.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			// previously the render function, render code was moved to a method below
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
