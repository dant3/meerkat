package meerkat

object -> {
  def unapply[A, B](pair: (A, B)): Option[(A, B)] =
    Some(pair)
}
