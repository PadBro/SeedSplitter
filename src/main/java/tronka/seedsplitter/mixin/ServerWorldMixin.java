package tronka.seedsplitter.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tronka.seedsplitter.SeedSplitter;

import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Unique
    private long seed;

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void init(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionOptions dimensionOptions, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, RandomSequencesState randomSequencesState, CallbackInfo ci) {
        this.seed = SeedSplitter.getSeed(server, worldKey);
        ((World)(Object)this).biomeAccess = new BiomeAccess((BiomeAccess.Storage) this, BiomeAccess.hashSeed(this.seed));
    }

    @Redirect(method = {"<init>", "getSeed", "locateStructure"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SaveProperties;getGeneratorOptions()Lnet/minecraft/world/gen/GeneratorOptions;"))
    private GeneratorOptions generatorOptionsWithSeed(SaveProperties instance) {
        return instance.getGeneratorOptions().withSeed(OptionalLong.of(this.seed));
    }
}
