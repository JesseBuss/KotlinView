package com.jbuss.kotlinview.model

import android.os.Parcel
import android.os.Parcelable
import com.jbuss.kotlinview.utils.getMmSsString

/**
 * Created by jbuss on 2/23/18.
 */

data class LapRow(val index: Int, val totalSeconds: Int, val lapTime: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt())

    override fun toString(): String {
        return "Lap ${index + 1}: ${lapTime.getMmSsString()}"
    }

    // region Parcelable
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(index)
        dest?.writeInt(totalSeconds)
        dest?.writeInt(lapTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LapRow> {
        override fun createFromParcel(parcel: Parcel): LapRow {
            return LapRow(parcel)
        }

        override fun newArray(size: Int): Array<LapRow?> {
            return arrayOfNulls(size)
        }
    }

    // endregion
}