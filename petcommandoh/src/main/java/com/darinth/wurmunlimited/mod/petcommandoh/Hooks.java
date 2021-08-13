package com.darinth.wurmunlimited.mod.petcommandoh;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.BehaviourDispatcher;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Hooks {
    private static Logger logger = Logger.getLogger(Hooks.class.getName());

    public static List<ActionEntry> removePetEntries(List<ActionEntry> originalActionEntries) {
        //logger.info(String.format("%1$d", originalActionEntries.size()));
        List<ActionEntry> newActions = new LinkedList<ActionEntry>();
        Iterator<ActionEntry> actionIterator = originalActionEntries.iterator();

        while (actionIterator.hasNext()) {
            ActionEntry action = actionIterator.next();
            //logger.info(action.getActionString());
            if(action.getActionString().equals("Pet")) {
                for(int I = 0;I < action.getNumber() * -1;I++) {
                    actionIterator.next();
                }
            } else {
                newActions.add(action);
            }
        }

        return newActions;
    }

    public static BehaviourDispatcher.RequestParam tileBehaviourHook(BehaviourDispatcher.RequestParam result, Creature performer, long target, boolean onSurface, Item source) {
        return new BehaviourDispatcher.RequestParam(removePetEntries(result.getAvailableActions()), result.getHelpString());
    }

    public static List<ActionEntry> creatureBehaviorHook(List<ActionEntry> actionEntries, @Nonnull Creature performer, @Nonnull Creature target) {
        return removePetEntries(actionEntries);
    }
}
