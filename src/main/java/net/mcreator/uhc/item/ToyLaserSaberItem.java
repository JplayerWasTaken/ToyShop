
package net.mcreator.uhc.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.uhc.itemgroup.ToysItemGroup;
import net.mcreator.uhc.UhcModElements;

@UhcModElements.ModElement.Tag
public class ToyLaserSaberItem extends UhcModElements.ModElement {
	@ObjectHolder("uhc:toy_laser_saber")
	public static final Item block = null;
	public ToyLaserSaberItem(UhcModElements instance) {
		super(instance, 8);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 1000;
			}

			public float getEfficiency() {
				return 4f;
			}

			public float getAttackDamage() {
				return 8f;
			}

			public int getHarvestLevel() {
				return 1;
			}

			public int getEnchantability() {
				return 2;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, -2f, new Item.Properties().group(ToysItemGroup.tab).isImmuneToFire()) {
		}.setRegistryName("toy_laser_saber"));
	}
}
