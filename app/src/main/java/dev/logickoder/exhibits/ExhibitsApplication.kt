package dev.logickoder.exhibits

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dev.logickoder.exhibits.data.remote.RestExhibitsLoader
import dev.logickoder.exhibits.data.repository.ExhibitsRepository
import dev.logickoder.exhibits.di.ServiceLocator
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class ExhibitsApplication: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())
        initializeDependencies()
    }


    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(10 * 1024 * 1024)
                    .build()
            }
            .respectCacheHeaders(false)
            .build()
    }

    private fun initializeDependencies() {
        val exhibits = ExhibitsRepository(RestExhibitsLoader, this)
        ServiceLocator.save(exhibits.javaClass.simpleName, exhibits)
    }
}