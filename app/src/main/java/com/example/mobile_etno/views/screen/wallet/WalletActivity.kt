package com.example.mobile_etno.views.screen.wallet

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mobile_etno.R
import com.google.android.gms.pay.Pay
import com.google.android.gms.pay.PayApiAvailabilityStatus
import com.google.android.gms.pay.PayClient
import java.util.*

class WalletActivity: Activity() {
    private lateinit var buttonGooglePay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wallet_layout)

        buttonGooglePay = findViewById(R.id.google_pay)

        buttonGooglePay.setOnClickListener {

        }
    //Stripe to solve the problem -> https://stripe.com/docs/payments/accept-a-payment?integration=elements
    //https://www.youtube.com/watch?v=h12wNuOher4
    }
}