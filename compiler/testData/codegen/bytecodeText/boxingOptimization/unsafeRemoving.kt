fun returningBoxed() : Int? = 1
fun acceptingBoxed(x : Int?) : Int ? = x

class A(var x : Int? = null)

fun foo() {
    val rb = returningBoxed()
    acceptingBoxed(2)

    val a = A()
    a.x = 3

    val b = arrayOfNulls<Int>(4)
    b[100] = 5

    val x: Int? = 6
    val hc = x!!.hashCode()

    val y: Int? = 7
    val z: Int? = 8
    val res = y === z

    var p1 = 1
    var p2 = 1
    val c1: Any = if (p1 == p2) 0 else "abc"
    val c2: Any = if (p1 != p2) 0 else "abc"
}

// 9 java/lang/Integer.valueOf
// 0 intValue
