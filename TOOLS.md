# TOOLS.md - Local Notes

Skills define _how_ tools work. This file is for _your_ specifics — the stuff that's unique to your setup.

## What Goes Here

Things like:

- Camera names and locations
- SSH hosts and aliases
- Preferred voices for TTS
- Speaker/room names
- Device nicknames
- Anything environment-specific

## Examples

```markdown
### Cameras

- living-room → Main area, 180° wide angle
- front-door → Entrance, motion-triggered

### SSH

- home-server → 192.168.1.100, user: admin

### TTS

- Preferred voice: "Nova" (warm, slightly British)
- Default speaker: Kitchen HomePod
```

## Why Separate?

Skills are shared. Your setup is yours. Keeping them apart means you can update skills without losing your notes, and share skills without leaking your infrastructure.

---

## Models

Available providers and recommended use:

- **`minimax/MiniMax-M2.7`** — Primary model for heavy coding tasks, cron agents (github-inklang-coder, trello-agent-queue)
- **`minimax/MiniMax-M2.7-highspeed`** — Same capability, faster output; use when latency matters
- **`zai/glm-4.7`** — Default fallback for interactive/chat sessions
- **`zai/glm-4.7-flash`** / **`glm-4.7-flashx`** — Lighter tasks, quick responses

---

## Available Skills

### Trello
- Skill: `skills/trello/SKILL.md`
- Credentials in env: `TRELLO_API_KEY`, `TRELLO_TOKEN`
- Use `curl` with these env vars to manage boards, lists, and cards

### GitHub
- Skill: `skills/github-cli-tool/SKILL.md`
- Use `gh` CLI — already authenticated as `mintychochip`
- For issues/PRs: `gh issue list`, `gh pr list`, etc.

### Agent Browser
- Skill: `skills/openclaw-agent-browser/SKILL.md`
- Headless browser automation for web tasks

---

Add whatever helps you do your job. This is your cheat sheet.
