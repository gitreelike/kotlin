package bitwise_demo

fun Long?.shl(bits : Int?) : Long = this!!.shl(bits!!)

fun box() : String {
    val x : Long? = 10
    val y : Int? = 12

    System.out?.println(x.shl(y))
    return "OK"
}
