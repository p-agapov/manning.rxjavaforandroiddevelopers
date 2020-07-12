package com.agapovp.android.manning.rxjavaforandroiddevelopers.chapter2.newsreader.network

import java.util.*

class Entry(
    val id: String,
    val title: String,
    val link: String,
    val updated: Long
) : Comparable<Entry> {

    override fun toString(): String = "${Date(updated)}\n$title"

    override fun compareTo(other: Entry) =
        when {
            this.updated > other.updated -> -1
            this.updated < other.updated -> 1
            else -> 0
        }
}
