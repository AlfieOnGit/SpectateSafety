# SpectateSafety
_By AlfieJay_

A spectator plugin for events. This plugin is designed to add some security to Minecraft spectator mode to make it more suitable to be used by the playerbase. Most noteably, 1: The player's original gamemode and location can be restored when they leave spectate mode, 2: Players are automatically removed from spectate mode upon leaving the server, 3: Optional "spec points" can be set which players will be teleported to when entering or leaving spectate mode and 4: Players require permission to use the vanilla spectator teleport feature. I also got a bit carried away so there's a fair amount of unnecessary optimisation. Anyway enjoy spectating!

## Commands
**/spec [player]** - Activates spectate mode for the sender or a target player if specified

**/unspec [player]** - Deactivates spectate mode for the sender or a target player if specified

**/specpoint <set/clear>** - Sets the location players will be teleported to when they enable spectate mode

**/unspecpoint <set/clear>** - Sets the location players will be teleported to when they disable spectate mode


## Permissions
**spectatesafety.spectate** - Allows the player to enter spectate mode

**spectatesafety.unspectate** - Allows the player to leave spectate mode

**spectatesafety.teleport** - Allows the player to teleport to other players while in spectate mode

**spectatesafety.spectate.others** - Allows the player to put other players (including themself) in spectate mode

**spectatesafety.unspectate.others** - Allows the player to take other players (including themself) out of spectate mode

**spectatesafety.specpoint** - Allows the player to set or clear the spectate point

**spectatesafety.unspecpoint** - Allows the player to set or clear the unspectate point
