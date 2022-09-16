package dev.logickoder.exhibits.data.remote

import dev.logickoder.exhibits.data.model.Exhibit

interface ExhibitsLoader {

    fun getExhibitsList(): List<Exhibit>
}