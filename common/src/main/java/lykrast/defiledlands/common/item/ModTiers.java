package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.registry.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
    UMBRIUM(2, 600, 6.0F, 2.0F, 14, () -> Ingredient.of(ModItems.SCARLITE.get())),
    SCARLITE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(ModItems.SCARLITE.get())),
    RAVAGING(3, 2048, 8.0F, 4.0F, 10, () -> Ingredient.of(ModItems.SCARLITE.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() { return this.uses; }

    @Override
    public float getSpeed() { return this.speed; }

    @Override
    public float getAttackDamageBonus() { return this.damage; }

    @Override
    public int getLevel() { return this.level; }

    @Override
    public int getEnchantmentValue() { return this.enchantmentValue; }

    @Override
    public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }
}
