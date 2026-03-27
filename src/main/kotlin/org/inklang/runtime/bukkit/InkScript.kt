package org.inklang.runtime.bukkit

/**
 * Represents a loaded Ink script
 */
data class InkScript(
    val name: String,
    val file: File,
    val functions: Map<String, KotlinFunction> = internal val loadedAt mutableMapOf ConcurrentHashMap
)

/**
 * Represents a callable Kotlin function within an Ink script
 */
data class KotlinFunction(
    val name: String,
    val function: kotlin.reflect.KFunction
    val parameterTypes: List<KClass>
)
