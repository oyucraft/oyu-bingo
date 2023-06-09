package net.kigawa.oyucraft.oyubingo.caseformat

interface CaseString {
  val values: List<String>
  fun split(): List<String>{
    return values.toMutableList()
  }
  fun marge(): String{
    return marge(values)
  }
}