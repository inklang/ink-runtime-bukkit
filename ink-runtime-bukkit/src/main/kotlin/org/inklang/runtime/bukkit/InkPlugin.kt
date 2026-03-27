package org.inklang.runtime.bukkit

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.ChatColor
import java.io.File
import java.nio.file.Files

/**
 * Main Bukkit plugin class for Ink Runtime
 * 
 * This plugin loads and executes Ink programming language scripts
 * on Bukkit/Paper Minecraft servers.
 */
class InkPlugin : JavaPlugin() {
    
    private lateinit var runtime: BukkitInkRuntime
    private lateinit var scriptsFolder: File
    
    override fun onEnable() {
        // Initialize runtime
        runtime = BukkitInkRuntime(this)
        
        // Create scripts folder
        scriptsFolder = File(dataFolder, "scripts")
        if (!scriptsFolder.exists()) {
            scriptsFolder.mkdirs()
            logger.info("Created scripts folder at ${scriptsFolder.absolutePath}")
            
            // Create example script
            createExampleScript()
        }
        
        // Load all scripts on startup
        loadAllScripts()
        
        logger.info("Ink Runtime enabled! Scripts folder: ${scriptsFolder.absolutePath}")
        logger.info("Loaded ${runtime.scriptCount} Ink scripts")
    }
    
    override fun onDisable() {
        runtime.unloadAll()
        logger.info("Ink Runtime disabled")
    }
    
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when (command.name.lowercase()) {
            "ink" -> {
                if (args.isEmpty()) {
                    sendHelp(sender)
                    return true
                }
                
                when (args[0].lowercase()) {
                    "reload" -> {
                        if (!sender.hasPermission("ink.admin")) {
                            sender.sendMessage("${ChatColor.RED}You don't have permission!")
                            return true
                        }
                        runtime.unloadAll()
                        loadAllScripts()
                        sender.sendMessage("${ChatColor.GREEN}Reloaded ${runtime.scriptCount} Ink scripts!")
                    }
                    "run" -> {
                        if (args.size < 2) {
                            sender.sendMessage("${ChatColor.RED}Usage: /ink run <script>")
                            return true
                        }
                        
                        if (!sender.hasPermission("ink.use")) {
                            sender.sendMessage("${ChatColor.RED}You don't have permission!")
                            return true
                        }
                        
                        val scriptName = args[1]
                        val script = runtime.getScript(scriptName)
                        
                        if (script == null) {
                            sender.sendMessage("${ChatColor.RED}Script '$scriptName' not found!")
                            return true
                        }
                        
                        // Execute main function
                        val result = runtime.execute(script, "main", listOf(sender))
                        sender.sendMessage("${ChatColor.GREEN}Script executed: $result")
                    }
                    "list" -> {
                        val scripts = runtime.getLoadedScripts()
                        if (scripts.isEmpty()) {
                            sender.sendMessage("${ChatColor.YELLOW}No scripts loaded")
                        } else {
                            sender.sendMessage("${ChatColor.GREEN}Loaded scripts:")
                            scripts.forEach { sender.sendMessage("${ChatColor.GRAY}- $it") }
                        }
                    }
                    else -> sendHelp(sender)
                }
            }
        }
        return true
    }
    
    private fun sendHelp(sender: CommandSender) {
        sender.sendMessage("${ChatColor.GOLD}=== Ink Runtime ===")
        sender.sendMessage("${ChatColor.YELLOW}/ink reload ${ChatColor.GRAY}- Reload all scripts")
        sender.sendMessage("${ChatColor.YELLOW}/ink run <script> ${ChatColor.GRAY}- Execute a script")
        sender.sendMessage("${ChatColor.YELLOW}/ink list ${ChatColor.GRAY}- List loaded scripts")
    }
    
    private fun loadAllScripts() {
        scriptsFolder.listFiles()
            ?.filter { it.extension == "ink" }
            ?.forEach { file ->
                try {
                    val script = runtime.loadScript(file)
                    logger.info("Loaded script: ${script.name}")
                } catch (e: Exception) {
                    logger.severe("Failed to load script ${file.name}: ${e.message}")
                    e.printStackTrace()
                }
            }
    }
    
    private fun createExampleScript() {
        val exampleScript = File(scriptsFolder, "hello.ink")
        if (!exampleScript.exists()) {
            exampleScript.writeText("""
                // Example Ink script
                // This demonstrates basic Bukkit API usage
                
                fun main(sender) {
                    sender.sendMessage("Hello from Ink!")
                    sender.sendMessage("You are at: " + sender.location.toString())
                    
                    if (sender is Player) {
                        sender.sendMessage("Your health: " + sender.health)
                        sender.sendMessage("Your level: " + sender.level)
                    }
                }
                
                fun greet(player, message) {
                    player.sendMessage("§a" + message)
                }
            """.trimIndent())
            logger.info("Created example script at ${exampleScript.absolutePath}")
        }
    }
    
    private val File.extension: String
        get() = name.substringAfterLast('.', "")
}
