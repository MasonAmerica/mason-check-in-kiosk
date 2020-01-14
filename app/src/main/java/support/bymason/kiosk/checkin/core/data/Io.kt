package support.bymason.kiosk.checkin.core.data

import java.io.File

/** @return this file if it exists, null otherwise */
fun File.orNull(): File? = takeIf { exists() }

/** @return this directory after ensuring that it either already exists or was created */
fun File.safeMkdirs(): File = apply {
    check(exists() || mkdirs()) { "Unable to create $this" }
}

/** @return this file after ensuring that it either already exists or was created */
fun File.safeCreateNewFile(): File = apply {
    parentFile?.safeMkdirs()
    check(exists() || createNewFile()) { "Unable to create $this" }
}

/** @return this string if it contains content, null otherwise */
fun String?.nullOrFull(): String? = takeUnless { isNullOrBlank() }
