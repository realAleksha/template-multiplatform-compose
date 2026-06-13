package shared.data.cache

import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.Duration.Companion.seconds

class BasicCacheSourceTest {

    private val cacheSource: CacheSource = BasicCacheSource()

    @Test
    fun `WHEN access different keys THEN all actions are performed in not blocking way`() = runBlocking {
        val iterations = 1000
        val cached = ConcurrentSet<Int>()
        repeat(iterations) { iteration ->
            GlobalScope.launch {
                cacheSource
                    .get(IdKey(iteration)) {
                        delay(2.seconds)
                        iteration
                    }
                    .get()
                    ?.let(cached::add)
            }
        }
        delay(3.seconds)
        assertEquals(iterations, cached.size)
    }

    @Test
    fun `WHEN access same key multiple times THEN value is the same`() = runBlocking {
        val key = UUIDKey(Int.MAX_VALUE)
        val iterations = 1000
        val cached = ConcurrentSet<UUID>()
        repeat(iterations) {
            launch {
                delay(300)
                cacheSource
                    .get(key) {
                        delay(1.seconds)
                        UUID.randomUUID()
                    }
                    .get()
                    ?.let(cached::add)
            }
        }
        delay(2.seconds)
        assertEquals(1, cached.size)
    }

    @Test
    fun `WHEN access same key in parallel THEN key is evaluated once`() = runBlocking {
        val cached = ConcurrentSet<UUID>()
        val key = UUIDKey(Int.MAX_VALUE)
        val iterations = 1000
        repeat(iterations) {
            launch {
                delay(300)
                cacheSource
                    .get(key) {
                        delay(1.seconds)
                        UUID.randomUUID()
                    }
                    .changes()
                    .filterNotNull()
                    .take(1)
                    .first()
                    .let(cached::add)
            }
        }
        delay(2.seconds)
        assertEquals(1, cached.size)
    }

    @Test
    fun `WHEN access expired key THEN new value is returned`() = runBlocking {
        val key = UUIDKey(Int.MAX_VALUE, ttl = 100)
        val entry = cacheSource.get(key) { UUID.randomUUID() }
        val value1 = entry.get()
        val value1Last = entry.last()
        delay(100)
        val value2 = entry.get()
        delay(100)
        assertNotEquals(value1, value2)
        assertEquals(value1, value1Last)
        assertNotEquals(value2, entry.get())
    }

    private data class IdKey(
        val id: Int,
        override val ttl: Long = CacheKey.TTL_UNLIMITED
    ) : CacheKey<Int>

    private data class UUIDKey(
        val id: Int,
        override val ttl: Long = CacheKey.TTL_UNLIMITED
    ) : CacheKey<UUID>

}