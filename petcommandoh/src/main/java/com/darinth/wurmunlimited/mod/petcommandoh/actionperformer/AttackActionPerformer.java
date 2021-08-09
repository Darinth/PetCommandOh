package com.darinth.wurmunlimited.mod.petcommandoh.actionperformer;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Server;
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

import java.util.logging.Level;
import java.util.logging.Logger;

public class AttackActionPerformer implements ModAction, ActionPerformer {
    public final ActionEntry actionEntry;
    private static Logger logger = Logger.getLogger(FollowActionPerformer.class.getName());

    public AttackActionPerformer()
    {
        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Attack", "ordering", new int[]{5, 22, 33}).priority(8).blockedByUseOnGroundOnly(false).range(80).build();
        ModActions.registerAction(actionEntry);
        ModActions.registerActionPerformer(this);
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

        Creature pet = performer.getPet();
        logger.info("Attack action");
        Communicator comm = performer.getCommunicator();

        if(pet == null) {
            return true;
        }

        if (DbCreatureStatus.getIsLoaded(pet.getWurmId()) == 1) {
            comm.sendNormalServerMessage("The " + performer.getPet().getName() + " tilts " + performer.getPet().getHisHerItsString() + " head while looking at you. There is a cage stopping " + performer.getPet().getHimHerItString() + " from attacking.", (byte)3);
            return true;
        }


        if (!pet.isWithinDistanceTo(performer.getPosX(), performer.getPosY(), performer.getPositionZ(), 200.0F, 0.0F)) {
            performer.getCommunicator().sendNormalServerMessage("The " + pet.getName() + " is too far away.");
        } else if (!pet.mayReceiveOrder()) {
            performer.getCommunicator().sendNormalServerMessage("The " + pet.getName() + " ignores your order.");
        } else if (target.getWurmId() == pet.getWurmId()) {
            performer.getCommunicator().sendNormalServerMessage("The " + pet.getName() + " seems to ignore your order.");
        } else {
            Village v = target.getCurrentVillage();
            if (v != null && v.isEnemy(performer)) {
                performer.getCommunicator().sendNormalServerMessage("The " + pet.getName() + " hesitates and does not enter " + v.getName() + ".");
                return true;
            }

            if (target.getTileX() < 10 || target.getTileY() < 10 || target.getTileX() > Zones.worldTileSizeX - 10 || target.getTileY() > Zones.worldTileSizeY - 10) {
                performer.getCommunicator().sendNormalServerMessage("The " + pet.getName() + " hesitates and does not go there.");
                return true;
            }

            if (target.isInvulnerable()) {
                performer.getCommunicator().sendNormalServerMessage("The " + pet.getName() + " ignores your order.");
            } else {
                if (pet.getLeader() == performer) {
                    pet.setLeader((Creature)null);
                    if (pet.getVisionArea() != null) {
                        pet.getVisionArea().broadCastUpdateSelectBar(pet.getWurmId());
                    }
                }

                Order o = new Order(target.currentTile.tilex, target.currentTile.tiley, target.currentTile.getLayer());
                pet.clearOrders();
                pet.addOrder(o);

                pet.setTarget(target.getWurmId(), true);
                pet.attackTarget();
                performer.getCommunicator().sendNormalServerMessage("You issue an attack order to the " + pet.getName() + ".");
            }
        }
        return true;
    }
}
