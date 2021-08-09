package com.darinth.wurmunlimited.mod.petcommandoh.actionperformer;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.DbCreatureStatus;
import com.wurmonline.server.creatures.ai.Order;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.villages.Villages;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Logger;

public class AttackMoveActionPerformer implements ModAction, ActionPerformer {
    public final ActionEntry actionEntry;
    private static Logger logger = Logger.getLogger(FollowActionPerformer.class.getName());
    private AttackActionPerformer attackActionPerformer;
    private MoveActionPerformer moveActionPerformer;

    public AttackMoveActionPerformer(AttackActionPerformer attackActionPerformer, MoveActionPerformer moveActionPerformer)
    {
        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Attack/Move", "ordering", new int[]{5, 22, 33}).priority(8).blockedByUseOnGroundOnly(false).range(80).build();
        ModActions.registerAction(actionEntry);
        ModActions.registerActionPerformer(this);
        this.attackActionPerformer = attackActionPerformer;
        this.moveActionPerformer = moveActionPerformer;
    }

    public short getActionId() {
        return actionEntry.getNumber();
    }

    //
    // Creatures
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, Creature target, short num, float counter) {
        return this.action(action, performer, target, num, counter);
    }

    @Override
    public boolean action(Action action, Creature performer, Creature target, short num, float counter) {
        return attackActionPerformer.action(action, performer, target, num, counter);
    }

    //
    // Tiles
    //
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, int tile, short num, float counter) {
        return moveActionPerformer.action(action, performer, tilex, tiley, onSurface, tile, num, counter);
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, short num, float counter) {
        return this.action(action, performer, tilex, tiley, onSurface, tile, num, counter);
    }
}
