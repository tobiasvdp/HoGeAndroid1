package tech.gim.scroble.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tech.gim.scroble.model.entity.ShowImages

@Dao
interface ImagesDatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(show: ShowImages)

    @Query("SELECT * FROM IMAGES WHERE TRAKT_ID = :id")
    fun getImagesByTraktId(id: Int): LiveData<ShowImages?>

    @Query("SELECT * FROM IMAGES WHERE TRAKT_ID = :id")
    fun getImagesByTraktIdSync(id: Int): ShowImages?

    @Query("SELECT * FROM IMAGES WHERE TRAKT_ID IN (:ids)")
    fun getImagesByTraktIds(ids: List<Int>): LiveData<List<ShowImages>>

    @Query("SELECT * FROM IMAGES WHERE TRAKT_ID IN (:ids)")
    fun getImagesByTraktIdsSync(ids: List<Int>): List<ShowImages>

}