package revilo.multihelper.item.behaviors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.GuiFactories;
import com.cleanroommc.modularui.factory.PlayerInventoryGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import gregapi.item.multiitem.MultiItem;
import gregapi.item.multiitem.behaviors.IBehavior.AbstractBehaviorDefault;

public class BaseBehaviors {

    /*
     * Opens a specified ModularUI Gui upon onItemRightClick();
     */
    public abstract class AbstractBehaviorDefaultWithGui extends AbstractBehaviorDefault
        implements IGuiHolder<PlayerInventoryGuiData> {

        @Override
        public abstract ModularScreen createScreen(PlayerInventoryGuiData data, ModularPanel mainPanel);

        @Override
        public abstract ModularPanel buildUI(PlayerInventoryGuiData guiData, PanelSyncManager guiSyncManager,
            UISettings settings);

        @Override
        public ItemStack onItemRightClick(MultiItem aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
            if (!aWorld.isRemote) {
                GuiFactories.playerInventory()
                    .openFromMainHand(aPlayer);
            }
            return super.onItemRightClick(aItem, aStack, aWorld, aPlayer);
        }
    }

}
