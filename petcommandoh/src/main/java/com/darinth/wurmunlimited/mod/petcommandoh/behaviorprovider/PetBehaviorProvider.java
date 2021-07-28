package com.darinth.wurmunlimited.mod.petcommandoh.behaviorprovider;

import com.darinth.wurmunlimited.mod.petcommandoh.actionperformer.AttackActionPerformer;
import com.darinth.wurmunlimited.mod.petcommandoh.actionperformer.FollowActionPerformer;
import com.darinth.wurmunlimited.mod.petcommandoh.actionperformer.MoveActionPerformer;
import com.darinth.wurmunlimited.mod.petcommandoh.actionperformer.RedirectActionPerformer;
import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.behaviours.ActionEntry;
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
    private List<ActionEntry> generalActionList;
    private List<ActionEntry> tileActionList;
    private List<ActionEntry> creatureActionList;

    public PetBehaviorProvider()
    {
        FollowActionPerformer followActionPerformer = new FollowActionPerformer();
        MoveActionPerformer moveActionPerformer = new MoveActionPerformer();
        AttackActionPerformer attackActionPerformer = new AttackActionPerformer();
        RedirectActionPerformer redirectActionPerformer = new RedirectActionPerformer();

        ModActions.registerBehaviourProvider(this);
        generalActionList = new LinkedList<ActionEntry>();
        generalActionList.add(followActionPerformer.actionEntry);

        tileActionList = new LinkedList<ActionEntry>();
        tileActionList.add(followActionPerformer.actionEntry);
        tileActionList.add(moveActionPerformer.actionEntry);

        creatureActionList = new LinkedList<ActionEntry>();
        creatureActionList.add(followActionPerformer.actionEntry);
        creatureActionList.add(attackActionPerformer.actionEntry);
        creatureActionList.add(redirectActionPerformer.actionEntry);

        generalActionList.add(0, new ActionEntry((short)-generalActionList.size(), "PetOh", ""));
        tileActionList.add(0, new ActionEntry((short)-tileActionList.size(), "PetOh", ""));
        creatureActionList.add(0, new ActionEntry((short)-creatureActionList.size(), "PetOh", ""));
    }

    //
    // Tiles
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile) {
        logger.log(Level.INFO, String.format("tile source x/y %s %s %d/%d", performer.getName(), object.getActualName(), tilex, tiley));
        return performer.getPet() == null ? null : tileActionList;
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile) {
        logger.log(Level.INFO, String.format("tile x/y %s %d/%d", performer.getName(), tilex, tiley));
        return performer.getPet() == null ? null : tileActionList;
    }

    //
    // ?????????????????????????????????????????????????????????????
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile, int dir) {
        logger.log(Level.INFO, String.format("tile %s %d/%d %b %d %d", performer.getName(), tilex, tiley, onSurface, tile, dir));
        return performer.getPet() == null ? null : generalActionList;
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile, int dir) {
        logger.log(Level.INFO, String.format("tile %s %d/%d %b %d %d", performer.getName(), tilex, tiley, onSurface, tile, dir));
        return performer.getPet() == null ? null : generalActionList;
    }

    //
    // Tiles border
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
        return performer.getPet() == null ? null : generalActionList;
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
        return performer.getPet() == null ? null : generalActionList;
    }

    //
    // Tile corners
    //
    @Deprecated
    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
        return performer.getPet() == null ? null : generalActionList;
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset) {
        return performer.getPet() == null ? null : generalActionList;
    }

    @Deprecated
    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
        return performer.getPet() == null ? null : generalActionList;
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, int heightOffset) {
        return performer.getPet() == null ? null : generalActionList;
    }

    //
    // Creatures
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Creature target) {
        return performer.getPet() == null ? null : creatureActionList;
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Creature target) {
        return performer.getPet() == null ? null : creatureActionList;
    }

    //
    // Walls
    //
    public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Wall target) {
        return performer.getPet() == null ? null : generalActionList;
    }

    public List<ActionEntry> getBehavioursFor(Creature performer, Wall target) {
        return performer.getPet() == null ? null : generalActionList;
    }
}
