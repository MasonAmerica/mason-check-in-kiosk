package com.bymason.kiosk.checkin.feature.employeefinder

import androidx.core.view.isVisible
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.bymason.kiosk.checkin.CheckInNavHostFragment
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.data.Auth
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.core.model.Guest
import com.bymason.kiosk.checkin.databinding.EmployeeFinderFragmentBinding
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class EmployeeFinderFragmentTest {
    private val mockAuth = mock(Auth::class.java)
    private val mockEmployeeRepository = mock(EmployeeRepository::class.java)

    @Test
    fun `No employees found hint is visible on launch`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = EmployeeFinderFragmentBinding.bind(fragment.requireView())

            assertThat(binding.noEmployeesHint.isVisible).isTrue()
        }
    }

    @Test
    fun `Searching for employee displays results`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = EmployeeFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockEmployeeRepository.find(any())).thenReturn(listOf(
                        Employee("id", "Mr Robot", null)))
            }

            binding.search.setText("Person")
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()

            assertThat(binding.employees.adapter!!.itemCount).isEqualTo(1)
            onView(withId(R.id.name)).check(matches(withText("Mr Robot")))
        }
    }

    @Test
    fun `No employees found hint is hidden with employee results`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = EmployeeFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockEmployeeRepository.find(any())).thenReturn(listOf(
                        Employee("id", "name", null)))
            }

            binding.search.setText("Person")

            assertThat(binding.noEmployeesHint.isVisible).isFalse()
        }
    }

    @Test
    fun `No employees found hint is visible with empty employee results`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = EmployeeFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockEmployeeRepository.find(any())).thenReturn(emptyList())
            }

            binding.search.setText("Person")

            assertThat(binding.noEmployeesHint.isVisible).isTrue()
        }
    }

    @Test
    fun `Selecting employee result navigates to next destination`() {
        val guest = Guest("Name", "foobar@example.com")
        val employee = Employee("id", "name", null)
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment(guest)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            val binding = EmployeeFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockEmployeeRepository.find(any())).thenReturn(listOf(employee))
            }
            binding.search.setText("Person")
        }

        onView(withId(R.id.name)).perform(click())

        verify(mockNavController).navigate(EmployeeFinderFragmentDirections.next(employee, guest))
    }

    private fun launchFragment(
            guest: Guest = Guest("Name", "foobar@example.com")
    ) = launchFragmentInContainer<EmployeeFinderFragment>(
            EmployeeFinderFragmentArgs(guest).toBundle(),
            R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
            CheckInNavHostFragment.Factory(
                    auth = mockAuth,
                    employeeRepository = mockEmployeeRepository
            )
    )
}
