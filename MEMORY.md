# MEMORY.md - Long-term Memory

## Coding Preferences

**Use Codex for ALL coding tasks**
- Tool: Codex (OpenAI coding agent)
- **IMPORTANT:** Do NOT use Claude Code, OpenCode, or any other coding agents
- User preference: Codex only
- Use coding-agent skill to spawn Codex sub-agents for coding work

## Superpowers Skill

**Superpowers by @obra** - Enhanced agent capabilities
- Installed: superpowers skill
- Author: @obra
- Location: /home/justin-lo/.openclaw/workspace/skills/superpowers/
- Purpose: Advanced reasoning, memory management, error recovery, tool orchestration
- Features: Complex problem decomposition, context awareness, self-correction
- Usage: Automatically active - enhances all agent operations

## Trello Integration

**Board:** "Dev Work"
- URL: https://trello.com/b/IEjcgqb4/dev-work
- Lists: Backlog, Agent Queue, In Progress, Done
- Created: 2026-03-24
- Can manage via Trello skill (TRELLO_API_KEY and TRELLO_TOKEN are set)

## Security Tools

**Skill Vetter** - Security-first vetting for AI agent skills
- Installed: skill-vetter v1.0.0
- Location: /home/justin-lo/.openclaw/workspace/skills/skill-vetter/
- Purpose: Vet any new skills before installation
- Checks: Red flags, permission scope, suspicious patterns
- Usage: Always run skill-vetter before installing unknown skills

## GitHub Integration

**GitHub Skill** - Interact with GitHub via `gh` CLI
- Installed: github v1.0.0
- Author: @steipete (top ClawHub contributor)
- Location: /home/justin-lo/.openclaw/workspace/skills/github/
- Vetted: ✅ Safe (🟢 LOW risk, 348.3K downloads, 130K stars)
- Purpose: Manage repos, issues, PRs, CI runs
- Authenticated as: mintychochip
- Usage: Use `gh issue`, `gh pr`, `gh run`, `gh api` commands

## Browser Automation

**Agent Browser** - Headless browser automation for AI agents
- Skill: agent-browser v0.2.0
- CLI: agent-browser v0.22.3
- Author: @thesethrose
- Location: /home/justin-lo/.openclaw/workspace/skills/agent-browser/
- Vetted: ⚠️ Medium risk (instruction-only with external CLI)
- Purpose: Navigate, click, fill forms, screenshot, extract data
- Browser: Chrome 147.0.7727.24 (installed at ~/.agent-browser/browsers/)
- Usage: `agent-browser open <url>`, `snapshot -i`, `click @e1`, `fill @e2 "text"`
- **Note:** Use responsibly - can access any website

**Playwright CLI** - End-to-end testing and browser automation
- Version: 1.58.2
- Purpose: E2E testing, code generation, browser automation
- Browser: Chromium headless shell 145.0.7632.6 installed
- Location: /home/justin-lo/.cache/ms-playwright/
- Usage: `playwright codegen <url>` (records actions, generates code)
- Features: Multi-browser support, codegen in JS/Python/C#/Java, geolocation, proxies
- When to use: Complex automation, code generation, cross-browser testing
