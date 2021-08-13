package com.darinth.wurmunlimited.mod.petcommandoh;

import com.darinth.wurmunlimited.mod.petcommandoh.behaviorprovider.PetBehaviorProvider;
import javassist.*;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PetCommandOh implements WurmServerMod, PreInitable, Initable, org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener{
    private static Logger logger = Logger.getLogger(PetCommandOh.class.getName());
    PetBehaviorProvider petBehaviorProvider;

    public void init() {
        logger.log(Level.INFO, "Init");
    }

    @Override
    public void preInit() {
        try {
            logger.log(Level.INFO, "Preinit");

            ModActions.init();

            ClassPool classPool = HookManager.getInstance().getClassPool();

            CtClass ctBehaviourDispatcher = classPool.get("com.wurmonline.server.behaviours.BehaviourDispatcher");
            ctBehaviourDispatcher.getMethod("requestActionForTiles", "(Lcom/wurmonline/server/creatures/Creature;JZLcom/wurmonline/server/items/Item;Lcom/wurmonline/server/behaviours/Behaviour;)Lcom/wurmonline/server/behaviours/BehaviourDispatcher$RequestParam;")
                    .insertAfter("return com.darinth.wurmunlimited.mod.petcommandoh.Hooks.tileBehaviourHook($_, $1, $2, $3, $4);");

            CtClass ctCreatureBehaviour = classPool.getCtClass("com.wurmonline.server.behaviours.CreatureBehaviour");
            ctCreatureBehaviour.getMethod("getBehavioursFor", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/creatures/Creature;)Ljava/util/List;")
                    .insertAfter("return com.darinth.wurmunlimited.mod.petcommandoh.Hooks.creatureBehaviorHook($_, $1, $2);");
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
