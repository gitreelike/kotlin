// !CHECK_TYPE
open class Outer<X, Y> {
    inner class Inner<Z>
}

open class BaseDerived1<E, F> : Outer<F, E>()
open class BaseDerived2<X> : BaseDerived1<String, X>()

class Derived : BaseDerived2<Int>() {
    fun foo(): <!WRONG_NUMBER_OF_TYPE_ARGUMENTS!>Inner<!><Char> = null!!
}

fun foo() {
    Derived().foo() <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>checkType<!> { <!UNRESOLVED_REFERENCE!>_<!><Outer<Int, String>.Inner<Char>>() }
}
