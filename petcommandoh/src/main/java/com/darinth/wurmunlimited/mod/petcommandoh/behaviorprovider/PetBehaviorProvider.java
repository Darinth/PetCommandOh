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
    private LinkedList<ActionEntry> generalActionList;
    private LinkedList<ActionEntry> tileActionList;
    private LinkedList<ActionEntry> creatureActionList;

    public PetBehaviorProvider()
    {
        FollowActionPerformer followActionPerformer = new FollowActionPerformer();
        StatusActionPerformer statusActionPerformer = new StatusActionPerformer();
        MoveActionPerformer moveActionPerformer = new MoveActionPerformer();
        AttackActionPerformer attackActionPerformer = new AttackActionPerformer();
        RedirectActionPerformer redirectActionPerformer = new RedirectActionPerformer();
        AttackMoveActionPerformer attackMoveActionPerformer = new AttackMoveActionPerformer(attackActionPerformer, moveActionPerformer);
        LocateActionPerformer locateActionPerformer = new LocateActionPerformer();

        ModActions.registerBehaviourProvider(this);
        generalActionList = new LinkedList<ActionEntry>();
        generalActionList.add(followActionPerformer.actionEntry);
        generalActionList.add(locateActionPerformer.actionEntry);
        generalActionList.add(statusActionPerformer.actionEntry);
        generalActionList.add(Actions.actionEntrys[41]);
        generalActionList.add(Actions.actionEntrys[44]);

        tileActionList = new LinkedList<ActionEntry>();
        tileActionList.add(followActionPerformer.actionEntry);
        tileActionList.add(moveActionPerformer.actionEntry);
        tileActionList.add(attackMoveActionPerformer.actionEntry);
        tileActionList.add(locateActionPerformer.actionEntry);
        tileActionList.add(statusActionPerformer.actionEntry);
        tileActionList.add(Actions.actionEntrys[41]);
        tileActionList.add(Actions.actionEntrys[44]);

        creatureActionList = new LinkedList<ActionEntry>();
        creatureActionList.add(followActionPerformer.actionEntry);
        creatureActionList.add(attackActionPerformer.actionEntry);
        creatureActionList.add(redirectActionPerformer.actionEntry);
        creatureActionList.add(attackMoveActionPerformer.actionEntry);
        creatureActionList.add(locateActionPerformer.actionEntry);
        creatureActionList.add(statusActionPerformer.actionEntry);
        creatureActionList.add(Actions.actionEntrys[41]);
        creatureActionList.add(Actions.actionEntrys[44]);


        generalActionList.add(0, new ActionEntry((short)-generalActionList.size(), "PetOh", ""));
        tileActionList.add(0, new ActionEntry((short)-tileActionList.size(), "PetOh", ""));
        creatureActionList.add(0, new ActionEntry((short)-creatureActionList.size(), "PetOh", ""));
    }

    private void updateActionList(Creature performer, LinkedList<ActionEntry> actionList)
    {
        actionList.set(actionList.size()-1, performer.getPet().isStayonline() ? Actions.actionEntrys[45] : Actions.actionEntrys[44]);
    }

    private LinkedList<ActionEntry> getTileActionList(Creature performer)
    {
        updateActionList(performer, tileActionList);
        return tileActionList;
    }

    private LinkedList<ActionEntry> getGeneralActionList(Creature performer)
    {
        updateActionList(performer, generalActionList);
        return generalActionList;
    }

    private LinkedList<ActionEntry> getCreatureActionList(Creature performer)
    {
        updateActionList(performer, creatureActionList);
        return creatureActionList;
    }

    //
    // Tiles
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile) {
        //logger.log(Level.INFO, String.format("tile source x/y %s %s %d/%d", performer.getName(), object.getActualName(), tilex, tiley));
        return performer.getPet() == null ? null : getTileActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile) {
        //logger.log(Level.INFO, String.format("tile x/y %s %d/%d", performer.getName(), tilex, tiley));
        return performer.getPet() == null ? null : getTileActionList(performer);
    }

    //
    // ?????????????????????????????????????????????????????????????
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile, int dir) {
        logger.log(Level.INFO, String.format("tile %s %d/%d %b %d %d", performer.getName(), tilex, tiley, onSurface, tile, dir));
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile, int dir) {
        logger.log(Level.INFO, String.format("tile %s %d/%d %b %d %d", performer.getName(), tilex, tiley, onSurface, tile, dir));
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    //
    // Tiles border
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    //
    // Tile corners
    //
    @Deprecated
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    @Deprecated
    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    //
    // Creatures
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Creature target) {
        return performer.getPet() == null ? null : getCreatureActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Creature target) {
        return performer.getPet() == null ? null : getCreatureActionList(performer);
    }

    //
    // Walls
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Wall target) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Wall target) {
        return performer.getPet() == null ? null : getGeneralActionList(performer);
    }
}
