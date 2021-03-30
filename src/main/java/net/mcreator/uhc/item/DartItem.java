
package net.mcreator.uhc.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.uhc.itemgroup.ToysItemGroup;
import net.mcreator.uhc.UhcModElements;

@UhcModElements.ModElement.Tag
public class DartItem extends UhcModElements.ModElement {
	@ObjectHolder("uhc:dart")
	public static final Item block = null;
	public DartItem(UhcModElements instance) {
		super(instance, 6);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(ToysItemGroup.tab).maxStackSize(64).rarity(Rarity.COMMON));
			setRegistryName("dart");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
