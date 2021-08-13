package com.darinth.wurmunlimited.mod.petcommandoh.actionperformer;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.behaviours.*;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.DbCreatureStatus;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.structures.Fence;
import com.wurmonline.server.structures.Wall;
import com.wurmonline.server.utils.CoordUtils;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Logger;

public class StatusActionPerformer implements ModAction, ActionPerformer {
    public final ActionEntry actionEntry;
    private static Logger logger = Logger.getLogger(StatusActionPerformer.class.getName());

    public StatusActionPerformer()
    {
        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Status", "examining", new int[]{22, 33}).priority(8).blockedByUseOnGroundOnly(false).range(80).build();
        ModActions.registerAction(actionEntry);
        ModActions.registerActionPerformer(this);
    }

    public short getActionId() {
        return actionEntry.getNumber();
    }

    public boolean action(Action action, Creature performer)
    {
        logger.info("Status action");

        Creature pet = performer.getPet();
        if(pet == null) {
            performer.getCommunicator().sendNormalServerMessage("You have no pet.", (byte)3);
            return true;
        }

        float f = pet.getStatus().getPositionZ() + pet.getAltOffZ();
        if (!performer.isWithinTileDistanceTo(pet.getTileX(), pet.getTileY(), CoordUtils.WorldToTile(f), 20)) {
            performer.getCommunicator().sendNormalServerMessage("You are too far away to examine " + pet.getNameWithGenus() + ".");
            return true;
        }

        try{
            Behaviours.getInstance().getBehaviour((short)4).action(action, performer, pet, (short)1, 0f);
        } catch (NoSuchBehaviourException ex) {
            performer.getCommunicator().sendNormalServerMessage("Failed to retrieve CreatureBehaviour object. o.O.");
            logger.severe("Failed to retrieve CreatureBehaviour object. o.O.");
            return true;
        }

        if (performer.getPet() != null && DbCreatureStatus.getIsLoaded(performer.getPet().getWurmId()) == 1) {
            performer.getCommunicator().sendNormalServerMessage("The " + performer.getPet().getName() + " is within a cage.");
        }

        float hungerPercent = pet.getStatus().getHunger() / 45000f * 100f;
        performer.getCommunicator().sendNormalServerMessage(String.format("The %1$s is %2$.2f percent of the way to hungry.", performer.getPet().getName(), hungerPercent));

        return true;
    }

    //
    // Tiles
    //
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, int tile, short num, float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, short num, float counter) {
        return this.action(action, performer);
    }

    //
    // Tile corners
    //
    @Deprecated
    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, boolean corner, int tile, short num, float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset, short num, float counter) {
        return this.action(action, performer);
    }

    @Deprecated
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, short num, float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset, short num, float counter) {
        return this.action(action, performer);
    }

    //
    // Walls
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, Wall target, short num, float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Wall target, short num, float counter) {
        return this.action(action, performer);
    }

    //
    // Fences
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, boolean onSurface, Fence target, short num, float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, boolean onSurface, Fence target, short num, float counter) {
        return this.action(action, performer);
    }

    //
    // Tiles border
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, Tiles.TileBorderDirection dir, long borderId, short num, float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, long borderId, short num, float counter) {
        return this.action(action, performer);
    }

    //
    // Cave tiles
    //
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, int tile, int dir, short num, final float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, int dir, short num, float counter) {
        return this.action(action, performer);
    }

    //
    // Creatures
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, Creature target, short num, float counter) {
        return this.action(action, performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Creature target, short num, float counter) {
        return this.action(action, performer);
    }
}
