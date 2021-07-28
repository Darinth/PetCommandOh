package com.darinth.wurmunlimited.mod.petcommandoh;

import com.darinth.wurmunlimited.mod.petcommandoh.actionperformer.FollowActionPerformer;
import com.darinth.wurmunlimited.mod.petcommandoh.behaviorprovider.PetBehaviorProvider;
import com.wurmonline.server.behaviours.ActionEntry;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
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

    public void preInit() {
        //try {
        logger.log(Level.INFO, "Preinit");
            ModActions.init();


            //ActionEntry.createEntry((short)ModActions.getNextActionId(), "Follow", "ordering", new int[]{22, 33});

            //ClassPool classPool = HookManager.getInstance().getClassPool();
            //CtClass ctBehaviourDispatcher = classPool.getCtClass("com.wurmonline.server.behaviours.BehaviourDispatcher");
            //ctBehaviourDispatcher.getMethod("requestActionForTiles", "(Lcom/wurmonline/server/creatures/Creature;JZLcom/wurmonline/server/items/Item;Lcom/wurmonline/server/behaviours/Behaviour;)Lcom/wurmonline/server/behaviours/BehaviourDispatcher$RequestParam;")
                    //.insertAfter("return net.bdew.wurm.betterfarm.area.AreaActions.tileBehaviourHook($_, $1, $2, $3, $4);");

            //ctBehaviourDispatcher.getMethod("requestActionForItemsBodyIdsCoinIds", "(Lcom/wurmonline/server/creatures/Creature;JLcom/wurmonline/server/items/Item;Lcom/wurmonline/server/behaviours/Behaviour;)Lcom/wurmonline/server/behaviours/BehaviourDispatcher$RequestParam;")
                    //.insertAfter("return net.bdew.wurm.betterfarm.area.AreaActions.itemBehaviourHook($_, $1, $2, $3);");
        //} catch (NotFoundException e) {
            //throw new RuntimeException(e);
        //} catch (CannotCompileException e) {
            //throw new RuntimeException(e);
        //}
    }

    @Override
    public void onServerStarted() {
        logger.log(Level.INFO, "onServerStarted");
        petBehaviorProvider = new PetBehaviorProvider();
    }
}
