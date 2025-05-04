# SeedSplitter
SeedSplitter is a Minecraft Fabric server only mod for 1.21 and above,
which allows to configure unique seeds for every dimension (by dimension registry key).
## Config
The config can be found in `config/seed-splitter.txt`, if the file is not present it will be created on startup,
and you will be asked to edit it. The config acts an override for the default seed, so that any dimension without an override will use the world seed as expected.
### Config Structure
`<dimension> <seed>`   
Example:   
`minecraft:the_nether 0`   
Note: by default nether and end will be added to the config, if undesired remove the respective lines. Please enter the seed you wish to use instead of the default value 0.
