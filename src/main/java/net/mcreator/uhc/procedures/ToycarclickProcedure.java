package net.mcreator.uhc.procedures;

@UhcModElements.ModElement.Tag
public class ToycarclickProcedure extends UhcModElements.ModElement {

	public ToycarclickProcedure(UhcModElements instance) {
		super(instance, 14);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				UhcMod.LOGGER.warn("Failed to load dependency entity for procedure Toycarclick!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				UhcMod.LOGGER.warn("Failed to load dependency world for procedure Toycarclick!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");
		IWorld world = (IWorld) dependencies.get("world");

		double Amount_Of_Item_Needed = 0;
		boolean ItemBought = false;
		if (((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(CoinItem.block, (int) (1))) : false)) {
			{
				AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
				entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> _iitemhandlerref.set(capability));
				if (_iitemhandlerref.get() != null) {
					for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
						ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx).copy();
						if ((new ItemStack(CoinItem.block, (int) (1)).getItem() == (itemstackiterator).getItem())) {
							Amount_Of_Item_Needed = (double) ((Amount_Of_Item_Needed) + (((itemstackiterator)).getCount()));
						}
						if (((Amount_Of_Item_Needed) > 63)) {
							if (((ItemBought) == (false))) {
								ItemBought = (boolean) (true);
								if (entity instanceof PlayerEntity) {
									ItemStack _stktoremove = new ItemStack(CoinItem.block, (int) (1));
									((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 64,
											((PlayerEntity) entity).container.func_234641_j_());
								}
								{
									Entity _ent = entity;
									if (!_ent.world.isRemote && _ent.world.getServer() != null) {
										_ent.world.getServer().getCommandManager().handleCommand(
												_ent.getCommandSource().withFeedbackDisabled().withPermissionLevel(4), "summon uhc:toycar");
									}
								}
							}
						}
					}
				}
			}
		}

	}

}
