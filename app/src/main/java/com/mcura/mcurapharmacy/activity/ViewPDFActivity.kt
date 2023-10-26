package com.mcura.mcurapharmacy.activity

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.*
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import com.mcura.mcurapharmacy.BuildConfig
import com.mcura.mcurapharmacy.R
import com.mcura.mcurapharmacy.service.DownloadService
import java.io.File

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ViewPDFActivity : AppCompatActivity() {
    private lateinit var iv_download: ImageButton
    private lateinit var patContact: String
    private lateinit var patName: String
    private lateinit var pdf: String
    private lateinit var pdf_viewer: PDFView
    private lateinit var progress_bar: ProgressBar
    private lateinit var pdfInternalFile: File
    private lateinit var mToolbar: Toolbar
    private val MY_NOTIFICATION_ID = 1
    var notificationManager: NotificationManager? = null
    private var myNotification: NotificationCompat.Builder? = null
    private val DownloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Display message from DownloadService
            val b = intent.extras
            if (b != null) {
                //Toast.makeText(VisitSummaryActivity.this,b.getString(DownloadService.EXTRA_MESSAGE),Toast.LENGTH_SHORT).show();
                //tv.setText(b.getString(DownloadService.EXTRA_MESSAGE));
                val fileName = patName + patContact.trim { it <= ' ' } + ".pdf"
                val filePath = Environment.getExternalStorageDirectory().toString() + "/" + fileName
                val file = File(filePath)
                Log.d("downloadLink", "File to download = $file")
                val mime = MimeTypeMap.getSingleton()
                val ext = file.name.substring(file.name.indexOf(".") + 1)
                val type = mime.getMimeTypeFromExtension(ext)
                val openFile = Intent(Intent.ACTION_VIEW, Uri.fromFile(file))
                openFile.setDataAndType(Uri.fromFile(file), type)
                openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                val p = PendingIntent.getActivity(this@ViewPDFActivity, 0, openFile, 0)
                myNotification = NotificationCompat.Builder(this@ViewPDFActivity)
                myNotification!!.setContentTitle("PDF Download")
                        .setContentText("download completed")
                        .setTicker("mCURA")
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.app_logo)
                        .setContentIntent(p)
                notificationManager!!.notify(MY_NOTIFICATION_ID, myNotification!!.build())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_p_d_f)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = this.intent
        val bundle = intent.extras

        pdf = bundle?.getString("pdf").toString()
        patName = bundle?.getString("patname").toString()
        patContact = bundle?.getString("patcontact").toString()
        val path = BuildConfig.BASE_URL + pdf
        mToolbar = findViewById(R.id.toolbar) as Toolbar
        progress_bar = findViewById(R.id.progress_bar)
        pdf_viewer = findViewById(R.id.pdf_viewer)
        iv_download = findViewById(R.id.iv_download)
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
            supportActionBar!!.subtitle = ""
        }
        mToolbar.setNavigationOnClickListener(View.OnClickListener {
            // back button pressed
            onBackPressed()
            overridePendingTransition(R.anim.stay, R.anim.layout_slide_out_up)
        })

        progress_bar.visibility = View.VISIBLE
        iv_download.setOnClickListener {
            val path = BuildConfig.BASE_URL + "/" + pdf
            val newIntent = Intent(this@ViewPDFActivity, DownloadService::class.java)
            newIntent.action = DownloadService.ACTION_DOWNLOAD
            newIntent.putExtra(DownloadService.EXTRA_URL, path)
            newIntent.putExtra("patname", patName)
            newIntent.putExtra("patcontact", patContact)
            // Start Download Service
            // Start Download Service
            Toast.makeText(this@ViewPDFActivity, "Downloading...", Toast.LENGTH_SHORT).show()
            this@ViewPDFActivity.startService(newIntent)
        }

        FileLoader.with(this)
                .load(path)
                .fromDirectory("PDFFiles", FileLoader.DIR_INTERNAL)
                .asFile(object: FileRequestListener<File> {
                    override fun onLoad(fileLoadRequest: FileLoadRequest, fileResponse: FileResponse<File>) {
                        progress_bar.visibility = View.GONE
                        pdfInternalFile = fileResponse.getBody()
                        pdf_viewer.fromFile(pdfInternalFile)
                                .password(null)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .onDraw(object: OnDrawListener {
                                    override fun onLayerDrawn(canvas: Canvas, pageWidth:Float, pageHeight:Float, displayedPage:Int) {
                                    }
                                })
                                .onDrawAll(object:OnDrawListener {
                                    override fun onLayerDrawn(canvas:Canvas, pageWidth:Float, pageHeight:Float, displayedPage:Int) {
                                    }
                                })
                                .onPageError(object: OnPageErrorListener {
                                    override fun onPageError(page:Int, t:Throwable) {
                                        Toast.makeText(this@ViewPDFActivity, "Error", Toast.LENGTH_LONG).show()
                                    }
                                })
                                .onPageChange(object: OnPageChangeListener {
                                    override fun onPageChanged(page:Int, pageCount:Int) {
                                    }
                                })
                                .onTap(object: OnTapListener {
                                    override fun onTap(e: MotionEvent):Boolean {
                                        return true
                                    }
                                })
                                .onRender(object: OnRenderListener {
                                    override fun onInitiallyRendered(nbPages:Int, pageWidth:Float, pageHeight:Float) {
                                        pdf_viewer.fitToWidth()
                                    }
                                })
                                .enableAnnotationRendering(true)
                                .invalidPageColor(Color.WHITE)
                                .load()
                    }
                    override fun onError(fileLoadRequest:FileLoadRequest, throwable:Throwable) {
                        Toast.makeText(this@ViewPDFActivity, "Error", Toast.LENGTH_LONG).show()
                        progress_bar.setVisibility(View.GONE)
                    }
                })
    }
    override fun onResume() {
        super.onResume()
        // Register receiver to get message from DownloadService
        registerReceiver(DownloadReceiver, IntentFilter(DownloadService.ACTION_DOWNLOAD))
    }

    override fun onPause() {
        super.onPause()
        // Unregister the receiver
        unregisterReceiver(DownloadReceiver)
    }
    override fun onDestroy() {
        Log.d("onDestroy","onDestroy");
        if(pdfInternalFile!=null){
           var status = pdfInternalFile.delete()
            Log.d("onDestroy",status.toString());
        }
        super.onDestroy()
    }
}