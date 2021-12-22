package daniking.geoactivity.mixin.recharge;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import daniking.geoactivity.api.item.Rechargeable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin{

    @Shadow
    public abstract Item getItem();


    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void getAttributeModifiers(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> info) {
        if (this.getItem() instanceof Rechargeable item) {
            final Multimap<EntityAttribute, EntityAttributeModifier> modifiers = ArrayListMultimap.create(info.getReturnValue());
            item.handleAttributesModifiers(equipmentSlot, (ItemStack) (Object) this, modifiers);
            info.setReturnValue(ImmutableMultimap.copyOf(modifiers));
        }
    }
}
