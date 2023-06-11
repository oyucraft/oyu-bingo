package net.kigawa.oyucraft.oyubingo.caseformat

class CaseString(
  val values: List<String>,
  private val caseFormat: CaseFormat,
) {
  
  override fun toString(): String {
    return caseFormat.marge(values)
  }
}