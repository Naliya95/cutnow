package com.cutnow.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cutnow.sdk.ui.ProgressBorderView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Example of using a view from the SDK
        val sdkView = ProgressBorderView(this)
        setContentView(sdkView)
    }
}
