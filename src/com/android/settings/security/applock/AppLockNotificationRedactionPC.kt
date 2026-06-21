/*
 * Copyright (C) 2022 FlamingoOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.security.applock

import android.app.AxSandboxManager
import android.content.Context

import androidx.preference.Preference
import androidx.preference.PreferenceScreen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val KEY = "redact_notifications"

class AppLockNotificationRedactionPC(
    context: Context,
    private val packageName: String,
    private val coroutineScope: CoroutineScope
) : AppLockTogglePreferenceController(context, KEY) {

    // TODO: AxSandboxManager does not yet expose per-package notification
    // redaction (no packageData/setShouldRedactNotification equivalent).
    // Disabled until framework support lands.
    private var shouldRedactNotification = false
    private var preference: Preference? = null

    override fun getAvailabilityStatus() = UNSUPPORTED_ON_DEVICE

    override fun isChecked() = shouldRedactNotification

    override fun setChecked(checked: Boolean): Boolean {
        if (shouldRedactNotification == checked) return false
        shouldRedactNotification = checked
        // No backing API yet; toggle is unavailable (see getAvailabilityStatus).
        return false
    }

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)
        preference = screen.findPreference(preferenceKey)
    }
}
