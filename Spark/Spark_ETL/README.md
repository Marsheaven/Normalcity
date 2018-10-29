### SPARK ETL

Spark can be used as a pipeline for the simple ETL(Extract, Transform, and Load data into DataWarehouse). There are some video clips in Youtube claiming the elimination/substitution of ETL by Spark. I do not fully agree with that, however, it is clear that Spark is a very fast and convenient platform for large amount of data manipulation.

I tested the Spark ETL using STM GTFS files(available at http://www.stm.info/en/about/developers). I picked up only three files(trips.txt, frequencies.txt, and calendar_dates.txt) to add minimal enrichments by join query, and created a mini-sized version of trips2.txt(4 rows) and frequencies2(4 rows) for a test.

#### My test design is as follows:

- Extract
  I downloaded the STM GTFS files and stored them in the HDFS(staging area). And I opened the files into DataFrame in Spark.
- Transform
  I executed SQL Join query to integrate the columns of trips2.txt with those of frequencies2.txt 
- Load
  I saved the result as a csv file on the local disk. (Or, you can save them in your database created in HIVE)

Next is the detailed steps for Spark ETL.

1. Create a staging directory on HDFS

e.g. [root@localhost ~]# hadoop fs -mkdir /user/hive/warehouse/stm/gtfs/staging/frequencies2

2. Extract the downloaded data from STM and store them into staging area 

e.g. [root@localhost ~]# hadoop fs -put Downloads/gtfs_stm/frequencies2.txt /user/hive/warehouse/stm/gtfs/staging/frquencies2

3. Read files on HDFS into DataFrame 

scala> val frequencies2 = spark.read.format("csv").option("header","true").
     | load("hdfs://localhost:9000//user/hive/warehouse/stm/gtfs/staging/frequencies2/frequncies2.txt")

Then, a DataFrame is created as follows. In the readig process, the text file is converted into the csv file with Schema.

frequencies2: org.apache.spark.sql.DataFrame = [trip_id: string, start_time: string ... 2 more fields]

We can check the schema of the frequencies2 DataFrame

scala> frequencies2.printSchema()
root
 |-- trip_id: string (nullable = true)
 |-- start_time: string (nullable = true)
 |-- end_time: string (nullable = true)
 |-- headway_secs: string (nullable = true)

Likewise, I created a DataFrame for trips2

4. Create an enriched_trip DF using SQL

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

This image displays the outcome of the join. You can see there is no duplicated column.

5. Save DataFrame of enriched_trips as a csv file

scala> riched_trips2.write.format("csv").save("result.csv")

I attached the result file from mini-test, along with the result from the double join integrating trips, frequencies, and calendar_dates files together.

