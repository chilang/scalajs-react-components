
package chandu0101.scalajs.react

import chandu0101.scalajs.react.components.util.{CommonStyles, MTypes}
import japgolly.scalajs.react._

import scala.scalajs.js

package object components extends CommonStyles with MTypes {

  type U[T] = js.UndefOr[T]

  private[components] val uNone = js.undefined

  @inline private[components] implicit final class IdToU[T](private val t: T) extends AnyVal {
    @inline def undef: U[T] = t
  }
  
  @inline private[components] implicit final class CBX(private val c: Callback) {
    @inline def filter(pred: Boolean): Callback =
      if (pred) c else Callback.empty
  }
  
  //using `js.UndefOr` under here to work around some strange implicit resolving behaviour  

  @inline private[components] implicit final class UF0CB[R](private val uc: js.UndefOr[CallbackTo[R]]) extends AnyVal {
    @inline def voidU: Callback =
      uc.fold(Callback.empty)(_.void)

    @inline def runNowU(): Unit =
      uc.foreach(_.runNow())
  }

  @inline private[components] implicit final class UF1CB[T1, R](private val uc: js.UndefOr[T1 => CallbackTo[R]]) extends AnyVal {
    @inline def mapply(t1: T1): U[CallbackTo[R]] =
      uc.map(f => f(t1))

    @inline def liftParam: T1 => U[CallbackTo[R]] =
      t1 => uc.map(f => f(t1))
  }

  @inline private[components] implicit final class UF2CB[T1, T2, R](private val uc: js.UndefOr[(T1, T2) => CallbackTo[R]]) extends AnyVal {
    @inline def mapply(t1: T1, t2: T2): U[CallbackTo[R]] =
      uc.map(f => f(t1, t2))

    @inline def liftParam: T1 => U[T2 => CallbackTo[R]] =
      t1 => uc.map(f => t2 => f(t1, t2))
  }

  @inline private[components] implicit final class UF3CB[T1, T2, T3, R](private val uc: js.UndefOr[(T1, T2, T3) => CallbackTo[R]]) extends AnyVal {
    @inline def mapply(t1: T1, t2: T2, t3: T3): U[CallbackTo[R]] =
      uc.map(f => f(t1, t2, t3))

    @inline def liftParam: T1 => U[(T2, T3) => CallbackTo[R]] =
      t1 => uc.map(f => (t2, t3) => f(t1, t2, t3))
  }
}