### SQL Join Queries in Spark

Many kinds of queries are possible and there is no such a fixed answer.
All of the following queries execute join in Spark and show the result of join. But there are some restrictions depending on queries.

#### 1.
scala> val riched_trips = trips2.join(frequencies2, "trip_id")
riched_trips: org.apache.spark.sql.DataFrame = [trip_id: string, route_id: string ... 10 more fields]

#### 2.
scala> val riched_trips2 = trips2.join(frequencies2, Seq("trip_id"))
riched_trips2: org.apache.spark.sql.DataFrame = [trip_id: string, route_id: string ... 10 more fields]

Both the result of 1 and 2 show the clear join result without duplicated columns.

#### 3.
scala> val riched_trips3 = trips2.
     | join(frequencies2, trips2.col("trip_id") === frequencies2.col("trip_id"))
riched_trips3: org.apache.spark.sql.DataFrame = [route_id: string, service_id: string ... 11 more fields]

3 shows the duplicated columns of 'trip_id', thus this DataFrame can not be saved as a file. You can see the number of fields is one more than that of 1 and 2 at the underlined message.

#### 4.
scala> trips2.createOrReplaceTempView("trips2_view")
scala> frequencies2.createOrReplaceTempView("frequencies2_view")
scala> val outcome = spark.sql("SELEC * FROM trips2_view AS t JOIN frequencies2_view AS f ON f.trip_id = t.trip_id")

While the sql queries in 1-3 are based on the scala syntax, 4 uses the sql queries in a familiar way. We can input any SQL query freely in the spark.sql(""). However, this query refers data in the view, thus we should create two views(trips2_view, frequencies2_view) before executing the join query.

As Spark is optimal to scala when writing an application or queries, it would be better to comply with the scala syntax in Spark.
(Spark supports different languages including Python and Java. But the main language with wide availability for Spark is scala).
