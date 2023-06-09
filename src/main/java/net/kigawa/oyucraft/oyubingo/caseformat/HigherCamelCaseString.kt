package net.kigawa.oyucraft.oyubingo.caseformat


class HigherCamelCaseString(private val values: List<String>): CaseString {
  constructor(string: String): this()
  override fun split(string: String): List<String> {
    if (values.isEmpty()) {
      return values
    }
    
    val sb: StringBuilder = StringBuilder(camel.length() + camel.length())
    for (i in 0 until camel.length()) {
      val c: Char = camel.charAt(i)
      if (Character.isUpperCase(c)) {
        sb.append(if (sb.length != 0) '_' else "").append(c.lowercaseChar())
      } else {
        sb.append(c.lowercaseChar())
      }
    }
    return sb.toString()
  }
  
  override fun marge(): String {
    TODO("Not yet implemented")
  }
}