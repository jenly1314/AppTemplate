package com.king.template.util

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object Cache {

    fun initialize(context: Context){
        MMKV.initialize(context)
    }

    private fun mmkv() = MMKV.defaultMMKV()

    fun put(key: String,value: Boolean?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: Int?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: Long?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: Float?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: Double?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: String?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: Set<String>?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: ByteArray?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun put(key: String,value: Parcelable?){
        if(value != null){
            mmkv().encode(key,value)
        }else{
            removeValueForKey(key)
        }
    }

    fun getBool(key: String, defValue: Boolean = false) = mmkv().decodeBool(key,defValue)

    fun getInt(key: String, defValue: Int = 0) = mmkv().decodeInt(key,defValue)

    fun getLong(key: String, defValue: Long = 0) = mmkv().decodeLong(key,defValue)

    fun getFloat(key: String, defValue: Float = 0F) = mmkv().decodeFloat(key,defValue)

    fun getDouble(key: String, defValue: Double = 0.0) = mmkv().decodeDouble(key,defValue)

    fun getString(key: String, defValue: String? = null): String? = mmkv().decodeString(key,defValue)

    fun getByteArray(key: String, defValue: ByteArray? = null): ByteArray? = mmkv().decodeBytes(key,defValue)

    fun <T: Parcelable> getParcelable(key: String, clazz: Class<T>, defValue: T? = null): T? = mmkv().decodeParcelable(key,clazz,defValue)


    fun removeValueForKey(key: String){
        mmkv().removeValueForKey(key)
    }
}