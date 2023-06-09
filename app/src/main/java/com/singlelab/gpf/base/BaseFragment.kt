package com.singlelab.gpf.base

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import com.singlelab.gpf.MainActivity
import com.singlelab.gpf.R
import com.singlelab.gpf.base.listeners.OnActivityResultListener
import com.singlelab.gpf.base.listeners.OnPermissionListener
import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.profile.PersonNotifications
import com.singlelab.gpf.model.view.ToastType
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_edit_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import kotlin.coroutines.CoroutineContext

open class BaseFragment : MvpAppCompatFragment(), ErrorView, LoadingView {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private var snackbar: Snackbar? = null
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(false)
        auth = FirebaseAuth.getInstance()
        showBottomNavigation(isShow = auth.currentUser != null)
        if (auth.currentUser == null && this is OnlyForAuthFragments) {
            //todo подумать, как можно получше сделать общий обработчик того, что если
            // пользователь не залогинен, то с некоторых экранов должен осуществляться переход на авторизацию
            toAuth()
        } else {
            if (this is OnActivityResultListener) {
                (activity as MainActivity?)?.setActivityListener(this)
            }
            if (this is OnPermissionListener) {
                (activity as MainActivity?)?.setPermissionListener(this)
            }
        }
    }

    override fun onStop() {
        hideKeyboard()
        hideSnackbar()
        super.onStop()
    }


    override fun showLoading(isShow: Boolean, withoutBackground: Boolean) {
        (activity as MainActivity?)?.showLoading(isShow, withoutBackground)
    }

    override fun showLoadingText(text: String) {
        (activity as MainActivity?)?.showLoadingText(text)
    }

    override fun showError(
        message: String,
        withRetry: Boolean,
        callRetry: () -> Unit
    ) {
        showSnackbar(
            message,
            if (withRetry) ToastType.ERROR_RETRY else ToastType.ERROR,
            callRetry
        )
    }

    override fun showError(messageId: Int, withRetry: Boolean, callRetry: () -> Unit) {
//        showError(getString(messageId), withRetry, callRetry)
    }

    override fun toAuth() {
        showLoading(false)
        findNavController().popBackStack()
        findNavController().navigate(R.id.registration)
    }

    override fun showNotifications(notifications: PersonNotifications) {
        (activity as MainActivity).showNotifications(notifications)
    }

    override fun updateNewYearPromo(newYearPromoRewardEnabled: Boolean) {
    }

    protected fun invokeSuspend(block: suspend () -> Unit) {
        scope.launch { block.invoke() }
    }

    protected fun runOnMainThread(block: () -> Unit) {
        scope.launch(CoroutineContextProvider().main) {
            block.invoke()
        }
    }

    fun showKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun setSoftInputType(mode: Int) {
        activity?.window?.setSoftInputMode(mode)
    }

    fun hideKeyboard() {
        setSoftInputType(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val imm: InputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        setSoftInputType(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun showDialog(title: String, text: String? = null, listener: DialogInterface.OnClickListener) {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(getString(R.string.yes), listener)
                .setNegativeButton(getString(R.string.no), listener)
                .show()
        }
    }

    fun showListDialog(
        title: String,
        list: Array<String>,
        listener: DialogInterface.OnClickListener
    ) {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setItems(list, listener)
            val dialog = builder.create()
            dialog.show()
        }
    }

    fun showEditTextDialog(
        title: String?,
        text: String? = null,
        emptyText: String,
        callback: (String) -> Unit,
        cancelCallback: () -> Unit = {}
    ) {
        context?.let {
            val customLayout = layoutInflater.inflate(R.layout.view_edit_dialog, null)
            text?.let {
                customLayout.edit_text.setText(text)
            }
            customLayout.edit_text.requestFocus()

            val builder = AlertDialog.Builder(it)
            builder.setView(customLayout)
            builder.setTitle(title)
            builder.setPositiveButton(getString(R.string.apply_edit)) { _, _ ->
                if (customLayout.edit_text.text.isNullOrEmpty()) {
                    showSnackbar(emptyText)
                } else {
                    callback.invoke(customLayout.edit_text.text.toString())
                }
            }

            builder.setNegativeButton(
                getString(R.string.cancel_action)
            ) { _, _ -> cancelCallback.invoke() }
            builder.show()
        }
    }

    fun showBottomNavigation(isShow: Boolean) {
        (activity as MainActivity).showBottomNavigation(isShow)
    }

    fun onClickChangeImage() {
        activity?.let { activity ->
            CropImage.activity()
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(activity)
        }
    }

    fun onClickAddImages(countImage: Int = Const.MAX_COUNT_IMAGES) {
        activity?.let {
            ImagePicker.with(it)
                .setFolderMode(true)
                .setFolderTitle(Const.APP_NAME)
                .setRootDirectoryName(Config.ROOT_DIR_DCIM)
                .setDirectoryName(Const.FOLDER_NAME)
                .setShowCamera(false)
                .setMultipleMode(true)
                .setShowNumberIndicator(true)
                .setDoneTitle(getString(R.string.choose))
                .setMaxSize(countImage)
                .setLimitMessage(
                    getString(
                        R.string.chat_select_images_limit,
                        countImage
                    )
                )
                .setRequestCode(Const.SELECT_IMAGE_REQUEST_CODE)
                .start()
        }
    }

    fun openBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun shareText(text: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share)))
    }

    fun showSnackbar(
        message: String,
        type: ToastType = ToastType.WARNING,
        callRetry: () -> Unit = {}
    ) {
        context?.let {
            val snackbar = Snackbar.make(requireActivity().container, message, type.length)
            val snackbarLayout = snackbar.view

            snackbarLayout.elevation = 0f
            snackbarLayout.backgroundTintList = ContextCompat.getColorStateList(it, type.colorResId)

            val textView = snackbarLayout.findViewById<View>(R.id.snackbar_text) as TextView

            if (type.drawableResId != null) {
                textView.setCompoundDrawablesWithIntrinsicBounds(type.drawableResId, 0, 0, 0)
                textView.compoundDrawablePadding =
                    resources.getDimension(R.dimen.margin_small).toInt()
            }
            if (type == ToastType.ERROR_RETRY) {
                this.snackbar = snackbar
            }
            snackbarLayout.setOnClickListener {
                if (type == ToastType.ERROR_RETRY) {
                    callRetry.invoke()
                }
                this.snackbar?.dismiss()
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    private fun hideSnackbar() {
        snackbar?.dismiss()
    }
}