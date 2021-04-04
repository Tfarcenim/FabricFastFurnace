# Fast Furnace for Fabric

## About

**Based on FastFurnace by Shadows, ported to Fabric**

This mod does a few things relating to the vanilla furnace, blast furnace, and smoker so that they run faster during their update method, improving TPS.

- Similar to FastWorkbench, it caches the last recipe used, and checks this recipe first before re-scanning the entire registry.  This is significantly faster due to the vanilla furnace scanning the entire furnace registry each tick, which may be fine for vanilla, but in a modded environment, gets very large very quickly.
- The vanilla furnace does a very stupid thing when querying burn times, it gets the burntime of a fuel from a static method that recreates the entire map before returning it.  This mod caches the fuel to burntime map so that it's only recreated when datapacks are reloaded, which saves some additional cpu time, especially with large numbers of furnaces.
- Additionally, the mod fixes a bug in serialization of the furnace, which would cause burn times above 32767 to be ignored during saving (as the burn time was cast to a short even through an int was written).

These changes apply to the Furnace, Blast Furnace, and Smoker.  Mods should follow the same practices that this mod does in order to ensure fewer loops over the smelting list.

*Note: This mod does NOT increase smelting speed!*

## License

This mod is under the CC0 license.
