package org.inklang.runtime.bukkit

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.entity.Player
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import javax.script.ScriptEngineManager
import javax.script.ScriptEngine

/**
 * Core runtime for executing Ink scripts on Bukkit
 * 
 * This runtime:
 * - Loads .ink script files
 * - Compiles them to executable bytecode (using Quill)
 * - Executes functions within scripts
 * - Provides API bindings to Bukkit
 */
class BukkitInkRuntime(private val plugin: JavaPlugin) {
    
    private val scripts = ConcurrentHashMap<String, InkScript>()
    private val scriptEngine: ScriptEngine? = try {
        ScriptEngineManager().getEngineByExtension("kotlin") ?: 
        ScriptEngineManager().getEngineByName("kotlin")
    } catch (e: Exception) {
        plugin.logger.warning("Kotlin script engine not available: ${e.message}")
        null
    }
    
    /**
     * Load an Ink script from a file
     */
    fun loadScript(file: File): InkScript {
        val name = file.nameWithoutExtension
        val source = file.readText()
        
        plugin.logger.info("Loading script: $name from ${file.absolutePath}")
        
        // Create script object
        val script = InkScript(
            name = name,
            source = source,
            file = file,
            runtime = this
        )
        
        // Compile the script (for now, we'll use Kotlin script engine)
        // TODO: Replace with Quill compiler when available
        script.compile()
        
        // Store in memory
        scripts[name] = script
        
        return script
    }
    
    /**
     * Get a loaded script by name
     */
    fun getScript(name: String): InkScript? = scripts[name]
    
    /**
     * Execute a function in a script
     */
    fun execute(script: InkScript, functionName: String, args: List<Any?> = emptyList()): Any? {
        try {
            // For now, use Kotlin script engine
            // TODO: Replace with actual Ink execution
            
            val bindings = scriptEngine?.createBindings()
            if (bindings != null) {
                // Inject Bukkit API
                bindings["server"] = plugin.server
                bindings["plugin"] = plugin
                bindings["logger"] = plugin.logger
                
                // Add arguments
                args.forEachIndexed { index, arg ->
                    bindings["arg$index"] = arg
                }
                
                // Execute the script
                scriptEngine?.eval(script.source, bindings)
                
                // Try to call the function
                val functionCall = buildFunctionCall(functionName, args)
                return scriptEngine?.eval(functionCall, bindings)
            }
            
            return null
        } catch (e: Exception) {
            plugin.logger.severe("Error executing script ${script.name}: ${e.message}")
            e.printStackTrace()
            return null
        }
    }
    
    /**
     * Unload all scripts
     */
    fun unloadAll() {
        scripts.clear()
        plugin.logger.info("Unloaded all Ink scripts")
    }
    
    /**
     * Get count of loaded scripts
     */
    val scriptCount: Int
        get() = scripts.size
    
    /**
     * Get names of all loaded scripts
     */
    fun getLoadedScripts(): List<String> = scripts.keys.toList()
    
    /**
     * Build a function call string for the script engine
     */
    private fun buildFunctionCall(functionName: String, args: List<Any?>): String {
        val argList = args.mapIndexed { index, _ -> "arg$index" }.joinToString(", ")
        return "$functionName($argList)"
    }
    
    /**
     * Check if Quill compiler is available
     */
    fun isQuillAvailable(): Boolean {
        // TODO: Check for Quill compiler JAR
        return false
    }
    
    /**
     * Compile using Quill (when available)
     */
    fun compileWithQuill(source: String): ByteArray? {
        if (!isQuillAvailable()) {
            plugin.logger.warning("Quill compiler not available, using fallback")
            return null
        }
        
        // TODO: Integrate with Quill compiler
        // val compiler = QuillCompiler()
        // return compiler.compile(source)
        return null
    }
    
    private val File.nameWithoutExtension: String
        get() = name.substringBeforeLast('.')
}
