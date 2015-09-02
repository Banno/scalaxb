/*
 * Copyright (c) 2010 e.e d3si9n
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package scalaxb.compiler

import org.slf4j.{Logger, LoggerFactory}

case class Log(logger: Logger) {
  def info(message: String, args: Any*) {
    if (args.toSeq.isEmpty) logger.info(message)
    else try {
      logger.info(message format (args.toSeq: _*))
    }
    catch {
      case _: Throwable => logger.info(message)
    }
  }

  def debug(message: String, args: Any*) {
    if (args.toSeq.isEmpty) logger.debug(message)
    else try {
      logger.debug(message format (args.toSeq: _*))
    }
    catch {
      case _: Throwable => logger.debug(message)
    }
  }

  def warn(message: String, args: Any*) {
    if (args.toSeq.isEmpty) logger.warn(message)
    else try {
      logger.warn(message format (args.toSeq: _*))
    }
    catch {
      case _: Throwable => logger.warn(message)
    }
  }

  def error(message: String, args: Any*) {
    if (args.toSeq.isEmpty) logger.error(message)
    else try {
      logger.error(message format (args.toSeq: _*))
    }
    catch {
      case _: Throwable => logger.error(message)
    }
  }
}

object Log {
  def forName(name: String) = Log(LoggerFactory.getLogger(name))
}
