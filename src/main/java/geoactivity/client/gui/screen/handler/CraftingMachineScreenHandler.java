package geoactivity.client.gui.screen.handler;

import geoactivity.api.gui.handler.ScreenHandlerBase;
import geoactivity.common.item.util.GAChargeItem;
import geoactivity.common.recipe.crafting.IMachineCrafting;
import geoactivity.common.registry.GARecipeTypes;
import geoactivity.common.registry.GAScreenHandlerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;
import java.util.Optional;

public class CraftingMachineScreenHandler extends ScreenHandlerBase {

    private final CraftingInventory craftingInventory;
    private final CraftingResultInventory craftingResultInventory;
    private final PropertyDelegate propertyDelegate;
    private final ScreenHandlerContext context;

    public CraftingMachineScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3),  new CraftingResultInventory(), new ArrayPropertyDelegate(4), ScreenHandlerContext.EMPTY);
    }

    public CraftingMachineScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, CraftingResultInventory resultInventory, PropertyDelegate propertyDelegate, ScreenHandlerContext context) {
        super(GAScreenHandlerTypes.CRAFTING_MACHINE, syncId, playerInventory, inventory);
        this.craftingInventory = new CraftingInventory(this, 3, 3);
        this.craftingResultInventory = resultInventory;
        this.propertyDelegate = propertyDelegate;
        this.context = context;
        this.builder()
                .playerSetup()
                .container3x3(this.craftingInventory, 9, 17)
                .slot(0, 71, 53, CraftingMachineScreenHandler::matchesFuel)
                .slot(1, 111, 53)
                .slot(2, 91, 17)
                .craftingOutput(this.craftingInventory, this.craftingResultInventory, 0, 147,35)
                .build();
        this.addProperties(this.propertyDelegate);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((contextWorld, pos) -> this.dropInventory(player, this.craftingInventory));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((contextWorld, pos)-> craftRecipe(this, this.craftingInventory, this.craftingResultInventory));
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return super.transferSlot(player, index);
    }

    public static boolean matchesFuel(final ItemStack stack) {
        final Item item = stack.getItem();
        return item == Items.COAL_BLOCK || item instanceof GAChargeItem;
    }
    private static void craftRecipe(final ScreenHandlerBase handler, final CraftingInventory inventory, final CraftingResultInventory resultInventory) {
        if (!handler.getWorld().isClient) {
            ItemStack output = ItemStack.EMPTY;
            Objects.requireNonNull(handler.getWorld().getServer());
            Optional<IMachineCrafting> optional = handler.getWorld()
                    .getServer()
                    .getRecipeManager()
                    .getFirstMatch(GARecipeTypes.MACHINE_CRAFTING_RECIPE, inventory, handler.getWorld());
            if (optional.isPresent()) {
                IMachineCrafting craftingRecipe = optional.get();
                output = craftingRecipe.craft(inventory);
            }
            resultInventory.setStack(0, output);
            final int i = handler.getSlotIndex(resultInventory, 0).orElseThrow();
            handler.setPreviousTrackedSlot(i, output);
            ((ServerPlayerEntity)handler.getPlayer()).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), i, output));
        }
    }

    public int getCookProgress() {
        int i = this.propertyDelegate.get(2);
        int j = this.propertyDelegate.get(3);
        return j != 0 && i != 0 ? i * 78 / j : 0;
    }

    public int getFuelProgress() {
        int i = this.propertyDelegate.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.propertyDelegate.get(0) * 13 / i;
    }

    public boolean isBurning() {
        return this.propertyDelegate.get(0) > 0;
    }
}
