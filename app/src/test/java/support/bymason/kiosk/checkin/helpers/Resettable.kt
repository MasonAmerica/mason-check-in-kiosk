package support.bymason.kiosk.checkin.helpers

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Resettable<T : Any>(
        private val initializer: () -> T
) : TestWatcher(), ReadOnlyProperty<Any?, T> {
    private var value: T? = null

    override fun finished(description: Description?) {
        super.finished(description)
        value = null
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) =
            value ?: initializer().also { value = it }
}
