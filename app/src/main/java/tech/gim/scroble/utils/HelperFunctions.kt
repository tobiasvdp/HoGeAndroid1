package tech.gim.scroble.utils

import android.content.Context
import android.util.DisplayMetrics
import tech.gim.scroble.R
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.MinimizedShowWithImages
import tech.gim.scroble.model.ShowImages

/**
 * Class containing common functions use throughout the app
 */
class HelperFunctions {
    companion object {
        fun calculateNoOfColumns(context: Context?, columnWidthDp: Float): Int {
            if (context == null) return 1
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / columnWidthDp).toInt()
        }

        fun getCardWidth(context: Context?): Float {
            val displayMetrics = context?.resources?.displayMetrics
            val cardWidth = context?.resources?.getDimension(R.dimen.card_width)
            val spacerWidth = context?.resources?.getDimension(R.dimen.margin_xsmall)
            var totalWidth = cardWidth

            if (spacerWidth != null && totalWidth != null) {
                totalWidth += spacerWidth
            } else {
                totalWidth = 200f
            }
            if (displayMetrics !== null) {
                totalWidth /= displayMetrics.density
            }
            return totalWidth
        }

        fun combineShowsAndImages(shows: List<MinimizedShow?>?, img: List<ShowImages?>?): List<MinimizedShowWithImages> {
            if (shows == null) return ArrayList()
            if (img == null) return shows.map { MinimizedShowWithImages(it, null) }
            return shows.map {
                return@map MinimizedShowWithImages(it, img.find { showImages -> showImages!!.traktId == it!!.ids!!.trakt })
            }
        }
    }
}
