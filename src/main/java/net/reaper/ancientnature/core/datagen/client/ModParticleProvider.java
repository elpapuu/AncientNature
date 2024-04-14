package net.reaper.ancientnature.core.datagen.client;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModParticles;

public class ModParticleProvider extends ParticleDescriptionProvider {
    /**
     * Creates an instance of the data provider.
     *
     * @param output     the expected root directory the data generator outputs to
     * @param fileHelper the helper used to validate a texture's existence
     */
    public ModParticleProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(ModParticles.REVIVAL_STAND_PARTICLE.get(), AncientNature.modLoc("amber_revival_particle"));
    }
}
