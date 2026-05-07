package revilo.multihelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import gregapi.api.Abstract_Mod;
import gregapi.api.Abstract_Proxy;
import gregapi.code.ModData;

@Mod(
    modid = MultiHelper.MOD_ID,
    name = MultiHelper.MOD_NAME,
    version = MultiHelper.VERSION,
    dependencies = "required-after:gregapi_post")
public class MultiHelper extends Abstract_Mod {

    public static final String MOD_ID = "multihelper";
    public static final String MOD_NAME = "MultiHelper";
    public static final String VERSION = Tags.VERSION;
    public static ModData MOD_DATA = new ModData(MOD_ID, MOD_NAME);

    @SidedProxy(
        modId = MOD_ID,
        clientSide = "revilo.multihelper.ClientProxy",
        serverSide = "revilo.multihelper.CommonProxy")
    public static Abstract_Proxy PROXY;

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
    public Abstract_Proxy getProxy() {
        return PROXY;
    }

    @Mod.EventHandler
    public final void onPreLoad(FMLPreInitializationEvent aEvent) {
        onModPreInit(aEvent);
    }

    @Mod.EventHandler
    public final void onLoad(FMLInitializationEvent aEvent) {
        onModInit(aEvent);
    }

    @Mod.EventHandler
    public final void onPostLoad(FMLPostInitializationEvent aEvent) {
        onModPostInit(aEvent);
    }

    @Mod.EventHandler
    public final void onServerStarting(FMLServerStartingEvent aEvent) {
        onModServerStarting(aEvent);
    }

    @Mod.EventHandler
    public final void onServerStarted(FMLServerStartedEvent aEvent) {
        onModServerStarted(aEvent);
    }

    @Mod.EventHandler
    public final void onServerStopping(FMLServerStoppingEvent aEvent) {
        onModServerStopping(aEvent);
    }

    @Mod.EventHandler
    public final void onServerStopped(FMLServerStoppedEvent aEvent) {
        onModServerStopped(aEvent);
    }

    @Override
    public void onModPreInit2(FMLPreInitializationEvent aEvent) {}

    @Override
    public void onModInit2(FMLInitializationEvent aEvent) {}

    @Override
    public void onModPostInit2(FMLPostInitializationEvent aEvent) {}

    @Override
    public void onModServerStarting2(FMLServerStartingEvent aEvent) {}

    @Override
    public void onModServerStarted2(FMLServerStartedEvent aEvent) {}

    @Override
    public void onModServerStopping2(FMLServerStoppingEvent aEvent) {}

    @Override
    public void onModServerStopped2(FMLServerStoppedEvent aEvent) {}

}
