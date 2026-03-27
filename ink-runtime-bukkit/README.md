# Ink Runtime for Bukkit

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](![Paper](https://img.shields.io/badge/Paper-1.21.4-blue.svg)](![License](https://img.shields.io/badge/License-MIT-green.svg)](![Status](https://img.shields.io/badge/Status-Work%20In%20Progress-yellow.svg)]

> **Bukkit/Paper runtime for the [Ink programming language](https://github.com/inklang/ink) scripts

## Overview

This plugin enables running [Ink](https://github.com/inklang/ink) scripts on Minecraft Bukkit/Paper servers. It uses the [Quill compiler](https://github.com/inklang/quill) to compile Ink to Kotlin/Java bytecode at and executes it in-game.

## Features

- ✅ **Hot Reload** - Automatically reload scripts when files change
- ✅ **Kotlin Scripting** - Uses Kotlin script engine for execution
- ✅ **Bukkit API Bindings** - Access to Player, World, Server, and more
- ✅ **Easy to Use** - Simple API for script development
- ✅ **Type-Safe** - Full Kotlin type system support

## Installation

### Requirements

- Java 21+
- Paper/Spigot 1.21.4+
- Kotlin 1.9.22+

### Building

```bash
./gradlew build
```

The output JAR will be in `build/libs/ink-runtime-bukkit-1.0.0-SNAPSHOT.jar`

### Installation

1. Build the plugin
2. Place the JAR in your server's `plugins/` folder
3. Start the server
4. Scripts will be loaded from `plugins/InkRuntime/scripts/`

## Usage

### Commands

- `/ink reload` - Reload all scripts
- `/ink run <script>` - Execute a script
- `/ink list` - List loaded scripts

### Creating Scripts

Place `.ink` files in `plugins/InkRuntime/scripts/`:

#### Example Script

```kotlin
// hello.ink
fun main(sender) {
    sender.sendMessage("§aHello from Ink!")
    sender.sendMessage("§7You are at: ${sender.location}")
    
    if (sender is Player) {
        sender.sendMessage("§eHealth: ${sender.health}")
        sender.sendMessage("§bLevel: ${sender.level}")
    }
}

fun greet(player, message) {
    player.sendMessage("§a$message")
}
```

#### Calling Functions

```kotlin
// From another script or val script = inkRuntime.getScript("hello")
val result = script?.execute("greet", listOf(player, "Welcome!"))
```

## API Bindings

### Player

- `sender.sendMessage(message: String)`
- `sender.location` - Location object
- `sender.health` - Double
- `sender.level` - Int
- `sender.teleport(location: Location)`
- `sender.inventory` - PlayerInventory

### World

- `sender.world` - World object
- `sender.world.time` - Long
- `sender.world.weather` - Weather

### Server

- `Bukkit.broadcast(message: String)`
- `Bukkit.getPlayer(name: String)` - Player?
- `Bukkit.getOnlinePlayers()` - Collection<Player>

## Configuration

Edit `config.yml` in `plugins/InkRuntime/`:

```yaml
debug: false
auto-reload: true
script-timeout: 5000
max-scripts: 50
```

## Development

### Project Structure

```
ink-runtime-bukkit/
├── src/main/kotlin/org/inklang/runtime/bukkit/
│   ├── BukkitInkRuntime.kt
│   ├── InkPlugin.kt
│   └── InkScript.kt
├── src/main/resources/
│   ├── plugin.yml
│   └── config.yml
└── build.gradle.kts
```

### Building from Source

```bash
git clone https://github.com/inklang/ink-runtime-bukkit.git
cd ink-runtime-bukkit
./gradlew build
```

## Troubleshooting

### Scripts not loading

1. Check console for errors
2. Verify file extension is `.ink`
3. Check file syntax
4. Enable debug mode in config.yml

### Performance issues

- Reduce `max-scripts` in config
- Disable `auto-reload` if not needed
- Check script complexity

## License

MIT License

## Contributing

1. Fork the repository
2. Create feature branch
3. Make changes
4. Submit pull request

## Support

- GitHub Issues: https://github.com/inklang/ink-runtime-bukkit/issues
- Discord: https://discord.gg/inklang
- Wiki: https://github.com/inklang/ink-runtime-bukkit/wiki

## Credits

- Ink Language Team
- Quill Compiler
- Bukkit/Paper Team

## Roadmap

- [ ] Better error reporting
- [ ] More API bindings
- [ ] IDE support
- [ ] Hot reload improvements
- [ ] Performance optimizations
- [ ] Testing framework
