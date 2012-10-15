package pt.up.fe.luisfonseca.cp.api

trait JsonParser[T] {
  def parse(json: String) : T

}
