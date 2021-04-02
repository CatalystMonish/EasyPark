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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_reserve.*
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    var list: ArrayList<String> = ArrayList()
    var selectedArea:String ?= null
    var areaCode:Int = 0
    var loadDialog:Dialog ?= null
    var areaCodeReserve:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        areaCodeReserve = intent.getIntExtra("areaCodeReserve",0)
        areaCode = intent.getIntExtra("areaCodeReserve",0)
        showLoader()
        updateUI()
        getParkingInfo()
        hideAll()
        val areaSelect: List<String> = LinkedList(Arrays.asList("Sunshine Apratments", "Apsara Society", "Infinity Mall"))
        area_spinner.attachDataSource(areaSelect)
        area_spinner.setSelectedIndex(areaCodeReserve!!)
        area_spinner.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            selectedArea = parent.getItemAtPosition(position).toString()
            list.clear()
            when (selectedArea) {
                "Sunshine Apratments" -> {
                    areaCode = 0
                    showLoader()
                    getParkingInfo()
                }
                "Apsara Society" -> {
                    areaCode = 1
                    showLoader()
                    getParkingInfo()
                }
                "Infinity Mall" -> {
                    areaCode = 2
                    showLoader()
                    getParkingInfo()
                }
            }
        }
        btn_reserve.setOnClickListener {
            var elements = list.joinToString()
            if (elements != "") {
                val intent = Intent(this@HomeActivity, ReserveActivity::class.java)
                intent.putStringArrayListExtra("slotsArray", list)
                intent.putExtra("areaCode", areaCode)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@HomeActivity, "No slots Available",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun hideAll(){
        a1.visibility = View.INVISIBLE
        a1r.visibility = View.INVISIBLE
        a2.visibility = View.INVISIBLE
        a2r.visibility = View.INVISIBLE
        a3.visibility = View.INVISIBLE
        a3r.visibility = View.INVISIBLE
        a4.visibility = View.INVISIBLE
        a4r.visibility = View.INVISIBLE
        a5.visibility = View.INVISIBLE
        a5r.visibility = View.INVISIBLE
        b1.visibility = View.INVISIBLE
        b1r.visibility = View.INVISIBLE
        b2.visibility = View.INVISIBLE
        b2r.visibility = View.INVISIBLE
        b3.visibility = View.INVISIBLE
        b3r.visibility = View.INVISIBLE
        b4.visibility = View.INVISIBLE
        b4r.visibility = View.INVISIBLE
        b5.visibility = View.INVISIBLE
        b5r.visibility = View.INVISIBLE
    }

    private fun getParkingInfo(){
        getParkingInfoA1()
        getParkingInfoA2()
        getParkingInfoA3()
        getParkingInfoA4()
        getParkingInfoA5()
        getParkingInfoB1()
        getParkingInfoB2()
        getParkingInfoB3()
        getParkingInfoB4()
        getParkingInfoB5()
    }
    private fun showLoader(){
       loadDialog = Dialog(this)
        loadDialog!!.setContentView(R.layout.loading_dialog)
        loadDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadDialog!!.show()

    }
    private fun updateUI() {
        tv_empty_slots.setTextColor(Color.parseColor("#323232"))
        var elements = list.joinToString()
      tv_empty_slots.text = elements
        if(elements ==""){
            tv_empty_slots.setTextColor(Color.parseColor("#EC0000"))
            tv_empty_slots.text = "No slots available!"
        }
    }

    private fun getParkingInfoA1() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A1").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        a1t.visibility = View.VISIBLE
                        a1.visibility = View.INVISIBLE
                        list.add("A1")
                        a1t.setTextColor(Color.parseColor("#277A2A"))
                        updateUI()
                        checkForReservationA1()
                    }
                    else if(!isEmpty){
                        a1.visibility = View.VISIBLE
                        a1r.visibility = View.INVISIBLE
                     list.remove("A1")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun checkForReservationA1() {
      FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A1").child("reservationStatus").addValueEventListener(object: ValueEventListener{
          override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()){
                a1.visibility = View.INVISIBLE
                list.remove("A1")
                updateUI()
                val reservationStatus = snapshot.value.toString()
                a1r.visibility = View.VISIBLE
                a1r.text = "Reserved by\n$reservationStatus"
                a1t.setTextColor(Color.parseColor("#F9A825"))
            }
              else{
                updateUI()
                a1r.visibility = View.INVISIBLE
                a1t.setTextColor(Color.parseColor("#000000"))
              }
          }

          override fun onCancelled(error: DatabaseError) {
              TODO("Not yet implemented")
          }

      })
    }

    private fun getParkingInfoA2() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A2").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        a2t.visibility = View.VISIBLE
                        a2.visibility = View.INVISIBLE
                        list.add("A2")
                        a2t.setTextColor(Color.parseColor("#277A2A"))
                        updateUI()
                        checkForReservationA2()
                    }
                    else if(!isEmpty){
                        a2.visibility = View.VISIBLE
                        a2r.visibility = View.INVISIBLE
                        list.remove("A2")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationA2() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A2").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    a2.visibility = View.INVISIBLE
                    list.remove("A2")
                    updateUI()
                    val reservationStatus = snapshot.value.toString()
                    a2r.visibility = View.VISIBLE
                    a2r.text = "Reserved by\n$reservationStatus"
                    a2t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    a2r.visibility = View.INVISIBLE
                    a2t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getParkingInfoA3() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A3").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        list.add("A3")
                        a3t.setTextColor(Color.parseColor("#277A2A"))
                        a3t.visibility = View.VISIBLE
                        a3.visibility = View.INVISIBLE
                        updateUI()
                        checkForReservationA3()
                    }
                    else if(!isEmpty){
                        a3.visibility = View.VISIBLE
                        a3r.visibility = View.INVISIBLE
                        list.remove("A3")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationA3() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A3").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    a3.visibility = View.GONE
                    list.remove("A3")
                    updateUI()
                    val reservationStatus = snapshot.value.toString()
                    a3r.visibility = View.VISIBLE
                    a3r.text = "Reserved by\n$reservationStatus"
                    a3t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    a3r.visibility = View.INVISIBLE
                    a3t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getParkingInfoA4() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A4").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        a4t.setTextColor(Color.parseColor("#277A2A"))
                        a4t.visibility = View.VISIBLE
                        a4.visibility = View.INVISIBLE
                        list.add("A4")
                        updateUI()
                        checkForReservationA4()
                    }
                    else if(!isEmpty){
                        a4.visibility = View.VISIBLE
                        a4r.visibility = View.INVISIBLE
                        list.remove("A4")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationA4() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A4").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    a4.visibility = View.INVISIBLE
                    list.remove("A4")
                    updateUI()
                    val reservationStatus = snapshot.value.toString()
                    a4r.visibility = View.VISIBLE
                    a4r.text = "Reserved by\n$reservationStatus"
                    a4t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    a4r.visibility = View.GONE
                    a4t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getParkingInfoA5() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A5").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        a5t.setTextColor(Color.parseColor("#277A2A"))
                        a5t.visibility = View.VISIBLE
                        a5.visibility = View.INVISIBLE
                        list.add("A5")
                        updateUI()
                        checkForReservationA5()
                    }
                    else if(!isEmpty){
                        a5.visibility = View.VISIBLE
                        a5r.visibility = View.INVISIBLE
                        list.remove("A5")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationA5() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("A").child("A5").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    a5.visibility = View.GONE
                    list.remove("A5")
                    updateUI()
                    val reservationStatus = snapshot.value.toString()
                    a5r.visibility = View.VISIBLE
                    a5r.text = "Reserved by\n$reservationStatus"
                    a5t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    a5r.visibility = View.GONE
                    a5t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getParkingInfoB1() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B1").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        b1t.setTextColor(Color.parseColor("#277A2A"))
                        b1t.visibility = View.VISIBLE
                        b1.visibility = View.INVISIBLE
                        list.add("B1")
                        updateUI()
                        checkForReservationB1()
                    }
                    else if(!isEmpty){
                        b1.visibility = View.VISIBLE
                        b1r.visibility = View.INVISIBLE
                        list.remove("B1")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationB1() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B1").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    b1.visibility = View.INVISIBLE
                    list.remove("B1")
                    updateUI()
                    val reservationStatus = snapshot.value.toString()
                    b1r.visibility = View.VISIBLE
                    b1r.text = "Reserved by\n$reservationStatus"
                    b1t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    b1r.visibility = View.INVISIBLE
                    b1t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getParkingInfoB2() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B2").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        b2t.setTextColor(Color.parseColor("#277A2A"))
                        b2t.visibility = View.VISIBLE
                        b2.visibility = View.INVISIBLE
                        list.add("B2")
                        updateUI()
                        checkForReservationB2()
                    }
                    else if(!isEmpty){
                        b2.visibility = View.VISIBLE
                        list.remove("B2")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationB2() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B2").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    b2.visibility = View.GONE
                    list.remove("B2")
                    updateUI()
                    val reservationStatus = snapshot.value.toString()
                    b2r.visibility = View.VISIBLE
                    b2r.text = "Reserved by\n$reservationStatus"
                    b2t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    b2r.visibility = View.GONE
                    b2r.visibility = View.INVISIBLE
                    b2t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getParkingInfoB3() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B3").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        b3t.setTextColor(Color.parseColor("#277A2A"))
                        b3t.visibility = View.VISIBLE
                        b3.visibility = View.INVISIBLE
                        list.add("B3")
                        updateUI()
                        checkForReservationB3()
                    }
                    else if(!isEmpty){
                        b3.visibility = View.VISIBLE
                        b3r.visibility = View.INVISIBLE
                        list.remove("B3")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationB3() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B3").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    list.remove("B3")
                    updateUI()
                    b3.visibility = View.GONE
                    val reservationStatus = snapshot.value.toString()
                    b3r.visibility = View.VISIBLE
                    b3r.text = "Reserved by\n$reservationStatus"
                    b3t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    b3r.visibility = View.GONE
                    b3t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getParkingInfoB4() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B4").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        b4t.setTextColor(Color.parseColor("#277A2A"))
                        b4t.visibility = View.VISIBLE
                        b4.visibility = View.INVISIBLE
                        list.add("B4")
                        updateUI()
                        checkForReservationB4()
                    }
                    else if(!isEmpty){
                        b4.visibility = View.VISIBLE
                        b4r.visibility = View.INVISIBLE
                        list.remove("B4")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationB4() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B4").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    list.remove("B4")
                    updateUI()
                    b4.visibility = View.GONE
                    val reservationStatus = snapshot.value.toString()
                    b4r.visibility = View.VISIBLE
                    b4r.text = "Reserved by\n$reservationStatus"
                    b4t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    b4r.visibility = View.GONE
                    b4t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getParkingInfoB5() {
        hideAll()
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B5").child("isEmpty").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                loadDialog!!.dismiss()
                if(snapshot.exists()){
                    val isEmpty = snapshot.value.toString().toBoolean()
                    if(isEmpty){
                        b5t.setTextColor(Color.parseColor("#277A2A"))
                        b5t.visibility = View.VISIBLE
                        b5.visibility = View.INVISIBLE
                        list.add("B5")
                        updateUI()
                        checkForReservationB5()
                    }
                    else if(!isEmpty){
                        b5.visibility = View.VISIBLE
                        b5r.visibility = View.INVISIBLE
                        list.remove("B5")
                        updateUI()

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun checkForReservationB5() {
        FirebaseDatabase.getInstance().reference.child(areaCode.toString()).child("Wing").child("B").child("B5").child("reservationStatus").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    list.remove("B5")
                    updateUI()
                    val reservationStatus = snapshot.value.toString()
                    b5.visibility = View.GONE
                    b5r.visibility = View.VISIBLE
                    b5r.text = "Reserved by\n$reservationStatus"
                    b5t.setTextColor(Color.parseColor("#F9A825"))
                }
                else{
                    updateUI()
                    b5r.visibility = View.GONE
                    b5t.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}