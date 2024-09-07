package kz.hxncus.mc.minesonapikotlin.util

import java.io.File
import java.io.IOException

/**
 * Delete folder.
 *
 * @throws IOException the io exception
 */
@Throws(IOException::class)
fun File.deleteFolder() {
    if (this.isDirectory) {
        val files = this.listFiles() ?: return
        for (subFile in files) {
            subFile.deleteFolder()
        }
    }
    if (!this.delete()) {
        throw IOException("Didn't delete file: " + this.absolutePath)
    }
}
