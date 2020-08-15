package tech.gim.scroble.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import tech.gim.scroble.R
import tech.gim.scroble.model.DetailedShow
import tech.gim.scroble.model.Episode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    val imgUri = imgUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
    Glide.with(imgView.context)
        .load(imgUri)
        .fallback(R.drawable.ic_baseline_broken_image_24)
        .error(R.drawable.ic_baseline_broken_image_24)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24)
        )
        .into(imgView)
}

@BindingAdapter("show_runtime")
fun bindShowRuntime(view: TextView, minutes: Int?) {
    if (minutes == null) {
        view.text = "-"
    } else {
        view.text = "$minutes ${view.resources.getString(R.string.minutes)}"
    }
}

@BindingAdapter("show_genres")
fun bindShowGenres(view: TextView, list: List<String>?) {
    if (list == null || list.isEmpty()) {
        view.text = "-"
    } else {
        view.text = list.joinToString(", ")
    }
}

@BindingAdapter("show_airtime")
fun bindShowAirs(view: TextView, show: DetailedShow?) {
    if (show?.airs?.time == null) {
        view.text = "-"
    } else {
        view.text = view.resources.getString(R.string.show_details_airs_text)
            .replace("{day}", show.airs.day ?: "")
            .replace("{time}", show.airs.time ?: "00:00")
            .replace("{channel}", show.network ?: "-")
    }
}

@BindingAdapter("bindInt")
fun bindShowYear(view: TextView, i: Int?) {
    if (i == null) {
        view.text = "-"
    } else {
        view.text = i.toString()
    }
}

@BindingAdapter("season_number_formatter")
fun bindSeasonNumber(view: TextView, i: Int?) {
    if (i == null) {
        view.text = "-"
    } else {
        view.text = view.resources.getString(R.string.show_details_season_text).replace("{number}", i.toString())
    }
}

@BindingAdapter("episode_numbering")
fun bindSeasonNumber(view: TextView, episode: Episode?) {
    if (episode == null) {
        view.text = "0x00"
    } else {
        view.text = "${episode.season}x${episode.number}"
    }
}

@BindingAdapter("local_date_time")
fun bindLocalDateTime(view: TextView, date: LocalDateTime?) {
    if (date == null) {
        view.text = "0x00"
    } else {
        view.text = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a"))
    }
}

@BindingAdapter("percentage")
fun bindPercentage(view: TextView, perc: Int?) {
    if (perc == null) {
        view.text = "0x00"
    } else {
        view.text = "$perc%"
    }
}

@BindingAdapter("search_label")
fun bindPercentage(view: TextView, s: String?) {
    if (s == null) {
        view.text = "0x00"
    } else {
        view.text = view.resources.getString(R.string.search_label).replace("{query}", s, false)
    }
}
