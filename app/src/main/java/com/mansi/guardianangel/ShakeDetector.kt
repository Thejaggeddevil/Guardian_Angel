//package com.mansi.guardianangel.util
//
//import android.content.Context
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//
//class ShakeDetector(
//    private val context: Context,
//    private val onShake: () -> Unit
//) : SensorEventListener {
//
//    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//    private var accel = 0f
//    private var accelCurrent = SensorManager.GRAVITY_EARTH
//    private var accelLast = SensorManager.GRAVITY_EARTH
//
//    fun start() {
//        sensorManager.registerListener(
//            this,
//            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//            SensorManager.SENSOR_DELAY_UI
//        )
//    }
//
//    fun stop() {
//        sensorManager.unregisterListener(this)
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        val x = event?.values?.get(0) ?: return
//        val y = event.values[1]
//        val z = event.values[2]
//        accelLast = accelCurrent
//        accelCurrent = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
//        val delta = accelCurrent - accelLast
//        accel = accel * 0.9f + delta
//
//        if (accel > 12) { // Adjust threshold if needed
//            onShake()
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
//}
