package com.flowtube.app.discord

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DiscordFossIsolationTest {
    @Test
    fun `foss classpath excludes functional Discord implementation`() {
        val forbiddenClasses = listOf(
            "com.flowtube.app.discord.DiscordTokenStore",
            "com.flowtube.app.discord.DiscordAuthTokens",
            "com.flowtube.app.discord.DiscordPlaybackSource",
            "com.flowtube.app.discord.DiscordPresenceCoordinator",
            "com.flowtube.app.discord.KizzyDiscordPresenceTransport",
            "com.flowtube.app.discord.KizzyGatewayProtocol",
        )

        forbiddenClasses.forEach { className ->
            assertThat(runCatching { Class.forName(className) }.isFailure).isTrue()
        }
    }

    @Test
    fun `foss runtime reports Discord unavailable`() {
        assertThat(DiscordPresenceRuntime.settingsState.value.isAvailable).isFalse()
        assertThat(DiscordPresenceRuntime.settingsState.value.isEnabled).isFalse()
        assertThat(DiscordPresenceRuntime.settingsState.value.summary)
            .isEqualTo(DiscordSettingsSummary.UNAVAILABLE)
    }
}
