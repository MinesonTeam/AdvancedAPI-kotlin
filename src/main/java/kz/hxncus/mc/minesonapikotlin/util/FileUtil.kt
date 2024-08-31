package kz.hxncus.mc.minesonapikotlin.util

import java.io.File
import java.io.IOException

/**
 * Delete folder.
 *
 * @param file the file
 * @throws IOException the io exception
 */
@Throws(IOException::class)
fun deleteFolder(file: File) {
    if (file.isDirectory) {
        val files = file.listFiles() ?: return
        for (subFile in files) {
            deleteFolder(subFile)
        }
    }
    if (!file.delete()) {
        throw IOException("Failed to delete file: " + file.absolutePath)
    }
}
