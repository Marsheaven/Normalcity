#### 4. Create an enriched_trip DF using SQL

By importing the sql repository, it is possible to use SQL queries in Spark.

scala> import org.apache.spark.sql._

Many kinds of queries are possible and there is no such a fixed answer.
All of the following queries execute join and show the result of join. But there are some restrictions.

4.1
scala> val riched_trips = trips2.join(frequencies2, "trip_id")
riched_trips: org.apache.spark.sql.DataFrame = [trip_id: string, route_id: string ... 10 more fields]

4.2
scala> val riched_trips2 = trips2.join(frequencies2, Seq("trip_id"))
riched_trips2: org.apache.spark.sql.DataFrame = [trip_id: string, route_id: string ... 10 more fields]

Both the result of 4.1 and 4.2 show the clear join result without duplicated columns.

4.3
scala> val riched_trips3 = trips2.
     | join(frequencies2, trips2.col("trip_id") === frequencies2.col("trip_id"))
riched_trips3: org.apache.spark.sql.DataFrame = [route_id: string, service_id: string ... 11 more fields]

4.3 shows the duplicated columns of 'trip_id', thus this DataFrame can not be saved as a file. You can see the number of fields is one more than that of 4.1 and 4.2 at the underlined message.

4.4
scala> trips2.createOrReplaceTempView("trips2_view")
scala> frequencies2.createOrReplaceTempView("frequencies2_view")
scala> val outcome = spark.sql("SELEC * FROM trips2_view AS t JOIN frequencies2_view AS f ON f.trip_id = t.trip_id")

Whereas the sql queries in the 4.1-4.3 are based on the scala syntax, 4.4 uses the sql queries in a familiar way. We can input any SQL query freely in the spark.sql(""). However, this query refers data in the view, thus we should create two views(for trips2, frequencies2) before executing the join query.

* As Spark is optimal to scala when writing an application or quries, it would be better to comply with the scala syntax in Spark.
(Spark supports different languages including Python and Java. But the main language for Spark is scala).
