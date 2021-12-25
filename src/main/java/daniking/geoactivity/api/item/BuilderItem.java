package daniking.geoactivity.api.item;

import daniking.geoactivity.common.recipe.ConversionRecipe;
import daniking.geoactivity.common.registry.GARecipeTypes;
import daniking.geoactivity.common.util.GAInventory;
import daniking.geoactivity.common.util.RechargeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Objects;

/**
 * Base class for Block Builders.
 */
public abstract class BuilderItem extends Item implements Rechargeable{

    public BuilderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context == null) {
            return ActionResult.PASS;
        }
        if (context.getPlayer() == null || context.getWorld() == null) {
            return ActionResult.PASS;
        }

        final ItemStack stack = context.getStack();
        RechargeUtil.initDestroyedNbt(context.getPlayer(), stack);

        if (canConvert(context.getStack())) {
            convertItem(context);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public boolean canConvert(final ItemStack container) {
        if (container.isEmpty()) {
            return false;
        }
        if (RechargeUtil.isAlmostBroken(container)) {
            RechargeUtil.setDestroyed(container);
            return false;
        }
        final GAInventory inventory = this.builderInventory(container);
        final ItemStack inputStack = inventory.getStack(1);
        final ItemStack outputStack = inventory.getStack(2);
        if (inputStack.isEmpty() || outputStack.isEmpty()) {
            return false;
        }
        if (inputStack.getItem() == outputStack.getItem()) {
            return false;
        }
        return !RechargeUtil.isDestroyed(container);
    }

    protected void convertItem(final ItemUsageContext context) {
        /*
         * Fast fail if there is no input, no point checking other stuff if container is not complete
         * or the player is not using the main hand.
         */
        if (!canConvert(context.getStack())) {
            return;
        }

        if (context.getHand() != Hand.MAIN_HAND) {
            return;
        }

        final World world = context.getWorld();
        final BlockPos originPosition = context.getBlockPos();
        final BlockState originState = world.getBlockState(originPosition);
        final Direction side = context.getSide();
        final ItemStack stack = context.getStack();
        final Inventory inventory = this.builderInventory(stack);
        final ItemStack inputStack = inventory.getStack(1);
        final ItemStack outputStack = inventory.getStack(2);
        final PlayerEntity player = context.getPlayer();
        Objects.requireNonNull(player);

        final ConversionRecipe type = world
                .getRecipeManager()
                .listAllOfType(GARecipeTypes.CONVERSION_RECIPE_TYPE)
                .stream()
                .filter(recipe -> recipe.input() == inputStack.getItem() && recipe.output().getItem() == outputStack.getItem())
                .findFirst().orElse(null);

        if (type == null) {
            return;
        }

        //At this point we are ready to craft the new block.
        if (!world.isClient) {
            final int inputCount = inputStack.getCount();
            final int outputCount = outputStack.getCount();
            final ItemStack outputFromRecipe = type.getOutput();

            if (inputCount > 0 && outputCount > 0 || player.isCreative()) {
                final BlockState outputState = Block.getBlockFromItem(outputFromRecipe.getItem()).getDefaultState();
                if (outputState != null && !outputState.isAir()) {
                    /*
                     * Let's first check if we are facing a replaceable block.
                     */
                    boolean placed = false;
                    if (this.canPlaceBlock(world, outputState, originPosition) && originState.getMaterial().isReplaceable()) {
                        world.setBlockState(originPosition, outputState);
                        placed = true;
                    }
                    //Otherwise, lets check if the offset side is empty.
                    if (!placed) {
                        final BlockPos offsetPos = originPosition.offset(side);
                        final BlockState offsetState = world.getBlockState(offsetPos);
                        if (offsetState.isAir()) {
                            if (this.canPlaceBlock(world, outputState, offsetPos)) {
                                world.setBlockState(offsetPos, outputState);
                                placed = true;
                            }
                        }
                        //Done to place blocks on fluids.
                        if (!placed) {
                            if (!offsetState.getFluidState().isEmpty()) {
                                world.setBlockState(offsetPos, outputState);
                                placed = true;
                            }
                        }
                    }
                    if (placed) {
                        if (!player.isCreative()) {
                            stack.damage(5, player, (user -> user.sendToolBreakStatus(context.getHand())));
                            inventory.removeStack(1, 1);
                        }
                    }
                }
            }
        }
    }

    protected boolean canPlaceBlock(final World world, final BlockState state, final BlockPos at) {
        return world.canPlace(state, at, ShapeContext.absent());
    }

    protected GAInventory builderInventory(final ItemStack stack) {
        return GAInventory.create(stack, 3);
    }
}
