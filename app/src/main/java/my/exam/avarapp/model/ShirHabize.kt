package my.exam.avarapp.model


object ShirHabize {
    private fun maskalGhutzize(value: Int): Int {
        var mask = 0
        var prV = value
        while (prV != 0) {
            if (prV and 1 == 1) mask++
            prV = prV shr 1
        }
        mask = mask or ((8 - mask) shl 4)
        mask = mask xor ((mask shl 4) or (mask shr 4))
        return mask xor value
    }

    fun baytalHabize(kxvayBaytal: ByteArray, khulBaytal: ByteArray): ByteArray {
        var khulIdx = 0
        for (i in kxvayBaytal.indices) {
            if (khulIdx == khulBaytal.size) khulIdx = 0
            val b = kxvayBaytal[i].toInt()
            val k = maskalGhutzize(khulBaytal[khulIdx++].toInt())
            kxvayBaytal[i] = b.xor(k).toByte()
        }
        return kxvayBaytal
    }
}