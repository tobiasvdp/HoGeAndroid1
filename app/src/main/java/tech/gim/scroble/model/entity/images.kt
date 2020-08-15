package tech.gim.scroble.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.gim.scroble.model.ShowImages

@Entity(tableName = "IMAGES")
class ShowImages(){
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "DATA_FIELD")
    var data: ShowImages? = null
}