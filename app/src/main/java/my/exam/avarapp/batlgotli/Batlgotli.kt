package my.exam.avarapp.batlgotli

import my.exam.avarapp.model.ShirHabize

object Batlgotli {
    fun getKhigo() = String(ShirHabize.baytalHabize(betherabBatlgotliKhigo, betherabBatlgotliKhul))
    fun getAntlgo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliAntlgo, betherabBatlgotliKhul))

    fun getIcgo() = String(ShirHabize.baytalHabize(betherabBatlgotliIcgo, betherabBatlgotliKhul))
    fun getSchugo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliSchugo, betherabBatlgotliKhul))
    fun getKhiabilebKhigo() = String(ShirHabize.baytalHabize(betherabBatlgotliKhiabilebKhigo, betherabBatlgotliKhul))
    fun getTso() = String(ShirHabize.baytalHabize(betherabBatlgotliTso, betherabBatlgotliKhul))
    fun getKhiabilebUnqgo() = String(ShirHabize.baytalHabize(betherabBatlgotliKhiabilebUnqgo, betherabBatlgotliKhul))
    fun getAnqhgo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliAnqhgo, betherabBatlgotliKhul))
    fun getKhiabilebTlabgo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliKhiabilebTlabgo, betherabBatlgotliKhul))
    fun getKhiabilebSchugo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliKhiabilebSchugo, betherabBatlgotliKhul))
    fun getMiqhgo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliMiqhgo, betherabBatlgotliKhul))
    fun getUnqgo() = String(ShirHabize.baytalHabize(betherabBatlgotliUnqgo, betherabBatlgotliKhul))
    fun getKhiabilebTso() =
        String(ShirHabize.baytalHabize(betherabBatlgotliKhiabilebTso, betherabBatlgotliKhul))
    fun getAntzgo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliAntzgo, betherabBatlgotliKhul))
    fun getTlabgo() =
        String(ShirHabize.baytalHabize(betherabBatlgotliTlabgo, betherabBatlgotliKhul))

    fun prirkr() = betherabBatlgotliMiqhgo.decodeToString() +
            betherabBatlgotliIcgo.decodeToString() +
            betherabBatlgotliAntzgo.decodeToString()
}

private val betherabBatlgotliKhul = byteArrayOf(107, 104, 117, 108)
private val betherabBatlgotliKhigo = byteArrayOf(60, 54, 38, 66)
private val betherabBatlgotliAntlgo = byteArrayOf(108, 120, 114, 30)
private val betherabBatlgotliKhiabilebUnqgo = byteArrayOf(60, 59, 34, 86)
private val betherabBatlgotliSchugo = byteArrayOf(53, 62, 43, 93, 34)
private val betherabBatlgotliTso = byteArrayOf(101, 122, 103, 28, 55, 33, 60)
private val betherabBatlgotliTlabgo = byteArrayOf(57, 56, 61, 93, 60, 32)
private val betherabBatlgotliAnqhgo = byteArrayOf(32, 111, 99, 28)
private val betherabBatlgotliKhiabilebSchugo = byteArrayOf(53, 62, 43, 93, 34)
private val betherabBatlgotliMiqhgo = byteArrayOf(60, 54, 37, 66)
private val betherabBatlgotliUnqgo = byteArrayOf(60, 59, 34, 86)
private val betherabBatlgotliKhiabilebKhigo = byteArrayOf(60, 54, 38, 66)
private val betherabBatlgotliAntzgo = byteArrayOf(60, 58, 33, 67)
private val betherabBatlgotliIcgo = byteArrayOf(56, 58, 61, 94, 62, 32)
private val betherabBatlgotliKhiabilebTlabgo = byteArrayOf(57, 56, 61, 93, 60, 32)
private val betherabBatlgotliKhiabilebTso = byteArrayOf(122, 125, 41, 67, 34)



