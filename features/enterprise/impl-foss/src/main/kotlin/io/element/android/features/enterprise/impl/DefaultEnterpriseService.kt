/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2024, 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.enterprise.impl

import androidx.compose.ui.graphics.Color
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.element.android.compound.colors.SemanticColorsLightDark
import io.element.android.appconfig.AuthenticationConfig
import io.element.android.features.enterprise.api.BugReportUrl
import io.element.android.features.enterprise.api.EnterpriseService
import io.element.android.libraries.matrix.api.core.SessionId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private const val CORP_HOMESERVER_URL = "http://195.133.66.31:8443"
private const val CORP_PUSH_GATEWAY_URL = "http://195.133.66.31:5100/_matrix/push/v1/notify"

@ContributesBinding(AppScope::class)
class DefaultEnterpriseService : EnterpriseService {
    override val isEnterpriseBuild = true

    override suspend fun isEnterpriseUser(sessionId: SessionId) = true

    override fun defaultHomeserverList(): List<String> = listOf(CORP_HOMESERVER_URL)
    override suspend fun isAllowedToConnectToHomeserver(homeserverUrl: String): Boolean {
        return homeserverUrl == CORP_HOMESERVER_URL ||
            homeserverUrl == AuthenticationConfig.MATRIX_ORG_URL
    }

    override suspend fun overrideBrandColor(sessionId: SessionId?, brandColor: String?) = Unit

    override fun brandColorsFlow(sessionId: SessionId?): Flow<Color?> {
        return flowOf(null)
    }

    override fun semanticColorsFlow(sessionId: SessionId?): Flow<SemanticColorsLightDark> {
        return flowOf(SemanticColorsLightDark.default)
    }

    override fun firebasePushGateway(): String? = CORP_PUSH_GATEWAY_URL
    override fun unifiedPushDefaultPushGateway(): String? = CORP_PUSH_GATEWAY_URL

    override fun bugReportUrlFlow(sessionId: SessionId?): Flow<BugReportUrl> {
        return flowOf(BugReportUrl.UseDefault)
    }

    override fun getNoisyNotificationChannelId(sessionId: SessionId): String? = null
}
