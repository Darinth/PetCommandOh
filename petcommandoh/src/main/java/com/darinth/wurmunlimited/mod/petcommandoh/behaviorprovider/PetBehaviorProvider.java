package com.darinth.wurmunlimited.mod.petcommandoh.behaviorprovider;

import com.darinth.wurmunlimited.mod.petcommandoh.actionperformer.*;
import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.structures.Wall;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetBehaviorProvider implements ModAction, BehaviourProvider {
    private static Logger logger = Logger.getLogger(FollowActionPerformer.class.getName());
    private FollowActionPerformer followActionPerformer;
    private StatusActionPerformer statusActionPerformer;
    private MoveActionPerformer moveActionPerformer;
    private AttackActionPerformer attackActionPerformer;
    private RedirectActionPerformer redirectActionPerformer;
    private AttackMoveActionPerformer attackMoveActionPerformer;
    private LocateActionPerformer locateActionPerformer;

    public PetBehaviorProvider()
    {
        followActionPerformer = new FollowActionPerformer();
        statusActionPerformer = new StatusActionPerformer();
        moveActionPerformer = new MoveActionPerformer();
        attackActionPerformer = new AttackActionPerformer();
        redirectActionPerformer = new RedirectActionPerformer();
        attackMoveActionPerformer = new AttackMoveActionPerformer(attackActionPerformer, moveActionPerformer);
        locateActionPerformer = new LocateActionPerformer();

        ModActions.registerBehaviourProvider(this);
    }

    private LinkedList<ActionEntry> getGeneralActionList(Creature performer)
    {
        if(performer.getPet() == null)
            return null;
        LinkedList<ActionEntry> actionList = new LinkedList<ActionEntry>();
        actionList.add(followActionPerformer.actionEntry);
        actionList.add(locateActionPerformer.actionEntry);
        actionList.add(statusActionPerformer.actionEntry);
        actionList.add(Actions.actionEntrys[41]);
        actionList.add(performer.getPet().isStayonline() ? Actions.actionEntrys[45] : Actions.actionEntrys[44]);
        actionList.add(0, new ActionEntry((short)-actionList.size(), "PetOh", ""));
        return actionList;
    }

    private LinkedList<ActionEntry> getTileActionList(Creature performer)
    {
        if(performer.getPet() == null)
            return null;
        LinkedList<ActionEntry> actionList = new LinkedList<ActionEntry>();
        actionList.add(followActionPerformer.actionEntry);
        actionList.add(moveActionPerformer.actionEntry);
        actionList.add(attackMoveActionPerformer.actionEntry);
        actionList.add(locateActionPerformer.actionEntry);
        actionList.add(statusActionPerformer.actionEntry);
        actionList.add(Actions.actionEntrys[41]);
        actionList.add(performer.getPet().isStayonline() ? Actions.actionEntrys[45] : Actions.actionEntrys[44]);
        actionList.add(0, new ActionEntry((short)-actionList.size(), "PetOh", ""));
        return actionList;
    }

    private LinkedList<ActionEntry> getCreatureActionList(Creature performer, Creature target)
    {
        if(performer.getPet() == null)
            return null;
        LinkedList<ActionEntry> actionList = new LinkedList<ActionEntry>();
        actionList.add(followActionPerformer.actionEntry);
        if (!target.isInvulnerable())
            actionList.add(attackActionPerformer.actionEntry);
        actionList.add(redirectActionPerformer.actionEntry);
        actionList.add(attackMoveActionPerformer.actionEntry);
        actionList.add(locateActionPerformer.actionEntry);
        actionList.add(statusActionPerformer.actionEntry);
        actionList.add(Actions.actionEntrys[41]);
        if (target.getAttitude(performer) != 2 && performer.getPower() <= 0)
            actionList.add(Actions.actionEntrys[43]);
        actionList.add(performer.getPet().isStayonline() ? Actions.actionEntrys[45] : Actions.actionEntrys[44]);
        actionList.add(0, new ActionEntry((short)-actionList.size(), "PetOh", ""));
        return actionList;
    }

    //
    // Tiles
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile) {
        logger.log(Level.INFO, String.format("tile source x/y %s %s %d/%d", performer.getName(), object.getActualName(), tilex, tiley));
        return getTileActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile) {
        logger.log(Level.INFO, String.format("tile x/y %s %d/%d", performer.getName(), tilex, tiley));
        return getTileActionList(performer);
    }

    //
    // ?????????????????????????????????????????????????????????????
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile, int dir) {
        logger.log(Level.INFO, String.format("tile %s %d/%d %b %d %d", performer.getName(), tilex, tiley, onSurface, tile, dir));
        return getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile, int dir) {
        logger.log(Level.INFO, String.format("tile %s %d/%d %b %d %d", performer.getName(), tilex, tiley, onSurface, tile, dir));
        return getGeneralActionList(performer);
    }

    //
    // Tiles border
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
        return getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
        return getGeneralActionList(performer);
    }

    //
    // Tile corners
    //
    @Deprecated
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
        return getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset) {
        return getGeneralActionList(performer);
    }

    @Deprecated
    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
        return getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset) {
        return getGeneralActionList(performer);
    }

    //
    // Creatures
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Creature target) {
        return getCreatureActionList(performer, target);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Creature target) {
        return getCreatureActionList(performer, target);
    }

    //
    // Walls
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Wall target) {
        return getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Wall target) {
        return getGeneralActionList(performer);
    }
}
