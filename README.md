# Long Shoes (Fabric, Minecraft 1.21.11)

Client-side cosmetic mod: every player gets long pointed shoes.
Only people who have the mod installed will see them (the server needs nothing).

## How to build

1. Go to https://fabricmc.net/develop/template and generate a project:
   - Minecraft version: 1.21.11, Yarn mappings
   - Mod name: Long Shoes, mod id: `longshoes`, package: `com.example.longshoes`
   - If it offers "split client and common sources", turn it off
     (otherwise put the two .java files into `src/client/java/...` instead of `src/main/java/...`).
2. Unpack the template and DELETE its example files:
   `ExampleMod.java`, `ExampleModClient.java` (if any), everything in `mixin` folders,
   and any `*.mixins.json` in resources.
3. Copy the `src` folder from this archive into the template (merge, overwrite `fabric.mod.json`).
4. Install JDK 21, open a terminal in the project folder and run:
   - Windows: `gradlew build`
   - Linux/macOS: `./gradlew build`
5. The finished mod is in `build/libs/` (take the file WITHOUT `-sources` in its name).
   Put it in `mods/` together with Fabric API.

To test without building a jar: `gradlew runClient`.

## Tweaking the look

Open `LongShoesFeatureRenderer.java`, method `buildShoe()`.
Each `.cuboid(x, y, z, sizeX, sizeY, sizeZ)` is one box. Make the toe longer by
making the last Z values more negative, or change the texture in
`assets/longshoes/textures/entity/long_shoes.png`.
