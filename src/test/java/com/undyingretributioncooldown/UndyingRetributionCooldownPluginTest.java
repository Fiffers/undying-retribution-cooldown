package com.undyingretributioncooldown;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class UndyingRetributionCooldownPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(UndyingRetributionCooldownPlugin.class);
		RuneLite.main(args);
	}
}