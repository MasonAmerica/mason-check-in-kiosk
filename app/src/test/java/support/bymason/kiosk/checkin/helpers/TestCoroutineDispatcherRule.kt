package support.bymason.kiosk.checkin.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.DelayController
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import support.bymason.kiosk.checkin.core.data.DispatcherProvider

class TestCoroutineDispatcherRule(
        private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), DelayController by dispatcher {
    val dispatchers = object : DispatcherProvider {
        override val main = dispatcher
        override val default = dispatcher
        override val io = dispatcher
        override val unconfined = dispatcher
    }

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    fun runBlocking(block: suspend TestCoroutineScope.() -> Unit) =
            dispatcher.runBlockingTest(block)
}
