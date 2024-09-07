package kz.hxncus.mc.minesonapikotlin.bukkit.nms

import kz.hxncus.mc.minesonapikotlin.util.NMS_VERSION
import java.lang.reflect.InvocationTargetException

class NMSHandler {
    companion object {
        private var chunk: NMSChunk? = null

        fun getChunk(): NMSChunk? {
            if (chunk != null) {
                return chunk
            }
            try {
                return Class.forName("kz.hxncus.mc.minesonapi.nms.Chunk_$NMS_VERSION").getConstructor().newInstance() as NMSChunk
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException(e)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            } catch (e: InstantiationException) {
                throw RuntimeException(e)
            }
        }
    }
}
