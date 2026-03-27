package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    UMBRIUM("umbrium", 16, new int[]{2, 5, 6, 2}, 14, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(ModItems.UMBRIUM_INGOT.get())),
    UMBRA("umbra", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(ModItems.SCARLITE.get())),
    BOOK_WYRM("book_wyrm", 12, new int[]{2, 5, 6, 2}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ModItems.BOOK_WYRM_SCALE.get())),
    GOLDEN_BOOK_WYRM("golden_book_wyrm", 22, new int[]{3, 6, 8, 3}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 0.0F, () -> Ingredient.of(ModItems.BOOK_WYRM_SCALE_GOLDEN.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {13, 15, 16, 11};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        int index = switch (type) {
            case BOOTS -> 0;
            case LEGGINGS -> 1;
            case CHESTPLATE -> 2;
            case HELMET -> 3;
        };
        return BASE_DURABILITY[index] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        int index = switch (type) {
            case BOOTS -> 0;
            case LEGGINGS -> 1;
            case CHESTPLATE -> 2;
            case HELMET -> 3;
        };
        return this.protectionAmounts[index];
    }

    @Override
    public int getEnchantmentValue() { return this.enchantmentValue; }

    @Override
    public SoundEvent getEquipSound() { return this.equipSound; }

    @Override
    public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }

    @Override
    public String getName() { return DefiledLands.MOD_ID + ":" + this.name; }

    @Override
    public float getToughness() { return this.toughness; }

    @Override
    public float getKnockbackResistance() { return this.knockbackResistance; }
}
