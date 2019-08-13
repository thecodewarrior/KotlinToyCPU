package dev.thecodewarrior.kotlincpu.computer.util.extensions

import java.util.prefs.Preferences
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T: Any> Preferences.delegate(def: T, noinline onModification: (value: T) -> T = { it }): PreferenceDelegate<T> {
    if(T::class !in listOf<KClass<*>>(Boolean::class, Double::class, Float::class, Int::class, Long::class, String::class))
        throw IllegalArgumentException("Illegal type ${T::class.simpleName}")

    val get: (String) -> Any = when(T::class) {
        Boolean::class -> { propertyName: String -> this.getBoolean(propertyName, def as Boolean) }
        Double::class -> { propertyName: String -> this.getDouble(propertyName, def as Double) }
        Float::class -> { propertyName: String -> this.getFloat(propertyName, def as Float) }
        Int::class -> { propertyName: String -> this.getInt(propertyName, def as Int) }
        Long::class -> { propertyName: String -> this.getLong(propertyName, def as Long) }
        String::class -> { propertyName: String -> this.get(propertyName, def as String?) }
        else -> { _: String -> null!! } // Should never run because of above `T::class !in listOf(`
    }
    val set: (String, Any) -> Unit = when(T::class) {
        Boolean::class -> { propertyName: String, value: Any -> this.putBoolean(propertyName, value as Boolean) }
        Double::class -> { propertyName: String, value: Any -> this.putDouble(propertyName, value as Double) }
        Float::class -> { propertyName: String, value: Any -> this.putFloat(propertyName, value as Float) }
        Int::class -> { propertyName: String, value: Any -> this.putInt(propertyName, value as Int) }
        Long::class -> { propertyName: String, value: Any -> this.putLong(propertyName, value as Long) }
        String::class -> { propertyName: String, value: Any? -> this.put(propertyName, value as String?) }
        else -> { _: String, _: Any? -> } // Should never run because of above `T::class !in listOf(`
    }

    @Suppress("UNCHECKED_CAST")
    return PreferenceDelegate(get as (String) -> T, set as (String, T) -> Unit, onModification)
}

class PreferenceDelegate<T>(val get: (String) -> T, val set: (String, T) -> Unit, val onModification: (value: T) -> T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get(property.name)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if(get(property.name) != value) {
            val modifiedValue = onModification(value)
            set(property.name, modifiedValue)
        }
    }
}
