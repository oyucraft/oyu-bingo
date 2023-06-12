package net.kigawa.oyucraft.oyubingo

import net.kigawa.kutil.unitapi.extention.Message
import net.kigawa.kutil.unitapi.extention.UnitLogger
import java.util.logging.Logger

class OyuBingoUnitLogger(
  private val logger: Logger,
): UnitLogger {
  
  override fun log(message: Message) {
    logger.log(message.level, message.message)
    message.cause.forEach {
      logger.log(message.level, it?.stackTraceToString())
    }
    message.items.forEach {
      logger.log(message.level, it?.toString())
    }
  }
}