package ru.netology.nmedia.model.util

fun formatCounts(counter: Int): String {
    return when (counter) {
        in 0..999 -> "$counter"
        in 1_000..1_099 -> "1K"
        in 1_100..9_999 -> "${counter /
                1000}.${counter / 100 % 10}K"
        in 10_000..999_999 -> "${counter / 1000}K"
        in 1_000_000..1_099_999 -> "1M"
        else -> "${counter / 1_000_000}.${counter / 100_000 % 10}M"
    }
}