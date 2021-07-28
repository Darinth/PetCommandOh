# Pet Command Oh

Requires [Ago's Server Mod Launcher](https://github.com/ago1024/WurmServerModLauncher/releases) to run.

This mod is free software: you can redistribute it and/or modify it under the terms of the MIT License found found within this repository.

#### Introduction
This mod will add a new PetOh menu to a variety of objects, giving you options similar to the standard Pet menu.

####Details
Following actions will be added.
* Nearly everywhere in the world
  * PetOh > Follow: Will cause your pet to begin following you as if you were leading it. Uses the exact same code as leading in fact.
* Tiles
  * PetOh > Move: Causes your pet to move here. Functionally identical to the Pet > Go Here command, except where noted below.
* Creatures
  * PetOh > Attack: Causes your pet to attack the selected. Mostly the same as the standard Pet > Attack command, except not limited to aggressive creatures and you are allowed to send your pets after uniques.
  * PetOh > Redirect: Attempts to redirect the targeted creature to your Pet. Similar to taunting in most fashions (even uses taunt as it's skill for determining success), but on a successful taunt instead of the creature attacking you it begins attacking your pet.

In addition, all PetOh commands will automatically cancel any previous orders (no order queueing) & break following. The intention is to also eventually make it break attacking so you can call your pets back to you from dangerous/bad situations, but that's not done/working yet.

#### All options

None yet. Will probably add in some options later.

#### Future plans
* Add in the rest of the current Pet commands. Delete the vanilla pet command (probably configurable with options)
