package alex34567.meatrespawner.theoneprobe

import mcjty.theoneprobe.api.ITheOneProbe
import java.util.function.Function

class TheOneProbeSupport : Function<ITheOneProbe, Unit> {
    override fun apply(probe: ITheOneProbe) {
        probe.registerProvider(ProbeInfoMeatRespawner())
    }
}
