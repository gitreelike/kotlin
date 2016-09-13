// !CHECK_TYPE
open class Outer<X, Y> {
    inner class Inner<Z>
}

class Derived : Outer<String, Int>() {
    fun foo(): <!WRONG_NUMBER_OF_TYPE_ARGUMENTS!>Inner<!><Char> = null!!
}


class A : Outer<Double, Short>() {
    class B : Outer<Float, Long>() {
        fun bar(): <!WRONG_NUMBER_OF_TYPE_ARGUMENTS!>Inner<!><String> = null!!
    }
}

fun foo() {
    Derived().foo() <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>checkType<!> { <!UNRESOLVED_REFERENCE!>_<!><Outer<String, Int>.Inner<Char>>() }
    A.B().bar() <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>checkType<!> { <!UNRESOLVED_REFERENCE!>_<!><Outer<Float, Long>.Inner<String>>() }
}
