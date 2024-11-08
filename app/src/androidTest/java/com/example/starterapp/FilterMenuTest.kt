package com.example.starterapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.starterapp.components.FilterMenu
import com.example.starterapp.models.ToDoFilter
import com.example.starterapp.models.DoneStatusFilter
import com.example.starterapp.models.CreatedDateFilter
import com.example.starterapp.viewModels.ToDoViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterMenuTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFilterMenuSelection() {
        // Initialize the ViewModel and set up the Composable
        val toDoViewModel = ToDoViewModel()

        composeTestRule.setContent {
            FilterMenu(toDoViewModel = toDoViewModel)
        }

        // Open the filter menu
        composeTestRule.onNodeWithContentDescription("Filter")
            .performClick()

        // Select the "Done" filter option
        composeTestRule.onNodeWithText("Done")
            .performClick()

        // Verify that the filter was set to "Done"
        assert(toDoViewModel.filter.value?.doneStatus == DoneStatusFilter.DONE)

        // Open the filter menu again
        composeTestRule.onNodeWithContentDescription("Filter")
            .performClick()

        // Select the "Not Done" filter option
        composeTestRule.onNodeWithText("Not Done")
            .performClick()

        // Verify that the filter was set to "Not Done"
        assert(toDoViewModel.filter.value?.doneStatus == DoneStatusFilter.NOT_DONE)

        // Open the filter menu again
        composeTestRule.onNodeWithContentDescription("Filter")
            .performClick()

        // Select the "Today" created date filter option
        composeTestRule.onNodeWithText("Today")
            .performClick()

        // Verify that the filter was set to "Today"
        assert(toDoViewModel.filter.value?.createdDateFilter == CreatedDateFilter.TODAY)
    }
}
