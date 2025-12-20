package silly.chemthunder.macabre.index;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import silly.chemthunder.macabre.Macabre;

public interface MacabreDamageTypes {
    RegistryKey<DamageType> BUTCHERING = of("butchering");

    static DamageSource butchering(Entity entity) {
        return entity.getDamageSources().create(BUTCHERING); }

    RegistryKey<DamageType> ARROGANCE = of("arrogance");

    static DamageSource arrogance(Entity entity) {
        return entity.getDamageSources().create(ARROGANCE); }

    private static RegistryKey<DamageType> of(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Macabre.id(name));
    }
}
