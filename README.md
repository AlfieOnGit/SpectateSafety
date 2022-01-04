![SpectateSafety](https://user-images.githubusercontent.com/67489447/147997514-5352daea-283d-4ea3-800e-3a521da2f555.png)
<div align="center"> <a href="https://www.spigotmc.org/resources/spectate-safety.98654/">Spigot</a> - <a href="https://discord.gg/3CgM8nnNEY">Discord</a>  </div>  
<br>
A spectate plugin designed to add some additional security and usability to Vanilla Minecraft's spectator mode, providing servers with an easy-to-use spectate mechanic to be used for events with a playerbase. It's designed to be simple but useful in preventing exploits. I also got a bit carried away so there's a fair amount of unnecessary optimisation. Anyway enjoy and reach out if you've found this plugin useful, I'd love to hear <3

## How it works
**In its most basic form:**  
A player's location and gamemode is stored when they enter spectate mode. Then, when they unspectate, it teleports them back to where they were and puts them back in their original gamemode. Additionally, players are automatically removed from spectate mode when they leave or are kicked from the server

**Spectate points:**  
Server admins also have the option to set one "spec point" and one "unspec point". If the spec point is set, any player entering spectate mode will be teleported there. And the unspec point works the same way but with players exiting spectate mode. If the respective point isn't set, then the command will work as stated in the "In it's most basic form" paragraph

**For event staff:**  
Those with the required permissions are allowed to put specified players in spectate mode or take them out of it. Their locations will still be saved and will be put back upon exiting spectate mode. Just remember, if you don't want players to be able to unspectate after staff place them in spectate, you need to remove their permission to the unspectate command

**Spectate teleport:**  
Server admins can specify whether or not they'd like players in spectate mode to be able to use the Vanilla spectator teleport feature. This is done using the `spectatesafety.teleport` permission

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
