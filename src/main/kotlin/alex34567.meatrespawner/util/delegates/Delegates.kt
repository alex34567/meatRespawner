package alex34567.meatrespawner.util.delegates

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

class ForgeInject<T>(private val backing: KProperty0<T?>) : ReadOnlyProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return backing()!!
    }
}