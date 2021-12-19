package geoactivity.api.item;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IAdvancedArmor {

    /**
     * Called BEFORE the armor gets damaged from enemies.
     * @param source DamageSource
     * @param amount Float
     * @param slots Array of Integers.
     */
    default void onArmorDamage(final ItemStack stack, final PlayerEntity player, DamageSource source, final float amount, final int[] slots) {

    }

    /**
     * Returns the amount of armor protection
     * to be rendered.
     * By default, it just returns the armor of the player.
     * @param equipment ItemStack whose item is instance of this.
     * @param player PlayerEntity.
     * @return amount of protection.
     */
    default int getArmorProtection(final ItemStack equipment, final PlayerEntity player) {
        return player.getArmor();
    }
}
