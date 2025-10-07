/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.intake

import app.cash.turbine.test
import com.ashwinrao.packup.intake.model.IntakeField
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DirtyDesignatorTest {

    private lateinit var designator: DirtyDesignator

    @Before
    fun setup() {
        designator = DirtyDesignator()
    }

    @Test
    fun `initially all fields are clean`() = runTest {
        designator.isNameFieldDirty.test {
            assertFalse(awaitItem())
        }
        designator.isDescriptionFieldDirty.test {
            assertFalse(awaitItem())
        }
        designator.areRequiredFieldsDirty.test {
            assertFalse(awaitItem())
        }
    }

    @Test
    fun `designateDirty marks single field as dirty`() = runTest {
        designator.isNameFieldDirty.test {
            assertFalse(awaitItem())
            designator.designateDirty(IntakeField.Name)
            assertTrue(awaitItem())
        }
    }

    @Test
    fun `designateDirty marks multiple fields as dirty`() = runTest {
        designator.areRequiredFieldsDirty.test {
            assertFalse(awaitItem())
            designator.designateDirty(*IntakeField.REQUIRED.toTypedArray())
            assertTrue(awaitItem())
        }
    }

    @Test
    fun `designateDirty merges new fields with existing ones`() = runTest {
        designator.isNameFieldDirty.test {
            assertFalse(awaitItem())
            designator.designateDirty(IntakeField.Name)
            assertTrue(awaitItem())
        }
        designator.isDescriptionFieldDirty.test {
            assertFalse(awaitItem())
            designator.designateDirty(IntakeField.Description)
            assertTrue(awaitItem())
        }
    }

    @Test
    fun `designateDirty is idempotent`() = runTest {
        designator.isNameFieldDirty.test {
            assertFalse(awaitItem())
            designator.designateDirty(IntakeField.Name)
            assertTrue(awaitItem())
            designator.designateDirty(IntakeField.Name) // duplicate designation
            expectNoEvents()
        }
    }

    @Test
    fun `areRequiredFieldsDirty only true when all required fields dirty`() = runTest {
        designator.areRequiredFieldsDirty.test {
            assertFalse(awaitItem())
            designator.designateDirty(IntakeField.Name)
            assertFalse(awaitItem()) // both fields need to be dirtied
            designator.designateDirty(*IntakeField.REQUIRED.toTypedArray())
            assertTrue(awaitItem())
        }
    }
}
