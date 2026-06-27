## In-Progress Features

### Tag!
- Use `/tag join` and `/tag leave` to opt in or out
- Use `/tag online` and `/tag list` to get all (online) players opted in
- OPs can use `/tag set` to override the current tagged player
- All players can use `/tag vote` and `/tag unvote` to vote to choose a new random (online, opted in) player to be it.
    - Requires 3 players to vote, unless there's only 2 players on the server.
### Custom Player Heads!
- Players drop Player Heads upon death by a charged creeper!
- There may be a way to make this less painful...
- Wandering Traders will sometimes spawn with a trade to exchanged any Player Head for a Cleaned Player Head!
- Cleaned Player Heads can be set to any texture using `/head <texture>`, where texture can be a player name, a player UUID, or a base64 texture Value.

As an example, if you find a custom Player Head texture online with the following `/give` command:
```
/give @p minecraft:player_head{display:{Name:'{"text":"Egg Basket"}'},SkullOwner:{Id:[I;-500756633,1072776469,-1810775331,-1238834379],Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE2ZDdiMjMyYjhkN2M3MWIxZDRlOTk3YzJkMWEyNGVjOTk3ODc4MWU1OTdmYTI1MWExMGFmNTUxZTBmMjRmNyJ9fX0="}]}}} 1
```
The portion inside `Value:"<texture>"` is what you need, ie you would run the following command:
```
/head eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE2ZDdiMjMyYjhkN2M3MWIxZDRlOTk3YzJkMWEyNGVjOTk3ODc4MWU1OTdmYTI1MWExMGFmNTUxZTBmMjRmNyJ9fX0=
```

### Carry players!
- Right-click with empty hand on a player with a saddle in their helmet slot to ride.
- If you're carrying a player, sneak-left-click to eject them in the direction you're looking.

## TODO
- [ ] Sit up in bed after sleeping
- [ ] auto populate books
- [ ] tiered item despawning - more valuable items take longer
- [ ] chat channels
- [ ] /yoink and /unyoink
- [ ] Tag: require shown on dynmap
- [ ] Tag scoreboard: who's currently it, how long they've been it
- [ ] markdown syntax in chat messages
- [ ] Trident retrieve items
