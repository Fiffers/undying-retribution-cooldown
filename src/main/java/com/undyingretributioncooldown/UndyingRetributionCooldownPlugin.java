package com.undyingretributioncooldown;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ImageUtil;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@PluginDescriptor(
	name = "Undying Retribution Cooldown",
	description = "Shows you how long it'll be until your Undying Retribution relic is off cooldown",
	tags = {"undying", "retribution", "cooldown", "league", "leagues", "trailblazer", "relic"}
)
public class UndyingRetributionCooldownPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private UndyingRetributionCooldownConfig config;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Getter
	private UndyingRetributionCooldownTimer timer;

	@Getter
	Instant timerStart;

	private static final BufferedImage UNDYING_RETRIBUTION_IMAGE;
	private static final String ON_COOLDOWN_MESSAGE;
	private static final String ON_LOGIN_COOLDOWN_MESSAGE;
	private static final String OFF_COOLDOWN_MESSAGE;
	private static final String ON_DEATH_MESSAGE;

	static
	{
		ON_LOGIN_COOLDOWN_MESSAGE = "Undying Retribution Relic's effect is still on cooldown.";
		ON_COOLDOWN_MESSAGE = "Your Undying Retribution Relic saves your life. The Relic has lost power for 3 minutes.";
		OFF_COOLDOWN_MESSAGE = "You are able to benefit from the Undying Retribution Relic's effect.";
		ON_DEATH_MESSAGE = "Oh dear, you are dead!";
		UNDYING_RETRIBUTION_IMAGE = ImageUtil.loadImageResource(UndyingRetributionCooldownPlugin.class, "cooldown_icon.png");
	}

	@Subscribe
	public void onChatMessage(ChatMessage message)
	{
		if (message.getType() == ChatMessageType.GAMEMESSAGE && (message.getMessage().contains(ON_COOLDOWN_MESSAGE) || message.getMessage().contains(ON_LOGIN_COOLDOWN_MESSAGE)))
		{
			this.addTimer();
		} else if (message.getType() == ChatMessageType.GAMEMESSAGE && (message.getMessage().contains(OFF_COOLDOWN_MESSAGE) || message.getMessage().contains(ON_DEATH_MESSAGE)))
		{
			this.removeTimer();
		}
	}

	private void removeTimer()
	{
		infoBoxManager.removeInfoBox(timer);
		timer = null;
		timerStart = null;
	}

	private void addTimer()
	{
		final Instant now = Instant.now();
		Duration duration = Duration.ofMinutes(3);
		timer = new UndyingRetributionCooldownTimer(duration, UNDYING_RETRIBUTION_IMAGE, this);
		timerStart = now;
		infoBoxManager.addInfoBox(timer);
	}

	@Provides
	UndyingRetributionCooldownConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(UndyingRetributionCooldownConfig.class);
	}
}
