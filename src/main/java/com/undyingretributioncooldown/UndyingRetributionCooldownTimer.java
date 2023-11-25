package com.undyingretributioncooldown;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.Timer;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class UndyingRetributionCooldownTimer extends Timer
{
    UndyingRetributionCooldownTimer(Duration duration, BufferedImage image, Plugin plugin)
    {
        super(duration.toMillis(), ChronoUnit.MILLIS, image, plugin);
        setTooltip("Time until Undying Retribution is off cooldown");
    }
}
