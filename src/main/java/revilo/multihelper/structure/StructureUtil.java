package revilo.multihelper.structure;

import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.block.Block;

import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import revilo.multihelper.structure.elements.IStructureElement;
import revilo.multihelper.structure.elements.StructureElementBlock;
import revilo.multihelper.structure.elements.StructureElementMultiParts;
import revilo.multihelper.structure.elements.StructureElementPart;
import revilo.multihelper.structure.elements.StructureElementTiered;

public class StructureUtil {

    public static short getRegistryID(MultiTileEntityRegistry aRegistry) {
        return ST.id(aRegistry.mBlock);
    }

    public static short getGTRegistryID() {
        return getRegistryID(MultiTileEntityRegistry.getRegistry("gt.multitileentity"));
    }

    @SuppressWarnings("unchecked")
    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> counted3(String key1,
        IStructureElement<T> part1, String key2, IStructureElement<T> part2, String key3, IStructureElement<T> part3) {

        return new StructureElementMultiParts<T>(
            new String[] { key1, key2, key3 },
            new IStructureElement[] { part1, part2, part3 });
    }

    @SuppressWarnings("unchecked")
    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> counted2(String key1,
        IStructureElement<T> part1, String key2, IStructureElement<T> part2) {

        return new StructureElementMultiParts<T>(new String[] { key1, key2 }, new IStructureElement[] { part1, part2 });
    }

    @SuppressWarnings("unchecked")
    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> counted1(String key1,
        IStructureElement<T> part1) {

        return new StructureElementMultiParts<T>(new String[] { key1 }, new IStructureElement[] { part1 });
    }

    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> countedX(String[] keys,
        IStructureElement<T>[] elements) {

        return new StructureElementMultiParts<T>(keys, elements);
    }

    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> ofPart(int tRegistryMeta,
        int tRegistryID, int tDesign, int tMode) {
        return new StructureElementPart<T>(tRegistryMeta, tRegistryID, tDesign, tMode);
    }

    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> ofPartG(int tRegistryMeta) {
        return new StructureElementPart<T>(tRegistryMeta, getGTRegistryID(), 0, MultiTileEntityMultiBlockPart.NOTHING);
    }

    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> ofPart(int tRegistryMeta,
        int tRegistryID) {
        return new StructureElementPart<T>(tRegistryMeta, tRegistryID, 0, MultiTileEntityMultiBlockPart.NOTHING);
    }

    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> ofBlock(Block tBlock, int aMeta) {
        return new StructureElementBlock<T>(tBlock, aMeta);
    }

    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> ofBlock(Block tBlock) {
        return ofBlock(tBlock, 0);
    }

    public static <T extends ITileEntityMultiBlockController> IStructureElement<T> ofTiered(
        Map<IStructureElement<T>, Integer> tierMap, BiConsumer<T, Integer> setter) {
        return new StructureElementTiered<T>(tierMap, setter);
    }
}
