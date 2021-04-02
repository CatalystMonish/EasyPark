package com.catalystmedia.parkfirst

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_reserve.*
import kotlinx.android.synthetic.main.payment_dialog.*
import java.util.*
import kotlin.collections.ArrayList

class ReserveActivity : AppCompatActivity() {

    var list: ArrayList<String> = ArrayList()
    var selectedSlot: String ?= null
    var selectedTime: String ?= null
    var selectedWing: String ?= null
    var payDialog:Dialog ?= null
    var areaCode:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        list = intent.getStringArrayListExtra("slotsArray") as ArrayList<String>
        areaCode = intent.getIntExtra("areaCode", 0)
        when (areaCode) {
            0 -> {
                tv_area.text = "Sunshine Apratments"
            }
            1 -> {
                tv_area.text = "Apsara Society"
            }
            2 -> {
                tv_area.text = "Infinity Mall"
            }
        }
        val element = list[0].toString()
        selectedSlot = element
        checkWing()
        btn_pay.setOnClickListener {
            reservePark()
        }

        slot_spinner.attachDataSource(list)
       slot_spinner.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            selectedSlot = parent.getItemAtPosition(position).toString()
           checkWing()
        }
        val timeSelect: List<String> = LinkedList(Arrays.asList("30 min", "1 Hr", "2 Hr", ">3 Hr"))
        time_spinner.attachDataSource(timeSelect)
        time_spinner.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            selectedTime = parent.getItemAtPosition(position).toString()
            when (selectedTime) {
                "30 min" -> {
                    tv_price.visibility = View.VISIBLE
                    tv_price.text = "₹ 20"
                }
                "1 Hr" -> {
                    tv_price.visibility = View.VISIBLE
                    tv_price.text = "₹ 30"
                }
                "2 Hr" ->{
                    tv_price.visibility = View.VISIBLE
                    tv_price.text = "₹ 50"
                }
                ">3 Hr" ->{
                    tv_price.visibility = View.VISIBLE
                    tv_price.text = "₹ 100"
                }
            }

        }

    }

    private fun checkWing() {
        if(selectedSlot == "A1"){
            selectedWing = "A"
        }
        else if(selectedSlot == "A2"){
            selectedWing = "A"
        }
        else if(selectedSlot == "A3"){
            selectedWing = "A"
        }
        else if(selectedSlot == "A4"){
            selectedWing = "A"
        }
        else if(selectedSlot == "A5"){
            selectedWing = "A"
        }
        else if(selectedSlot == "B1"){
            selectedWing = "B"
        }
        else if(selectedSlot == "B2"){
            selectedWing = "B"
        }
        else if(selectedSlot == "B3"){
            selectedWing = "B"
        }
        else if(selectedSlot == "B4"){
            selectedWing = "B"
        }
        else if(selectedSlot == "B5"){
            selectedWing = "B"
        }
    }

    private fun reservePark() {
        var carNumber = et_car.text.toString()
        if (carNumber != ""){
            FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child(selectedWing.toString()).
            child(selectedSlot.toString()).child("reservationStatus").setValue(carNumber.toString()).addOnSuccessListener { task->
                showPayDialog()
                Handler(Looper.getMainLooper()).postDelayed({
                    payDialog!!.dismiss()
                    Toast.makeText(this@ReserveActivity, "Slot Reserved", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ReserveActivity, HomeActivity::class.java)
                    intent.putExtra("areaCodeReserve", areaCode)
                    startActivity(intent)
                }, 4000)
            }

        }
        else{
            Toast.makeText(this@ReserveActivity, "Please Enter Your Car Number", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showPayDialog(){
        payDialog = Dialog(this)
        payDialog!!.setContentView(R.layout.payment_dialog)
        payDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        payDialog!!.show()
        Handler(Looper.getMainLooper()).postDelayed({
        payDialog!!.tv_title_pay.text = "Payment Done!"
            payDialog!!.tv_title_pay.setTextColor(Color.parseColor("#228B22"))
        }, 3000)
    }
}