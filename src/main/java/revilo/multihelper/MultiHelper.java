package revilo.multihelper;

import cpw.mods.fml.common.Mod;
import gregapi.api.Abstract_Mod;

/*
 * TODO:
 * Add NEI viewer similar to StructureLib
 * Autobuilder, this includes all the functions included.
 * Maybe compat with structurelib?
 */

@Mod(
    modid = MultiHelper.MOD_ID,
    name = MultiHelper.MOD_NAME,
    version = MultiHelper.VERSION,
    dependencies = "required-after:gregapi_post")
public class MultiHelper extends Abstract_Mod {

    public static final String MOD_ID = "multihelper";
    public static final String MOD_NAME = "MultiHelper";
    public static final String VERSION = Tags.VERSION; // Keep in line with Reach Core Version
    public static gregapi.code.ModData MOD_DATA = new gregapi.code.ModData(MOD_ID, MOD_NAME);

    @cpw.mods.fml.common.SidedProxy(
        modId = MOD_ID,
        clientSide = "revilo.multihelper.ClientProxy",
        serverSide = "revilo.multihelper.CommonProxy")
    public static gregapi.api.Abstract_Proxy PROXY;

    @Override
    public String getModID() {
        return MOD_ID;
    }

    @Override
    public String getModName() {
        return MOD_NAME;
    }

    @Override
    public String getModNameForLog() {
        return MOD_NAME;
    }

    @Override
    public gregapi.api.Abstract_Proxy getProxy() {
        return PROXY;
    }

    // Do not change these 7 Functions. Just keep them this way.
    @cpw.mods.fml.common.Mod.EventHandler
    public final void onPreLoad(cpw.mods.fml.common.event.FMLPreInitializationEvent aEvent) {
        onModPreInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onLoad(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {
        onModInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onPostLoad(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
        onModPostInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStarting(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
        onModServerStarting(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStarted(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
        onModServerStarted(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStopping(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
        onModServerStopping(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStopped(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
        onModServerStopped(aEvent);
    }

    @Override
    public void onModPreInit2(cpw.mods.fml.common.event.FMLPreInitializationEvent aEvent) {}

    @Override
    public void onModInit2(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {}

    @Override
    public void onModPostInit2(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
        // Insert your PostInit Code here and not above
    }

    @Override
    public void onModServerStarting2(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
        // Insert your ServerStarting Code here and not above
    }

    @Override
    public void onModServerStarted2(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
        // Insert your ServerStarted Code here and not above
    }

    @Override
    public void onModServerStopping2(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
        // Insert your ServerStopping Code here and not above
    }

    @Override
    public void onModServerStopped2(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
        // Insert your ServerStopped Code here and not above
    }

}
