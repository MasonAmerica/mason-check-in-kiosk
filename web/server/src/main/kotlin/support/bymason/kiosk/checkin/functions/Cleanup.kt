package support.bymason.kiosk.checkin.functions

import firebase.firestore.DocumentSnapshot
import firebase.firestore.Timestamp
import firebase.firestore.Timestamps
import firebase.functions.admin
import firebase.functions.epoch
import firebase.functions.moment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlin.js.Promise

const val SESSION_TTL_HOURS = 1
const val SESSION_DATA_RETENTION_DAYS = 60

fun cleanupIncompleteSessions(): Promise<*>? = GlobalScope.async {
    val incompleteSessions = admin.firestore().collectionGroup("guest-visits")
            .where("state", ">=", 0)
            .get().await()

    supervisorScope {
        incompleteSessions.docs.map {
            async {
                cleanupIncompleteSession(it)
            }
        }
    }.awaitAll()
}.asPromise()

fun cleanupCompletedSessions(): Promise<*>? = GlobalScope.async {
    val oldSessions = admin.firestore().collectionGroup("guest-visits")
            .where(
                    "timestamp",
                    "<=",
                    Timestamps.fromDate(
                            moment().subtract(SESSION_DATA_RETENTION_DAYS, "days").toDate())
            )
            .get().await()

    supervisorScope {
        oldSessions.docs.map {
            async {
                console.log("Deleting session: ${it.ref.path}")
                it.ref.delete().await()
            }
        }
    }.awaitAll()
}.asPromise()

private suspend fun cleanupIncompleteSession(session: DocumentSnapshot) {
    val timestamp = session.data()["timestamp"]?.unsafeCast<Timestamp>() ?: epoch

    if (moment().diff(timestamp.toDate(), "hours") as Int >= SESSION_TTL_HOURS) {
        console.log("Deleting session: ${session.ref.path}")
        session.ref.delete().await()
    }
}
