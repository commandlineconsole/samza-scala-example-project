/*
 * Copyright (c) 2015 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.samza.example
package events

// Java
import java.util.UUID

// json4s
import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

// Joda-Time
import org.joda.time.{DateTime, DateTimeZone}

object WindowSummaryEvent {

  private implicit val formats = DefaultFormats ++ ext.JodaTimeSerializers.all ++
      ext.JavaTypesSerializers.all

  def apply(counts: CountMap): WindowSummaryEvent = {
    val id = UUID.randomUUID()
    val ts = new DateTime(DateTimeZone.UTC)
    WindowSummaryEvent(id, ts, counts)
  }

  def toJsonBytes(wse: WindowSummaryEvent): Array[Byte] =
    write(wse).getBytes
}

case class WindowSummaryEvent(id: UUID, timestamp: DateTime, counts: CountMap) {

  def toJsonBytes: Array[Byte] =
    WindowSummaryEvent.toJsonBytes(this)
}