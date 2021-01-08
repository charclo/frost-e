package com.frost_e.frost_e;

import android.util.Log;

public class VescParser {
    private final static String TAG = com.frost_e.frost_e.VescParser.class.getSimpleName();

    private byte[] mPacket = {};

    public VescParser(byte[] packet) {
        mPacket = packet;
    }

    public String parse(){
        final StringBuilder stringBuilder = new StringBuilder(mPacket.length);
        for(byte byteChar : mPacket)
            stringBuilder.append(String.format("%02X ", byteChar));
        //Log.d(TAG, stringBuilder.toString());
        double speed = Math.max(0, (get_rpm() / 3 / 5) * 550 * Math.PI / 1000 / 1000 * 60);
        return String.format("%.1f ", speed);
        //return "Voltage: " + get_voltage() + "V, Speed: " + String.format("%.1f ", speed) + "kmph";

    }

/*    int32_t rotations_to_meters(int32_t erpm) {
        float gear_ratio = float(WHEEL_PULLEY_TEETH) / float(MOTOR_PULLEY_TEETH);
        return (erpm / MOTOR_POLE_PAIRS / gear_ratio) * WHEEL_DIAMETER_MM * PI / 1000;
    }

    float erpm_to_kph(uint32_t erpm) {
        float km_per_minute = rotations_to_meters(erpm) / 1000.0;
        return km_per_minute * 60.0;
    }*/


    double get_temp_mosfet() {
        return ( get_word((byte) 3)) / 10.0;
    }

    int get_temp_motor() {
        return (get_word((byte) 5)) / 10;
    }

    double get_motor_current() {
        return (get_long((byte) 7)) / 100.0;
    }

    int get_battery_current() {
        return (get_long((byte) 11)) / 100;
    }

    double get_duty_cycle() {
        return get_word((byte) 23) / 1000.0;
    }

    int get_rpm() {
        return get_long((byte) 25);
    }

    double get_voltage() {
        return get_word((byte) 29) / 10.0;
    }

    double get_amphours_discharged() {
        return get_long((byte) 31) / 10000.0;
    }

    double get_amphours_charged() {
        return get_long((byte) 35) / 10000.0;
    }

    double get_watthours_discharged() {
        return get_long((byte) 39) / 10000.0;
    }

    double get_watthours_charged() {
        return get_long((byte) 43) / 10000.0;
    }

    public int get_tachometer() {
        return get_long((byte) 47);
    }

    int get_tachometer_abs() {
        return get_long((byte) 51);
    }

    public int get_word(byte index) {
        return  (mPacket[index] << 8 |  mPacket[index + 1]);
    }

    public int get_long(byte index) {
        return (mPacket[index]) << 24 |
                ( mPacket[index + 1]) << 16 |
                ( mPacket[index + 2]) << 8 |
                (mPacket[index + 3]);
    }

}
