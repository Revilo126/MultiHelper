package revilo.multihelper;

import static gregapi.data.CS.*;

import codechicken.nei.api.IConfigureNEI;
import gregapi.NEI_RecipeMap;
import gregapi.recipes.Recipe.RecipeMap;
import revilo.multihelper.recipe.RecipeMapBuilder;

public class NEIMultiHelperConfig implements IConfigureNEI, Runnable {

    @Override
    public void run() {
        for (RecipeMap tMap : RecipeMapBuilder.MULTI_MAP_LIST) if (tMap.mNEIAllowed) new NEI_RecipeMap(tMap).init();

    }

    @Override
    public void loadConfig() {
        if (GAPI_POST.mFinishedPostInit) run();
        else GAPI_POST.mAfterPostInit.add(this);
    }

    @Override
    public String getName() {
        return "MultiHelper NEI Plugin";
    }

    @Override
    public String getVersion() {
        return Tags.VERSION;
    }

}
