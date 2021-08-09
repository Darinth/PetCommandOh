package com.darinth.wurmunlimited.mod.petcommandoh.actionperformer;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Servers;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.MethodsCreatures;
import com.wurmonline.server.behaviours.Vehicles;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.Creatures;
import com.wurmonline.server.creatures.DbCreatureStatus;
import com.wurmonline.server.creatures.NoSuchCreatureException;
import com.wurmonline.server.endgames.EndGameItems;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.structures.Fence;
import com.wurmonline.server.structures.Wall;
import com.wurmonline.server.utils.CoordUtils;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocateActionPerformer implements ModAction, ActionPerformer {
    public final ActionEntry actionEntry;
    private static Logger logger = Logger.getLogger(LocateActionPerformer.class.getName());

    public LocateActionPerformer()
    {
        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Locate", "locating", new int[]{22, 33}).priority(8).blockedByUseOnGroundOnly(false).range(80).build();
        ModActions.registerAction(actionEntry);
        ModActions.registerActionPerformer(this);
    }

    public short getActionId() {
        return actionEntry.getNumber();
    }

    public boolean action(Creature performer)
    {
        logger.info("Locate action");

        if (performer.getPet() != null && DbCreatureStatus.getIsLoaded(performer.getPet().getWurmId()) == 1) {
            performer.getCommunicator().sendNormalServerMessage("Your " + performer.getPet().getName() + " is loaded in a cage, or on another server", (byte)3);
            return true;
        }

        Creature pet = performer.getPet();
        if(pet == null) {
            return true;
        }

        int centerX = pet.getTileX();
        int centerY = pet.getTileY();
        int distanceX = Math.abs(centerX - performer.getTileX());
        int distanceY = Math.abs(centerY - performer.getTileY());
        int minDist = (int)Math.sqrt((double)(distanceX * distanceX + distanceY * distanceY));
        int dir = MethodsCreatures.getDir(performer, centerX, centerY);
        String direction = MethodsCreatures.getLocationStringFor(performer.getStatus().getRotation(), dir, "you");
        String toReturn = EndGameItems.getDistanceString(minDist, pet.getName(), direction, false);
        performer.getCommunicator().sendNormalServerMessage(toReturn);

        return true;
    }

    //
    // Tiles
    //
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, int tile, short num, float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, short num, float counter) {
        return this.action(performer);
    }

    //
    // Tile corners
    //
    @Deprecated
    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, boolean corner, int tile, short num, float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset, short num, float counter) {
        return this.action(performer);
    }

    @Deprecated
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, short num, float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset, short num, float counter) {
        return this.action(performer);
    }

    //
    // Walls
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, Wall target, short num, float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Wall target, short num, float counter) {
        return this.action(performer);
    }

    //
    // Fences
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, boolean onSurface, Fence target, short num, float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, boolean onSurface, Fence target, short num, float counter) {
        return this.action(performer);
    }

    //
    // Tiles border
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, Tiles.TileBorderDirection dir, long borderId, short num, float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, long borderId, short num, float counter) {
        return this.action(performer);
    }

    //
    // Cave tiles
    //
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, int tile, int dir, short num, final float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, int dir, short num, float counter) {
        return this.action(performer);
    }

    //
    // Creatures
    //
    @Override
    public boolean action(Action action, Creature performer, Item source, Creature target, short num, float counter) {
        return this.action(performer);
    }

    @Override
    public boolean action(Action action, Creature performer, Creature target, short num, float counter) {
        return this.action(performer);
    }
}
