package com.baseapplication.location

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.baseapplication.R
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe

/**
 * Created by ben on 9/28/17.
 */
class PermissionRequestHandler() {
    var completableMap: MutableMap<Int, CompletableEmitter> = HashMap()
    fun recordAudio(): Permission {
        return Permission(
            Manifest.permission.RECORD_AUDIO,
            R.string.permission_record_audio_title,
            R.string.permission_record_audio_message
        )
    }

    fun writeExternalStorage(): Permission {
        return Permission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            R.string.permission_write_external_storage_title,
            R.string.permission_write_external_storage_message
        )
    }

    fun manageDocuments(): Permission {
        return Permission(
            Manifest.permission.MANAGE_DOCUMENTS,
            R.string.permission_manage_documents_storage_title,
            R.string.permission_manage_documents_message
        )
    }

    fun readExternalStorage(): Permission {
        return Permission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            R.string.permission_read_external_storage_title,
            R.string.permission_read_external_storage_message
        )
    }

    fun camera(): Permission {
        return Permission(
            Manifest.permission.CAMERA,
            R.string.permission_camera_title,
            R.string.permission_camera_message
        )
    }

    //    public Permission captureVideoOutput () {
    //        return new Permission(Manifest.permission.CAPTURE_VIDEO_OUTPUT, R.string.permission_video_output_title, R.string.permission_video_output_message);
    //    }
    fun readContacts(): Permission {
        return Permission(
            Manifest.permission.READ_CONTACTS,
            R.string.permission_read_contacts_title,
            R.string.permission_read_contacts_message
        )
    }

    fun accessFineLocation(): Permission {
        return Permission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            R.string.permission_location_title,
            R.string.permission_location_message
        )
    }

    fun accessCoarseLocation(): Permission {
        return Permission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            R.string.permission_location_title,
            R.string.permission_location_message
        )
    }

    fun requestRecordAudio(activity: Activity): Completable {
        return requestPermissions(
            activity,
            RECORD_AUDIO_REQUEST,
            recordAudio(),
            writeExternalStorage()
        )
    }

    //    public boolean recordPermissionGranted() {
    //        return ContextCompat.checkSelfPermission(ChatSDK.shared().context(), Manifest.permission.RECORD_AUDIO) != PERMISSION_DENIED;
    //    }
    fun requestWriteExternalStorage(activity: Activity): Completable {
        return requestPermission(activity, WRITE_EXTERNAL_STORAGE_REQUEST, writeExternalStorage())
    }

    fun requestManageDocumentsStorage(activity: Activity): Completable {
        return requestPermission(activity, MANAGE_DOCUMENTS_REQUEST, manageDocuments())
    }

    fun requestReadExternalStorage(activity: Activity): Completable {
        return requestPermission(activity, READ_EXTERNAL_STORAGE_REQUEST, readExternalStorage())
    }

    fun requestCameraAccess(activity: Activity): Completable {
        return requestPermissions(activity, CAMERA_REQUEST, camera(), writeExternalStorage())
    }

    //    public Completable requestVideoAccess(Activity activity) {
    //        return requestPermission(activity, RECORD_VIDEO_REQUEST, captureVideoOutput());
    //    }
    fun requestReadContact(activity: Activity): Completable {
        return requestPermission(activity, READ_CONTACTS_REQUEST, readContacts())
    }

    fun requestLocationAccess(activity: Activity): Completable {
        return requestPermissions(
            activity,
            ACCESS_LOCATION_REQUEST,
            accessFineLocation(),
            accessCoarseLocation()
        )
    }

    fun requestPermission(
        activity: Activity,
        permission: String,
        result: Int,
        dialogTitle: Int,
        dialogMessage: Int
    ): Completable {
        return requestPermissions(
            activity,
            result,
            Permission(permission, dialogTitle, dialogMessage)
        )
    }

    fun requestPermission(activity: Activity, result: Int, permission: Permission?): Completable {
        return requestPermissions(activity, result, (permission)!!)
    }

    fun requestPermissions(
        activity: Activity,
        result: Int,
        vararg permissions: Permission
    ): Completable {

        // If the method is called twice, just return success...
        if (completableMap.containsKey(result)) {
            return Completable.complete()
        }
        return Completable.create(CompletableOnSubscribe({ e: CompletableEmitter ->

            // So we can handle multiple requests at the same time, we store the emitter and wait...
            completableMap.put(result, e)
            val dialogs: ArrayList<AlertDialog.Builder> = ArrayList()
            val toRequest: ArrayList<String?> = ArrayList()
            var allGranted: Boolean = true
            for (permission: Permission in permissions) {
                val permissionCheck: Int = ContextCompat.checkSelfPermission(
                    activity.getApplicationContext(),
                    permission.name
                )
                if (permissionCheck == PermissionChecker.PERMISSION_DENIED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            permission.name
                        )
                    ) {
                        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                            .setTitle(permission.title(activity))
                            .setMessage(permission.description(activity))
                            .setPositiveButton(
                                "OK",
                                DialogInterface.OnClickListener({ dialog: DialogInterface?, which: Int ->
                                    ActivityCompat.requestPermissions(
                                        activity,
                                        permission.permissions(),
                                        result
                                    )
                                })
                            )
                            .setNegativeButton(
                                "Cancel",
                                DialogInterface.OnClickListener({ dialog: DialogInterface?, which: Int ->
                                    val message: String = String.format(
                                        activity.getString(R.string.__permission_not_granted),
                                        permission.description(activity)
                                    )
                                    completableMap.remove(result)
                                    e.onError(Throwable(message))
                                })
                            )
                        dialogs.add(builder)
                    } else {
                        toRequest.add(permission.name)
                    }
                }
                allGranted = allGranted && permissionCheck != PermissionChecker.PERMISSION_DENIED
            }

            // If the name is denied, we request it
            if (!allGranted) {
                for (b: AlertDialog.Builder in dialogs) {
                    b.show()
                }
                ActivityCompat.requestPermissions(
                    activity,
                    toRequest.toTypedArray<String?>(),
                    result
                )
            } else {
                // If the name isn't denied, we remove the emitter and return success
                completableMap.remove(result)
                e.onComplete()
            }
        }))
    }

    fun onRequestPermissionsResult(
        context: Context,
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val e: CompletableEmitter? = completableMap.get(requestCode)
        if (e != null) {
            completableMap.remove(requestCode)
            var errorCodes: String = ""
            for (i in permissions.indices) {
                if (grantResults.get(i) == PermissionChecker.PERMISSION_DENIED) {
                    errorCodes += Permission(permissions.get(i)).name + ", "
                }
            }
            if (!errorCodes.isEmpty()) {
                errorCodes = errorCodes.substring(0, errorCodes.length - 2)
            }
            if (errorCodes.isEmpty()) {
                e.onComplete()
            } else {
                // TODO: this is being called for the contact book (maybe it's better to request contacts
                // from inside the contact book module
                val throwable: Throwable =
                    Throwable(context.getString(R.string.error_permission_not_granted, errorCodes))
                e.onError(throwable)
            }
        }
    }

    companion object {
        val instance: PermissionRequestHandler = PermissionRequestHandler()
        private val WRITE_EXTERNAL_STORAGE_REQUEST: Int = 100
        private val READ_EXTERNAL_STORAGE_REQUEST: Int = 101
        private val RECORD_AUDIO_REQUEST: Int = 102
        private val RECORD_VIDEO_REQUEST: Int = 103
        private val CAMERA_REQUEST: Int = 104
        private val READ_CONTACTS_REQUEST: Int = 105
        private val MANAGE_DOCUMENTS_REQUEST: Int = 106
        private val ACCESS_LOCATION_REQUEST: Int = 107
        private val TAKE_PHOTOS: Int = 108
    }
}
