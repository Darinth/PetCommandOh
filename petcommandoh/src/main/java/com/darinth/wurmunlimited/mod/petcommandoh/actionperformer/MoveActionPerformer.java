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
import org.gotti.wurmunlimited.modsupport.actions.*;

import java.util.logging.Logger;

public class MoveActionPerformer implements ModAction, ActionPerformer {
    public final ActionEntry actionEntry;
    private static Logger logger = Logger.getLogger(FollowActionPerformer.class.getName());

    public MoveActionPerformer()
    {
        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Move", "ordering", new int[]{22, 33}).priority(8).blockedByUseOnGroundOnly(false).range(80).build();
        ModActions.registerAction(actionEntry);
        ModActions.registerActionPerformer(this);
    }

    public short getActionId() {
        return actionEntry.getNumber();
    }

    //
    // Tiles
    //
    @Override
    public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, int tile, short num, float counter) {
        logger.info("Move action");
        Communicator comm = performer.getCommunicator();

        if (performer.getPet() != null && DbCreatureStatus.getIsLoaded(performer.getPet().getWurmId()) == 1) {
            comm.sendNormalServerMessage("The " + performer.getPet().getName() + " tilts " + performer.getPet().getHisHerItsString() + " head while looking at you. There is a cage stopping " + performer.getPet().getHimHerItString() + " from following you.", (byte)3);
            return true;
        }

        Creature pet = performer.getPet();
        performer.getCommunicator();
        if (pet != null) {
            if (pet.isWithinDistanceTo(performer.getPosX(), performer.getPosY(), performer.getPositionZ(), 200.0F, 0.0F)) {
                if (pet.mayReceiveOrder()) {
                    boolean ok = true;
                    int layer = 0;
                    if (Tiles.decodeType(tile) != Tiles.Tile.TILE_CAVE.id && !Tiles.isReinforcedFloor(Tiles.decodeType(tile))) {
                        if (Tiles.decodeType(tile) == Tiles.Tile.TILE_CAVE_EXIT.id && pet.isOnSurface()) {
                            layer = -1;
                        } else if (Tiles.isSolidCave(Tiles.decodeType(tile))) {
                            comm.sendNormalServerMessage("You cannot order " + pet.getName() + " into the rock.");
                            ok = false;
                        } else if (tilex < 10 || tiley < 10 || tilex > Zones.worldTileSizeX - 10 || tiley > Zones.worldTileSizeY - 10) {
                            comm.sendNormalServerMessage("The " + pet.getName() + " hesitates and does not go there.");
                            ok = false;
                        }
                    } else {
                        layer = -1;
                    }

                    Village v = Villages.getVillage(tilex, tiley, true);
                    if (v != null && v.isEnemy(performer)) {
                        comm.sendNormalServerMessage("The " + pet.getName() + " hesitates and does not enter " + v.getName() + ".");
                        ok = false;
                    }

                    if (pet.getHitched() != null || pet.isRidden()) {
                        comm.sendNormalServerMessage("The " + pet.getName() + " is restrained and ignores your order.");
                        ok = false;
                    }

                    if (ok) {
                        if (pet.getLeader() == performer) {
                            pet.setLeader((Creature)null);
                            if (pet.getVisionArea() != null) {
                                pet.getVisionArea().broadCastUpdateSelectBar(pet.getWurmId());
                            }
                        }

                        if(pet.getTarget() != null)
                            pet.setTarget(-10L, true);

                        Order o = new Order(tilex, tiley, layer);
                        pet.clearOrders();
                        pet.addOrder(o);
                        comm.sendNormalServerMessage("You issue an order to " + pet.getName() + ".");
                    }
                } else {
                    comm.sendNormalServerMessage("The " + pet.getName() + " ignores your order.");
                }
            } else {
                comm.sendNormalServerMessage("The " + pet.getName() + " is too far away.");
            }
        }
        return true;
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, short num, float counter) {
        return this.action(action, performer, tilex, tiley, onSurface, tile, num, counter);
    }
}
