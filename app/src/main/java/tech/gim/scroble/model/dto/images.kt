package tech.gim.scroble.model.dto

data class FanartImage(var id: String = "", var url: String = "", var lang: String? = "", var likes: String = "", var season: String? = "")
data class FanartImages(
    var name: String = "",
    var thetvdb_id: String = "",
    var seasonposter: List<FanartImage>? = null,
    var showbackground: List<FanartImage>? = null,
    var hdtvlogo: List<FanartImage>? = null,
    var seasonbanner: List<FanartImage>? = null,
    var seasonthumb: List<FanartImage>? = null,
    var tvbanner: List<FanartImage>? = null,
    var tvposter: List<FanartImage>? = null,
    var hdclearart: List<FanartImage>? = null,
    var tvthumb: List<FanartImage>? = null,
    var characterart: List<FanartImage>? = null,
    var clearart: List<FanartImage>? = null,
    var clearlogo: List<FanartImage>? = null
)