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
import android.provider.Settings

import androidx.preference.ListPreference
import androidx.preference.Preference

import com.android.settings.core.BasePreferenceController

class AppLockTimeoutPreferenceController(
    context: Context,
    key: String,
) : BasePreferenceController(context, key),
    Preference.OnPreferenceChangeListener {

    override fun getAvailabilityStatus() = AVAILABLE

    override fun updateState(preference: Preference) {
        val timeout = Settings.Secure.getLong(
            mContext.contentResolver,
            AxSandboxManager.SETTING_LOCK_TIMEOUT,
            AxSandboxManager.DEFAULT_LOCK_TIMEOUT.toLong()
        )
        (preference as ListPreference).value = timeout.takeIf { it != -1L }?.toString()
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        Settings.Secure.putLong(
            mContext.contentResolver,
            AxSandboxManager.SETTING_LOCK_TIMEOUT,
            (newValue as String).toLong()
        )
        return true
    }
}
