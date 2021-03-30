package net.mcreator.uhc.procedures;

@UhcModElements.ModElement.Tag
public class LegoEntityWalksOnTheBlockProcedure extends UhcModElements.ModElement {

	public LegoEntityWalksOnTheBlockProcedure(UhcModElements instance) {
		super(instance, 11);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				UhcMod.LOGGER.warn("Failed to load dependency entity for procedure LegoEntityWalksOnTheBlock!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				UhcMod.LOGGER.warn("Failed to load dependency world for procedure LegoEntityWalksOnTheBlock!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");
		IWorld world = (IWorld) dependencies.get("world");

		for (int index0 = 0; index0 < (int) (100); index0++) {
			new Object() {

				private int ticks = 0;
				private float waitTicks;
				private IWorld world;

				public void start(IWorld world, int waitTicks) {
					this.waitTicks = waitTicks;
					MinecraftForge.EVENT_BUS.register(this);
					this.world = world;
				}

				@SubscribeEvent
				public void tick(TickEvent.ServerTickEvent event) {
					if (event.phase == TickEvent.Phase.END) {
						this.ticks += 1;
						if (this.ticks >= this.waitTicks)
							run();
					}
				}

				private void run() {
					entity.attackEntityFrom(DamageSource.GENERIC, (float) 1);

					MinecraftForge.EVENT_BUS.unregister(this);
				}

			}.start(world, (int) 20);
		}

	}

}
