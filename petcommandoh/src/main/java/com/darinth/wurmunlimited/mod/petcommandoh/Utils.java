package com.darinth.wurmunlimited.mod.petcommandoh;

import com.wurmonline.server.behaviours.ActionEntry;

import java.util.List;

public class Utils {

    public static void addSubActions(List<ActionEntry> originalList, String parentAction, List<ActionEntry> newActions) {
        int p = 0;
        for (ActionEntry actionEntry : originalList) {
            if (actionEntry.getActionString().equals(parentAction) && actionEntry.getNumber() < 0) {
                ActionEntry oldParent = originalList.remove(p);
                originalList.add(p, new ActionEntry((short)(oldParent.getNumber() - newActions.size()), oldParent.getActionString(), ""));
                //Subtract negative number to get positive number, should insert the new actions after the previous actions.
                originalList.addAll(p - oldParent.getNumber(), newActions);
            }
        }
    }
}
