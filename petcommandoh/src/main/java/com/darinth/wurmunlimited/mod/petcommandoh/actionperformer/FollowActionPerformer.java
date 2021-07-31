package com.darinth.wurmunlimited.mod.petcommandoh.actionperformer;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Servers;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Vehicles;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.Creatures;
import com.wurmonline.server.creatures.DbCreatureStatus;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.structures.Fence;
import com.wurmonline.server.structures.Wall;
import com.wurmonline.server.utils.CoordUtils;
import org.gotti.wurmunlimited.modsupport.actions.*;

import java.util.logging.Logger;

public class FollowActionPerformer implements ModAction, ActionPerformer {
    public final ActionEntry actionEntry;
    private static Logger logger = Logger.getLogger(FollowActionPerformer.class.getName());

    public FollowActionPerformer()
    {
        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Follow", "ordering", new int[]{22, 33}).priority(8).blockedByUseOnGroundOnly(false).range(80).build();
        ModActions.registerAction(actionEntry);
        ModActions.registerActionPerformer(this);
    }

    public short getActionId() {
        return actionEntry.getNumber();
    }

    public boolean action(Creature performer)
    {
        logger.info("Follow action");

        if (performer.getPet() != null && DbCreatureStatus.getIsLoaded(performer.getPet().getWurmId()) == 1) {
            performer.getCommunicator().sendNormalServerMessage("The " + performer.getPet().getName() + " tilts " + performer.getPet().getHisHerItsString() + " head while looking at you. There is a cage stopping " + performer.getPet().getHimHerItString() + " from moving there.", (byte)3);
            return true;
        }

        Creature pet = performer.getPet();
        if(pet == null) {
            return true;
        }
        if (pet.getLeader() == null && pet.isLeadable(performer)) {
            float f = pet.getStatus().getPositionZ() + pet.getAltOffZ();
            if (!performer.isWithinTileDistanceTo(pet.getTileX(), pet.getTileY(), CoordUtils.WorldToTile(f), 120)) {
                performer.getCommunicator().sendNormalServerMessage("You are too far away for " + pet.getNameWithGenus() + " to hear your command");
            } else if (!performer.mayLeadMoreCreatures()) {
                performer.getCommunicator().sendNormalServerMessage("You would get nowhere if you tried to lead more creatures.");
            } else {
                if (performer.getBridgeId() == -10L && pet.getBridgeId() == -10L && performer.getFloorLevel(true) != pet.getFloorLevel()) {
                    performer.getCommunicator().sendNormalServerMessage("You must be on the same floor level to lead.");
                    return true;
                }

                boolean lastLed = !pet.isBranded() && Creatures.getInstance().wasLastLed(performer.getWurmId(), pet.getWurmId());
                if (performer.getVehicle() > -10L && !performer.isVehicleCommander()) {
                    if (Vehicles.getVehicleForId(performer.getVehicle()).isChair()) {
                        performer.getCommunicator().sendNormalServerMessage("You can't lead while sitting.");
                    } else {
                        performer.getCommunicator().sendNormalServerMessage("You can't lead as a passenger.");
                    }
                } else if (!pet.mayLead(performer) && !lastLed && !Servers.isThisAPvpServer()) {
                    performer.getCommunicator().sendNormalServerMessage("You don't have permission to lead.");
                } else {
                    if(pet.getTarget() != null)
                        pet.setTarget(-10L, true);

                    pet.setLeader(performer);
                    performer.addFollower(pet, (Item)null);

                    if (pet.getVisionArea() != null) {
                        pet.getVisionArea().broadCastUpdateSelectBar(pet.getWurmId());
                    }
                }
            }
        }
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
