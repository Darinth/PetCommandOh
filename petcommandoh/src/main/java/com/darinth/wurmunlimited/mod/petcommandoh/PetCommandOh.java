package com.darinth.wurmunlimited.mod.petcommandoh;

import com.darinth.wurmunlimited.mod.petcommandoh.behaviorprovider.PetBehaviorProvider;
import javassist.*;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetCommandOh implements WurmServerMod, PreInitable, Initable, ServerStartedListener, Configurable {
    private static Logger logger = Logger.getLogger(PetCommandOh.class.getName());

    private PetBehaviorProvider petBehaviorProvider;
    private boolean removeVanillaPetMenu = true;

    public void init() {
        logger.log(Level.INFO, "Init");
    }

    @Override
    public void configure(Properties properties) {
        logger.info("Beginning configuration...");
        removeVanillaPetMenu = Boolean.parseBoolean(properties.getProperty("removeVanillaPetMenu", "true"));
    }

    @Override
    public void preInit() {
        try {
            logger.log(Level.INFO, "Preinit");

            ModActions.init();

            ClassPool classPool = HookManager.getInstance().getClassPool();
            if (removeVanillaPetMenu) {
                CtClass ctBehaviourDispatcher = classPool.get("com.wurmonline.server.behaviours.BehaviourDispatcher");
                ctBehaviourDispatcher.getMethod("requestActionForTiles", "(Lcom/wurmonline/server/creatures/Creature;JZLcom/wurmonline/server/items/Item;Lcom/wurmonline/server/behaviours/Behaviour;)Lcom/wurmonline/server/behaviours/BehaviourDispatcher$RequestParam;")
                        .insertAfter("return com.darinth.wurmunlimited.mod.petcommandoh.Hooks.tileBehaviourHook($_, $1, $2, $3, $4);");

                CtClass ctCreatureBehaviour = classPool.getCtClass("com.wurmonline.server.behaviours.CreatureBehaviour");
                ctCreatureBehaviour.getMethod("getBehavioursFor", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/creatures/Creature;)Ljava/util/List;")
                        .insertAfter("return com.darinth.wurmunlimited.mod.petcommandoh.Hooks.creatureBehaviorHook($_, $1, $2);");
            }
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onServerStarted() {
        logger.log(Level.INFO, "onServerStarted");
        petBehaviorProvider = new PetBehaviorProvider();
    }
}
