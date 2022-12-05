# HelloFresh JVM Take Home Test by Monica Raje Vishwakarma

Created an HTTP service for recording of statistics of some arbitrary data over
a closed period of time in Event driven architecture SpringBoot Application
with Maven as Building Tool in Java 17 SDK

Application is running on port 8080 on localhost with base_url as http://localhost:8080
and below two endpoints
* [`POST /event`](#post-event)
* [`GET /stats`](#get-stats)

## `POST /event`
http://localhost:8080/event/
This route accepts 3 values in JSON format
1. _timestamp_: is stored as Long to store the timestamp in milliseconds.
1. 𝑥: is stored as BigDecimal as we need to calculate the avg.
1. 𝑦: is stored as BigDecimal as we need to calculate the avg.

The 202 response is returned when the data is
successfully processed.

### Example /event Payload

```json
{
    "timestamp":1670181944376,
    "x":0.7125114846,
    "y":1110840765
}
```

## `GET /stats`
http://localhost:8080/stats/
Returns statistics about the data that was received so far. It is returns 5 comma separated
the data points that lie within the past 60 seconds:

1. Total
1. Sum 𝑥
1. Avg 𝑥
1. Sum 𝑦
1. Avg 𝑦

### Example /stats Response

```csv
1,0.7125114846,0.7125114846,1110840765,1110840765
```

Statistics are calculated in constant time by looking into map (in-memory map)
of StatisticsCache and statistics are calculated to form statistics summary 
for /event, which means method runs with O(1) complexity
