package com.darinth.wurmunlimited.mod.petcommandoh.actionperformer;

import com.wurmonline.server.MessageServer;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.combat.CombatEngine;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.DbCreatureStatus;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.Skill;
import com.wurmonline.server.skills.Skills;
import com.wurmonline.server.utils.CreatureLineSegment;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.Zones;
import com.wurmonline.shared.util.MulticolorLineSegment;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RedirectActionPerformer implements ModAction, ActionPerformer {
    public final ActionEntry actionEntry;
    private static Logger logger = Logger.getLogger(FollowActionPerformer.class.getName());

    public RedirectActionPerformer()
    {
        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Redirect", "ordering", new int[]{3, 5, 22, 33}).range(5).priority(8).blockedByUseOnGroundOnly(false).build();
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
        logger.info(String.format("Taunt %f/%d", counter, action.getTimeLeft()));

        Creature pet = performer.getPet();
        logger.info("Redirect action");
        Communicator comm = performer.getCommunicator();

        if(pet == null)
            return true;

        if (DbCreatureStatus.getIsLoaded(pet.getWurmId()) == 1) {
            comm.sendNormalServerMessage("The " + performer.getPet().getName() + " tilts " + performer.getPet().getHisHerItsString() + " head while looking at you. There is a cage stopping " + performer.getPet().getHimHerItString() + " from taunting.", (byte)3);
            return true;
        }

        if (!pet.isWithinDistanceTo(performer.getPosX(), performer.getPosY(), performer.getPositionZ(), 5.0f, 0.0F)) {
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
                return true;
            } else {
                if (pet.getLeader() == performer) {
                    pet.setLeader((Creature)null);
                    if (pet.getVisionArea() != null) {
                        pet.getVisionArea().broadCastUpdateSelectBar(pet.getWurmId());
                    }
                }

                if (target.isDead()) {
                    logger.log(Level.INFO, target.getName() + " is dead when taunted by " + performer.getName());
                    return true;
                } else {
                    Skill taunt = null;
                    int time = 70;
                    Skills skills = performer.getSkills();
                    Skill defPsyche = null;
                    Skills defSkills = target.getSkills();

                    try {
                        defPsyche = defSkills.getSkill(105);
                    } catch (NoSuchSkillException var20) {
                        defPsyche = defSkills.learn(105, 1.0F);
                    }

                    Skill attPsyche = null;

                    try {
                        attPsyche = skills.getSkill(105);
                    } catch (NoSuchSkillException var19) {
                        attPsyche = skills.learn(105, 1.0F);
                    }

                    double power = 0.0D;

                    try {
                        taunt = skills.getSkill(10057);
                    } catch (NoSuchSkillException var18) {
                        taunt = skills.learn(10057, 1.0F);
                    }

                    if (counter == 1.0F) {
                        action.setTimeLeft(time);
                        performer.sendActionControl(Actions.actionEntrys[103].getVerbString(), true, time);
                        ArrayList<MulticolorLineSegment> segments = new ArrayList<MulticolorLineSegment>();
                        segments.add(new CreatureLineSegment(performer));
                        segments.add(new MulticolorLineSegment(" starts to annoy ", (byte) 7));
                        segments.add(new CreatureLineSegment(target));
                        segments.add(new MulticolorLineSegment(" in all imaginable ways.", (byte) 7));
                        target.getCommunicator().sendColoredMessageCombat(segments);
                        Iterator segmentsIterator = segments.iterator();

                        while (segmentsIterator.hasNext()) {
                            MulticolorLineSegment s = (MulticolorLineSegment) segmentsIterator.next();
                            s.setColor((byte) 0);
                        }

                        MessageServer.broadcastColoredAction(segments, performer, target, 5, true);
                        segments.get(1).setText(" start to annoy ");
                        performer.getCommunicator().sendColoredMessageCombat(segments);
                    } else {
                        time = action.getTimeLeft();
                    }

                    if (counter * 10.0F > (float) time) {
                        boolean dryrun = target.isNoSkillFor(performer);
                        target.addAttacker(performer);
                        float mod = CombatEngine.getMod(performer, target, taunt);
                        power = taunt.skillCheck(Math.max(1.0D, Math.max(taunt.getRealKnowledge() - 10.0D, defPsyche.getKnowledge(0.0D) - attPsyche.getKnowledge(0.0D))), 0.0D, mod == 0.0F || dryrun, (float) ((long) Math.max(1.0F, 4.0F * mod)), performer, target);
                        ArrayList<MulticolorLineSegment> segments;
                        if (power <= 0.0D) {
                            segments = new ArrayList<MulticolorLineSegment>();
                            segments.add(new CreatureLineSegment(target));
                            segments.add(new MulticolorLineSegment(" ignores your pet.", (byte) 0));
                            performer.getCommunicator().sendColoredMessageCombat(segments);
                            segments.clear();
                            segments.add(new CreatureLineSegment(performer));
                            segments.add(new MulticolorLineSegment(" accepts that you will not go after his pet.", (byte) 0));
                            target.getCommunicator().sendColoredMessageCombat(segments);
                            segments.clear();
                            segments.add(new CreatureLineSegment(target));
                            segments.add(new MulticolorLineSegment(" ignores ", (byte) 0));
                            segments.add(new CreatureLineSegment(performer));
                            segments.add(new MulticolorLineSegment("'s pet.", (byte) 0));
                            MessageServer.broadcastColoredAction(segments, performer, target, 5, true);
                        } else {
                            segments = new ArrayList<MulticolorLineSegment>();
                            segments.add(new CreatureLineSegment(target));
                            segments.add(new MulticolorLineSegment(" sees red and turns to attack ", (byte) 0));
                            segments.add(new CreatureLineSegment(pet));
                            segments.add(new MulticolorLineSegment(" instead.", (byte) 0));
                            performer.getCommunicator().sendColoredMessageCombat(segments);
                            MessageServer.broadcastColoredAction(segments, performer, target, 5, true);
                            Iterator segmentsInterator = segments.iterator();

                            while (segmentsInterator.hasNext()) {
                                MulticolorLineSegment s = (MulticolorLineSegment) segmentsInterator.next();
                                s.setColor((byte) 7);
                            }

                            segments.get(1).setText(" see red and turn to attack ");
                            target.getCommunicator().sendColoredMessageCombat(segments);
                            target.removeTarget(target.target);
                            target.setTarget(pet.getWurmId(), true);
                            target.setOpponent(pet);
                        }
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
