package com.baseapplication.location

import io.reactivex.subjects.PublishSubject

/**
 * We have to use this because in some versions of Android when the camera
 * displays the chat activity is destroyed so we lose the publish subject
 */
object ActivityResultPushSubjectHolder {
    private val instance: PublishSubject<ActivityResult?> = PublishSubject.create()
    fun shared(): PublishSubject<ActivityResult?> {
        return instance
    }
}