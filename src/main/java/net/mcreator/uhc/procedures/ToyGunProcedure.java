package net.mcreator.uhc.procedures;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraft.world.IWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.uhc.item.ToyGunItemItem;
import net.mcreator.uhc.item.CoinItem;
import net.mcreator.uhc.UhcModElements;
import net.mcreator.uhc.UhcMod;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

@UhcModElements.ModElement.Tag
public class ToyGunProcedure extends UhcModElements.ModElement {
	public ToyGunProcedure(UhcModElements instance) {
		super(instance, 4);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				UhcMod.LOGGER.warn("Failed to load dependency entity for procedure ToyGun!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				UhcMod.LOGGER.warn("Failed to load dependency world for procedure ToyGun!");
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
						if (((Amount_Of_Item_Needed) > 99)) {
							if (((ItemBought) == (false))) {
								ItemBought = (boolean) (true);
								if (entity instanceof PlayerEntity) {
									ItemStack _stktoremove = new ItemStack(CoinItem.block, (int) (1));
									((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 100,
											((PlayerEntity) entity).container.func_234641_j_());
								}
								if (entity instanceof PlayerEntity) {
									ItemStack _setstack = new ItemStack(ToyGunItemItem.block, (int) (1));
									_setstack.setCount((int) 1);
									ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
								}
							}
						}
					}
				}
			}
		}
	}
}
